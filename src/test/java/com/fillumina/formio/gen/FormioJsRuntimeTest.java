package com.fillumina.formio.gen;

import com.sun.net.httpserver.HttpServer;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.WaitForSelectorOptions;
import com.microsoft.playwright.Playwright;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * Runs a generated page in a real browser against each supported formio.js
 * line, fills it in, submits it, and feeds the posted JSON back through
 * {@link Form#validateJsonFromFormio(String)}.
 *
 * <p>This is the only test that proves the emitted form JSON still works in a
 * browser, so it is the one that catches a formio.js release changing how a
 * component behaves.
 *
 * <p>It uses its own small form rather than {@link FormCreator}: Quill, the
 * editor behind {@link WysiwygComponent}, does not initialise in headless
 * Chromium, and a form that fails to attach never submits. The rich text
 * component itself is covered by the unit tests.
 *
 * <p>The test is skipped when no browser is available. Point
 * {@code -Dformio.browser.executable} at a Chromium binary, or install one with
 * {@code playwright install chromium}, to run it.
 */
public class FormioJsRuntimeTest {

    private static final String POST_PATH = "/form_post";
    private static final String BROWSER_PROPERTY = "formio.browser.executable";
    private static final String VALID_NAME = "Ada Lovelace";
    private static final String VALID_HEIGHT = "5";
    private static final String VALID_MAIL = "ada@example.com";

    /**
     * One broken submission each: the other field always holds a valid value,
     * so the only rule the browser can complain about is the one under test.
     */
    private static final List<RuleCase> RULE_CASES = List.of(
            new RuleCase("minLength", "Ab", VALID_HEIGHT, VALID_MAIL, "must be longer than"),
            new RuleCase("maxLength", "Ada Lovelace Junior", VALID_HEIGHT, VALID_MAIL,
                    "must be shorter than"),
            new RuleCase("pattern", "Ada 42", VALID_HEIGHT, VALID_MAIL,
                    "does not match the pattern"),
            new RuleCase("min", VALID_NAME, "0", VALID_MAIL, "cannot be less than"),
            new RuleCase("max", VALID_NAME, "20", VALID_MAIL, "cannot be greater than"),
            new RuleCase("email pattern", VALID_NAME, VALID_HEIGHT, "not-an-address",
                    "does not match the pattern"));

    private static final class RuleCase {

        private final String rule;
        private final String name;
        private final String height;
        private final String mail;
        private final String message;

        RuleCase(String rule, String name, String height, String mail, String message) {
            this.rule = rule;
            this.name = name;
            this.height = height;
            this.mail = mail;
            this.message = message;
        }
    }

    @ParameterizedTest
    @EnumSource(FormioRuntime.class)
    public void shouldRenderAndAcceptASubmissionOnEveryFormioLine(FormioRuntime runtime)
            throws Exception {
        Path browser = findBrowser();
        assumeTrue(browser != null, "no chromium found, skipping the browser test");

        Form form = createBrowserForm();
        List<String> posted = new CopyOnWriteArrayList<>();
        HttpServer server = startServer(pageFor(form, runtime), posted);

        try (Playwright playwright = Playwright.create()) {
            Page page = openPage(playwright, browser, server);
            List<String> pageErrors = new ArrayList<>();
            page.onPageError(pageErrors::add);

            page.waitForSelector("#formio .formio-component",
                    new WaitForSelectorOptions().setTimeout(20_000));

            assertEquals(runtime.version(), page.evaluate("() => Formio.version"),
                    "the page did not load the formio.js version it targets");

            page.fill("input[placeholder='Tell your name']", "Ada Lovelace");
            page.fill("input[placeholder='Tell your real height']", "1.68");
            page.click("button:has-text('Send Form')");
            waitForSubmission(posted);

            assertTrue(pageErrors.isEmpty(), "the page reported errors: " + pageErrors);
            assertEquals(1, posted.size(),
                    "the form did not post exactly one submission, the page reads: "
                            + page.locator("body").innerText());

            FormResponse response = form.validateJsonFromFormio(posted.get(0));
            assertFalse(response.isErrorPresent(), response.toString());
            assertEquals("Ada Lovelace", response.getJsonObject().getString("name123"),
                    posted.get(0));
        } finally {
            server.stop(0);
        }
    }

    @ParameterizedTest
    @EnumSource(FormioRuntime.class)
    public void shouldRefuseToSubmitWhileARequiredFieldIsEmpty(FormioRuntime runtime)
            throws Exception {
        Path browser = findBrowser();
        assumeTrue(browser != null, "no chromium found, skipping the browser test");

        Form form = createBrowserForm();
        List<String> posted = new CopyOnWriteArrayList<>();
        HttpServer server = startServer(pageFor(form, runtime), posted);

        try (Playwright playwright = Playwright.create()) {
            Page page = openPage(playwright, browser, server);

            page.waitForSelector("#formio .formio-component",
                    new WaitForSelectorOptions().setTimeout(20_000));

            page.click("button:has-text('Send Form')");
            page.waitForTimeout(1_500);

            assertTrue(posted.isEmpty(),
                    "the browser posted a submission with a required field empty");
            assertTrue(page.locator("text=is required").count() > 0,
                    "the browser did not report the missing field in the generated "
                            + "language, the page reads: " + page.locator("body").innerText());
        } finally {
            server.stop(0);
        }
    }

    /**
     * Checks that formio.js enforces the same rules in the browser as this
     * library enforces them in Java. If a formio.js release tightened or
     * loosened any of them, the browser and the server would disagree about
     * what a valid submission is, and nothing else in the suite would notice.
     */
    @ParameterizedTest
    @EnumSource(FormioRuntime.class)
    public void shouldRefuseTheSameValuesInTheBrowserAndInJava(FormioRuntime runtime)
            throws Exception {
        Path browser = findBrowser();
        assumeTrue(browser != null, "no chromium found, skipping the browser test");

        Form form = createRuleForm();
        List<String> posted = new CopyOnWriteArrayList<>();
        HttpServer server = startServer(pageFor(form, runtime), posted);

        try (Playwright playwright = Playwright.create()) {
            Page page = openPage(playwright, browser, server);
            page.waitForSelector("#formio .formio-component",
                    new WaitForSelectorOptions().setTimeout(20_000));

            for (RuleCase ruleCase : RULE_CASES) {
                posted.clear();
                fillRuleFields(page, ruleCase);

                page.click("button:has-text('Send Form')");
                page.waitForTimeout(1_000);

                assertTrue(posted.isEmpty(), "formio.js " + runtime.version()
                        + " accepted a submission that breaks " + ruleCase.rule);
                assertTrue(page.locator("text=" + ruleCase.message).count() > 0,
                        "formio.js " + runtime.version() + " did not report " + ruleCase.rule
                                + ", the page reads: " + page.locator("body").innerText());

                JSONObject submitted = new JSONObject()
                        .put("name123", ruleCase.name)
                        .put("height123", ruleCase.height)
                        .put("mail123", ruleCase.mail);
                assertTrue(form.validateJson(submitted).isErrorPresent(),
                        "Java accepted a submission that breaks " + ruleCase.rule);
            }
        } finally {
            server.stop(0);
        }
    }

    private static void fillRuleFields(Page page, RuleCase ruleCase) {
        page.fill("input[placeholder='Tell your name']", ruleCase.name);
        page.fill("input[placeholder='Tell your real height']", ruleCase.height);
        page.fill("input[placeholder='Tell your email']", ruleCase.mail);
    }

    /**
     * Renders every component added in this release on each formio.js line,
     * fills in the ones that are plain inputs, and checks the posted JSON
     * comes back through the Java validator unchanged. A value that arrived
     * HTML escaped, an at sign turned into an entity for instance, would fail
     * the equality check.
     */
    @ParameterizedTest
    @EnumSource(FormioRuntime.class)
    public void shouldRoundTripEveryNewComponentOnEveryFormioLine(FormioRuntime runtime)
            throws Exception {
        Path browser = findBrowser();
        assumeTrue(browser != null, "no chromium found, skipping the browser test");

        Form form = createEveryComponentForm();
        List<String> posted = new CopyOnWriteArrayList<>();
        HttpServer server = startServer(pageFor(form, runtime), posted);

        try (Playwright playwright = Playwright.create()) {
            Page page = openPage(playwright, browser, server);
            List<String> pageErrors = new ArrayList<>();
            page.onPageError(pageErrors::add);
            page.waitForSelector("#formio .formio-component",
                    new WaitForSelectorOptions().setTimeout(20_000));

            assertEquals(runtime.version(), page.evaluate("() => Formio.version"),
                    "the page did not load the formio.js version it targets");
            assertTrue(pageErrors.isEmpty(), "the page reported errors: " + pageErrors);

            for (String key : new String[]{"inside123", "mail123", "site123", "phone123",
                    "pwd123", "price123", "sex123", "colours123", "tags123", "members123"}) {
                assertTrue(page.locator(".formio-component-" + key).count() > 0,
                        "formio.js " + runtime.version() + " did not render " + key
                                + ", the page reads: " + page.locator("body").innerText());
            }
            assertTrue(page.locator("text=Terms apply").count() > 0,
                    "formio.js " + runtime.version() + " did not render the html element"
                            + ", the page reads: " + page.locator("body").innerText());

            page.fill("input[placeholder='your email']", "ada@example.com");
            page.fill("input[placeholder='your site']", "https://example.com/page");
            page.fill("input[placeholder='your phone']", "+39 06-5555 1234");
            page.fill("input[placeholder='your password']", "hunter2");
            page.fill("input[placeholder='your price']", "19.99");
            page.click("button:has-text('Send Form')");
            waitForSubmission(posted);

            assertTrue(pageErrors.isEmpty(), "the page reported errors: " + pageErrors);
            assertEquals(1, posted.size(),
                    "the form did not post exactly one submission, the page reads: "
                            + page.locator("body").innerText());

            String body = posted.get(0);
            assertTrue(body.contains("ada@example.com"),
                    "the email address arrived escaped: " + body);
            assertTrue(body.contains("\"usr123\":\"ada\""),
                    "the hidden default did not reach the submission: " + body);

            FormResponse response = form.validateJsonFromFormio(body);
            assertFalse(response.isErrorPresent(),
                    response.getErrorMessage(Locale.ENGLISH) + " in " + body);
        } finally {
            server.stop(0);
        }
    }

    private static Form createEveryComponentForm() {
        FormBuilder builder = new FormBuilder("everything", "Every component", "everything123");
        builder.addComponent(new WellComponent("note123")
                .title("Before you start")
                .addComponent(new TextFieldComponent("inside123")
                        .label("Inside the well").placeholder("a nested field")));
        builder.addComponent(new HtmlElementComponent("legal123")
                .content("<p>Terms apply</p>"));
        builder.addComponent(new EmailComponent("mail123")
                .label("Email").placeholder("your email").required(true));
        builder.addComponent(new UrlComponent("site123")
                .label("Site").placeholder("your site"));
        builder.addComponent(new PhoneNumberComponent("phone123")
                .label("Phone").placeholder("your phone"));
        builder.addComponent(new PasswordComponent("pwd123")
                .label("Password").placeholder("your password"));
        builder.addComponent(new HiddenComponent("usr123").defaultValue("ada"));
        builder.addComponent(new CurrencyComponent("price123")
                .label("Price").placeholder("your price").currency("EUR"));
        builder.addComponent(new RadioComponent("sex123")
                .label("Sex").values("Male", "Female"));
        builder.addComponent(new SelectBoxesComponent("colours123")
                .label("Colours").values("red", "blue"));
        builder.addComponent(new TagsComponent("tags123").label("Tags"));
        builder.addComponent(new EditGridContainer("members123")
                .label("Members")
                .addComponent(new TextFieldComponent("name123")
                        .label("Name").placeholder("a member name")));
        builder.addComponent(new SubmitComponent().label("Send Form"));
        return builder.build();
    }

    private static Form createRuleForm() {
        FormBuilder builder = new FormBuilder("rules", "Rule form", "rules123");
        builder.addComponent(new TextFieldComponent("name123")
                .label("Name")
                .placeholder("Tell your name")
                .required(true)
                .minLength(5)
                .maxLength(12)
                .pattern("[A-Za-z ]+"));
        // whole numbers only, so the check does not depend on the browser locale
        builder.addComponent(new DecimalComponent("height123")
                .label("Height")
                .placeholder("Tell your real height")
                .required(true)
                .min(1)
                .max(10));
        builder.addComponent(new EmailComponent("mail123")
                .label("Email")
                .placeholder("Tell your email")
                .required(true));
        builder.addComponent(new ColumnsContainer("col123")
                .createColumn().addComponent(new SubmitComponent().label("Send Form")).endCol()
                .createColumn().addComponent(new CancelComponent().label("Clear Data")).endCol());
        return builder.build();
    }

    private static Form createBrowserForm() {
        FormBuilder builder = new FormBuilder("browser", "Browser form", "browser123");
        builder.addComponent(new BooleanComponent("agree123")
                .label("is this true")
                .required(false));
        builder.addComponent(new TextFieldComponent("name123")
                .label("Name")
                .placeholder("Tell your name")
                .required(true));
        builder.addComponent(new DecimalComponent("height123")
                .label("Height")
                .placeholder("Tell your real height")
                .required(true));
        builder.addComponent(new EnumComponent("sex123")
                .label("Sex")
                .placeholder("Say your sex")
                .values("Male", "Female"));
        builder.addComponent(new ColumnsContainer("col123")
                .createColumn().addComponent(new SubmitComponent().label("Send Form")).endCol()
                .createColumn().addComponent(new CancelComponent().label("Clear Data")).endCol());
        return builder.build();
    }

    private static String pageFor(Form form, FormioRuntime runtime) {
        return CodeGenerator.generateHtml(form.toFormioJSONObject(), POST_PATH, false, runtime);
    }

    private static Page openPage(Playwright playwright, Path browser, HttpServer server) {
        Browser chromium = playwright.chromium().launch(new LaunchOptions()
                .setExecutablePath(browser)
                .setHeadless(true));
        Page page = chromium.newPage();
        page.navigate(urlOf(server));
        return page;
    }

    private static void waitForSubmission(List<String> posted) throws InterruptedException {
        for (int i = 0; i < 50 && posted.isEmpty(); i++) {
            Thread.sleep(100);
        }
    }

    private static String urlOf(HttpServer server) {
        return "http://127.0.0.1:" + server.getAddress().getPort() + "/";
    }

    private static HttpServer startServer(String html, List<String> posted) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            byte[] body = html.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
        });
        server.createContext(POST_PATH, exchange -> {
            try (InputStream in = exchange.getRequestBody()) {
                posted.add(new String(in.readAllBytes(), StandardCharsets.UTF_8));
            }
            exchange.sendResponseHeaders(201, -1);
            exchange.close();
        });
        server.start();
        return server;
    }

    private static Path findBrowser() throws IOException {
        String configured = System.getProperty(BROWSER_PROPERTY);
        if (configured != null && Files.isExecutable(Paths.get(configured))) {
            return Paths.get(configured);
        }
        Path cache = Paths.get(System.getProperty("user.home"), ".cache", "ms-playwright");
        if (!Files.isDirectory(cache)) {
            return null;
        }
        try (Stream<Path> installs = Files.list(cache)) {
            return installs
                    .map(dir -> dir.resolve("chrome-linux64").resolve("chrome"))
                    .filter(Files::isExecutable)
                    .findFirst()
                    .orElse(null);
        }
    }
}
