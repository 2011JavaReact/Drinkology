package com.gerardoperez.drinkology.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gerardoperez.drinkology.model.Recipe;
import com.gerardoperez.drinkology.model.Ingredient;
import com.gerardoperez.drinkology.util.JDBCUtility;
import com.gerardoperez.drinkology.exception.IngredientNotFoundException;
import com.gerardoperez.drinkology.exception.RecipeException;

public class DatabaseRecipeDAO {
	
	public ArrayList<Recipe> getAllRecipes() {
		
		String sqlQuery = "SELECT * "
						+ "FROM recipe r "
						+ "INNER JOIN liquor l "
						+ "ON r.liquor1_id = l.id "
						+ "INNER JOIN liquor l2 "
						+ "ON r.liquor2_id = l2.id "
						+ "INNER JOIN mixer m "
						+ "ON r.mixer_id = m.id "
						+ "INNER JOIN sweetener s "
						+ "ON r.sweetener_id = s.id "
						+ "INNER JOIN garnish g "
						+ "ON r.garnish_id = g.id";
		
		ArrayList<Recipe> recipes = new ArrayList<>();
		
		try (Connection connection = JDBCUtility.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			
			while(resultSet.next()) {
				int recipe_id = resultSet.getInt(1);
				String recipe_name = resultSet.getString(2);
				int liquor1_id = resultSet.getInt(8);
				String liquor1_name = resultSet.getString(9);
				int liquor2_id = resultSet.getInt(10);
				String liquor2_name = resultSet.getString(11);
				int mixer_id = resultSet.getInt(12);
				String mixer_name = resultSet.getString(13);
				int sweetener_id = resultSet.getInt(14);
				String sweetener_name = resultSet.getString(15);
				int garnish_id = resultSet.getInt(16);
				String garnish_name = resultSet.getString(17);
				int owner_id = resultSet.getInt(18);
				
				Ingredient liquor1 = new Ingredient(liquor1_id, liquor1_name, "Liquor");
				Ingredient liquor2 = new Ingredient(liquor2_id, liquor2_name, "Liquor");
				Ingredient mixer = new Ingredient(mixer_id, mixer_name, "Mixer");
				Ingredient sweetener = new Ingredient(sweetener_id, sweetener_name, "Sweetener");
				Ingredient garnish = new Ingredient(garnish_id, garnish_name, "Garnish");
				
				Recipe recipe = new Recipe(recipe_id, recipe_name, liquor1, liquor2, mixer, sweetener, garnish, owner_id);
				
				recipes.add(recipe);
			}
			
			return recipes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recipes;
	}
	
	public Recipe insertRecipe(String recipe_name, Ingredient liquor1, Ingredient liquor2, Ingredient mixer, Ingredient sweetener, Ingredient garnish, int owner_id) {
		
		String sqlQuery = "INSERT INTO recipe "
						+ "(recipe_name, liquor1_id, liquor2_id, mixer_id, sweetener_id, garnish_id, owner_id) "
						+ "VALUES"
						+ "(?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, recipe_name);
			preparedStatement.setInt(2, liquor1.getId());
			preparedStatement.setInt(3, liquor2.getId());
			preparedStatement.setInt(4, mixer.getId());
			preparedStatement.setInt(5, sweetener.getId());
			preparedStatement.setInt(6, garnish.getId());
			
			if (preparedStatement.executeUpdate() != 1) {
				throw new SQLException("Recipe insertion failed. Database not affected.");
			}
			
			int autoId = 0;
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Recipe insertion failed. No ID generated.");
			}
			
			connection.commit();
			
			return new Recipe(autoId, recipe_name, liquor1, liquor2, mixer, sweetener, garnish, owner_id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Recipe getRecipeById(int id) throws RecipeException {
		
		String sqlQuery = "SELECT * "
				+ "FROM recipe r "
				+ "INNER JOIN liquor l "
				+ "ON r.liquor1_id = l.id "
				+ "INNER JOIN liquor l2 "
				+ "ON r.liquor2_id = l2.id "
				+ "INNER JOIN mixer m "
				+ "ON r.mixer_id = m.id "
				+ "INNER JOIN sweetener s "
				+ "ON r.sweetener_id = s.id "
				+ "INNER JOIN garnish g "
				+ "ON r.garnish_id = g.id "
				+ "WHERE r.recipe_id = ? LIMIT 1";
		
		try (Connection connection = JDBCUtility.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				int recipe_id = resultSet.getInt(1);
				String recipe_name = resultSet.getString(2);
				int liquor1_id = resultSet.getInt(8);
				String liquor1_name = resultSet.getString(9);
				int liquor2_id = resultSet.getInt(10);
				String liquor2_name = resultSet.getString(11);
				int mixer_id = resultSet.getInt(12);
				String mixer_name = resultSet.getString(13);
				int sweetener_id = resultSet.getInt(14);
				String sweetener_name = resultSet.getString(15);
				int garnish_id = resultSet.getInt(16);
				String garnish_name = resultSet.getString(17);
				int owner_id = resultSet.getInt(18);

				Ingredient liquor1 = new Ingredient(liquor1_id, liquor1_name, "Liquor");
				Ingredient liquor2 = new Ingredient(liquor2_id, liquor2_name, "Liquor");
				Ingredient mixer = new Ingredient(mixer_id, mixer_name, "Mixer");
				Ingredient sweetener = new Ingredient(sweetener_id, sweetener_name, "Sweetener");
				Ingredient garnish = new Ingredient(garnish_id, garnish_name, "Garnish");

				Recipe recipe = new Recipe(recipe_id, recipe_name, liquor1, liquor2, mixer, sweetener, garnish, owner_id);
				return recipe;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		throw new RecipeException("Unable to get recipe from the database.");
	}

	public Recipe updateRecipe(String recipe_name, Ingredient liquor1, Ingredient liquor2, Ingredient mixer, Ingredient sweetener, Ingredient garnish, int recipeId) throws RecipeException, IngredientNotFoundException {
		
		String sqlQuery = "UPDATE recipe "
						+ "SET recipe_name = ?, "
						+ "liquor1_id = ?, "
						+ "liquor2_id = ?, "
						+ "mixer_id = ?, "
						+ "sweetener_id = ?, "
						+ "garnish_id = ? "
						+ "WHERE recipe_id = ?";
		
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, recipe_name);
			preparedStatement.setInt(2, liquor1.getId());
			preparedStatement.setInt(3, liquor2.getId());
			preparedStatement.setInt(4, mixer.getId());
			preparedStatement.setInt(5, sweetener.getId());
			preparedStatement.setInt(6, garnish.getId());
			preparedStatement.setInt(7, recipeId);
			
			if (preparedStatement.executeUpdate() != 1) {
				throw new SQLException("Recipe insertion failed. Database not affected.");
			}
			
			connection.commit();
			
			return getRecipeById(recipeId);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void deleteRecipe(int recipeId) throws RecipeException {
		String sqlQuery = "DELETE FROM Recipe "
						+ "WHERE recipe_id = ?";
		
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, recipeId);
			
			int queryResult = preparedStatement.executeUpdate();
			if (queryResult != 1) {
				throw new RecipeException("Recipe deletion failed. Recipe ID not valid.");
			}
			
			connection.commit();
		} catch (SQLException e) {
			
		}
	}

	public ArrayList<Recipe> getSomeRecipes(String recipeCriteria) throws SQLException {
		
		String sqlQuery = "SELECT * "
				+ "FROM recipe r "
				+ "INNER JOIN liquor l "
				+ "ON r.liquor1_id = l.id "
				+ "INNER JOIN liquor l2 "
				+ "ON r.liquor2_id = l2.id "
				+ "INNER JOIN mixer m "
				+ "ON r.mixer_id = m.id "
				+ "INNER JOIN sweetener s "
				+ "ON r.sweetener_id = s.id "
				+ "INNER JOIN garnish g "
				+ "ON r.garnish_id = g.id "
				+ "WHERE l.liquor_name = ?";
		
		ArrayList<Recipe> recipes = new ArrayList<>();
		
		try (Connection connection = JDBCUtility.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, recipeCriteria);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int recipe_id = resultSet.getInt(1);
				String recipe_name = resultSet.getString(2);
				int liquor1_id = resultSet.getInt(8);
				String liquor1_name = resultSet.getString(9);
				int liquor2_id = resultSet.getInt(10);
				String liquor2_name = resultSet.getString(11);
				int mixer_id = resultSet.getInt(12);
				String mixer_name = resultSet.getString(13);
				int sweetener_id = resultSet.getInt(14);
				String sweetener_name = resultSet.getString(15);
				int garnish_id = resultSet.getInt(16);
				String garnish_name = resultSet.getString(17);
				int owner_id = resultSet.getInt(18);
				
				Ingredient liquor1 = new Ingredient(liquor1_id, liquor1_name, "Liquor");
				Ingredient liquor2 = new Ingredient(liquor2_id, liquor2_name, "Liquor");
				Ingredient mixer = new Ingredient(mixer_id, mixer_name, "Mixer");
				Ingredient sweetener = new Ingredient(sweetener_id, sweetener_name, "Sweetener");
				Ingredient garnish = new Ingredient(garnish_id, garnish_name, "Garnish");
				
				Recipe recipe = new Recipe(recipe_id, recipe_name, liquor1, liquor2, mixer, sweetener, garnish, owner_id);
				
				recipes.add(recipe);
			}
			
			return recipes;
		}
	}

}
