package com.fillumina.formio.gen;

/**
 * A list of short free text values the user adds and removes one by one.
 *
 * <p>Values are submitted as a JSON array, which is what the item count rules
 * ({@link #minItems(Integer)} and {@link #maxItems(Integer)}) need to mean
 * something. formio would otherwise submit one comma separated string.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TagsComponent extends StringComponent<TagsComponent> {

    public TagsComponent(String key) {
        super("tags", key, false);
        json.put("storeas", "array");
        multiple(true);
    }

    public TagsComponent delimiter(String delimiter) {
        if (delimiter != null) {
            json.put("delimeter", delimiter);
        } else {
            json.remove("delimeter");
        }
        return this;
    }
}
