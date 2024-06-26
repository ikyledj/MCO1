import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class simulates bookings and cancellations for a list of hotels.
 * It allows manual addition and cancellation of reservations.
 */
public class SimulateBookings {
    private ArrayList<Hotel> hotels;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a SimulateBookings object with a list of hotels.
     *
     * @param hotels the list of hotels to simulate bookings for
     */
    public SimulateBookings(ArrayList<Hotel> hotels) {
        this.hotels = hotels;
    }

    /**
     * Executes the booking and cancellation simulation for each hotel in the list.
     * It prompts the user to choose between adding a reservation or canceling a reservation.
     */
    public void execute() {
        System.out.println("Simulating bookings and cancellations...");
        if (hotels.isEmpty()) {
            System.out.println("No hotels available for simulation.");
            return;
        }

        // Iterate over each hotel to simulate bookings and cancellations
        for (Hotel hotel : hotels) {
            while (true) {
                printSimulationMenu(hotel.getName());
                int choice = getIntInput("Choose an option: ");

                switch (choice) {
                    case 1:
                        addReservationToHotel(hotel);
                        break;
                    case 2:
                        cancelReservationFromHotel(hotel);
                        break;
                    case 3:
                        return; // Exit simulation for this hotel
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        System.out.println("Simulation complete.");
    }

    /**
     * Prints the simulation menu for a specific hotel.
     *
     * @param hotelName Name of the hotel being managed.
     */
    private void printSimulationMenu(String hotelName) {
        System.out.println("\nSimulating for Hotel: " + hotelName);
        System.out.println("1. Add a reservation");
        System.out.println("2. Cancel a reservation");
        System.out.println("3. Back to main menu");
    }

    /**
     * Adds a reservation to the selected hotel.
     *
     * @param hotel The hotel to which the reservation will be added.
     */
    private void addReservationToHotel(Hotel hotel) {
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        String roomName = getStringInput("Enter room name: ");
        LocalDate checkIn = getDateInput("Enter check-in date (YYYY-MM-DD): ");
        LocalDate checkOut = getDateInput("Enter check-out date (YYYY-MM-DD): ");

        Room room = hotel.getRoom(roomName);
        if (room == null || !room.isAvailable(checkIn, checkOut)) {
            System.out.println("Room is not available for the specified check-in or check-out date.");
            return;
        }

        room.bookRoom(checkIn, checkOut);
        hotel.addReservation(new Reservation(guestName, room, checkIn, checkOut));
        System.out.println("Reservation added successfully.");
    }

    /**
     * Cancels a reservation from the selected hotel.
     *
     * @param hotel The hotel from which the reservation will be canceled.
     */
    private void cancelReservationFromHotel(Hotel hotel) {
        System.out.print("Enter guest name to cancel reservation: ");
        String guestName = scanner.nextLine();

        Reservation reservation = hotel.getReservation(guestName);
        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        reservation.getRoom().cancelBooking(reservation.getCheckIn(), reservation.getCheckOut());
        hotel.removeReservation(guestName);
        System.out.println("Reservation cancelled successfully.");
    }

    /**
     * Prompts the user for a string input.
     *
     * @param prompt The prompt message for the input.
     * @return The string value input by the user.
     */
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Prompts the user for a date input.
     *
     * @param prompt The prompt message for the input.
     * @return The LocalDate value input by the user.
     */
    private LocalDate getDateInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return LocalDate.parse(scanner.next(), DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                System.out.print("Invalid date format. " + prompt);
            }
        }
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
}
