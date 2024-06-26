/**
 * Represents a deluxe room in a hotel, which is a type of Room with additional amenities or a nicer view.
 */
public class DeluxeRoom extends Room {
    private double pricePerNight;

    /**
     * Constructs a new DeluxeRoom with the specified name and price per night.
     *
     * @param name the name of the deluxe room
     * @param pricePerNight the price per night for the deluxe room
     */
    public DeluxeRoom(String name, double pricePerNight) {
        super(name, pricePerNight);
        this.pricePerNight = pricePerNight;
    }

    /**
     * Gets the price per night for the deluxe room.
     *
     * @return the price per night for the deluxe room
     */
    @Override
    public double getPricePerNight() {
        return pricePerNight;
    }

    /**
     * Sets the price per night for the deluxe room.
     *
     * @param price the new price per night for the deluxe room
     */
    @Override
    public void setPricePerNight(double price) {
        this.pricePerNight = price;
    }
}
