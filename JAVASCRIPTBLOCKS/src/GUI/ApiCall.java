package GUI;

import AnoParser.MethodInfo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiCall {
    private String BaseUrl;

    @MethodInfo(name = "ApiCall(String baseUrl)", date = "05/07/18", arguments = "1: String baseUrl, URL of the API", comments = "Constructor of The APICall needs the base url", returnValue="None" ,revision = 1)
    public ApiCall(String baseUrl) {
        BaseUrl = baseUrl;
    }

    @MethodInfo(name = "ApiGetResponse(String call)", date = "05/07/18", arguments = "1: String call, the arguments for the API call", comments = "Api call with get arguments", returnValue="String, the JSON result" ,revision = 1)
    public String ApiGetResponse(String call) throws IOException {

        URL obj = new URL(this.BaseUrl+call);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + this.BaseUrl+call);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    @MethodInfo(name = "ApiPostResponse(String call, String urlParameters)", date = "05/07/18", arguments = "1: String call, the arguments for the API call, 2: String urlParameters, the post parameters for the Call", comments = "Api call with post arguments", returnValue="String, the JSON result" ,revision = 1)
    public String ApiPostResponse(String call, String urlParameters, String[][] headers) throws MalformedURLException, IOException {
        URL obj = new URL(this.BaseUrl+call);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");

        for(String[] head : headers){
            con.setRequestProperty(head[0], head[1]);
        }

        // Send post request
        con.setDoOutput(true);
        OutputStream  wr = con.getOutputStream();
        wr.write(urlParameters.getBytes("UTF-8"));
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();

        return response.toString();
    }
}
