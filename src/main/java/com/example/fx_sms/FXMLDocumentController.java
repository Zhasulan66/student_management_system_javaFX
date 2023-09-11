package com.example.fx_sms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FXMLDocumentController {

    @FXML
    private AnchorPane main_form;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Button close;

    //    DATABASE staff
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void loginAdmin(){


        Alert alert;

        String sql = "SELECT * FROM admin where username=? and password=?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username.getText());
            pst.setString(2, password.getText());

            result = pst.executeQuery();

            if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
                if (result.next()) {
                    getData.username = username.getText();
                    System.out.println(getData.username);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();


                    // TO HIDE THE LOGIN FORM
                    loginBtn.getScene().getWindow().hide();
                    //LINK YOUR DASHBOARD
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Parent root = loader.load();

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }
                else{
                    // THEN ERROR MESSAGE WILL APPEAR
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }



        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        System.exit(0);
    }

    public void getUsers(){

        String sql = "SELECT * FROM admin";

        try (Connection con = DBManager.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            result = pst.executeQuery();
            List<String> names = new ArrayList<>();
            while (result.next()) {
                String userName = result.getString("username");
                names.add(userName);
            }

            for(String name: names){
                System.out.println("User: " + name);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String name, String pass){

        // query
        String query = "INSERT INTO admin(username, password) VALUES(?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, pass);
            pst.executeUpdate();
            System.out.println("Sucessfully created.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
