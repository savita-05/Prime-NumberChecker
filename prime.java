
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

public class AuthPrimeChecker {

    private static HashMap<String, String> userDB = new HashMap<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option;

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            option = input.nextInt();
            input.nextLine(); // consume newline

            switch (option) {
                case 1:
                    registerUser(input);
                    break;
                case 2:
                    if (loginUser(input)) {
                        checkPrime(input);  // Allow prime check only if login succeeds
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void registerUser(Scanner input) {
        System.out.print("Enter new username: ");
        String username = input.nextLine();

        if (userDB.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("Enter password: ");
        String password = input.nextLine();
        String hashedPassword = hashPassword(password);

        userDB.put(username, hashedPassword);
        System.out.println("User registered successfully!");
    }

    private static boolean loginUser(Scanner input) {
        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();
        String hashedPassword = hashPassword(password);

        if (userDB.containsKey(username) && userDB.get(username).equals(hashedPassword)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Incorrect username or password.");
            return false;
        }
    }

    private static void checkPrime(Scanner input) {
        while (true) {
            System.out.print("Enter a number to check if it's prime (or type -1 to logout): ");
            int num = input.nextInt();

            if (num == -1) {
                System.out.println("Logging out...");
                break;
            }

            boolean isPrime = true;

            if (num <= 1) {
                isPrime = false;
            } else {
                for (int i = 2; i <= Math.sqrt(num); i++) {
                    if (num % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }

            if (isPrime) {
                System.out.println(num + " is a prime number.");
            } else {
                System.out.println(num + " is not a prime number.");
            }
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing error: " + e.getMessage());
        }
    }
}
