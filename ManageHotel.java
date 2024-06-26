import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The ManageHotel class provides functionalities to manage a hotel,
 * including adding/removing rooms, updating prices, and handling reservations.
 */
public class ManageHotel {
    private ArrayList<Hotel> hotels;
    private Scanner scanner;

    /**
     * Constructor for ManageHotel.
     *
     * @param hotels  List of hotels to manage.
     * @param scanner Scanner for user input.
     */
    public ManageHotel(ArrayList<Hotel> hotels, Scanner scanner) {
        this.hotels = hotels;
        this.scanner = scanner;
    }

    /**
     * Executes the main management operations for the hotel.
     */
    public void execute() {
        Hotel hotel = selectHotel();
        if (hotel == null) return;

        while (true) {
            printManageMenu(hotel.getName());
            switch (getIntInput("Choose an option: ")) {
                case 1:
                    addRoomToHotel(hotel);
                    break;
                case 2:
                    removeRoomFromHotel(hotel);
                    break;
                case 3:
                    renameHotel(hotel);
                    break;
                case 4:
                    updateHotelBasePrice(hotel);
                    break;
                case 5:
                    updateRoomPrice(hotel);
                    break;
                case 6:
                    simulateBookings(hotel);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Prints the management menu.
     *
     * @param hotelName Name of the hotel being managed.
     */
    private void printManageMenu(String hotelName) {
        System.out.println("\nManaging Hotel: " + hotelName);
        System.out.println("1. Add a room");
        System.out.println("2. Remove a room");
        System.out.println("3. Rename the hotel");
        System.out.println("4. Update base price");
        System.out.println("5. Update price of a room");
        System.out.println("6. Simulate bookings and cancellations");
        System.out.println("7. Back to main menu");
    }

    /**
     * Prompts the user to select a hotel.
     *
     * @return The selected hotel or null if no hotels are available.
     */
    private Hotel selectHotel() {
        if (hotels.isEmpty()) {
            System.out.println("No hotels available.");
            return null;
        }

        for (int i = 0; i < hotels.size(); i++) {
            System.out.println((i + 1) + ". " + hotels.get(i).getName());
        }

        int choice = getIntInput("Select a hotel: ");
        if (choice < 1 || choice > hotels.size()) {
            System.out.println("Invalid selection.");
            return null;
        }

        return hotels.get(choice - 1);
    }

    /**
     * Adds a room to the selected hotel.
     *
     * @param hotel The hotel to which the room will be added.
     */
    private void addRoomToHotel(Hotel hotel) {
        System.out.print("Enter room type (Standard/Deluxe): ");
        String roomType = scanner.nextLine();
        System.out.print("Enter room name: ");
        String roomName = scanner.nextLine();
        double roomPrice = getDoubleInput("Enter price for room: ");
        scanner.nextLine();

        Room room = null;
        if (roomType.equalsIgnoreCase("Standard")) {
            room = new StandardRoom(roomName, roomPrice);
        } else if (roomType.equalsIgnoreCase("Deluxe")) {
            room = new DeluxeRoom(roomName, roomPrice);
        } else {
            System.out.println("Invalid room type.");
            return;
        }

        hotel.addRoom(room);
        System.out.println("Room added successfully.");
    }

    /**
     * Removes a room from the selected hotel.
     *
     * @param hotel The hotel from which the room will be removed.
     */
    private void removeRoomFromHotel(Hotel hotel) {
        System.out.print("Enter room name to remove: ");
        String roomName = scanner.nextLine();

        if (hotel.getRoom(roomName) != null && hotel.hasNoReservations()) {
            hotel.removeRoom(roomName);
            System.out.println("Room removed successfully.");
        } else {
            System.out.println("Cannot remove room with active reservations.");
        }
    }

    /**
     * Renames the selected hotel.
     *
     * @param hotel The hotel which will be renamed.
     */
    private void renameHotel(Hotel hotel) {
        System.out.print("Enter new hotel name: ");
        String newHotelName = scanner.nextLine();

        if (isUniqueHotelName(newHotelName)) {
            hotel.setName(newHotelName);
            System.out.println("Hotel renamed successfully.");
        } else {
            System.out.println("Hotel name already exists. Please enter a different name.");
        }
    }

    /**
     * Checks if the hotel name is unique among existing hotels.
     *
     * @param name The name to check for uniqueness.
     * @return true if the name is unique, false otherwise.
     */
    private boolean isUniqueHotelName(String name) {
        for (Hotel h : hotels) {
            if (h.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the base price of the selected hotel.
     *
     * @param hotel The hotel whose base price will be updated.
     */
    private void updateHotelBasePrice(Hotel hotel) {
        if (hotel.hasNoReservations()) {
            double newPrice = getDoubleInput("Enter new base price: ");
            hotel.updateBasePrice(newPrice);
            System.out.println("Base price updated successfully.");
        } else {
            System.out.println("Cannot update base price with active reservations.");
        }
    }

    /**
     * Updates the price of a specific room in the selected hotel.
     *
     * @param hotel The hotel whose room price will be updated.
     */
    private void updateRoomPrice(Hotel hotel) {
        System.out.print("Enter room name to update price: ");
        String roomName = scanner.nextLine();
        double newPrice = getDoubleInput("Enter new price for room: ");

        Room room = hotel.getRoom(roomName);
        if (room != null && newPrice >= 100.0 && hotel.getReservations().stream().noneMatch(reservation -> reservation.getRoom().getName().equals(roomName))) {
            room.setPricePerNight(newPrice);
            System.out.println("Room price updated successfully.");
        } else {
            System.out.println("Cannot update price with active reservations or price is below 100.");
        }
    }

    /**
     * Simulates bookings and cancellations for the selected hotel using SimulateBookings class.
     *
     * @param hotel The hotel for which to simulate bookings and cancellations.
     */
    private void simulateBookings(Hotel hotel) {
        SimulateBookings simulateBookings = new SimulateBookings(hotels);
        simulateBookings.execute();
    }

    /**
     * Prompts the user for an integer input.
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
        scanner.nextLine();
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
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}
