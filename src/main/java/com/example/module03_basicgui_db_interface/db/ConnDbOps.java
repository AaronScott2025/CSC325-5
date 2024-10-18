/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.module03_basicgui_db_interface.db;

import com.example.module03_basicgui_db_interface.Person;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author MoaathAlrajab
 */
public class ConnDbOps {
    final String MYSQL_SERVER_URL = "jdbc:mysql://scota311server.mysql.database.azure.com/";
    final String DB_URL = "jdbc:mysql://scota311server.mysql.database.azure.com/DBnew";

    final String USERNAME = "scotadmin";
    final String PASSWORD = "Farmingdale14@";
    
    public  boolean connectToDatabase() {
        boolean hasRegistredUsers = false;


        //Class.forName("com.mysql.jdbc.Driver");
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            //First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS DBnew");
            statement.close();
            conn.close();

            //Second, connect to the database and create the table "users" if cot created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            //    public Person(Integer id, String firstName, String lastName, String dept, String major,String img) {
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "firstname VARCHAR(200) NOT NULL,"
                    + "lastname VARCHAR(200) NOT NULL,"
                    + "dept VARCHAR(200),"
                    + "major VARCHAR(200),"
                    + "img VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            //check if we have users in the table users
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }

            statement.close();
            conn.close();
            System.out.println("Database Ready!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasRegistredUsers;
    }

    public  void queryAndEditUser(Integer id, String firstName, String lastName, String dept, String major,String img) {


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE users SET firstname = ?, lastname = ?, dept = ?, major = ?, img = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, dept);
            preparedStatement.setString(4, major);
            preparedStatement.setString(5, img);
            preparedStatement.setInt(6, id);

            int updated = preparedStatement.executeUpdate();
            if(updated == 1) {
                System.out.println("Updated user of ID: " + id);
            }

            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void deleteUser(Integer id) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            int updated = preparedStatement.executeUpdate();
            if(updated == 1) {
                System.out.println("DELETED user of ID: " + id);
            }

            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Person> listAllUsers(ObservableList<Person> data) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String dept = resultSet.getString("dept");
                String major = resultSet.getString("major");
                String image = resultSet.getString("img");
                Person p = new Person(id,firstname,lastname,dept,major,image);
                data.add(p);
            }
            preparedStatement.close();
            conn.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void insertUser(Integer id, String firstName, String lastName, String dept, String major,String img) {


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (id, firstname, lastname, dept, major,img) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, dept);
            preparedStatement.setString(5, major);
            preparedStatement.setString(6, img);

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new user was inserted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int totalNum() {
        int number = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT COUNT(*) FROM users ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                number = resultSet.getInt(1);
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("ERROR RIGHT HERE =============================");
            e.printStackTrace();
        }
        return number;
    }

    
}
