package com.fillumina.formio.gen;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class JSONUtils {

    /** Immutable array list. */
    public static class NodeList extends AbstractList<Node> {

        private final Node[] array;

        public static NodeList EMPTY = new NodeList(new Node[0]);

        private NodeList(Node[] array) {
            this.array = array;
        }

        public NodeList appendValue(String name) { return append(new ValueNode(name)); }
        public NodeList appendObject(String name) { return append(new ObjectNode(name)); }
        public NodeList appendArray(String name) { return append(new ArrayNode(name)); }

        /** @return a new list with the new appended value */
        private NodeList append(Node item) {
            Node[] newArray = new Node[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = item;
            return new NodeList(newArray);
        }

        @Override
        public Node get(int index) {
            return array[index];
        }

        @Override
        public int size() {
            return array.length;
        }
    }

    public static class Node {
        private final String value;

        public Node(String value) {
            this.value = value;
        }

        @Override public String toString() {
            return value + " " + getClass().getSimpleName().replace("Node", "");
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Node other = (Node) obj;
            if (!Objects.equals(this.value, other.value)) {
                return false;
            }
            return true;
        }

    }

    public static class ObjectNode extends Node {
        public ObjectNode(String value) { super(value); }
    }

    public static class ArrayNode extends Node {
        public ArrayNode(String value) { super(value); }
    }

    public static class ValueNode extends Node {
        public ValueNode(String value) { super(value); }
    }

    public static JSONObject clone(JSONObject jsonObject) {
        return new JSONObject(jsonObject.toString());
    }

    /**
     * Return a flat map. Useful to compare different trees. The JSON object can be built back
     * by using {@link #toJSONObject(java.util.Map) }.
     *
     * @param jsonObject
     * @return
     */
    public static Map<List<Node>,Object> toFlatMap(JSONObject jsonObject) {
        Map<List<Node>,Object> map = new LinkedHashMap<>();
        toMap(map, NodeList.EMPTY, null, jsonObject);
        return map;
    }

    private static void toMap(Map<List<Node>,Object> map, NodeList path, String prop, Object obj) {
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            NodeList forward = (prop == null) ? path : path.appendObject(prop);
            for (String name : JSONObject.getNames(jsonObject)) {
                toMap(map, forward, name, jsonObject.get(name));
            }
        } else if (obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;
            NodeList forward = (prop == null) ? path : path.appendArray(prop);
            for (int index = 0; index < jsonArray.length(); index++) {
                Object item = jsonArray.get(index);
                toMap(map, forward, "" + index, item);
            }
        } else {
            NodeList forward = (prop == null) ? path : path.appendValue(prop);
            map.put(forward,  obj);
        }
    }

    public static JSONObject toJSONObject(Map<List<Node>,Object> flatMap) {
        Map<List<Node>,Object> objectMap = new HashMap<>();
        JSONObject root = new JSONObject();
        objectMap.put(NodeList.EMPTY, root);
        flatMap.forEach((l,o) -> {
            NodeList path = NodeList.EMPTY;
            Object lastObject = root;
            for (Node n : l) {
                if (n instanceof ValueNode) {
                    if (lastObject instanceof JSONObject) {
                        ((JSONObject)lastObject).put(n.value, o);
                    } else if (lastObject instanceof JSONArray) {
                        ((JSONArray)lastObject).put(o);
                    }
                } else {
                    path = path.append(n);
                    Object obj = objectMap.get(path);
                    if (obj == null) {
                        if (n instanceof ObjectNode) {
                            obj = new JSONObject();
                        } else if (n instanceof ArrayNode) {
                            obj = new JSONArray();
                        }
                        objectMap.put(path, obj);
                    }
                    if (lastObject instanceof JSONObject) {
                        ((JSONObject)lastObject).put(n.value, obj);
                    } else if (lastObject instanceof JSONArray) {
                        ((JSONArray)lastObject).put(obj);
                    }
                    lastObject = obj;
                }
            }
        });
        return root;
    }

    /** Tries to match two JSON object ignoring positions. */
    public static boolean jsonEquals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 instanceof JSONObject && obj2 instanceof JSONObject) {
            return equals((JSONObject) obj1, (JSONObject) obj2);
        }
        if (obj1 instanceof JSONArray && obj2 instanceof JSONArray) {
            return equals((JSONArray) obj1, (JSONArray) obj2);
        }
        return Objects.equals(obj1, obj2);
    }

    /**
     * Check for equality considering only property values and not positions (just like maps).
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean equals(JSONObject obj1, JSONObject obj2) {
        if (obj1 == obj2) {
            return true;
        }
        return Objects.equals(obj1.toMap(), obj2.toMap());
    }

    /**
     * Check for equality considering only property values and not positions (just like maps).
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean equals(JSONArray arr1, JSONArray arr2) {
        if (arr1 == arr2) {
            return true;
        }
        return Objects.equals(arr1.toList(), arr2.toList());
    }

    /**
     * Merges obj2 over obj1 (only 1 level)
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static JSONObject mergeFlat(JSONObject obj1, JSONObject obj2) {
        JSONObject merged = new JSONObject(obj1, JSONObject.getNames(obj1));
        for (String key : JSONObject.getNames(obj2)) {
            merged.put(key, obj2.get(key));
        }
        return merged;
    }

    /**
     * Remove from obj1 all properties that are equals to obj2 (only 1 level).
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static JSONObject removeFlat(JSONObject obj1, JSONObject obj2) {
        Set<String> obj1Names = new HashSet<>(Arrays.asList(JSONObject.getNames(obj1)));
        Set<String> obj2Names = new HashSet<>(Arrays.asList(JSONObject.getNames(obj2)));
        Set<String> obj1Copy = new HashSet<>(obj1Names);
        obj1Names.retainAll(obj2Names);
        obj2Names.retainAll(obj1Names);
        if (!obj1Names.equals(obj2Names)) {
            // they must be equals removing all differences
            throw new AssertionError();
        }
        obj1Copy.removeAll(obj1Names);
        JSONObject result = new JSONObject();
        for (String name : obj1Names) {
            Object o1 = obj1.get(name);
            Object o2 = obj2.get(name);
            if (!jsonEquals(o1, o2)) {
                result.put(name, o1);
            }
        }
        obj1Copy.removeAll(obj1Names);
        for (String name : obj1Copy) {
            result.put(name, obj1.get(name));
        }
        return result;
    }

    /**
     * Adds the {@code propertyName} property to the {@code json} object according to what
     * specified in {@code values} which is an object where:
     * <ul>
     * <li>key: specify the object to modify
     * <li>value: specify the value to add to the property {@code propertyName}
     * </ul>
     *
     * @param json can be a JSONObject or a JSONArray
     * @param values the values that are to set (i.e. {@code { "alpha": true, "beta": false } })
     * @param propertyName the properties in the target object that should be set with the value
     *        (i.e. {@code 'disable'}).
     */
    public static void setValuesToProperty(Object json, JSONObject values, String propertyName) {
        Set<String> valueKeys = values.keySet();
        JSONUtils.deepTraverse(json, jobj -> {
            if (jobj.keySet().contains("key") ) {
                String propName = jobj.getString("key");
                if (valueKeys.contains(propName)) {
                    Object valueToSet = values.get(propName);
                    jobj.put(propertyName, valueToSet);
                }
            }
        });
    }

    public static void deepTraverse(Object obj, Consumer<JSONObject> nodeConsumer) {
        if (obj instanceof JSONObject) {
            deepTraverseObject((JSONObject) obj, nodeConsumer);
        } else if (obj instanceof JSONArray) {
            deepTraverseArray((JSONArray) obj, nodeConsumer);
        }
    }

    public static void deepTraverseObject(JSONObject obj, Consumer<JSONObject> nodeConsumer) {
        Object valueToSet = null;
        for (String name : obj.keySet()) {
            Object prop = obj.get(name);
            if (prop instanceof JSONArray) {
                deepTraverseArray((JSONArray) prop, nodeConsumer);
            } else if (prop instanceof JSONObject) {
                deepTraverseObject((JSONObject) prop, nodeConsumer);
            }
        }
        nodeConsumer.accept(obj);
    }

    public static void deepTraverseArray(JSONArray array, Consumer<JSONObject> nodeConsumer) {
        for (Object item : array) {
            if (item instanceof JSONObject) {
                deepTraverseObject((JSONObject) item, nodeConsumer);
            } else if (item instanceof JSONArray) {
                deepTraverseArray((JSONArray) item, nodeConsumer);
            }
        }
    }
}
