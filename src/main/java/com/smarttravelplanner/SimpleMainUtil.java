package com.smarttravelplanner;

import java.util.Scanner;

public class SimpleMainUtil {
    public static void main(String[] args) {
        System.out.println("==============================");
        System.out.println("    SMART TRAVEL PLANNER");
        System.out.println("==============================");
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.println("Welcome, " + name + "!");
        System.out.println("This is a simplified version of the Smart Travel Planner.");
        System.out.println("In a full implementation, you would be able to plan trips, estimate costs, and more.");
        
        scanner.close();
    }
}