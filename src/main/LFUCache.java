package main;
import java.util.*;

public class LFUCache<K, V> implements iCache<K, V>, iBounded, iEvictable<K> {

	class Node<K, V>{
	    K key;
	    V value;
	    int frequency;
	    int order;
	 
	    public Node(K key, V value){
	    	this.key = key;
	    	this.value = value;
	        this.frequency = 1;
	        this.order = autoInc++;
	    }
	};

    private static int autoInc = 1;
	protected int size;
	protected Map<K, Node<K, V>> map = new HashMap<K, Node<K, V>>();
	protected PriorityQueue<Node<K, V>> queue
	= new PriorityQueue<Node<K, V>>(100,
			(a, b) -> {
				int cmp = a.frequency - b.frequency;
				if (cmp == 0) {
					cmp = a.order - b.order;
				}
				return cmp;
			});

	public LFUCache(int size) {
		this.size = size;
	}

	@Override
	public void set(K key, V value) {
		if (this.get(key) != null) {
			return;
		} else if (isFull()) {
			this.evict();
		}
		Node<K, V> createdNode = new Node<K, V>(key, value);
		this.map.put(key, createdNode);
		this.queue.add(createdNode);
	}

	@Override
	public V get(K key) {
		Node<K, V> node = this.map.get(key);
		if (node == null) {
			return null;
		}
		node.frequency++;
		return node.value;
	}

	@Override
	public K evict() {
		Node<K, V> evicted = this.queue.remove();
		this.map.remove(evicted.key);
		return evicted.key;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public boolean isFull() {
		return this.map.size() >= this.size;
	}
}
