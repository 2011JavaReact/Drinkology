package com.gerardoperez.drinkology.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerardoperez.drinkology.service.RecipeService;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class RecipesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ObjectMapper objectMapper = new ObjectMapper();
	private RecipeService recipeService = new RecipeService();
	
	public RecipesServlet() {
		super();
	}
	
	public RecipesServlet(ObjectMapper objectMapper, RecipeService recipeService) {
		this.objectMapper = objectMapper;
		this.recipeService = recipeService;
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (request.getPathInfo().length() > 1) {
    		try {
    			String recipeCriteria = request.getPathInfo().split("/")[1];
    			String jsonString = objectMapper.writeValueAsString(recipeService.getSomeRecipes(recipeCriteria));
    			response.getWriter().append(jsonString);
    	    	response.setContentType("application/json");
    	    	response.setStatus(200);
    		} catch (SQLException e) {
    			e.printStackTrace();
    			Logger logger = Logger.getLogger(RecipesServlet.class);
    			logger.debug(e.toString() + "QueryString: " + request.getQueryString());
    		}
    	} else {
    	String jsonString = objectMapper.writeValueAsString(recipeService.getAllRecipes());
    	response.getWriter().append(jsonString);
    	response.setContentType("application/json");
    	response.setStatus(200);
    	}			
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.getWriter().append("Goodbye World.");
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }


}
