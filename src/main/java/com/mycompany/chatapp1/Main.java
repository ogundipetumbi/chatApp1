package com.mycompany.chatapp1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<Message> sentMessages = new ArrayList<>();
    private static final Random random = new Random();
    private static final String JSON_FILE = "messages.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        System.out.println("Welcome to QuickChat\n");
        loadMessagesFromJson();

        Scanner input = new Scanner(System.in);
        Login login = new Login();

        String username, password, phoneNumber;
        String registrationMessage;

        System.out.println("=== REGISTRATION ===");

        while (true) {
            System.out.print("Enter username: ");
            username = input.nextLine();
            System.out.print("Enter password: ");
            password = input.nextLine();
            System.out.print("Enter phone number: ");
            phoneNumber = input.nextLine();

            registrationMessage = login.registerUser(username, password, phoneNumber);
            System.out.println(registrationMessage);

            if (registrationMessage.equals("User registered successfully.")) {
                break;
            }
            System.out.println("\nPlease try again...\n");
        }

        System.out.println("\n=== LOGIN ===");
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();
            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();
            isLoggedIn = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.returnLoginStatus(isLoggedIn));
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== QUICKCHAT MENU ===");
            System.out.println("1. Send messages");
            System.out.println("2. Show previously sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");
            
            String choiceStr = input.nextLine();
            int choice = 0;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("How many messages do you wish to enter and send? ");
                    String numStr = input.nextLine();
                    int numMessages = 0;
                    try {
                        numMessages = Integer.parseInt(numStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number.");
                        break;
                    }
                    
                    if (numMessages <= 0) {
                        System.out.println("Please enter a positive number.");
                        break;
                    }

                    for (int i = 1; i <= numMessages; i++) {
                        System.out.println("\n--- Message Draft " + i + " ---");
                        String messageId = generate10DigitId();
                        int currentMessageNum = Message.returnTotalMessages(sentMessages) + 1;
                        
                        // Recipient Input
                        String recipient = "";
                        while (true) {
                            System.out.print("Enter recipient cell number (e.g., +27...): ");
                            recipient = input.nextLine().trim();
                            // Create dummy message to use its validation logic
                            Message temp = new Message(messageId, currentMessageNum, recipient, "", "", "");
                            String validation = temp.checkRecipientCell();
                            if (validation.equals("Recipient number is valid.")) {
                                break;
                            } else {
                                System.out.println(validation);
                            }
                        }
                        
                        // Content Input
                        String content = "";
                        while (true) {
                            System.out.print("Enter message content (max 250 chars): ");
                            content = input.nextLine().trim();
                            if (content.length() > 0 && content.length() <= 250) {
                                break;
                            } else if (content.length() > 250) {
                                System.out.println("Message too long (" + content.length() + " chars).");
                            } else {
                                System.out.println("Message cannot be empty.");
                            }
                        }
                        
                        // Create Message object to generate hash and handle actions
                        Message msg = new Message(messageId, currentMessageNum, recipient, content, "", "DRAFT");
                        String hash = msg.createMessageHash();
                        
                        boolean drafted = true;
                        while(drafted) {
                            System.out.println("\nDraft Ready. Hash: " + hash);
                            System.out.println("1 - Send message");
                            System.out.println("0 - Disregard message");
                            System.out.println("2 - Store message to send later");
                            System.out.print("Choice: ");
                            String action = input.nextLine().trim();
                            
                            if (action.equals("1")) {
                                Message finalMsg = new Message(messageId, currentMessageNum, recipient, content, hash, "SENT");
                                sentMessages.add(finalMsg);
                                Message.storeMessage(sentMessages);
                                System.out.println("Message sent successfully.");
                                drafted = false;
                            } else if (action.equals("2")) {
                                Message finalMsg = new Message(messageId, currentMessageNum, recipient, content, hash, "STORED");
                                sentMessages.add(finalMsg);
                                Message.storeMessage(sentMessages);
                                System.out.println("Message stored successfully.");
                                drafted = false;
                            } else if (action.equals("0")) {
                                System.out.println("Message deleted.");
                                drafted = false;
                            } else {
                                System.out.println("Invalid choice.");
                            }
                        }
                    }
                    break;
                case 2:
                    if (sentMessages.isEmpty()) {
                        System.out.println("No messages sent or stored yet.");
                    } else {
                        System.out.println("\n=== SENT/STORED MESSAGES ===");
                        for (Message m : sentMessages) {
                            System.out.println(m.printMessage());
                        }
                        System.out.println("Total Messages: " + Message.returnTotalMessages(sentMessages));
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        input.close();
    }
    
    private static String generate10DigitId() {
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(9) + 1);
        for (int i = 0; i < 9; i++) sb.append(random.nextInt(10));
        return sb.toString();
    }

    private static void loadMessagesFromJson() {
        try (Reader reader = new FileReader(JSON_FILE)) {
            Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
            List<Message> loaded = gson.fromJson(reader, listType);
            if (loaded != null) {
                sentMessages = loaded;
                System.out.println("Loaded " + sentMessages.size() + " messages from history.");
            }
        } catch (IOException e) { }
    }
}
