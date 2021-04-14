package com.fillumina.formio.gen;

import com.fillumina.formio.gen.JSONUtils.Node;
import com.fillumina.formio.gen.JSONUtils.NodeList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class JSONUtilsTest {

    @Test
    public void shouldBeEquals() {
        JSONObject o1 = new JSONObject("{\"one\":1, \"two\":2, \"three\":[0,2,3]}");
        JSONObject o2 = new JSONObject("{\"one\":1, \"two\":2, \"three\":[0,2,3]}");

        assertTrue(JSONUtils.equals(o1, o2));
    }

    @Test
    public void shouldBeEqualsIgnoringPositions() {
        JSONObject o1 = new JSONObject("{\"one\":1, \"two\":2, \"three\":[0,2,3]}");
        JSONObject o2 = new JSONObject("{\"two\":2, \"one\":1, \"three\":[0,2,3]}");

        assertTrue(JSONUtils.equals(o1, o2));
    }

    @Test
    public void shouldNotBeEquals() {
        JSONObject o1 = new JSONObject("{\"one\":1, \"two\":2, \"three\":[0,2,3]}");
        JSONObject o2 = new JSONObject("{\"four\":4, \"two\":2, \"three\":[0,2,3]}");

        assertFalse(JSONUtils.equals(o1, o2));
    }

    @Test
    public void shouldMerge() {
        JSONObject o1 = new JSONObject("{\"one\":1, \"two\":2, \"three\":[0,2,3]}");
        JSONObject o2 = new JSONObject("{\"four\":4, \"two\":2, \"three\":[0,2,3]}");

        JSONObject expected =
                new JSONObject("{\"one\":1, \"four\":4, \"two\":2, \"three\":[0,2,3]}");

        assertTrue(JSONUtils.equals(expected, JSONUtils.mergeFlat(o1, o2)));
    }

    @Test
    public void shouldRemove() {
        JSONObject o1 = new JSONObject("{\"one\":1, \"two\":2, \"three\":[6,6,6]}");
        JSONObject o2 = new JSONObject("{\"four\":4, \"two\":2, \"three\":[0,2,3]}");

        JSONObject expected = new JSONObject("{\"one\":1, \"three\":[6,6,6]}");

        assertTrue(JSONUtils.equals(expected, JSONUtils.removeFlat(o1, o2)));
    }

    @Test
    public void shouldNodeListBeEquals() {
        NodeList nl1 = NodeList.EMPTY.appendObject("one").appendArray("two").appendValue("three");
        NodeList nl2 = NodeList.EMPTY.appendArray("one").appendObject("two").appendValue("three");
        NodeList nl3 = NodeList.EMPTY.appendArray("three").appendObject("two").appendValue("one");
        NodeList nl4 = NodeList.EMPTY.appendArray("one").appendObject("two");
        NodeList nl5 = NodeList.EMPTY.appendArray("one");

        assertEquals(nl1, nl1);
        assertEquals(nl2, nl2);
        assertEquals(nl3, nl3);
        assertEquals(nl4, nl4);
        assertEquals(nl5, nl5);
        assertNotEquals(nl1, nl2);
        assertNotEquals(nl2, nl3);
        assertNotEquals(nl1, nl3);
        assertNotEquals(nl1, nl4);
        assertNotEquals(nl1, nl5);
        assertNotEquals(nl4, nl5);
        assertNotEquals(nl2, nl4);
    }

    @Test
    public void shouldTransformToMapAndBack() {
        JSONObject o1 = new JSONObject(
                "{\"one\":1, \"two\":2, \"three\": [6,7, { \"four\":4 } ] }");
        Map<List<Node>,Object> map = JSONUtils.toFlatMap(o1);
        JSONObject o2 = JSONUtils.toJSONObject(map);
        assertEquals(o1.toString(), o2.toString());
    }

    @Test
    public void shouldGetMapString() {
        JSONObject o1 = new JSONObject(
                "{\"one\":1, \"two\":2, \"three\": [6,7, { \"four\":4 } ] }");
        Map<List<Node>,Object> map = JSONUtils.toFlatMap(o1);
        String mapString = map.toString();
        assertEquals("{" +
                "[one Value]=1, " +
                "[two Value]=2, " +
                "[three Array, 0 Value]=6, " +
                "[three Array, 1 Value]=7, " +
                "[three Array, 2 Object, four Value]=4" +
                "}", mapString);
    }

}
