package com.example.module03_basicgui_db_interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField first_name;

    @FXML
    private TextField last_name;

    @FXML
    private Button loginbtn;

    @FXML
    void login(ActionEvent event) {
        Stage stage = DB_Application.getPrimaryStage(); //Passed to controller to avoid error occuring
        DB_Application db = new DB_Application();
        db.changeScene("db_interface_gui.fxml");
    }

}


