package com.example.rofor.NeventProj;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpReqHandler {

    public HttpReqHandler(){}

    public String getHTTPData(String requestURL){
        URL url;
        String response = "";
        try{
            url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            con.connect();
            //int responseCode = con.getResponseCode();
            //if(responseCode == HttpURLConnection.HTTP_OK){
                String txt;
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while((txt = br.readLine()) != null){
                    response+=txt;
                }
           // }


        }
        catch (ProtocolException e){
            e.printStackTrace();
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return response;
    }
}

