import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class provides functionality to view detailed information about hotels.
 * It allows the user to select a hotel and view both high-level and low-level information
 * including room status, room details, and reservation details.
 */
public class ViewHotelInfo {
    private ArrayList<Hotel> hotels;
    private Scanner scanner;

    /**
     * Constructs a ViewHotelInfo object with a list of hotels and a scanner for user input.
     * 
     * @param hotels the list of hotels to view information for
     * @param scanner the scanner to read user input
     */
    public ViewHotelInfo(ArrayList<Hotel> hotels, Scanner scanner) {
        this.hotels = hotels;
        this.scanner = scanner;
    }

    /**
     * Executes the process of selecting a hotel and viewing its details.
     * The user is prompted to select a hotel from the list.
     */
    public void execute() {
        Hotel hotel = selectHotel();
        if (hotel != null) {
            viewHotelDetails(hotel);
        }
    }

    /**
     * Prompts the user to select a hotel from the list of hotels.
     * 
     * @return the selected hotel, or null if no valid selection is made
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
     * Displays a menu for viewing high-level and low-level information of the selected hotel.
     * 
     * @param hotel the hotel to view details for
     */
    private void viewHotelDetails(Hotel hotel) {
        while (true) {
            printHotelInfoMenu(hotel.getName());
            int choice = getIntInput("Choose an option: ");
            switch (choice) {
                case 1:
                    viewHighLevelInfo(hotel);
                    break;
                case 2:
                    viewLowLevelInfo(hotel);
                    break;
                case 3:
                    return;  // Back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Prints the menu options for viewing hotel information.
     * 
     * @param hotelName the name of the hotel
     */
    private void printHotelInfoMenu(String hotelName) {
        System.out.println("\nViewing Hotel: " + hotelName);
        System.out.println("1. View high-level information");
        System.out.println("2. View low-level information");
        System.out.println("3. Back to main menu");
    }

    /**
     * Displays high-level information about the hotel including total number of rooms
     * and estimated earnings for the month.
     * 
     * @param hotel the hotel to view high-level information for
     */
    private void viewHighLevelInfo(Hotel hotel) {
        int totalRooms = hotel.getRooms().size();
        double estimatedEarnings = hotel.getReservations().stream()
            .mapToDouble(reservation -> reservation.getRoom().getPricePerNight() * 
                          (reservation.getCheckOut().toEpochDay() - reservation.getCheckIn().toEpochDay()))
            .sum();
        System.out.println("Hotel Name: " + hotel.getName());
        System.out.println("Total Number of Rooms: " + totalRooms);
        System.out.println("Estimated Earnings for the Month: " + estimatedEarnings);
    }

    /**
     * Displays low-level information options and handles user input for selecting an option.
     * 
     * @param hotel the hotel to view low-level information for
     */
    private void viewLowLevelInfo(Hotel hotel) {
        while (true) {
            printLowLevelInfoMenu();
            int choice = getIntInput("Choose an option: ");
            switch (choice) {
                case 1:
                    viewRoomsStatus(hotel);
                    break;
                case 2:
                    viewRoomDetails(hotel);
                    break;
                case 3:
                    viewReservationDetails(hotel);
                    break;
                case 4:
                    return;  // Back to hotel info menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Prints the menu options for viewing low-level information.
     */
    private void printLowLevelInfoMenu() {
        System.out.println("\nLow-Level Information Options");
        System.out.println("1. View total number of available and booked rooms for a selected date");
        System.out.println("2. View information about a selected room");
        System.out.println("3. View information about a selected reservation");
        System.out.println("4. Back to previous menu");
    }

    /**
     * Displays the number of available and booked rooms for a selected date.
     * 
     * @param hotel the hotel to view room status for
     */
    private void viewRoomsStatus(Hotel hotel) {
        LocalDate date = getDateInput("Enter the date (YYYY-MM-DD) to check room availability: ");
        long availableRooms = hotel.getRooms().stream().filter(room -> room.isAvailable(date)).count();
        long bookedRooms = hotel.getRooms().size() - availableRooms;
        System.out.println("Date: " + date);
        System.out.println("Total Available Rooms: " + availableRooms);
        System.out.println("Total Booked Rooms: " + bookedRooms);
    }

    /**
     * Displays information about the rooms in the hotel and allows the user to view
     * reservations for a selected room.
     * 
     * @param hotel the hotel to view room details for
     */
    private void viewRoomDetails(Hotel hotel) {
        for (int i = 0; i < hotel.getRooms().size(); i++) {
            Room room = hotel.getRooms().get(i);
            System.out.println("[" + (i + 1) + "] " + room.getName() + " {Status: " + (room.isAvailable(LocalDate.now()) ? "Available" : "Booked") + ", Price: " + room.getPricePerNight() + ", Availability: " + getRoomAvailability(room) + "}");
        }

        int choice = getIntInput("Select a room to view details (or 0 to return): ");
        if (choice == 0) return;

        if (choice < 1 || choice > hotel.getRooms().size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Room selectedRoom = hotel.getRooms().get(choice - 1);
        viewReservationsForRoom(selectedRoom, hotel);
    }

    /**
     * Returns the availability status of the room as a string.
     * 
     * @param room the room to check availability for
     * @return "Available" if the room is available, "Booked" otherwise
     */
    private String getRoomAvailability(Room room) {
        return room.isAvailable(LocalDate.now()) ? "Available" : "Booked";
    }

    /**
     * Displays the reservations for a selected room.
     * 
     * @param room the room to view reservations for
     * @param hotel the hotel containing the room
     */
    private void viewReservationsForRoom(Room room, Hotel hotel) {
        ArrayList<Reservation> reservations = hotel.getReservations().stream()
            .filter(reservation -> reservation.getRoom().equals(room))
            .collect(Collectors.toCollection(ArrayList::new));

        if (reservations.isEmpty()) {
            System.out.println("There are no reservations for this room.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println("Reservation for " + reservation.getGuestName() + " from " + reservation.getCheckIn() + " to " + reservation.getCheckOut() + ", Total Price: " + (reservation.getRoom().getPricePerNight() * (reservation.getCheckOut().toEpochDay() - reservation.getCheckIn().toEpochDay())));
            }
        }
    }

    /**
     * Displays the details of a reservation for a given guest name.
     * 
     * @param hotel the hotel to view reservation details for
     */
    private void viewReservationDetails(Hotel hotel) {
        System.out.print("Enter guest name to view reservation details: ");
        String guestName = scanner.nextLine();

        Reservation reservation = hotel.getReservation(guestName);
        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        System.out.println("Reservation Details:");
        System.out.println("Guest Name: " + reservation.getGuestName());
        System.out.println("Room: " + reservation.getRoom().getName());
        System.out.println("Check-In Date: " + reservation.getCheckIn());
        System.out.println("Check-Out Date: " + reservation.getCheckOut());
        System.out.println("Total Price: " + (reservation.getRoom().getPricePerNight() * (reservation.getCheckOut().toEpochDay() - reservation.getCheckIn().toEpochDay())));
    }

    /**
     * Prompts the user for integer input and returns the input value.
     * 
     * @param prompt the prompt message to display
     * @return the integer input from the user
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character
        return value;
    }

    /**
     * Prompts the user for a date input and returns the input value.
     * 
     * @param prompt the prompt message to display
     * @return the LocalDate input from the user
     */
    private LocalDate getDateInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return LocalDate.parse(scanner.next(), java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                System.out.print("Invalid date format. " + prompt);
            }
        }
    }
}

