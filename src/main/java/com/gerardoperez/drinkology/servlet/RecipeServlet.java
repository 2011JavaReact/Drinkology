package com.gerardoperez.drinkology.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import com.gerardoperez.drinkology.exception.IngredientNotFoundException;
import com.gerardoperez.drinkology.exception.RecipeException;
import com.gerardoperez.drinkology.model.Recipe;
import com.gerardoperez.drinkology.service.RecipeService;
import com.gerardoperez.drinkology.template.InsertRecipeTemplate;

public class RecipeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ObjectMapper objectMapper = new ObjectMapper();
	private RecipeService recipeService = new RecipeService();
	
	public RecipeServlet() {
		super();
	}
	
	public RecipeServlet(ObjectMapper objectMapper, RecipeService recipeService) {
		this.objectMapper = new ObjectMapper();
		this.recipeService = new RecipeService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getPathInfo().length() > 1) {
			try {
				int recipeId = Integer.parseInt(request.getPathInfo().split("/")[1]);
				Recipe requestedRecipe = recipeService.getRecipeById(recipeId);
				response.getWriter().append(objectMapper.writeValueAsString(requestedRecipe));
				response.setContentType("application/json");
				response.setStatus(200);
			} catch (RecipeException e) {
				response.setStatus(404);
			}
		} else {
			response.setStatus(400);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BufferedReader bufferedReader = request.getReader();

		StringBuilder stringBuilder = new StringBuilder();
		String line;
		
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}

		String jsonString = stringBuilder.toString();

		try {
			InsertRecipeTemplate recipeData = objectMapper.readValue(jsonString, InsertRecipeTemplate.class);

			Recipe recipe = recipeService.insertRecipe(recipeData);

			String insertedRecipeJSON = objectMapper.writeValueAsString(recipe);

			response.getWriter().append(insertedRecipeJSON);
			response.setContentType("application/json");
			response.setStatus(201);
			response.getWriter().append("New Recipe Created.");
		} catch (JsonProcessingException | IngredientNotFoundException e) {
			response.setStatus(400);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if (request.getPathInfo().length() > 1) {
			int recipeId = Integer.parseInt(request.getPathInfo().split("/")[1]);
			
			BufferedReader bufferedReader = request.getReader();
			
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}

			String jsonString = stringBuilder.toString();
			
			try {
				InsertRecipeTemplate recipeData = objectMapper.readValue(jsonString, InsertRecipeTemplate.class);

				Recipe recipe = recipeService.updateRecipe(recipeData, recipeId);

				String insertedRecipeJSON = objectMapper.writeValueAsString(recipe);

				response.getWriter().append(insertedRecipeJSON);
				response.setContentType("application/json");
				response.setStatus(201);
				response.getWriter().append("Recipe " + recipeId + " updated.");
			} catch (JsonProcessingException | RecipeException e) {
				response.setStatus(400);
			} catch (IngredientNotFoundException e) {
				response.setStatus(404);
			}
			
		} else {
			response.setStatus(400);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if (request.getPathInfo().length() > 1) {
			try {
				int recipeId = Integer.parseInt(request.getPathInfo().split("/")[1]);
				
				recipeService.deleteRecipe(recipeId);
				
				response.setStatus(200);
			} catch (RecipeException e) {
				response.setStatus(404);
			}

		} else {
			response.setStatus(400);
		}
	}

}
