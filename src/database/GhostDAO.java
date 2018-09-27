package database;

import java.sql.ResultSet;

import java.sql.*;

import models.Ghost;

/*
 * The DAO layer for the Ghost object
 * Tightly coupled with the Ghost model
 */
public class GhostDAO {

	Connection conn;
	public GhostDAO() {
		conn = DBConnectFactory.getConnection();
	}

	public Ghost find(String key, String type) {
		String query = "SELECT * from ghost where cache_key='" + key + "' and type='" + type + "'";
		try {
			Statement stmt = conn.createStatement();
			ResultSet set = stmt.executeQuery(query);
			if (set.next()) {
				return this.createModel(set);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected Ghost createModel(ResultSet set) {
		try {
			return new Ghost(set.getString("cache_key"), set.getString("type"));
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean save(Ghost model) {
		String query = "INSERT into ghost (cache_key, type) VALUES (?, ?)";
		query += " ON DUPLICATE KEY UPDATE cache_key = cache_key";

		try {
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, model.key);
		stmt.setString(2, model.type);
		int res = stmt.executeUpdate();
		if(res == 1) {
			return true;
		}
		return false;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(String key, String type) {
		String query = "DELETE FROM ghost where cache_key='" + key + "' and type='" + type + "'";
		try {
			Statement stmt = conn.createStatement();
			int res = stmt.executeUpdate(query);
			if(res == 1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
