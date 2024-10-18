package com.example.module03_basicgui_db_interface;

import com.example.module03_basicgui_db_interface.db.ConnDbOps;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class DB_GUI_Controller implements Initializable {

    private ObservableList<Person> data =
            FXCollections.observableArrayList(
            );


    @FXML
    TextField first_name, last_name, department, major, image, idtxt;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, Integer> tv_id;
    @FXML
    private TableColumn<Person, String> tv_fn, tv_ln, tv_dept, tv_major;
    @FXML
    private AnchorPane panes;

    @FXML
    ImageView img_view;

    private static ConnDbOps cdbop;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cdbop = new ConnDbOps();
        data = cdbop.listAllUsers(data); //data filled with Database entries

        tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tv_fn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tv_ln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tv_dept.setCellValueFactory(new PropertyValueFactory<>("dept"));
        tv_major.setCellValueFactory(new PropertyValueFactory<>("major"));


        tv.setItems(data); //Put into table
    }


    @FXML
    protected void addNewRecord() {
        cdbop = new ConnDbOps();
        System.out.println("Here " + cdbop.totalNum()); //Total users
        int id = (cdbop.totalNum());
        System.out.println(id);
        if(id == 0) { //Id starts with 100
            id = 101; //First user
        } else {
            id = id + 100; //Every other user
        }
        cdbop.insertUser(id,first_name.getText(), last_name.getText(), department.getText(), major.getText(),image.getText()); //Insert based on text
    }

    @FXML
    protected void clearForm() {
        first_name.clear();
        last_name.setText("");
        department.setText("");
        major.setText("");
    }

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }


    @FXML
    protected void editRecord() {
        cdbop = new ConnDbOps();
        Person p= tv.getSelectionModel().getSelectedItem();
        int id = tv.getSelectionModel().getSelectedItem().getId();
        cdbop.queryAndEditUser(Integer.valueOf(idtxt.getText()),first_name.getText(), last_name.getText(), department.getText(), major.getText(),image.getText());

    }

    @FXML
    protected void deleteRecord() {
        cdbop = new ConnDbOps();
        cdbop.deleteUser(Integer.parseInt(idtxt.getText()));
    }



    @FXML
    protected void showImage() {
        File file= (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
        if(file!=null){
            img_view.setImage(new Image(file.toURI().toString()));

        }
    }





    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        Person p= tv.getSelectionModel().getSelectedItem();
        first_name.setText(p.getFirstName());
        last_name.setText(p.getLastName());
        department.setText(p.getDept());
        major.setText(p.getMajor());
        image.setText(p.getImg());
        idtxt.setText(String.valueOf(p.getId()));


    }
    @FXML
    protected void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case X:
                if(event.isControlDown()) { //Ctrl + X
                    System.exit(0);
                }
                break;
            case E:
                if(event.isControlDown()) { //Ctrl + E
                    editRecord();
                }
                break;
            case D:
                if(event.isControlDown()) { //Ctrl + D
                    deleteRecord();
                }
                break;
            case A:
                if(event.isControlDown()) { //Ctrl + A
                    System.out.println("Detected");
                    addNewRecord();
                }
                break;
        }
    }
}