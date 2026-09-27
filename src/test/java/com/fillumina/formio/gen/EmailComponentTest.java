package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class EmailComponentTest {

    @Test
    public void shouldDeclareTheEmailType() {
        EmailComponent comp = new EmailComponent("mail123");

        assertEquals("email", comp.toJSONObject().getString("type"));
    }

    @Test
    public void shouldAcceptAValidAddress() {
        EmailComponent comp = new EmailComponent("mail123");

        ResponseValue cv = comp.validate("ada@example.com");

        assertFalse(cv.isErrorPresent());
    }

    @Test
    public void shouldRejectSomethingThatIsNotAnAddress() {
        EmailComponent comp = new EmailComponent("mail123");

        ResponseValue cv = comp.validate("not-an-address");

        assertEquals(FormError.PATTERN_NOT_MATCHING, cv.getError());
    }

    @Test
    public void shouldLetTheCallerTightenTheFormat() {
        EmailComponent comp = new EmailComponent("mail123");
        comp.pattern("^ada@.*$");

        assertTrue(comp.validate("ada@example.com").isErrorPresent() == false);
        assertTrue(comp.validate("bob@example.com").isErrorPresent());
    }
}
