# Adaptive Replacement Cache
Implementation of the Adaptive Replacement cache in Java.

[Link to the paper](ftp://paranoidbits.com/ebooks/Outperforming%20LRU%20with%20an%20Adaptive%20Replacement%20Cache.pdf)

## Algorithm:
1. Initialize the cache with equal share of capacity between LRU and LFU caches.
2. If a key does not exist, insert into LRU. If the key already exists, update the existing.
3. If there is a GET request for a key, promote it to LFU.
4. On eviction, move a key to the database archive of the corresponding cache type.
5. If a key is not found and is present in the DB archive:
   - Expand this cache type
   - Shrink the other cache type

## Notes:
The heuristic in use for adaptation between recent and frequent caches is such that if there are subsequent *GET* requests for a *GHOST* entry, then the corresponding cache will keep on growing in size. This although can be improved with a better heuristic.

Demo test cases are available in the `tests/ARCacheTest.java` file.