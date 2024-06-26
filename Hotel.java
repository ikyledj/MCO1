import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Hotel with a name, a list of rooms, reservations, and a base price.
 */
public class Hotel {
    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;
    private double basePrice;

    /**
     * Constructs a new Hotel with the specified name and base price.
     *
     * @param name      the name of the hotel
     * @param basePrice the base price of the hotel
     */
    public Hotel(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    /**
     * Gets the name of the hotel.
     *
     * @return the name of the hotel
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the hotel.
     *
     * @param name the new name of the hotel
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of rooms in the hotel.
     *
     * @return the list of rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Gets the list of reservations in the hotel.
     *
     * @return the list of reservations
     */
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Adds a new room to the hotel.
     *
     * @param room the room to be added
     */
    public void addRoom(Room room) {
        rooms.add(room);
    }

    /**
     * Removes a room from the hotel by its name if it has no active reservations.
     *
     * @param roomName the name of the room to be removed
     */
    public void removeRoom(String roomName) {
        rooms.removeIf(room -> room.getName().equals(roomName) && noActiveReservations(roomName));
    }

    /**
     * Checks if there are no active reservations for a given room.
     *
     * @param roomName the name of the room
     * @return true if there are no active reservations for the room, false otherwise
     */
    private boolean noActiveReservations(String roomName) {
        return reservations.stream().noneMatch(reservation -> reservation.getRoom().getName().equals(roomName));
    }

    /**
     * Updates the base price of the hotel.
     *
     * @param basePrice the new base price
     */
    public void updateBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * Adds a new reservation to the hotel.
     *
     * @param reservation the reservation to be added
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Removes a reservation from the hotel by the guest's name.
     *
     * @param guestName the name of the guest whose reservation is to be removed
     */
    public void removeReservation(String guestName) {
        reservations.removeIf(reservation -> reservation.getGuestName().equals(guestName));
    }

    /**
     * Gets a room from the hotel by its name.
     *
     * @param roomName the name of the room
     * @return the room with the specified name, or null if no such room exists
     */
    public Room getRoom(String roomName) {
        return rooms.stream().filter(room -> room.getName().equals(roomName)).findFirst().orElse(null);
    }

    /**
     * Gets a reservation from the hotel by the guest's name.
     *
     * @param guestName the name of the guest
     * @return the reservation for the specified guest, or null if no such reservation exists
     */
    public Reservation getReservation(String guestName) {
        return reservations.stream().filter(reservation -> reservation.getGuestName().equals(guestName)).findFirst().orElse(null);
    }

    /**
     * Checks if the hotel has no reservations.
     *
     * @return true if there are no reservations, false otherwise
     */
    public boolean hasNoReservations() {
        return reservations.isEmpty();
    }

    /**
     * Returns a string representation of the hotel.
     *
     * @return a string representation of the hotel
     */
    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", rooms=" + rooms.size() +
                ", reservations=" + reservations.size() +
                ", basePrice=" + basePrice +
                '}';
    }
}
