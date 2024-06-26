import java.time.LocalDate;
import java.util.Arrays;

/**
 * Represents a room in a hotel.
 */
public abstract class Room {
    private String name;
    private boolean[] availability;
    private double pricePerNight;

    /**
     * Constructs a new Room with the specified name and price per night.
     *
     * @param name the name of the room
     * @param pricePerNight the price per night for the room
     */
    public Room(String name, double pricePerNight) {
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.availability = new boolean[31];
        Arrays.fill(availability, true);
    }

    /**
     * Gets the name of the room.
     *
     * @return the name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * Renames the room.
     *
     * @param newName the new name for the room
     */
    public void renameRoom(String newName) {
        this.name = newName;
    }

    /**
     * Gets the price per night for the room.
     *
     * @return the price per night for the room
     */
    public double getPricePerNight() {
        return pricePerNight;
    }

    /**
     * Sets the price per night for the room.
     *
     * @param pricePerNight the new price per night for the room
     */
    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    /**
     * Checks if the room is available on the specified date.
     *
     * @param date the date to check availability for
     * @return true if the room is available, false otherwise
     */
    public boolean isAvailable(LocalDate date) {
        int day = date.getDayOfMonth() - 1;
        return availability[day];
    }

    /**
     * Checks if the room is available between the specified check-in and check-out dates.
     *
     * @param checkIn the check-in date
     * @param checkOut the check-out date
     * @return true if the room is available for the entire period, false otherwise
     */
    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        int startDay = checkIn.getDayOfMonth() - 1;
        int endDay = checkOut.getDayOfMonth() - 1;
        for (int i = startDay; i <= endDay; i++) {
            if (!availability[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Books the room for the specified period.
     *
     * @param checkIn the check-in date
     * @param checkOut the check-out date
     */
    public void bookRoom(LocalDate checkIn, LocalDate checkOut) {
        int startDay = checkIn.getDayOfMonth() - 1;
        int endDay = checkOut.getDayOfMonth() - 1;
        for (int i = startDay; i <= endDay; i++) {
            availability[i] = false;
        }
    }

    /**
     * Cancels the booking for the specified period.
     *
     * @param checkIn the check-in date
     * @param checkOut the check-out date
     */
    public void cancelBooking(LocalDate checkIn, LocalDate checkOut) {
        int startDay = checkIn.getDayOfMonth() - 1;
        int endDay = checkOut.getDayOfMonth() - 1;
        for (int i = startDay; i <= endDay; i++) {
            availability[i] = true;
        }
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", pricePerNight=" + pricePerNight +
                '}';
    }
}



