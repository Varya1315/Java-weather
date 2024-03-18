package weather.springwea.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Cache<K, V> {
    private final Map<K, V> cacheMap = new ConcurrentHashMap<>();

    private static final int MAX_SIZE = 100;

    public void put(K key, V value) {
        cacheMap.put(key, value);
        if (cacheMap.size() >= MAX_SIZE)
            cacheMap.clear();
    }

    public V get(K key) {
        return cacheMap.get(key);
    }

    public void remove(K key) {
        cacheMap.remove(key);
    }

    public Iterable<Map.Entry<K, V>> getNativeCache() {
        return cacheMap.entrySet();
    }

}
