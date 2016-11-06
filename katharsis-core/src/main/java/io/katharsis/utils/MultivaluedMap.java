package io.katharsis.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @param <K> key
 * @param <V> value
 */
public class MultivaluedMap<K, V> {

	private Map<K, List<V>> map = new HashMap<>();

	public void add(K key, V value) {
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<>();
			map.put(key, list);
		}
		list.add(value);
	}

	public void addAll(K key, Iterable<V> values) {
		for (V value : values) {
			add(key, value);
		}
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	public V getUnique(K key) {
		List<V> list = map.get(key);
		if (list.size() > 1) {
			throw new IllegalStateException("expected unique key=" + key + ", got=" + list);
		}
		return list.get(0);
	}

	public List<V> getList(K key) {
		List<V> list = map.get(key);
		PreconditionUtil.assertNotNull("key not available", list);
		return list;
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public void set(K key, List<V> values) {
		map.put(key, values);
	}
}
