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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Roshan
 */
public class ExchangeRate {

    //get the exchange rate from external API
    public static double getExchangeRate(String currency) {
        double result = 0;
        String wurl = "https://api.apilayer.com/exchangerates_data/convert?";
        final String to = currency;
        final String amount = "1";
        final String apikey = "d90YcCaKF60YMLcbGIigA0hanJQsapeK";
        try {
            //construct the REST service query string
            String urlString = wurl + "to=" + to + "&from=USD" + "&amount=" + amount
                    + "&apikey=" + apikey;

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

            while ((line = ins.readLine()) != null) {
                jsonText = jsonText + line;
            }

            ins.close();
            connURL.disconnect();

            JSONObject jObj = new JSONObject(jsonText);
            System.out.println(jObj.toString());
            result = jObj.getDouble("result");
            System.out.println(result);

        } catch (MalformedURLException me) {
            System.out.println("MalformedURLException: " + me);
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
        return result;
    }
}
