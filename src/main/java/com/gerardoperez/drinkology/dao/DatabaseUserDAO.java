package com.gerardoperez.drinkology.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gerardoperez.drinkology.model.Recipe;
import com.gerardoperez.drinkology.model.User;
import com.gerardoperez.drinkology.util.JDBCUtility;

public class DatabaseUserDAO {

	private DatabaseRecipeDAO recipeDAO = new DatabaseRecipeDAO();

	/**
	 * Gets all the registered users from the database. Requires admin credentials to be accessed. 
	 * @return ArrayList<User>, or an empty list if search fails. 
	 */

	public ArrayList<User> getAllUsers() {
		String sqlQuery = "SELECT * " 
						+ "FROM users u " 
						+ "INNER JOIN role r " 
						+ "ON u.user_role_id = r.id;";

		ArrayList<User> users = new ArrayList<>();

		try (Connection connection = JDBCUtility.getConnection()) {

			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int user_id = resultSet.getInt(1);
				String user_name = resultSet.getString(2);
				String user_email = resultSet.getString(3);
				String user_password = resultSet.getString(4);
				int user_role_id = resultSet.getInt(5);
				String user_role = resultSet.getString(6);

				ArrayList<Recipe> allRecipes = recipeDAO.getAllRecipes();
				ArrayList<Recipe> ownedRecipes = new ArrayList<>();

				for (Recipe recipe : allRecipes) {
					if (recipe.getOwner() == user_id) {
						ownedRecipes.add(recipe);
					}

					User user = new User(user_id, user_name, user_email, user_password, user_role_id, user_role);
					user.setRecipes(ownedRecipes);
					users.add(user);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * Searches for user by ID. 
	 * @param id of the user being searched.
	 * @return User found by ID, or null if it isn't found. 
	 */

	public User getUserById(int id) {
		String sqlQuery = "SELECT * " 
						+ "FROM users u " 
						+ "INNER JOIN roles r " 
						+ "ON u.user_role_id = r.id";

		User user = null;

		try (Connection connection = JDBCUtility.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int user_id = resultSet.getInt(1);
				if (user_id == id) {
					String user_name = resultSet.getString(2);
					String user_email = resultSet.getString(3);
					String user_password = resultSet.getString(4);
					int user_role_id = resultSet.getInt(5);
					String user_role = resultSet.getString(7);

					ArrayList<Recipe> allRecipes = recipeDAO.getAllRecipes();
					ArrayList<Recipe> ownedRecipes = new ArrayList<>();

					for (Recipe recipe : allRecipes) {
						if (recipe.getOwner() == user_id) {
							ownedRecipes.add(recipe);
						}

						user = new User(user_id, user_name, user_email, user_password, user_role_id, user_role);
						user.setRecipes(ownedRecipes);
					}
				}
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUserByName(String name) {
		String sqlQuery = "SELECT * " 
						+ "FROM users u " 
						+ "INNER JOIN roles r " 
						+ "ON u.user_role_id = r.id";

		User user = null;

		try (Connection connection = JDBCUtility.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String user_name = resultSet.getString(2);
				if (user_name == name) {
					int user_id = resultSet.getInt(1);
					String user_email = resultSet.getString(3);
					String user_password = resultSet.getString(4);
					int user_role_id = resultSet.getInt(5);
					String user_role = resultSet.getString(7);

					ArrayList<Recipe> allRecipes = recipeDAO.getAllRecipes();
					ArrayList<Recipe> ownedRecipes = new ArrayList<>();

					for (Recipe recipe : allRecipes) {
						if (recipe.getOwner() == user_id) {
							ownedRecipes.add(recipe);
						}

						user = new User(user_id, user_name, user_email, user_password, user_role_id, user_role);
						user.setRecipes(ownedRecipes);
					}
				}
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Inserts a new user into the database, assigning it a new unique ID. 
	 * Requires all fields to be filled.
	 * @param user The User object to be inserted, with its id initialized to be -1.
	 * @return User object with the assigned unique ID. 
	 */
	public User insertUser(User user) {
		String sqlQuery = "INSERT INTO users "
						+ "(user_name, user_email, user_password, user_role_id) "
						+ "VALUES "
						+ "(?, ?, ?, ?)";
		
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setInt(4, user.getRole_id());
			
			if (preparedStatement.executeUpdate() != 1) {
				throw new SQLException("User insertion failed. No user created.");
			}
			
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			int auto_id;
			if (generatedKeys.next()) {
				auto_id = generatedKeys.getInt(1);
			} else {
				throw new SQLException("User insertion failed. No ID generated.");
			}
			connection.commit();
			return new User(auto_id, user.getUsername(), user.getEmail(), user.getPassword(), user.getRole_id(), user.getRole());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Updates used information, using a User with the updated information and an ID of -1. 
	 * @param currentUser
	 * @return User with the updated information with the correct ID.
	 */
	public void updateUser(User updatedUser) {
		String sqlQuery = "UPDATE users "
						+ "SET user_name = ?, "
						+ "user_email = ?, "
						+ "user_password = ?, "
						+ "user_role_id = ? "
						+ "WHERE id = ?;";
		
		try (Connection connection = JDBCUtility.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, updatedUser.getUsername());
			preparedStatement.setString(2, updatedUser.getEmail());
			preparedStatement.setString(3, updatedUser.getPassword());
			preparedStatement.setInt(4, updatedUser.getRole_id());
			preparedStatement.setInt(5, updatedUser.getId());
			
			if(preparedStatement.executeUpdate() != 1) {
				throw new SQLException("User update failed. Database unchanged");
			}
			
			connection.commit();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes a user by name 
	 * @param name
	 */
	public void deleteUserbyName(String name) {
		String sqlQuery = "DELETE FROM users WHERE user_name = ?;";
		
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, name);
			
			if (preparedStatement.executeUpdate() != 1) {
				throw new SQLException("User deletion failed.");
			}
			
			connection.commit();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes a used by ID.
	 * @param id
	 */
	public void deleteUserById(int id) {
		String sqlQuery = "DELETE FROM users WHERE id = ?;";
		
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, id);
			
			if (preparedStatement.executeUpdate() != 1) {
				throw new SQLException("User deletion failed.");
			}
			
			connection.commit();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
