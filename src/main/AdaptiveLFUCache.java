package main;

import database.GhostDAO;
import models.Ghost;

public class AdaptiveLFUCache<K, V> extends LFUCache<K, V> implements iSizable, iStorable<K> {

	public static final String TYPE = "frequent";
	
	public AdaptiveLFUCache(int size) {
		super(size);
	}

	@Override
	public void expand() {
		super.size++;
	}

	/*
	 * Some duplicacy. Can be removed using composition
	 */
	@Override
	public void shrink() {
		if (this.map.size() == super.size) {
			this.evict();
		}
		if (super.size > 0) {
			super.size--;
		}
	}

	@Override
	public void store(K key) {
		GhostDAO storeObj = new GhostDAO();
		storeObj.save(new Ghost(key.toString(), TYPE));
	}

	public boolean isGhost(K key) {
		GhostDAO storeObj = new GhostDAO();
		Ghost ghost = storeObj.find(key.toString(), TYPE);
		return ghost != null;
	}

	public boolean killGhost(K key) {
		GhostDAO storeObj = new GhostDAO();
		return storeObj.delete(key.toString(), TYPE);
	}

	@Override
	public void set(K key, V value) {
		super.set(key, value);
		if (this.isGhost(key)) {
			this.killGhost(key);
		}
	}

	public K evict() {
		K key = super.evict();
		this.store(key);
		return key;
	}
}
