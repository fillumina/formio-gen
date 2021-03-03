package com.fillumina.formio.gen;

import java.text.ParseException;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

/**
 * Not to be used directly.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class StringComponent<T extends Component<T, String>> extends Component<T, String> {

    //private static final PolicyFactory POLICY = Sanitizers.FORMATTING;
    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
            .allowElements()
            .allowUrlProtocols()
            .allowAttributes().onElements()
            .requireRelNofollowOnLinks()
            .toFactory();
    
    protected StringComponent(String type, String key) {
        super(type, key);
    }

    @Override
    public String convert(String txt) throws ParseException {
        // clean text from all dangerous code.
        return POLICY.sanitize(txt);
    }
}
