package ir.piana.dev.common.cache;

/**
 * @author Mohammad Rahmati, 3/6/2017 12:00 PM
 */
public interface PianaCacheLoader<K, V> {
    V load(K key) throws Exception;
}
