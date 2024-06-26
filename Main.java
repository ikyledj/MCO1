import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the Hotel Reservation System.
 */
public class Main {
    private static ArrayList<Hotel> hotels = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main method to run the Hotel Reservation System.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        while (true) {
            printMenu();
            switch (getIntInput("Choose an option: ")) {
                case 1: new CreateHotel(hotels, scanner).execute(); break;
                case 2: new ViewHotelInfo(hotels, scanner).execute(); break;
                case 3: new ManageHotel(hotels, scanner).execute(); break;
                case 4: new SimulateBookings(hotels).execute(); break;
                case 5: exit(); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Prints the menu options for the Hotel Reservation System.
     */
    private static void printMenu() {
        System.out.println("\nHotel Reservation System");
        System.out.println("1. Create a hotel");
        System.out.println("2. View hotel information");
        System.out.println("3. Manage a hotel");
        System.out.println("4. Simulate bookings");
        System.out.println("5. Exit");
    }

    /**
     * Gets an integer input from the user.
     * @param prompt The prompt message to display to the user.
     * @return The integer input from the user.
     */
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    /**
     * Exits the Hotel Reservation System.
     */
    private static void exit() {
        System.out.println("Exiting the system...");
        System.exit(0);
    }
}
