package com.mycompany.chatapp1;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class MessageTest {

    private Message message1;
    private Message message2;

    @Before
    public void setUp() {
        message1 = new Message("0011223344", 1, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?", "", "Sent");

        message2 = new Message("9988776655", 2, "08575975889",
                "Hi Keegan, did you receive the payment?", "", "Discard");
    }

    // --- checkMessageID ---

    @Test
     public void testCheckMessageID_Valid() {
        assertTrue(message1.checkMessageID());
    }

    @Test
    public void testCheckMessageID_Invalid() {
        Message badMessage = new Message("ABC", 3, "+27700000000", "Test message", "", "Sent");
        assertFalse(badMessage.checkMessageID());
    }

    // --- checkRecipientCell ---

    @Test
    public void testCheckRecipientCell_Valid() {
        String result = message1.checkRecipientCell();
        assertEquals("Recipient number is valid.", result);
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        String result = message2.checkRecipientCell();
        assertEquals("Invalid recipient number. Must contain an international code (+) and be longer than 10 characters.", result);
    }

    // --- message length ---

    @Test
    public void testMessageLength_Under250() {
        String content = message1.getContent();
        assertTrue(content.length() <= 250);
    }

    @Test
    public void testMessageLength_Over250() {
        String longContent = "A".repeat(260);
        Message longMessage = new Message("1234567890", 3, "+27700000000", longContent, "", "Sent");
        assertFalse(longMessage.getContent().length() <= 250);
    }

    // --- createMessageHash ---

    @Test
    public void testCreateMessageHash_Message1() {
        String hash = message1.createMessageHash();
        assertEquals("00:1:HI TONIGHT?", hash);
    }

    @Test
    public void testCreateMessageHash_Message2() {
        String hash = message2.createMessageHash();
        assertEquals("99:2:HI PAYMENT?", hash);
    }

    // --- returnTotalMessages ---

    @Test
    public void testReturnTotalMessages() {
        ArrayList<Message> list = new ArrayList<>();
        list.add(message1);
        list.add(message2);
        assertEquals(2, Message.returnTotalMessages(list));
    }

    // --- MessageSent status checks ---

    @Test
    public void testMessageStatus_Sent() {
        assertEquals("Sent", message1.getStatus());
    }

    @Test
    public void testMessageStatus_Discard() {
        assertEquals("Discard", message2.getStatus());
    }

    @Test
    public void testMessageStatus_Stored() {
        Message storedMessage = new Message("5544332211", 3, "+27900000000",
                "Test store message", "", "Stored");
        assertEquals("Stored", storedMessage.getStatus());
    }

    // --- sentMessages ---

    @Test
    public void testSentMessages_ContainsRecipient() {
        String result = message1.sentMessages();
        assertTrue(result.contains("+27718693002"));
    }

    // --- printMessage ---

    @Test
    public void testPrintMessage_ContainsMessageID() {
        String result = message1.printMessage();
        assertTrue(result.contains("0011223344"));
    }

}