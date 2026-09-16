import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

       
        System.out.println("       REGISTRATION SYSTEM");
     

        // Get user's first name
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        // Get user's last name
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        // Get username
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        // Get password
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Get cellphone number
        System.out.print("Enter your South African cellphone number: ");
        String cellPhoneNumber = scanner.nextLine();

        // Create Login object
        Login user = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        System.out.println();
        System.out.println("=================================");
        System.out.println("       REGISTRATION RESULTS");
        System.out.println("=================================");

        // Check username
        if (user.checkUserName()) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println(
                    "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length."
            );
        }

        // Check password
        if (user.checkPasswordComplexity()) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println(
                    "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character."
            );
        }

        // Check cellphone number
        if (user.checkCellPhoneNumber()) {
            System.out.println("Cell phone number successfully added.");
        } else {
            System.out.println(
                    "Cell phone number incorrectly formatted or does not contain international code."
            );
        }

        // Register user
        String registrationMessage = user.registerUser();

        if (registrationMessage.equals("User successfully registered.")) {

            System.out.println();
            System.out.println(registrationMessage);

            // Login section
            System.out.println();
            System.out.println("=================================");
            System.out.println("            LOGIN");
            System.out.println("=================================");

            System.out.print("Enter your username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = scanner.nextLine();

            // Display login status
            System.out.println();
            System.out.println(
                    user.returnLoginStatus(loginUsername, loginPassword)
            );

        } else {

            System.out.println();
            System.out.println("Registration unsuccessful.");
        }

        scanner.close();
    }
}

