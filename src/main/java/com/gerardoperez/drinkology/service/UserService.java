package com.gerardoperez.drinkology.service;

import java.util.ArrayList;

import com.gerardoperez.drinkology.dao.DatabaseUserDAO;
import com.gerardoperez.drinkology.model.User;

public class UserService {
	
	private DatabaseUserDAO userDAO;
	private ArrayList<User> users;
	
	public UserService() {
		this.userDAO = new DatabaseUserDAO();
	}
	
	public ArrayList<User> getAllUsers() {
		users = userDAO.getAllUsers();
		return users;
	}
	
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}
	
	public User getUserByName(String name) {
		return userDAO.getUserByName(name);
	}
	
	public User insertUser(User newUser) {
		return userDAO.insertUser(newUser);
	}
	
	public void updateUser(User updatedUser) {
		userDAO.updateUser(updatedUser);
	}
	
	public void deleteUserbyName(String name) {
		userDAO.deleteUserbyName(name);
	}
	
	public void deleteUserById(int id) {
		userDAO.deleteUserById(id);
	}

	/**
	 * Returns a plain user object.
	 * This is used by the login servlet.
	 * @param userName The name of the user we are looking for
	 * @return the User if found and a blank user if no user was found
	 */
	public User getAUserByName(String userName) {
		ArrayList<User> users = getAllUsers();
		for (User user : users) {
			if (user.getUsername().equals(userName)) {
				return user;
			}
		}
		return new User();
	}

	public boolean login(User completeUser, User userTryingToLogin ) {
		return completeUser.getPassword().equals(userTryingToLogin.getPassword())
				&& completeUser.getUsername().equals(userTryingToLogin.getUsername());

	}
	

}
