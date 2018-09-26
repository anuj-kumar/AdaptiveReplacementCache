package main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ARCacheTest {

	@Test
	final void testARCache() {
		ARCache<String, String> cache = new ARCache<String, String>(4);
		cache.set("1", "a");
		cache.set("2", "b");
		// [2 1|   ]
		cache.set("3", "c");
		// [3 2|   ]
		cache.set("4", "d");
		// [4 3|   ]
		assertEquals(null, cache.get("1"), "evicted from lru");
		cache.get("3");
		// [4  |3  ]
		cache.set("5", "e");
		// [5 4|3  ]
		cache.set("6", "f");
		// [6 5|3  ]
		assertEquals("c", cache.get("3"), "promoted to lfu");
		assertEquals(null, cache.get("4"), "evicted from lru");
		// 4 is a ghost entry, so size of recency cache should increase
		// [6 5  |3]
		cache.get("5");
		// So 3 should become invalid now, since LFU size shrunk
		// [6    |5]
		assertEquals(null, cache.get("3"), "lfu shrunk and 3 evicted");
		// now the recency cache expands again shrinking the frequency cache
		// [6 5|3  ]
		cache.get("6");
		// 6 gets promoted to LFU
		// 5 should still be there
		// [5  |3 6]
		assertEquals("e", cache.get("5"), "not evicted");
		// [   |3 5]
		assertEquals(null, cache.get("6"), "evicted from lfu");
		// resize happens now
		// [ |  3 5]
		cache.set("4", "dd");
		// [4|  3 5]
	}

}
