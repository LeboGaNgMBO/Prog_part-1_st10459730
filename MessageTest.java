
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MessageTest {

    // Test 1: Message ID is generated and valid
    @Test
    public void testCheckMessageID() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertNotNull(message.getMessageID());
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.checkMessageID());
    }

    // Test 2: Correctly formatted recipient number
    @Test
    public void testRecipientCorrectlyFormatted() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    // Test 3: Incorrectly formatted recipient number
    @Test
    public void testRecipientIncorrectlyFormatted() {

        Message message = new Message(
                0,
                "08575975889",
                "Hi Keegan, did you receive the payment?"
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or "
                        + "does not contain an international code. "
                        + "Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }

    // Test 4: Message contains fewer than 250 characters
    @Test
    public void testMessageLengthSuccess() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertTrue(message.checkMessageLength());

        assertEquals(
                "Message ready to send.",
                message.validateMessage()
        );
    }

    // Test 5: Message contains more than 250 characters
    @Test
    public void testMessageLengthFailure() {

        String longMessage = "A".repeat(251);

        Message message = new Message(
                0,
                "+27718693002",
                longMessage
        );

        assertFalse(message.checkMessageLength());

        assertEquals(
                "Message exceeds 250 characters by 1; "
                        + "please reduce the size.",
                message.validateMessage()
        );
    }

    // Test 6: Message hash is generated correctly
    @Test
    public void testCreateMessageHash() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        String hash = message.createMessageHash();

        assertNotNull(hash);

        // The first two digits are random.
        // The remaining format must match the assignment.
        assertTrue(
                hash.matches("[0-9]{2}:0:HITONIGHT"),
                "The message hash should contain the first "
                        + "two ID digits, message number, "
                        + "first word and last word."
        );
    }

    // Test 7: Message can be sent
    @Test
    public void testSentMessageSuccessfully() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message successfully sent.",
                message.SentMessage(1)
        );
    }

    // Test 8: Message can be disregarded
    @Test
    public void testDisregardMessage() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Keegan, did you receive the payment?"
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage(2)
        );
    }

    // Test 9: Message can be stored
    @Test
    public void testStoreMessage() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message successfully stored.",
                message.SentMessage(3)
        );
    }

    // Test 10: Message details contain the required information
    @Test
    public void testGetMessageDetails() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        String details = message.getMessageDetails();

        assertTrue(details.contains("Message ID:"));
        assertTrue(details.contains("Message Hash:"));
        assertTrue(details.contains("Recipient: +27718693002"));
        assertTrue(details.contains(
                "Message: Hi Mike, can you join us for dinner tonight?"
        ));
    }

    // Test 11: Total number of sent messages increases
    @Test
    public void testReturnTotalMessages() {

        int totalBefore = Message.returnTotalMessagess();

        Message message = new Message(
                0,
                "+27718693002",
                "Testing the total number of sent messages."
        );

        message.SentMessage(1);

        assertEquals(
                totalBefore + 1,
                Message.returnTotalMessagess()
        );
    }

    // Test 12: Sent messages can be retrieved
    @Test
    public void testPrintMessages() {

        Message message = new Message(
                0,
                "+27718693002",
                "Testing the message list."
        );

        message.SentMessage(1);

        String messages = Message.printMessages();

        assertTrue(messages.contains(message.getMessageID()));
        assertTrue(messages.contains("Testing the message list."));
    }

    // Test 13: Invalid message action is rejected
    @Test
    public void testInvalidMessageOption() {

        Message message = new Message(
                0,
                "+27718693002",
                "Testing an invalid option."
        );

        assertEquals(
                "Invalid option.",
                message.SentMessage(4)
        );
    }
}

