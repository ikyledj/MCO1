import java.time.LocalDate;

/**
 * Represents a reservation in the hotel reservation system.
 */
public class Reservation {
    private String guestName;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;

    /**
     * Constructs a new Reservation with the specified guest name, room, check-in date, and check-out date.
     *
     * @param guestName the name of the guest
     * @param room the room reserved
     * @param checkIn the check-in date
     * @param checkOut the check-out date
     */
    public Reservation(String guestName, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.guestName = guestName;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    /**
     * Gets the name of the guest.
     *
     * @return the guest's name
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * Gets the room reserved.
     *
     * @return the reserved room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Gets the check-in date.
     *
     * @return the check-in date
     */
    public LocalDate getCheckIn() {
        return checkIn;
    }

    /**
     * Gets the check-out date.
     *
     * @return the check-out date
     */
    public LocalDate getCheckOut() {
        return checkOut;
    }
}
