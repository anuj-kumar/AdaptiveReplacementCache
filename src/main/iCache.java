package main;

interface iCache<K, V> {
	public void set(K key, V value);
	public V get(K key);
}
