/**
 * Represents a standard type of hotel room with a specific price per night.
 * Inherits from the Room class.
 */
public class StandardRoom extends Room {
    private double pricePerNight;

    /**
     * Constructs a new StandardRoom with the specified name and price per night.
     *
     * @param name the name of the standard room
     * @param pricePerNight the price per night for the standard room
     */
    public StandardRoom(String name, double pricePerNight) {
        super(name, pricePerNight);
        this.pricePerNight = pricePerNight;
    }

    /**
     * Gets the price per night for the standard room.
     *
     * @return the price per night for the standard room
     */
    @Override
    public double getPricePerNight() {
        return pricePerNight;
    }

    /**
     * Sets the price per night for the standard room.
     *
     * @param price the new price per night for the standard room
     */
    @Override
    public void setPricePerNight(double price) {
        this.pricePerNight = price;
    }
}
