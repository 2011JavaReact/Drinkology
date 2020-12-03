package com.gerardoperez.drinkology.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.gerardoperez.drinkology.dao.DatabaseRecipeDAO;
import com.gerardoperez.drinkology.dao.DatabaseIngredientDAO;
import com.gerardoperez.drinkology.exception.IngredientNotFoundException;
import com.gerardoperez.drinkology.exception.RecipeException;
import com.gerardoperez.drinkology.model.Recipe;
import com.gerardoperez.drinkology.model.Ingredient;
import com.gerardoperez.drinkology.template.InsertRecipeTemplate;

public class RecipeService {
	
	private DatabaseRecipeDAO recipeDAO;
	private DatabaseIngredientDAO ingredientDAO;
	
	//Constructor used for unit testing.
	//TODO: Look into mocking the DatabaseRecipeDAO object with Mockito, in order to simulate an actual database.
	public RecipeService() {
		this.recipeDAO = new DatabaseRecipeDAO();
		this.ingredientDAO = new DatabaseIngredientDAO();
	}
	
	public RecipeService(DatabaseRecipeDAO recipeDAO, DatabaseIngredientDAO ingredientDAO) {
		this.recipeDAO = recipeDAO;
		this.ingredientDAO = ingredientDAO;
	}
	
	public ArrayList<Recipe> getAllRecipes() {
		return recipeDAO.getAllRecipes();
	}
	
	public Recipe insertRecipe(InsertRecipeTemplate recipeTemplate) throws IngredientNotFoundException {
		Ingredient liquor1;
		liquor1 = ingredientDAO.findIngredientByName(recipeTemplate.getLiquor1_name(), "Liquor");
		if (liquor1 == null) {
			throw new IngredientNotFoundException("Liquor1 not found in the database.");
		}
		Ingredient liquor2;
		liquor2 = ingredientDAO.findIngredientByName(recipeTemplate.getLiquor2_name(), "Liquor");
		if (liquor2 == null) {
			throw new IngredientNotFoundException("Liquor2 not found in the database.");
		}
		Ingredient mixer;
		mixer = ingredientDAO.findIngredientByName(recipeTemplate.getMixer_name(), "Mixer");
		if (mixer == null) {
			throw new IngredientNotFoundException("Mixer not found in the database.");
		}
		Ingredient sweetener;
		sweetener = ingredientDAO.findIngredientByName(recipeTemplate.getSweetener_name(), "Sweetener");
		if (sweetener == null) {
			throw new IngredientNotFoundException("Sweetener not found in the database.");
		}
		Ingredient garnish;
		garnish = ingredientDAO.findIngredientByName(recipeTemplate.getGarnish_name(), "Garnish");
		if (garnish == null) {
			throw new IngredientNotFoundException("Garnish not found in the database.");
		}
		
		return recipeDAO.insertRecipe(recipeTemplate.getRecipe_name(), liquor1, liquor2, mixer, sweetener, garnish, recipeTemplate.getOwner_id());	
	}

	public Recipe getRecipeById(int id) throws RecipeException{
		return recipeDAO.getRecipeById(id);
	}

	public Recipe updateRecipe(InsertRecipeTemplate recipeData, int recipeId) throws RecipeException, IngredientNotFoundException{
		Ingredient liquor1 = ingredientDAO.findIngredientByName(recipeData.getLiquor1_name(), "Liquor");
		if (liquor1 == null) {
			throw new IngredientNotFoundException("Liquor1 not found in the database.");
		}
		Ingredient liquor2 = ingredientDAO.findIngredientByName(recipeData.getLiquor2_name(), "Liquor");
		if (liquor2 == null) {
			throw new IngredientNotFoundException("Liquor2 not found in the database.");
		}
		Ingredient mixer = ingredientDAO.findIngredientByName(recipeData.getMixer_name(), "Mixer");
		if (mixer == null) {
			throw new IngredientNotFoundException("Mixer not found in the database.");
		}
		Ingredient sweetener = ingredientDAO.findIngredientByName(recipeData.getSweetener_name(), "Sweetener");
		if (sweetener == null) {
			throw new IngredientNotFoundException("Sweetener not found in the database.");
		}
		Ingredient garnish = ingredientDAO.findIngredientByName(recipeData.getGarnish_name(), "Garnish");
		if (garnish == null) {
			throw new IngredientNotFoundException("Garnish not found in the database.");
		}
		return recipeDAO.updateRecipe(recipeData.getRecipe_name(), liquor1, liquor2, mixer, sweetener, garnish, recipeId);
	}

	public void deleteRecipe(int recipeId) throws RecipeException {
		recipeDAO.deleteRecipe(recipeId);
	}

	public ArrayList<Recipe> getSomeRecipes(String recipeCriteria) throws SQLException {
		return recipeDAO.getSomeRecipes(recipeCriteria);
	}
	

}
