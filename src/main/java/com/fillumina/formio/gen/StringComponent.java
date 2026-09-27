package com.fillumina.formio.gen;

import java.text.ParseException;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

/**
 * Not to be used directly.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class StringComponent<T extends StringComponent<T>> extends Component<T, String> {

    //https://github.com/OWASP/java-html-sanitizer#prepackaged-policies
    private static final PolicyFactory FORBID_ALL_TAGS = new HtmlPolicyBuilder()
            .allowElements()
            .allowUrlProtocols()
            .allowAttributes().onElements()
            .requireRelNofollowOnLinks()
            .toFactory();

    protected StringComponent(String type, String key) {
        super(type, key);
    }

    public T spellcheck(Boolean spellecheck) {
        if (spellecheck == Boolean.TRUE) {
            json.put("spellecheck", spellecheck);
        }
        return (T) this;
    }

    public T upperCase() {
        json.put("case", "uppercase");
        return (T) this;
    }

    public T lowerCase() {
        json.put("case", "lowercase");
        return (T) this;
    }

    public T mixedCase() {
        json.put("case", "mixed");
        return (T) this;
    }

    /** Minimum amount of words that can be added. */
    public T minWords(int minWords) {
        validate.put("minWords", minWords);
        return (T) this;
    }

    /** Minimum amount of words that can be added. */
    public T maxWords(int maxWords) {
        validate.put("maxWords", maxWords);
        return (T) this;
    }

    public T showWordCount(boolean showWordCount) {
        json.put("showWordCount", showWordCount);
        return (T) this;
    }

    public T showCharCount(boolean showCharCount) {
        json.put("showCharCount", showCharCount);
        return (T) this;
    }

    /**
     * Strips markup out of the value and leaves everything else as the user
     * typed it.
     *
     * <p>The sanitiser escapes whatever it keeps, so running it over a plain
     * address turns it into {@code ada&#64;example.com}. That corrupts the
     * value and breaks any pattern mentioning an at sign, so it is only
     * applied when the value actually contains markup. Only a {@code <} can
     * open a tag, so a value without one has nothing to strip, and a lone
     * {@code >} cannot break out of anything.
     */
    @Override
    public String convert(Object obj) throws ParseException {
        if (obj == null) {
            return null;
        }
        String txt = obj.toString();
        if (txt.indexOf('<') < 0) {
            return txt;
        }
        return FORBID_ALL_TAGS.sanitize(txt);
    }
}
