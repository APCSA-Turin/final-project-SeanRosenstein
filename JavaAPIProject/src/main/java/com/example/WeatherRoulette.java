package com.example;

import java.util.Scanner;

public class WeatherRoulette {    //game logic class

    private static final String[] CITIES = {
            "New York", "Paris", "London", "Tokyo", "Rome",
            "Dubai", "Sydney", "Hong Kong", "Los Angeles", "Barcelona",
            "Berlin", "Istanbul", "Shanghai", "Rio", "Singapore",              //array filled with 30 of the world's most famous cities
            "Moscow", "Mexico City", "Madrid", "Toronto", "Bangkok",
            "Amsterdam", "Vienna", "Buenos Aires", "Seoul", "Mumbai",
            "Beijing", "Prague", "Chicago", "Cape Town", "Athens"
    };

    public static void play() {       //play method, all game logic is here

        Scanner scanner = new Scanner(System.in);
        int spareLives = (int) (Math.random() * 6); //randomly sets spare lives from 0-5
        int score = 0;
        int numTemps = 0;               //setting up variables for later use
        double sumTemps = 0;

        System.out.println("\n🌎 Welcome to Weather Roulette! 🌎");        //intro to game
        System.out.println("Ready to test your weather intuition? In this game, you'll see the weather for a city — but beware!");
        System.out.println("Some cities display actual weather reports, while others are replaced with data from a randomly selected city!");
        System.out.println("You'll have to guess whether the given weather is REAL or FAKE!");
        System.out.println("Get 10 correct guesses, and you survive! Guess wrong too many times, and... well, let's just say you won't be seeing tomorrow's forecast. 😈");
        System.out.println("Let's begin!\n");

        while ((spareLives >= 0) && (score != 10)) {               //while loop that ends either when a score of 10 is achieved or all spare lives are lost
            int realIndex;
            realIndex = (int) (Math.random() * CITIES.length);
            String realCity = CITIES[realIndex];
            double realTemp = WeatherFetcher.getTemperature(realCity);              //sets up the displayed temp, conditions, and wind speed
            String realConditions = WeatherFetcher.getConditions(realCity);
            int realWindSpeed = WeatherFetcher.getWindSpeed(realCity);

            int fakeIndex = realIndex;
            while (fakeIndex == realIndex) {
                fakeIndex = (int) (Math.random() * CITIES.length);
            }                                                              //picks out a fake city that the program might swap the real city with
            String fakeCity = CITIES[fakeIndex];

            boolean showReal = Math.random() < 0.5;            //determines if the program will display the real city or the fake city

            String shownCity;
            double shownTemp;
            String shownConditions;
            int shownWindSpeed;

            if (showReal) {
                shownCity = realCity;

            } else {
                shownCity = fakeCity;
            }                                     //displays either the real or fake city's name, and all of the real city's stats
            shownTemp = realTemp;
            shownConditions = realConditions;
            shownWindSpeed = realWindSpeed;

            numTemps++;
            sumTemps+=shownTemp;

            System.out.println("City: " + shownCity);
            System.out.println("Temperature: " + shownTemp + "°F");
            System.out.println("Conditions: " + shownConditions);                  //prints out either the real or fake city's name, and all of the real city's stats
            System.out.println("Wind Speed: " + shownWindSpeed + " mph");
            System.out.print("🤔 Is this real? (real/fake): ");                  //real?? or cake???

            String input = scanner.nextLine().trim().toLowerCase();
            boolean guess = false;                                   //defaults boolean guess to false, otherwise if the user inputs "real" it will set to true
            if (input.equals("real")) {
                guess = true;
            }

            if (guess == showReal) {                        //if guess and showReal correspond eg. user says real and showReal is true or user says fake and showReal is false
                score++;                                    //then it will add 1 to the score and display "✅ Correct!"
                System.out.println("\n✅ Correct!");
                if (score == 10) {
                    System.out.println("\n🎉🎉🎉 Congrats! You get to live!.... For now. 😈");       //displays a congrats message if user wins (achieves score of 10)
                    double avgTemp = sumTemps/numTemps;                                                  
                    System.out.println("Average temperature: " + avgTemp + "°F");                        //displays average temp over the course of the game
                } else {
                    if (!showReal) {
                        System.out.println("Real City: " + realCity);            //if user doesn't win yet but gets question correct, prints the realCity if it was fake originally
                    }
                    System.out.println("😁 Score: " + score + "/10 😁\n");      //prints score
                }
            } else {

                
                if (!showReal) {                               // REDEMPTION (if user guessed real on a fake city)

                    System.out.println("\n❌ Wrong!");

                    int randomIndex = (int) (Math.random() * CITIES.length);
                    while ((randomIndex == realIndex) || (randomIndex == fakeIndex)) {  //picks a new random city
                        randomIndex = (int) (Math.random() * CITIES.length);
                    }
                    boolean isChoice1 = Math.random() < 0.5;
                    String choice1;
                    String choice2;
                    if (isChoice1) {
                        choice1 = realCity;                          //randomly picks if the real city will be the first option or the second
                        choice2 = CITIES[randomIndex];
                    } else {
                        choice1 = CITIES[randomIndex];
                        choice2 = realCity;
                    }
                    System.out.println(
                            "😰 Redemption! The real city is either " + choice1 + " or " + choice2 + ". Which is it? 😰");
                    String redemption = scanner.nextLine().toLowerCase();
                    if (redemption.equals(realCity.toLowerCase())) {
                        System.out.println("\n✅ Close one..");
                        score++;                                                     //if user guesses the correct city on redemption they still get no penalty and score++
                        System.out.println("😁 Score: " + score + "/10 😁\n");

                    } else {
                        spareLives--;                                          //if user gets redemption wrong spareLives--
                        if (spareLives == -1) {
                            System.out.println("\n💀💥💥💥🔫");       //if user is out of spare lives and lost the redemption they lose
                            System.out.println(
                                    "Welp, you lose. Now I have to clean all this up..... Mm, fresh brain, tasty!");
                            break;
                        } else {
                            System.out.println("\n❌ Wrong!");                       //displays this if user gets redemption wrong
                            System.out.println("🔫 **Click** 🔫\nSafe for now!\n");
                        }
                    }
                }

                else {
                    spareLives--;                       //no redemption if user guessed fake on a real city and lives--
                    if (spareLives == -1) {
                        System.out.println("\n💀💥💥💥🔫");       //if user is out of spare lives and answered fake on a real city they lose
                        System.out.println(
                                "Welp, you lose. Now I have to clean all this up..... Mm, fresh brain, tasty!");
                        break;
                    } else {
                        System.out.println("\n❌ Wrong!");                       //displays this if user guessed fake on a real city
                        System.out.println("🔫 **Click** 🔫\nSafe for now!\n");
                    }
                }
            }

        }
        scanner.close();
    }
}