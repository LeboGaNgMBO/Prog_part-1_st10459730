import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Message {

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String message;
    private final String messageHash;

    private static final List<Message> sentMessages =
            new ArrayList<>();

    private static final List<Message> allMessages =
            new ArrayList<>();

    public Message(int messageNumber, String recipient,
                   String message) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generate a random ten-digit message ID.
    private String generateMessageID() {
        long id = ThreadLocalRandom.current().nextLong(
                1_000_000_000L, 10_000_000_000L);

        return String.valueOf(id);
    }

    // Check that the ID has no more than ten characters.
    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("[0-9]{1,10}");
    }

    // Validate a South African international cellphone number.
    public String checkRecipientCell() {
        if (recipient != null
                && recipient.matches("^\\+27[0-9]{9}$")) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or "
                + "does not contain an international code. "
                + "Please correct the number and try again.";
    }

    // Validate message length.
    public boolean checkMessageLength() {
        return message != null && message.length() <= 250;
    }

    // Return the required message-length feedback.
    public String validateMessage() {
        if (checkMessageLength()) {
            return "Message ready to send.";
        }

        int excess = message.length() - 250;

        return "Message exceeds 250 characters by "
                + excess + "; please reduce the size.";
    }

    // Create the message hash.
    public String createMessageHash() {
        if (message == null || message.trim().isEmpty()) {
            return "";
        }

        String[] words = message.trim().split("\\s+");

        String firstWord = words[0]
                .replaceAll("[^a-zA-Z0-9]", "");

        String lastWord = words[words.length - 1]
                .replaceAll("[^a-zA-Z0-9]", "");

        return (messageID.substring(0, 2)
                + ":" + messageNumber
                + ":" + firstWord + lastWord).toUpperCase();
    }

    // Send, disregard or store the message.
    // 1 = Send, 2 = Disregard, 3 = Store.
    public String SentMessage(int option) {

        if (option == 1) {
            if (!sentMessages.contains(this)) {
                sentMessages.add(this);
            }

            if (!allMessages.contains(this)) {
                allMessages.add(this);
            }

            return "Message successfully sent.";

        } else if (option == 2) {
            return "Press 0 to delete the message.";

        } else if (option == 3) {
            if (!storeMessage()) {
                return "Message could not be stored.";
            }

            if (!allMessages.contains(this)) {
                allMessages.add(this);
            }

            return "Message successfully stored.";
        }

        return "Invalid option.";
    }

    // Remove a disregarded message when the user enters 0.
    public boolean deleteMessage() {
        if (allMessages.contains(this)) {
            return allMessages.remove(this);
        }

        return true;
    }

    // Return all sent messages during this program run.
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent.";
        }

        StringBuilder result = new StringBuilder();

        for (Message msg : sentMessages) {
            result.append(msg.getMessageDetails())
                    .append("\n--------------------\n");
        }

        return result.toString();
    }

    // Return the total number of messages sent.
    public static int returnTotalMessagess() {
        return sentMessages.size();
    }

    // Display the full details in the required order.
    public String getMessageDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    // Store messages in a valid JSON array.
    // Uses only standard Java libraries.
    public boolean storeMessage() {
        Path path = Paths.get("messages.json");

        try {
            String existing = "";

            if (Files.exists(path)) {
                existing = Files.readString(
                        path, StandardCharsets.UTF_8).trim();
            }

            String object = "{"
                    + "\"messageID\":\"" + escapeJson(messageID) + "\","
                    + "\"messageNumber\":" + messageNumber + ","
                    + "\"messageHash\":\"" + escapeJson(messageHash) + "\","
                    + "\"recipient\":\"" + escapeJson(recipient) + "\","
                    + "\"message\":\"" + escapeJson(message) + "\""
                    + "}";

            String updated;

            if (existing.isEmpty() || existing.equals("[]")) {
                updated = "[\n" + object + "\n]";
            } else {
                if (!existing.startsWith("[")
                        || !existing.endsWith("]")) {
                    System.out.println(
                            "The existing JSON file is not a valid array.");
                    return false;
                }

                String contents = existing.substring(
                        1, existing.length() - 1).trim();

                updated = "[\n" + contents
                        + (contents.isEmpty() ? "" : ",\n")
                        + object + "\n]";
            }

            Files.writeString(
                    path, updated, StandardCharsets.UTF_8);

            return true;

        } catch (IOException e) {
            System.out.println(
                    "Error storing message: " + e.getMessage());
            return false;
        }
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Getters used by Main and JUnit tests.
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public int getMessageNumber() {
        return messageNumber;
    }
}
