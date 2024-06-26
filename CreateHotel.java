import java.util.ArrayList;
import java.util.Scanner;

/**
 * The CreateHotel class provides functionalities to create a new hotel,
 * including specifying the number of rooms and setting the base price for rooms.
 */
public class CreateHotel {
    private ArrayList<Hotel> hotels;
    private Scanner scanner;

    /**
     * Constructor for CreateHotel.
     *
     * @param hotels  List of hotels to which the new hotel will be added.
     * @param scanner Scanner for user input.
     */
    public CreateHotel(ArrayList<Hotel> hotels, Scanner scanner) {
        this.hotels = hotels;
        this.scanner = scanner;
    }

    /**
     * Executes the hotel creation process.
     */
    public void execute() {
        String name;
        boolean uniqueName;
        
        do {
            System.out.print("Enter hotel name: ");
            name = scanner.nextLine();
            uniqueName = isUniqueName(name);
            if (!uniqueName) {
                System.out.println("Hotel name already exists. Please enter a different name.");
            }
        } while (!uniqueName);
        
        int numRooms;
        do {
            numRooms = getIntInput("Enter number of rooms (1-50): ");
        } while (numRooms < 1 || numRooms > 50);
        
        double basePrice = getDoubleInput("\nEnter base price for rooms (Enter 0 to set default base price): ");
        scanner.nextLine();

        if (basePrice == 0) {
            basePrice = 1299.00;
        }

        Hotel hotel = new Hotel(name, basePrice);
        hotels.add(hotel);

        for (int i = 1; i <= numRooms; i++) {
            String roomName = "Room " + i;
            hotel.addRoom(new StandardRoom(roomName, basePrice));
        }

        System.out.println("Hotel created successfully with " + numRooms + " rooms.");
    }

    /**
     * Checks if the hotel name is unique among existing hotels.
     *
     * @param name The name to check for uniqueness.
     * @return true if the name is unique, false otherwise.
     */
    private boolean isUniqueName(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prompts the user for an integer input within the specified range.
     *
     * @param prompt The prompt message for the input.
     * @return The integer value input by the user.
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        return value;
    }

    /**
     * Prompts the user for a double input.
     *
     * @param prompt The prompt message for the input.
     * @return The double value input by the user.
     */
    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        double value;
        do {
            while (!scanner.hasNextDouble()) {
                System.out.print("Invalid input. " + prompt);
                scanner.next();
            }
            value = scanner.nextDouble();
            if (value < 0) {
                System.out.print("Base price cannot be negative. " + prompt);
            }
            scanner.nextLine(); // Consume newline left-over
        } while (value < 0);
        return value;
    }
}
