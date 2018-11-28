package co.m1ke.matrix.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtils {

    public static <K, V> Map.Entry<K, V> getFirstEntry(Map<K, V> map) {
        if (map.isEmpty()) return null;
        return map.entrySet().iterator().next();
    }

    public static <K, V> Map.Entry<K, V> getLastEntry(Map<K, V> map) {
        try {
            if (map instanceof LinkedHashMap) return getLastViaReflection(map);
        } catch (Exception ignore) { }
        return getLastByIterating(map);
    }

    public static <K, V> Map.Entry<K, V> getLastByIterating(Map<K, V> map) {
        Map.Entry<K, V> last = null;
        for (Map.Entry<K, V> e : map.entrySet()) last = e;
        return last;
    }

    public static <K,V> Map.Entry<K,V> getLast(LinkedHashMap<K,V> map) {
        Iterator<Map.Entry<K,V>> iterator = map.entrySet().iterator();
        Map.Entry<K, V> result = null;
        while (iterator.hasNext()) {
            result = iterator.next();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Map.Entry<K, V> getLastViaReflection(Map<K, V> map) throws NoSuchFieldException, IllegalAccessException {
        Field tail = map.getClass().getDeclaredField("tail");
        tail.setAccessible(true);
        return (Map.Entry<K, V>) tail.get(map);
    }

}
