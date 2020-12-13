package com.gerardoperez.drinkology.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerardoperez.drinkology.model.User;
import com.gerardoperez.drinkology.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Login endpoint that works currently with an un-hashed password.
 * Expects a post request with a body {"username": "person's name" , "password": "thePassword"}
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService   = new UserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader br = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();
        // userTryingToLogin = username and password from body request
        User userTryingToLogin = objectMapper.readValue(jsonString , User.class);
        // completedUser = the user found in the database with name that matches userTryingToLogin.username
        User completeUser = userService.getAUserByName(userTryingToLogin.getUsername());
        if (userService.login(completeUser , userTryingToLogin)) {
            HttpSession session = req.getSession();
            session.setAttribute("role_id" , completeUser.getRole_id());
            resp.getWriter().append("Successful login for user: ").append(completeUser.getUsername()).append(" id is: " + completeUser.getId());
        } else {
            resp.getWriter().append("The username of password does not match...");
        }
        resp.setContentType("application/json");
    }
}
