/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyRSW;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Lenovo
 */
public class StockPrice {
    public static double getStockPrice(String company_symbol) {
        double result = 1;
        final String company = company_symbol;
        
        try {
            //construct the REST service query string
            //String urlString = wurl + "access_key="+ apikey + "&symbols=" + company;
            String urlString = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=" + company;
            
            //associate the query string with HTTP Get connection stream
            URL url = new URL(urlString);
            HttpURLConnection connURL = (HttpURLConnection) url.openConnection();
            connURL.setRequestMethod("GET");
            
            //Call the REST API using HTTP get 
            System.out.println("\nREST API call: " + connURL.getRequestProperties().toString() + "\n");
            connURL.connect();
            
            //build the JSON response string
            BufferedReader ins = new BufferedReader(new InputStreamReader(connURL.getInputStream()));
            String jsonText = "";
            String line = "";
         

            while ((line = ins.readLine()) != null)  {
               jsonText = jsonText + line;
            }
            
           ins.close();
           connURL.disconnect();
           
           JSONObject jObj = new JSONObject(jsonText);
           JSONObject arr = jObj.getJSONObject("quoteResponse");
           JSONArray a = arr.getJSONArray("result");
           JSONObject o = a.getJSONObject(0);
           System.out.println(o.getDouble("regularMarketPrice"));
           result =  o.getDouble("regularMarketPrice");
           

        } 
        catch (MalformedURLException me) {
            System.out.println("MalformedURLException: " + me);
        } 
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
        return result;
    }
}
