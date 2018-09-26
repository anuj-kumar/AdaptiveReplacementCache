package database;

import java.util.Optional;

import models.Ghost;

public interface DAO<T> {
	Optional<T> get(String key);
	boolean create(Optional<T> model);
	Ghost get(String key, String type);
}
