package com.gerardoperez.drinkology.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gerardoperez.drinkology.model.Ingredient;
import com.gerardoperez.drinkology.util.JDBCUtility;

public class DatabaseIngredientDAO {
	
	public Ingredient findIngredientByName(String name, String type) {
		String sqlQuery = "SELECT * "
						+ "FROM " + type + " i "
						+ "WHERE i." + type + "_name = ?";
		
		try (Connection connection = JDBCUtility.getConnection()) {
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, name);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				String ingredient_name = resultSet.getString(2);
				return new Ingredient(id, ingredient_name, type.toLowerCase());
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
