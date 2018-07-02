package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AdAstraApi {

    private String BaseUrl;
    private String ResponderUrl;
    private String responderParameters;

    public AdAstraApi() {

        this.BaseUrl = "http://127.0.0.1:8080/";
        this.ResponderUrl = "block/full?instruction=0";
        this.responderParameters = "";

    }

    public void ApiResponder() throws MalformedURLException, IOException {

        URL obj = new URL(this.BaseUrl+ResponderUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + this.BaseUrl+ResponderUrl);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }

    public static void main(String[] args) throws Exception {
        AdAstraApi api = new AdAstraApi();
        api.ApiResponder();
    }

}