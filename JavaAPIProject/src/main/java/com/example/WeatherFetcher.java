package com.example;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherFetcher {    //utility class
    private static final String API_KEY = "b41181db2f7b5bbc8526b022ee838500";   //my api key

    public static double getTemperature(String city) {     //gets temp (in °F) of a city
        try {
            String urlString;
            if (city.indexOf(" ") == -1) {
                urlString = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        city + "&appid=" + API_KEY + "&units=imperial";                     //stores the url I will be connecting to in urlString
            } else {                                                                        //if a city is two words long, I made sure the url still works using the if else
                urlString = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        city.substring(0, city.indexOf(" ")) + "%20" + city.substring(city.indexOf(" ") + 1) + "&appid="
                        + API_KEY + "&units=imperial";
            }
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();           //establishes the url connection
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder jsonText = new StringBuilder();                              //uses StringBuilder to extract all text from JSON
            while (scanner.hasNext()) {
                jsonText.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject json = new JSONObject(jsonText.toString());
            return json.getJSONObject("main").getDouble("temp");            //filters the JSON based on the criteria I need

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());          //error handling
            return -1;
        }
    }

    public static String getConditions(String city) {       //gets conditions eg. scattered clouds or rain
        try {                                               //works substantially the same as previous method
            String urlString;
            if (city.indexOf(" ") == -1) {
                urlString = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        city + "&appid=" + API_KEY + "&units=imperial";
            } else {
                urlString = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        city.substring(0, city.indexOf(" ")) + "%20" +
                        city.substring(city.indexOf(" ") + 1) + "&appid=" + API_KEY + "&units=imperial";
            }

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder jsonText = new StringBuilder();
            while (scanner.hasNext()) {
                jsonText.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject json = new JSONObject(jsonText.toString());
            JSONArray weatherArray = json.getJSONArray("weather");
            return weatherArray.getJSONObject(0).getString("description");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "Unknown";
        }
    }

    public static int getWindSpeed(String city) {         //gets wind speed in mph
        try {                                             //works substantially the same as previous methods
            String urlString;
            if (city.indexOf(" ") == -1) {
                urlString = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        city + "&appid=" + API_KEY + "&units=imperial";
            } else {
                urlString = "https://api.openweathermap.org/data/2.5/weather?q=" +
                        city.substring(0, city.indexOf(" ")) + "%20" +
                        city.substring(city.indexOf(" ") + 1) + "&appid=" + API_KEY + "&units=imperial";
            }

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder jsonText = new StringBuilder();
            while (scanner.hasNext()) {
                jsonText.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject json = new JSONObject(jsonText.toString());
            return json.getJSONObject("wind").getInt("speed");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }

}