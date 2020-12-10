import com.gerardoperez.drinkology.model.User;
import com.gerardoperez.drinkology.service.RecipeService;
import com.gerardoperez.drinkology.service.UserService;
import com.gerardoperez.drinkology.util.JDBCUtility;
import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Seeder {

    private static UserService us   = new UserService();
    public static void main(String[] args) {
        createAllTables();

        RecipeService rs = new RecipeService();
        Faker faker      = new Faker();

        for (int i = 0; i < 10; i++) {
            String name     = faker.name().fullName();
            String email    = (name + "@yahoo.com").replaceAll("\\s+", "");
            String password = "secret";
            String  role    = "user";
            int role_id     = 2;
            User user       = new User(i , name, email, password, role_id, role);
            us.insertUser(user);
        }
    }

    private static void createAllTables() {
        dropTables();
        createLiquourTable();
        insertLiquors();
        createMixerTable();
        insertMixers();
        createSweetenerTable();
        insertSweeteners();
        createGarnishTable();
        insertGarnishes();
        createRolesTable();
        insertRoles();
        createUserTable();
        User admin = new User(1 , "gera" , "gera@gera.com" , "secret" , 1 , "admin");
        us.insertUser(admin);
        createRecipeTable();
        insertRecipe();
    }

    private static void dropTables() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String dropTablesQuery = "drop table if exists liquor, mixer, sweetener , garnish, roles, users, recipe cascade ";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(dropTablesQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createLiquourTable() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "create table liquor " +
                    "(id SERIAL primary key, " +
                    "liquor_name VARCHAR(255) NOT NULL)";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createMixerTable() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "create table mixer " +
                    "(id SERIAL primary key, " +
                    "mixer_name VARCHAR(255) NOT NULL)";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createSweetenerTable() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "create table sweetener " +
                    "(id SERIAL primary key, " +
                    "sweetener_name VARCHAR(255) NOT NULL)";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createGarnishTable() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "create table garnish " +
                    "(id SERIAL primary key, " +
                    "garnish_name VARCHAR(255) NOT NULL)";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createRolesTable() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "create table roles " +
                    "(id SERIAL primary key, " +
                    "role_name VARCHAR(255) NOT NULL)";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createUserTable() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "CREATE TABLE users " +
                    "(id SERIAL PRIMARY KEY, " +
                    "user_name VARCHAR(255) NOT NULL, " +
                    "user_email VARCHAR(255) NOT NULL, " +
                    "user_password VARCHAR(255) NOT NULL, " +
                    "user_role_id INTEGER REFERENCES roles(id))";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createRecipeTable() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "CREATE TABLE recipe " +
                    "(recipe_id SERIAL PRIMARY KEY, " +
                    "recipe_name VARCHAR(255) NOT NULL, " +
                    "liquor1_id INTEGER REFERENCES liquor(id), " +
                    "liquor2_id INTEGER REFERENCES liquor(id), " +
                    "mixer_id INTEGER REFERENCES mixer(id), " +
                    "sweetener_id INTEGER REFERENCES sweetener(id), " +
                    "garnish_id INTEGER REFERENCES garnish(id), " +
                    "owner_id INTEGER REFERENCES users(id))";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void insertLiquors() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "INSERT INTO liquor " +
                    "(liquor_name) " +
                    "VALUES " +
                    "('(none)'), " +
                    "('Tequila'), " +
                    "('Vodka'), " +
                    "('Whiskey'), " +
                    "('Gin'), " +
                    "('Sweet Vermouth'), " +
                    "('Dry Vermouth'), " +
                    "('Negroni'), " +
                    "('Triple Sec'), " +
                    "('Champagne'), " +
                    "('Bitters'), " +
                    "('Rum')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void insertMixers() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "INSERT INTO mixer " +
                    "(mixer_name) " +
                    "VALUES " +
                    "('(none)'), " +
                    "('Lime Juice'), " +
                    "('Lemon Juice'), " +
                    "('Orange Juice'), " +
                    "('Peach Puree'), " +
                    "('Coke'), " +
                    "('Cranberry Juice'), " +
                    "('Pineapple Juice')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void insertSweeteners() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "INSERT INTO sweetener " +
                    "(sweetener_name) " +
                    "VALUES " +
                    "('(none)'), " +
                    "('Simple Syrup'), " +
                    "('Sugar Cube'), " +
                    "('Cranberry Juice'), " +
                    "('Coconut Cream')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void insertGarnishes() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "INSERT INTO garnish " +
                    "(garnish_name) " +
                    "VALUES " +
                    "('(none)'), " +
                    "('Lime Wheel'), " +
                    "('Orange Wheel'), " +
                    "('Orange Peel'), " +
                    "('Pineapple Slice'), " +
                    "('Peach Slice'), " +
                    "('Maraschino Cherry')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void insertRoles() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "INSERT INTO roles " +
                    "(role_name) " +
                    "VALUES " +
                    "('admin'), " +
                    "('user')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void insertRecipe() {
        try(Connection con = JDBCUtility.getConnection()) {
            con.setAutoCommit(false);
            String createTableQuery = "INSERT INTO recipe " +
                    "(recipe_name, liquor1_id, liquor2_id, mixer_id, sweetener_id, garnish_id, owner_id) " +
                    "VALUES " +
                    "('Margarita', 2, 9, 2, 2, 2, 1), " +
                    "('Old Fashioned', 4, 11, 1, 3, 4, 1), " +
                    "('Cosmopolitan', 3, 1, 2, 4, 2, 1), " +
                    "('Bellini', 10, 1, 5, 2, 6, 1)";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableQuery);
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }






}
