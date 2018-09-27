package tests;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.AdaptiveLFUCache;
import main.LFUCache;

public class AdaptiveLFUCacheTest {
	LFUCache<String, String> cache;

	public AdaptiveLFUCacheTest() {
		this.cache = new AdaptiveLFUCache<String, String>(3);
	}

	@Test
	public void test() {
		assertEquals(null, this.cache.get("0"), "cache miss");
		this.cache.set("key", "value");
		assertEquals("value", this.cache.get("key") , "cache hit");
		this.cache.set("1", "1");
		this.cache.set("2", "2");
		this.cache.set("key", "value");
		this.cache.set("3", "3");
		assertEquals(null, this.cache.get("1") , "get evicted entry");
	}

}
