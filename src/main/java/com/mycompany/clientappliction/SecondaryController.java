/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientappliction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class SecondaryController implements Initializable {

    @FXML
    private Label lbName;
    @FXML
    private Label lbSymbol;
    @FXML
    private Label lbPrice;
    @FXML
    private Label lbSharesAvailable;
    @FXML
    private Button buyShares;
    @FXML
    private Button sellShares;

    private String companySymbol;
    private String currency;
    private String numOfShares;
    private String companyName;

    public void setCompanySymbol(String companySymbol) {
        this.companySymbol = companySymbol;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Initializes the controller class.
     */
    public void setStockInfo() {
        String wUrl = "http://localhost:8080/SharesBrokeringService/webresources/sharesbrokering?";

        try {
            String urlString = wUrl + "comname=" + companySymbol + "&curr=" + currency;
            // Performs HTTP GET request to url
            URL url = new URL(urlString);
            HttpURLConnection connURL = (HttpURLConnection) url.openConnection();
            connURL.setRequestMethod("GET");
            System.out.println("\nREST API call: " + connURL.getRequestProperties().toString() + "\n");
            connURL.connect();

            BufferedReader ins = new BufferedReader(new InputStreamReader(connURL.getInputStream()));
            String jsonText = "";
            String line = "";
            
            //Loop reads each line from the BufferedReader and adds it to jsonText
            while ((line = ins.readLine()) != null) {
                jsonText = jsonText + line;
                System.out.println(jsonText);
            }

            ins.close();
            connURL.disconnect();

            JSONObject jObj = new JSONObject(jsonText);

            lbName.setText(jObj.getString("companyName"));
            companyName = jObj.getString("companyName");

            lbSymbol.setText(jObj.getString("companySymbol"));
            
            double price = jObj.getDouble("price");
            String priceText = Double.toString(price);
            lbPrice.setText(priceText);

            int sharesAvailable = jObj.getInt("numOfShares");
            String sharesAvailableText = Integer.toString(sharesAvailable);
            lbSharesAvailable.setText(sharesAvailableText);
            numOfShares = sharesAvailableText;

        } catch (MalformedURLException ex) {
            System.out.println("MalformedURLException" + ex);

        } catch (IOException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onClickBuyShares(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader f = new FXMLLoader(getClass().getResource("buyShares.fxml"));
        stage.setTitle("BUY SHARES");
        Parent root = f.load();
        BuySharesController bc = f.getController();
        bc.setNumOfShares(numOfShares);
        bc.setCompanyName(companyName);
        bc.buyShares();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        buyShares.getScene().getWindow().hide();
    }

    @FXML
    public void onClickSellShares(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader f = new FXMLLoader(getClass().getResource("sellShares.fxml"));
        stage.setTitle("SELL SHARES");
        Parent root = f.load();
        SellSharesController sc = f.getController();
        sc.setCompanyName(companyName);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        sellShares.getScene().getWindow().hide();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
