package com.example.javaendassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainController implements Initializable {
    private Database db = new Database();

    private Item item;
    private User user;

    public MainController(User user) {
        this.user = user;
    }

    @FXML
    private Label lblWelcome, lblLendMsg, lblReceiveMsg, lblAddItemError;

    @FXML
    private TextField txtReceiveID, txtLendID, txtLendMember, txtAddItemCode, txtAddItemTitle, txtAddItemAuthor;

    @FXML
    private Group lendGroup, collectionGroup, addItemGroup;

    @FXML
    private TableView<Item> tvItems;

    @FXML
    private TableColumn<Item, Integer> colItemCode;

    @FXML
    private TableColumn<Item, Boolean> colAvailable;

    @FXML
    private TableColumn<Item, String> colTitle;

    @FXML
    private TableColumn<Item, String> colAuthor;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lendGroup.setVisible(true);
        collectionGroup.setVisible(false);
        lblWelcome.setText(String.format("Welcome %s", user.username));

        ObservableList<Item> items = FXCollections.observableList(db.getItems());

        if (!items.isEmpty()) {
            colItemCode.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));
            colAvailable.setCellValueFactory(new PropertyValueFactory<Item, Boolean>("status"));
            colTitle.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<Item, String>("author"));
            //tvItems.setItems(items);
        }
    }



    public void onButtonLendClick(ActionEvent actionEvent) {
        try {
            if (txtLendID.getText().isEmpty() || txtLendMember.getText().isEmpty()) {
                lblLendMsg.setText("Please fill the fields");
                return;
            }

            item = db.getItemByID(Integer.parseInt(txtLendID.getText()));
            user = db.getUserByID(Integer.parseInt(txtLendMember.getText()));

            if (item != null && Objects.equals(item.status, true) && user != null) {
                db.setItemStatusFalse(item);
                item.date = LocalDate.now();
                lblLendMsg.setText(String.format("Item %s has been lend", item.id));
                clearAddItemTextFields();
            } else if (item != null && Objects.equals(item.status, false) && user != null) {
                lblLendMsg.setText(String.format("Item %s is unavailable", item.id));
            } else {
                lblLendMsg.setText("Incorrect Item ID or Member ID");
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void onButtonReceiveClick(ActionEvent actionEvent) {
        try{
            if (Objects.equals(txtReceiveID.getText(), "")){
                lblReceiveMsg.setText("Please enter a item code");
                return;
            }

            item = db.getItemByID(Integer.parseInt(txtReceiveID.getText()));

            if (Objects.equals(item, null)){
                lblReceiveMsg.setText("Item not found");
            }

            if (Objects.equals(item.status, false)){

                if (DAYS.between(item.date, LocalDate.now()) > 21){
                    int days = (int) (DAYS.between(item.date, LocalDate.now()) - 21);
                    lblReceiveMsg.setText(String.format("Item is %s days too late", days));
                    return;
                }

                lblReceiveMsg.setText(String.format("Item %s has been received", item.id));
                clearAddItemTextFields();
                item.date = null;
                item.status = true;
                return;
            }
            lblReceiveMsg.setText(String.format("Item %s is available", item.id));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void onButtonLendTabClick(ActionEvent actionEvent) {
        hideAllGroups();
        lendGroup.setVisible(true);
    }

    public void onButtonCollectionClick(ActionEvent actionEvent) {
        hideAllGroups();
        collectionGroup.setVisible(true);
    }

    public void onButtonMembersClick(ActionEvent actionEvent) {

    }

    public void onButtonAddItemClick(ActionEvent actionEvent) {
        hideAllGroups();
        addItemGroup.setVisible(true);
    }

    public void onButtonEditItemClick(ActionEvent actionEvent) {
    }

    public void onButtonDeleteItemClick(ActionEvent actionEvent) {
    }

    public void onButtonAddItemToTableView(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtAddItemCode.getText());
            boolean status = true;
            String title = txtAddItemTitle.getText();
            String author = txtAddItemAuthor.getText();

            if (Objects.equals(txtAddItemCode.getText(), "") || Objects.equals(title, "") || Objects.equals(author, "")) {
                lblAddItemError.setText("Please fill in all fields");
            }
            try {
                Integer.parseInt(txtAddItemCode.getText());
            } catch (Exception e) {
                lblAddItemError.setText("Please enter a Item Code");
            }

            ArrayList<Item> items = db.getItems();
            Item item = new Item(id, status, title, author);
            items.add(item);

            lblAddItemError.setText("Item Added");
            tvItems.refresh();
            clearAddItemTextFields();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void onButtonCancelItemToTableView(ActionEvent actionEvent) {
        clearAddItemTextFields();
        hideAllGroups();
        collectionGroup.setVisible(true);
    }

    public void hideAllGroups(){
        lendGroup.setVisible(false);
        collectionGroup.setVisible(false);
        addItemGroup.setVisible(false);
    }

    public void clearAddItemTextFields(){
        txtAddItemCode.clear();
        txtAddItemTitle.clear();
        txtAddItemAuthor.clear();
        txtLendID.clear();
        txtLendMember.clear();
        txtReceiveID.clear();
    }
}
