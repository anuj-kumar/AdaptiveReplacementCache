package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import main.ARCache;

public class ARCacheTest {

	@Test
	public void testARCache() {
		ARCache<String, String> cache = new ARCache<String, String>(4);
		cache.set("1", "a");
		cache.set("2", "b");
		// [2 1|   ]
		cache.set("3", "c");
		// [3 2|   ]
		cache.set("4", "d");
		// [4 3|   ]
		assertEquals(null, cache.get("1"), "evicted from lru");
		cache.set("3", "cc");
		assertEquals("cc", cache.get("3"), "Update existing cache entry");
		cache.get("3");
		// [4    |3]
		cache.set("5", "e");
		// [5 4  |3]
		cache.set("6", "f");
		// [6 5 4|3]
		assertEquals("cc", cache.get("3"), "promoted to lfu");
		cache.get("4");
		assertEquals("d", cache.get("4"), "promoted from lru");
		assertEquals(null, cache.get("3"), "evicted from lfu");
		// 3 is a ghost entry, so size of recency cache should increase
		cache.get("5");
		// 5 gets promoted to lfu
		// [6  |4 5]
		assertEquals(null, cache.get("3"), "lfu shrunk and 3 evicted");
		// now the frequency cache expands again shrinking the recency cache
		// [6|4 5  ]
		cache.get("6");
		// 6 gets promoted to LFU
		// 5 should still be there
		// [ |4 5 6]
		assertEquals(null, cache.get("1"), "resize LRU");
		// [   |4 6]
		cache.set("4", "dd");
		// [4  |3 6]
		assertEquals(null, cache.get("5"));
		assertEquals("dd", cache.get("4"));
	}

}
