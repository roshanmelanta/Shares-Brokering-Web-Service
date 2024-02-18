/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package MyRSW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Lenovo
 */
@Path("sharesbrokering")
public class SharesBrokeringService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SharesBrokeringService
     */
    public SharesBrokeringService() {
    }


    /**
     * Retrieves representation of an instance of MyRSW.SharesBrokeringService
     *
     * @return an instance of java.lang.String
     */
    //returns the rate of currency against USD
    @GET
    @Produces(MediaType.TEXT_PLAIN)

    public String getJson(@QueryParam("comname") String company_name, @QueryParam("curr") String currency) throws FileNotFoundException {
        //get the latest exchange rate of the specified currency
        double exchange_rate = ExchangeRate.getExchangeRate(currency);
        //get the latest stock price of the specified company name
        double price = StockPrice.getStockPrice(company_name);

        System.out.println(price);
        JSONObject ans = null;

        File file = new File("C:\\Users\\Lenovo\\OneDrive\\Desktop\\SOCT Coursework\\shareInfo(1).json");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            //This block of code reads each line of the file
            String line = "";
            String jsonText = "";
            while ((line = reader.readLine()) != null) {
                jsonText = jsonText + line;
            }

            JSONObject x = new JSONObject(jsonText);
            //This line retrieves an array of JSON objects from the Shares field of the x JSONObject. 
            JSONArray shares = x.getJSONArray("Shares");
            for (int i = 0; i < shares.length(); ++i) {
                JSONObject r = shares.getJSONObject(i);
                String name = r.getString("companySymbol");
                if (company_name.equalsIgnoreCase(name)) {
                    double p = price * exchange_rate;
                    System.out.println(p);
                    r.put("price", p);
                    try (FileWriter fileWriter = new FileWriter(file)) {
                        fileWriter.write(x.toString());
                        fileWriter.flush(); //ensures data is written to file
                    }
                    ans = r;
                    System.out.println(ans.toString());
                    break;
                }

            }

        } catch (IOException ex) {

        }
        return ans.toString();
    }

    /**
     * PUT method for updating or creating an instance of SharesBrokeringService
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
}
