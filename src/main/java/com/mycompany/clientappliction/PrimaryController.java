package com.mycompany.clientappliction;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class PrimaryController implements Initializable {

    @FXML
    private TableView<Shares> stocksTableView;
    @FXML
    private TableColumn<Shares, String> colStockName;
    @FXML
    private TableColumn<Shares, String> colSymbol;
    @FXML
    private TextField stockName;
    @FXML
    private Button searchButton;
    @FXML
    private ChoiceBox<String> selectCurrencyBox;

    private ObservableList<Shares> data;
    private ArrayList<Shares> al;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] currencyArray = {"INR", "GBP", "USD", "EUR"};
        selectCurrencyBox.getItems().addAll(currencyArray);

        File file = new File("C:\\Users\\Lenovo\\OneDrive\\Desktop\\SOCT Coursework\\shareInfo(1).json");
        String jsonString = "";
        al = new ArrayList<>();
        //if file exists extract data from it
        if (file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                String line = "";
                while ((line = br.readLine()) != null) {
                    jsonString = jsonString + line;
                }

                //create a jsonobject of the string acquired
                JSONObject jobj = new JSONObject(jsonString);
                JSONArray arrJson = jobj.getJSONArray("Shares");

                //iterate through all the objects of the Shares json array and add each object to al list;
                for (int i = 0; i < arrJson.length(); ++i) {
                    JSONObject current = arrJson.getJSONObject(i);
                    String symbol = current.getString("companySymbol");
                    String name = current.getString("companyName");
                    Shares share = new Shares(name, symbol);
                    al.add(share);
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Some error occured while finding this file.");
        }

        //add data into observable list
        data = FXCollections.observableArrayList(al);

        //associate data with columns
        colSymbol.setCellValueFactory(new PropertyValueFactory<Shares, String>("companySymbol"));
        colStockName.setCellValueFactory(new PropertyValueFactory<Shares, String>("companyName"));

        stocksTableView.setItems(data);
    }

    @FXML
    private void getStockInfo(ActionEvent e) throws IOException {
        String companySymbol = stockName.getText();
        String currency = selectCurrencyBox.getValue();

        Stage stage = new Stage();
        FXMLLoader f = new FXMLLoader(getClass().getResource("secondary.fxml"));
        stage.setTitle("STOCK INFORMATION");
        Parent root = f.load();
        SecondaryController sc = f.getController();
        sc.setCompanySymbol(companySymbol);
        sc.setCurrency(currency);
        sc.setStockInfo();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
