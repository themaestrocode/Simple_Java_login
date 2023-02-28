import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Scanner scanner;
    private static ArrayList<User> users;
    private static Pattern pattern;
    private static Matcher matcher;
    private static String email;
    private static String phoneNumber;
    private static String username;
    private static String password;


    public static void main(String[] args) {

        users = new ArrayList<>();

        System.out.println("WELCOME TO THE MAESTRO CODE'S SIMPLE JAVA LOG IN PROGRAM");
        mainMenu();
    }


    private static void mainMenu() {
        System.out.println("MAIN MENU\n");
        System.out.println("\t1. Create an Account\n\t2. Log in (if you already have an account)\n\t3. Quit and Exit");
        scanner = new Scanner(System.in);
        int opcode = scanner.nextInt();
        scanner.nextLine();

        switch (opcode) {
            case 1:
                createAccount();
                break;

            case 2:
                logIn();
                break;

            case 3:
                System.out.println("Application closed!");
                break;

            default:
                invalidOption();
                mainMenu();
        }
    }

    /**
     * method to create a new account
     */
    private static void createAccount() {
        System.out.println("CREATE A FREE ACCOUNT\n");
        System.out.println("Please provide your correct details in the fields below");
        System.out.print("Enter your first name: ");
        String fName = scanner.next();
        System.out.print("Enter your last name: ");
        String lName = scanner.next();
        //time to enter email
        emailAuthentication();
        //time to enter phone number
        phoneNumberAuthentication();
        //time to input username
        usernameValidation();
        //time to input password
        passwordAuthentication();

        User newUser = new User(fName, lName, email, phoneNumber, username, password);
        users.add(newUser);
        System.out.println("\nCongratulations. You have successfully created your account!");

        System.out.println();
        newUser.profileDetails();

        mainMenu();
    }

    /**
     * method to log in
     */
    private static void logIn() {
        System.out.println("LOG INTO YOUR ACCOUNT");
        System.out.print("\nEnter your username, phone number, or email: ");
        String userInput = scanner.next();
        System.out.print("Enter your password: ");
        String loginPassword = scanner.next();

        boolean userFound = false;
        for(User u: users) {
            if((u.getUserName().equals(userInput) || u.getPhoneNumber().equals(userInput) || u.getEmail().equals(userInput))
                    && (u.getPassWord().equals(loginPassword))) {
                userFound = true;
                System.out.println("\nLog in successful!");
            }
        }

        if(!userFound) {
            System.out.println("!!! Incorrect login details. Check the details you've provided and try again !!!");
        }
        mainMenu();
    }

    /**
     * method to register a username and validate it
     */
    private static String usernameValidation() {
        //checking if the username is valid
        System.out.print("Choose a username: ");
        username = scanner.next();

        boolean matchFound = Pattern.matches("^[a-zA-Z][a-zA-Z0-9_]{5,14}$", username);

        if(matchFound) {
            //test if the name is already taken by another user
            for(User u: users) {
                if(u.getUserName().equals(username)) {
                    System.out.println("\n!!! Username not available. Try something else !!!");
                    usernameValidation();
                }
            }
        }
        else {
            System.out.println("!!! Invalid username !!!");
            System.out.println("* HINT" +
                    "\ni.   Usernames must start with a letter." +
                    "\nii.  Usernames can only contain alphabets, numbers, and underscore. No space." +
                    "\niii. Usernames must be at least 6 characters and at most 15 characters long.");
            usernameValidation();
        }
        return username;
    }

    /**
     * method to register a password and validate it
     */
    private static String passwordAuthentication() {
        System.out.print("Choose a strong password: ");
        password = scanner.next();
        System.out.print("Confirm your password by entering it again: ");
        String passwordConfirmation = scanner.next();

        if(password.length() < 8) {
            //checking if the password is up to 8 characters
            System.out.println("\n!!! password cannot be less than 8. Please try again !!!");
            passwordAuthentication();
        }
        else if(!password.equals(passwordConfirmation)) {
            //checking if the 2 password are the same
            System.out.println("\n!!! passwords do not match. Please try again !!!");
            passwordAuthentication();
        }
        else {

        }

        return password;
    }

    /**
     * method to authenticate email
     */
    private static String emailAuthentication() {
        System.out.print("Enter your email: ");
        email = scanner.next();

        boolean emailExists = false;

        for(User u: users) {
            if(u.getEmail().equals(email)) {
                emailExists = true;
                System.out.println("* This email is already registered to another account. *");
                System.out.println("If this is your email, go to the Login page and log in. If not, ");
                emailAuthentication();
            }
        }
        return email;
    }

    /**
     * method to authenticate phone number
     */
    private static String phoneNumberAuthentication() {
        System.out.print("Enter your phone number: ");
        phoneNumber = scanner.next();

        boolean numberValid = false;

        if(phoneNumber.startsWith("0") && phoneNumber.length() == 11) {
            numberValid = true;

            boolean numberFound = false;

            for(User u: users) {
                if(u.getPhoneNumber().equals(phoneNumber)) {
                    numberFound = true;
                    System.out.println("* This  phone number is already registered to another account. *");
                    System.out.println("If this is your phone number, go to the Login page and log in. If not, ");
                    phoneNumberAuthentication();
                }
            }
        }

        if(!numberValid) {
            System.out.println("!!! The phone number you have entered is not valid. Please, check and try again !!!");
            phoneNumberAuthentication();
        }
        return phoneNumber;
    }

    /**
     * method to notify the user if an invalid option is entered out a range of options
     */
    private static void invalidOption() {
        System.out.println("Invalid option!");
    }
}
