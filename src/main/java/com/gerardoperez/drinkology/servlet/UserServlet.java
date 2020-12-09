package com.gerardoperez.drinkology.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerardoperez.drinkology.model.User;
import com.gerardoperez.drinkology.service.UserService;

public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ObjectMapper objectMapper = new ObjectMapper();;
	private UserService userService = new UserService();
	
	public UserServlet() {
		super();
	}
	
	public UserServlet(ObjectMapper objectMapper, UserService userService) {
		this.objectMapper = new ObjectMapper();
		this.userService = new UserService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		//Check length to ensure that an index is in the URL. Retrieve a single user.
		if(request.getPathInfo() != null && request.getPathInfo().length() > 1) {
			try {
				int user_id = Integer.parseInt(request.getPathInfo().split("/")[1]);
				User requestedUser = userService.getUserById(user_id);
				if (requestedUser == null) {
					response.getWriter().append("ID not valid. User not found.");
					response.setStatus(404);
				} else {
					response.getWriter().append(objectMapper.writeValueAsString(requestedUser));
					response.setContentType("application/json");
					response.setStatus(200);
				}
			} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
				response.setStatus(404);
			}
		//No index means that it wants all the users. Authorization is required. 
		} else {
			if (!request.isRequestedSessionIdValid()){
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You're not authorized to access this resource.");
			} else {
				if (((Integer) session.getAttribute("role_id")) == 1) {
					response.getWriter().append(objectMapper.writeValueAsString(userService.getAllUsers()));
				} else {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in as admin.");
				}
			}
		}
		response.setContentType("application/json");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader bufferedReader = request.getReader();
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}
		
		String jsonString = stringBuilder.toString();
		
		try {
			User newUser = objectMapper.readValue(jsonString, User.class);
			newUser.setId(-1);
			User insertedUser = userService.insertUser(newUser);
			String insertedUserJSON = objectMapper.writeValueAsString(insertedUser);

			if (insertedUserJSON.equals("null")) {
				response.getWriter().append("could not add user. Object was null");
			} else {
				response.getWriter().append(insertedUserJSON);
				response.setStatus(201);
				response.getWriter().append("New User Created.");
			}
			response.setContentType("application/json");

		} catch (JsonProcessingException e) {
			response.setStatus(400);
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getPathInfo().length() > 1) {
			int user_id = Integer.parseInt(request.getPathInfo().split("/")[1]);
			
			BufferedReader bufferedReader = request.getReader();
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			
			String jsonString = stringBuilder.toString();
			
			try {
				User updatedUser = objectMapper.readValue(jsonString, User.class);
				updatedUser.setId(user_id);
				userService.updateUser(updatedUser);
				String updatedUserJSON = objectMapper.writeValueAsString(updatedUser);
				
				response.getWriter().append(updatedUserJSON);
				response.setContentType("application/json");
				response.setStatus(201);
				response.getWriter().append("User " + user_id + " Created.");
				
			} catch (JsonProcessingException e) {
				response.setStatus(400);
			}
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getPathInfo().length() > 1) {
			try {
				int user_id = Integer.parseInt(request.getPathInfo().split("/")[1]);
				userService.deleteUserById(user_id);
				response.setStatus(200);
				response.getWriter().append("User deleted successfully");
			} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
				response.setStatus(404);
			}
		} else {
			response.setStatus(400);
		}
	}

}