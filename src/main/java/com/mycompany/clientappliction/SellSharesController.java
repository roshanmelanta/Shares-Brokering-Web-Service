/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clientappliction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class SellSharesController implements Initializable {

    @FXML
    private TextField sharesQuantity;
    @FXML
    private Button confirmButton;

    private String companyName;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @FXML
    public void onClickConfirm(ActionEvent e) throws IOException {
        String q = sharesQuantity.getText();
        System.out.println(q);
        int quantity = Integer.parseInt(q); // Integer : input quantity

        File file = new File("C:\\Users\\Lenovo\\OneDrive\\Desktop\\SOCT Coursework\\shareInfo(1).json");
        if (file.exists()) {
            String json = "";
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                while ((line = br.readLine()) != null) {
                    json = json + line;
                }
                //create JSONObject of the string
                JSONObject jobj = new JSONObject(json);
                JSONArray jarr = jobj.getJSONArray("Shares");

                //iterate through all the objects
                int length = jarr.length();
                for (int i = 0; i < length; ++i) {
                    JSONObject current = jarr.getJSONObject(i);
                    String name = current.getString("companyName");
                    int num = current.getInt("numOfShares");
                    System.out.println(num);
                    //if name is same as companyName get the companySymbol
                    if (companyName.equalsIgnoreCase(name)) {
                        current.put("numOfShares", (num + quantity));
                        break;
                    }
                }
                //write the updated array back to the local currency rates file
                JSONObject rootJobj = new JSONObject();
                rootJobj.put("Shares", jarr);

                FileWriter filew = new FileWriter(file);
                filew.write(rootJobj.toString());
                filew.flush();
                filew.close();

                confirmButton.getScene().getWindow().hide();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(SellSharesController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SellSharesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
