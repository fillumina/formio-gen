package com.fillumina.formio.gen;

import java.text.ParseException;
import java.util.List;

/**
 * A day without a time, which formio submits as a slash separated string. The
 * order of the parts is the one the {@link #dayFirst(Boolean)} property asks
 * for, so this component reads the same property to parse what it receives.
 *
 * <p>The user may fill in only some of the parts, and the rest arrive as zero.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DayComponent extends Component<DayComponent, PartialDate> {

    private boolean dayFirst;

    public DayComponent(String key) {
        super("day", key);
    }

    /**
     * @param dayFirst true to have formio submit the day before the month
     */
    public DayComponent dayFirst(Boolean dayFirst) {
        this.dayFirst = dayFirst == Boolean.TRUE;
        json.put("dayFirst", this.dayFirst);
        return this;
    }

    public boolean isDayFirst() {
        return dayFirst;
    }

    @Override
    public PartialDate convert(Object obj) throws ParseException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PartialDate) {
            return (PartialDate) obj;
        }
        String text = obj.toString().trim();
        if (text.isEmpty()) {
            return null;
        }
        String[] parts = text.split("/");
        if (parts.length != 3) {
            throw new ParseException(text, 0);
        }
        int first = number(parts[0], text);
        int second = number(parts[1], text);
        int year = number(parts[2], text);
        // the order of the first two parts follows the dayFirst property
        int month = dayFirst ? second : first;
        int day = dayFirst ? first : second;
        if (month > 12 || day > 31) {
            throw new ParseException(text, 0);
        }
        return new PartialDate(year, month, day);
    }

    private static int number(String part, String text) throws ParseException {
        try {
            int value = Integer.parseInt(part.trim());
            if (value < 0) {
                throw new ParseException(text, 0);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new ParseException(text, 0);
        }
    }
}
