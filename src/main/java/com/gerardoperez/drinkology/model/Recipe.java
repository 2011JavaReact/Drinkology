package com.gerardoperez.drinkology.model;

public class Recipe {

    private int id;
    private String name;
    private Ingredient liquor1;
    private Ingredient liquor2;
    private Ingredient mixer;
    private Ingredient sweetener;
    private Ingredient garnish;

    public Recipe(int id, String name, Ingredient liquor1, Ingredient liquor2, Ingredient mixer, Ingredient sweetener, Ingredient garnish) {
        this.id = id;
        this.name = name;
        this.liquor1 = liquor1;
        this.liquor2 = liquor2;
        this.mixer = mixer;
        this.sweetener = sweetener;
        this.garnish = garnish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient getLiquor1() {
        return liquor1;
    }

    public void setLiquor1(Ingredient liquor1) {
        this.liquor1 = liquor1;
    }

    public Ingredient getLiquor2() {
        return liquor2;
    }

    public void setLiquor2(Ingredient liquor2) {
        this.liquor2 = liquor2;
    }

    public Ingredient getMixer() {
        return mixer;
    }

    public void setMixer(Ingredient mixer) {
        this.mixer = mixer;
    }

    public Ingredient getSweetener() {
        return sweetener;
    }

    public void setSweetener(Ingredient sweetener) {
        this.sweetener = sweetener;
    }

    public Ingredient getGarnish() {
        return garnish;
    }

    public void setGarnish(Ingredient garnish) {
        this.garnish = garnish;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", liquor1=" + liquor1 +
                ", liquor2=" + liquor2 +
                ", mixer=" + mixer +
                ", sweetener=" + sweetener +
                ", garnish=" + garnish +
                '}';
    }
}
