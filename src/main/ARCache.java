package main;

import exceptions.KeyNotFoundException;

public class ARCache<K, V> implements iCache<K, V> {

	protected AdaptiveLRUCache<K, V> recentCache;
	protected AdaptiveLFUCache<K, V> frequentCache;
	protected int size;

	public ARCache(int size) {
		this.size = size;
		this.recentCache   = new AdaptiveLRUCache<K, V>(size / 2);
		this.frequentCache = new AdaptiveLFUCache<K, V>(size - size / 2);
	}
	
	@Override
	public void set(K key, V value) {
		if (this.frequentCache.get(key) != null) {
			this.frequentCache.set(key, value);
		}
		this.recentCache.set(key, value);
	}

	/*
	 * get an entry from the cache
	 * Algorithm:
	 * 	If key exists in LRU cache:
	 * 		promote it to LFU
	 *  or if key exists in LFU:
	 *  	return the value from LFU
	 *  or if key is a recency ghost:
	 *  	increase the recency cache size by 1
	 *  	decrease frequency cache size by 1
	 *  or if key is a frequency ghost:
	 *  	increase frequency cache size by 1
	 *  	decrease recency cache size by 1
	 * 
	 */
	@Override
	public V get(K key) {
		V value = null;
		try {
			value = this.recentCache.remove(key);
			this.frequentCache.set(key, value);
			return value;
		} catch(KeyNotFoundException e) {
			value = this.frequentCache.get(key);
			if (value != null) {
				return value;
			}

			if (this.recentCache.isGhost(key)) {
				int recentCacheSize = this.recentCache.getSize();
				if (recentCacheSize < this.size) {
					this.recentCache.expand();
					this.frequentCache.shrink();
				}
			}

			if (this.frequentCache.isGhost(key)) {
				int frequentCacheSize = frequentCache.getSize();
				if (frequentCacheSize < this.size) {
					this.frequentCache.expand();
					this.recentCache.shrink();
				}
			}
		}
		return null;
	}

}
