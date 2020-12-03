package com.gerardoperez.drinkology.template;

public class InsertRecipeTemplate {
	
	private String recipe_name;
	private String liquor1_name;
	private String liquor2_name;
	private String mixer_name;
	private String sweetener_name;
	private String garnish_name;
	private int owner_id;
	
	public InsertRecipeTemplate() {
		super();
	}
	
	public InsertRecipeTemplate(String recipe_name, String liquor1_name, String liquor2_name, String mixer_name, String sweetener_name, String garnish_name, int owner_id) {
		this.setRecipe_name(recipe_name);
		this.setLiquor1_name(liquor1_name);
		this.setLiquor2_name(liquor2_name);
		this.setMixer_name(mixer_name);
		this.setSweetener_name(sweetener_name);
		this.setGarnish_name(garnish_name);
		this.setOwner_id(owner_id);
	}

	public String getRecipe_name() {
		return recipe_name;
	}

	public void setRecipe_name(String recipe_name) {
		this.recipe_name = recipe_name;
	}

	public String getLiquor1_name() {
		return liquor1_name;
	}

	public void setLiquor1_name(String liquor1_name) {
		this.liquor1_name = liquor1_name;
	}

	public String getLiquor2_name() {
		return liquor2_name;
	}

	public void setLiquor2_name(String liquor2_name) {
		this.liquor2_name = liquor2_name;
	}

	public String getMixer_name() {
		return mixer_name;
	}

	public void setMixer_name(String mixer_name) {
		this.mixer_name = mixer_name;
	}

	public String getSweetener_name() {
		return sweetener_name;
	}

	public void setSweetener_name(String sweetener_name) {
		this.sweetener_name = sweetener_name;
	}

	public String getGarnish_name() {
		return garnish_name;
	}

	public void setGarnish_name(String garnish_name) {
		this.garnish_name = garnish_name;
	}
	
	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}
	
	@Override
	public String toString() {
		return "InsertRecipeTemplate [name = " + recipe_name + 
								", liquor1 = " + liquor1_name + 
								", liquor2_name = " + liquor2_name +
								", mixer_name = " + mixer_name +
								", sweetener_name = " + sweetener_name +
								", garnish_name = " + garnish_name + "]";
	}

}
