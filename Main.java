import javax.swing.JOptionPane;

public class Main {

    private static final String TITLE = "QuickChat";

    public static void main(String[] args) {

        // ==============================
        // REGISTRATION
        // ==============================

        String firstName = ask("Registration - Enter your first name:");
        if (firstName == null) return;

        String lastName = ask("Registration - Enter your last name:");
        if (lastName == null) return;

        String username = ask("Registration - Enter your username:");
        if (username == null) return;

        String password = ask("Registration - Enter your password:");
        if (password == null) return;

        String cellPhoneNumber = ask(
                "Registration - Enter your South African cellphone number:");
        if (cellPhoneNumber == null) return;

        Login user = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        // Build the registration results in one window
        StringBuilder results = new StringBuilder();

        if (user.checkUserName()) {
            results.append("Username successfully captured.\n\n");
        } else {
            results.append("Username is not correctly formatted; "
                    + "please ensure that your username contains "
                    + "an underscore and is no more than five "
                    + "characters in length.\n\n");
        }

        if (user.checkPasswordComplexity()) {
            results.append("Password successfully captured.\n\n");
        } else {
            results.append("Password is not correctly formatted; "
                    + "please ensure that the password contains "
                    + "at least eight characters, a capital letter, "
                    + "a number, and a special character.\n\n");
        }

        if (user.checkCellPhoneNumber()) {
            results.append("Cell phone number successfully added.");
        } else {
            results.append("Cell phone number incorrectly formatted "
                    + "or does not contain international code.");
        }

        JOptionPane.showMessageDialog(
                null,
                results.toString(),
                "Registration Results",
                JOptionPane.INFORMATION_MESSAGE
        );

        String registrationMessage = user.registerUser();

        if (!registrationMessage.equals("User successfully registered.")) {
            JOptionPane.showMessageDialog(
                    null,
                    "Registration unsuccessful.\n" + registrationMessage,
                    "Registration",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                null,
                registrationMessage,
                "Registration",
                JOptionPane.INFORMATION_MESSAGE
        );

        // ==============================
        // LOGIN
        // ==============================

        String loginUsername = ask("Login - Enter your username:");
        if (loginUsername == null) return;

        String loginPassword = ask("Login - Enter your password:");
        if (loginPassword == null) return;

        String loginStatus = user.returnLoginStatus(
                loginUsername,
                loginPassword
        );

        JOptionPane.showMessageDialog(
                null,
                loginStatus,
                "Login",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Only logged-in users can access QuickChat
        if (user.loginUser(loginUsername, loginPassword)) {
            quickChat();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "You cannot access QuickChat until you "
                    + "log in successfully.",
                    "Access denied",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // Shows an input pop-up. Returns null if the user presses Cancel / X.
    private static String ask(String prompt) {
        return JOptionPane.showInputDialog(
                null,
                prompt,
                "Input",
                JOptionPane.QUESTION_MESSAGE
        );
    }

    private static void info(String text) {
        JOptionPane.showMessageDialog(
                null, text, TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    // ==============================
    // QUICKCHAT APPLICATION
    // ==============================

    public static void quickChat() {

        JOptionPane.showMessageDialog(
                null,
                "Welcome to QuickChat.",
                TITLE,
                JOptionPane.INFORMATION_MESSAGE
        );

        // Ask how many messages the user wants to enter
        int messageLimit;

        while (true) {

            String input = ask("How many messages would you like to enter?");

            // Cancel / close window
            if (input == null) {
                return;
            }

            try {
                messageLimit = Integer.parseInt(input.trim());

                if (messageLimit >= 0) {
                    break;
                }

                info("Please enter zero or a positive number.");

            } catch (NumberFormatException e) {
                info("Please enter a valid whole number.");
            }
        }

        int messagesEntered = 0;

        String[] menuButtons = {
                "Send Messages",
                "Show recently sent messages",
                "Quit"
        };

        // Display menu until the user chooses Quit
        while (true) {

            int menuOption = JOptionPane.showOptionDialog(
                    null,
                    "Select an option:",
                    "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    menuButtons,
                    menuButtons[0]
            );

            // Window closed (-1) or Quit (2)
            if (menuOption == -1 || menuOption == 2) {
                JOptionPane.showMessageDialog(
                        null,
                        "Thank you for using QuickChat.",
                        TITLE,
                        JOptionPane.INFORMATION_MESSAGE
                );
                break;
            }

            // OPTION 1: SEND MESSAGE
            if (menuOption == 0) {

                if (messagesEntered >= messageLimit) {
                    info("You have reached your message limit.\n"
                            + "You can still view the menu or quit.");
                    continue;
                }

                String recipient = ask("Enter recipient cellphone number:");
                if (recipient == null) {
                    continue;
                }

                String messageText = ask("Enter your message:");
                if (messageText == null) {
                    continue;
                }

                // Message numbers start at zero
                Message message = new Message(
                        messagesEntered,
                        recipient,
                        messageText
                );

                // Check message ID
                if (!message.checkMessageID()) {
                    info("Invalid message ID.");
                    continue;
                }

                // Check recipient cellphone number
                String recipientResult = message.checkRecipientCell();

                if (!recipientResult.equals(
                        "Cell phone number successfully captured.")) {
                    info(recipientResult);
                    continue;
                }

                // Check message length
                String lengthResult = message.validateMessage();

                if (!message.checkMessageLength()) {
                    info(lengthResult);
                    continue;
                }

                info("Message ID generated: " + message.getMessageID()
                        + "\n" + recipientResult
                        + "\n" + lengthResult);

                // Message action options
                String[] actionButtons = {
                        "Send Message",
                        "Disregard Message",
                        "Store Message to send later"
                };

                int actionIndex = JOptionPane.showOptionDialog(
                        null,
                        "Choose an option:",
                        "Message Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        actionButtons,
                        actionButtons[0]
                );

                // Window closed: do nothing with this message
                if (actionIndex == -1) {
                    continue;
                }

                int action = actionIndex + 1; // 1, 2 or 3

                // Process selected message action
                String actionResult = message.SentMessage(action);

                info(actionResult);

                // SEND MESSAGE
                if (action == 1) {

                    messagesEntered++;

                    JOptionPane.showMessageDialog(
                            null,
                            "Message ID: " + message.getMessageID()
                            + "\nMessage Hash: " + message.getMessageHash()
                            + "\nRecipient: " + message.getRecipient()
                            + "\nMessage: " + message.getMessage(),
                            "Message Details",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

                // DISREGARD MESSAGE
                else if (action == 2) {

                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to delete the message?",
                            TITLE,
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        message.deleteMessage();
                        info("Message deleted.");
                    }

                    messagesEntered++;
                }

                // STORE MESSAGE
                else if (action == 3) {

                    // Count the stored message towards the
                    // allowed number of message entries.
                    messagesEntered++;
                }
            }

            // OPTION 2: RECENT MESSAGES
            else if (menuOption == 1) {
                info("Coming Soon.");
            }
        }

        // Display total successfully sent messages
        info("Total messages sent: " + Message.returnTotalMessagess());
    }
}