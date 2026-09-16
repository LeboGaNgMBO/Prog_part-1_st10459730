public class Login {

    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    // Constructor
    public Login(String firstName, String lastName, String username,
                 String password, String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Checks whether the username is correctly formatted
    public boolean checkUserName() {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    // Checks whether the password meets the complexity requirements
    public boolean checkPasswordComplexity() {

        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (char character : password.toCharArray()) {

            if (Character.isUpperCase(character)) {
                hasCapitalLetter = true;
            }

            if (Character.isDigit(character)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(character)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }

    // Checks whether the South African cellphone number is valid
    public boolean checkCellPhoneNumber() {

        /*
         * South African international cellphone format:
         * +27 followed by 9 digits.
         *
         * Example:
         * +27838968976
         *
         * Regular expression:
         * ^\\+27[0-9]{9}$
         */
        String cellPhoneRegex = "^\\+27[0-9]{9}$";

        return cellPhoneNumber != null
                && cellPhoneNumber.matches(cellPhoneRegex);
    }

    // Registers the user
    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "User successfully registered.";
    }

    // Checks the entered login details
    public boolean loginUser(String enteredUsername, String enteredPassword) {

        return username != null
                && password != null
                && username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }

    // Returns the login status message
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + " " + lastName
                    + ", it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}
