
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ogund
 */
public class messagegithub {
      private String messageId;
    private int messageNumber;
    private String recipientNumber;
    private String content;
    private String messageHash;
    private String status;

    private static final String JSON_FILE = "messages.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //sending a message
    public messagegithub(String messageId, int messageNumber, String recipientNumber, String content, String messageHash, String status) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipientNumber = recipientNumber;
        this.content = content;
        this.messageHash = messageHash;
        this.status = status;
    }

    // Validation for Message ID (Should be 10 digits)
    public boolean checkMessageID() {
        return messageId != null && messageId.matches("\\d{10}");
    }

    // Validation for Recipient Cell
    public String checkRecipientCell() {
        if (recipientNumber.length() > 10 && recipientNumber.startsWith("+")) {
            return "Recipient number is valid.";
        } else {
            return "Invalid recipient number. Must contain an international code (+) and be longer than 10 characters.";
        }
    }

    // Creates the Message Hash
    public String createMessageHash() {
        String firstTwoId = messageId.substring(0, 2);
        String[] words = content.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";
        return (firstTwoId + ":" + messageNumber + ":" + firstWord + " " + lastWord).toUpperCase();
    }

    // Returns a summary line
    public String sentMessages() {
        return "Msg #" + messageNumber + " to " + recipientNumber + " [" + status + "]";
    }

    // Returns a detailed print out
    public String printMessage() {
        return "-----------------------------------\n" +
               "MESSAGE ID:     " + messageId + "\n" +
               "MESSAGE NO:     " + messageNumber + "\n" +
               "RECIPIENT:      " + recipientNumber + "\n" +
               "HASH:           " + messageHash + "\n" +
               "STATUS:         " + status + "\n" +
               "CONTENT:        " + content + "\n" +
               "-----------------------------------";
    }

    // Static helper for list size
    public static int returnTotalMessages(List<messagegithub> list) {
        return list.size();
    }

    // Static helper for JSON storage
    public static void storeMessage(List<messagegithub> list) {
        try (Writer writer = new FileWriter(JSON_FILE)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    // Getters
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getContent() { return content; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
}

    
