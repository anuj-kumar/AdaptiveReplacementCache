package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.AdaptiveLRUCache;
import main.LRUCache;

class AdaptiveLRUCacheTest {
	LRUCache<String, String> cache;

	public AdaptiveLRUCacheTest() {
		this.cache = new AdaptiveLRUCache<String, String>(3);
	}

	@Test
	public void test() {
		assertEquals(null, this.cache.get("0"), "cache miss");
		this.cache.set("key", "value");
		assertEquals("value", this.cache.get("key") , "cache hit");
		this.cache.set("1", "1");
		this.cache.set("2", "2");
		this.cache.get("key");
		this.cache.set("3", "3");
		assertEquals(null, this.cache.get("1") , "get evicted entry");
	}

}
