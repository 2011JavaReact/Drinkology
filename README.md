#Drinkology
**Drinkology** is an API created to allow you to keep a log of your favorite cocktail recipes, and it even allows you to save your own creations ! 

##What does it do?
- Shows your saved cocktail recipes.
- Allows search of cocktail recipe by index. 
- Allows recipe manipulation (Create, Update, Delete).

##Technologies Used
- Java Version 1.8
- Java Servlet API Version 3.1
- Apache Tomcat Server Version 9.0.40
- PostgreSQL Database Version 13
- Maven
- Jackson
- Postman

### GET all recipes: 
http://localhost:8080/Drinkology/recipes/

### GET recipes by liquor:
Ex: http://localhost:8080/Drinkology/recipes/Tequila

### GET recipe by ID:
Ex: http://localhost:8080/Drinkology/recipe/1

### POST Request to make new recipe
Requirements:
- That all fields are full.
- All ingredients exist in the database.
- Data must be in JSON format.

Use the following data to test POST request:
{
	"recipe_name":"Pina Colada",
	"liquor1_name":"Rum",
	"liquor2_name":"(none)",
	"mixer_name":"Pineapple Juice",
	"sweetener_name":"Coconut Cream",
	"garnish_name":"Pineapple Slice"
}

### PUT Update an entry with new information
Requirements:
- That all fields are full.
- All ingredients exist in the database.
- Data must be in JSON format.

Use the following data to test PUT request:
{
	"recipe_name":"Maximum Margarita",
	"liquor1_name":"Tequila",
	"liquor2_name":"Vodka",
	"mixer_name":"Orange Juice",
	"sweetener_name":"Sugar Cube",
	"garnish_name":"Maraschino Cherry"
}

### DELETE recipe by Id
Ex: http://localhost:8080/Drinkology/recipes/1 