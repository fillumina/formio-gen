package com.fillumina.formio.gen;

import java.util.Objects;

/**
 * A date the user may have filled in only partly, which is what formio sends
 * for a day component when only some of the parts were chosen. A part that was
 * not chosen is zero.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public final class PartialDate {

    private final int year;
    private final int month;
    private final int day;

    public PartialDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /** @return the year, or 0 if the user did not choose one */
    public int getYear() {
        return year;
    }

    /** @return the month from 1 to 12, or 0 if the user did not choose one */
    public int getMonth() {
        return month;
    }

    /** @return the day of the month, or 0 if the user did not choose one */
    public int getDay() {
        return day;
    }

    public boolean isComplete() {
        return year != 0 && month != 0 && day != 0;
    }

    public boolean isEmpty() {
        return year == 0 && month == 0 && day == 0;
    }

    /**
     * @param dayFirst true to write the day before the month, which is the
     *                 order formio uses when its {@code dayFirst} is set
     * @return the date in the slash separated form formio submits
     */
    public String format(boolean dayFirst) {
        String first = dayFirst ? pad(day) : pad(month);
        String second = dayFirst ? pad(month) : pad(day);
        return first + "/" + second + "/" + pad(year);
    }

    private static String pad(int value) {
        return value < 10 ? "0" + value : String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PartialDate)) {
            return false;
        }
        PartialDate other = (PartialDate) obj;
        return year == other.year && month == other.month && day == other.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @Override
    public String toString() {
        return format(false);
    }
}
