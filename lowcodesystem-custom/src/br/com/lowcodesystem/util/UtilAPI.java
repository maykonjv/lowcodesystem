/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 *
 * @author Maykon
 */
public class UtilAPI {

    // HTTP GET request
    public static String get(String _url) throws Exception {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        InputStream _is;
        if (con.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            _is = con.getInputStream();
        } else {
            /* error from server */
            _is = con.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(_is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    // HTTP POST request
    public static String post(Map<String, String> header, String bodyJson, String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json");
        for (String key : header.keySet()) {
            con.setRequestProperty(key, header.get(key));
        }

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(bodyJson);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + bodyJson  );
        System.out.println("Response Code : " + responseCode);

        InputStream _is;
        if (con.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            _is = con.getInputStream();
        } else {
            /* error from server */
            _is = con.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(_is));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

}
