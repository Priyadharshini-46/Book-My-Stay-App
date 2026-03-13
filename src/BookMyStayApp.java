import java.util.*;

// Custom Exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// Validator class
class InvalidBookingValidator {

    private Map<String, Integer> roomInventory;

    public InvalidBookingValidator(Map<String, Integer> roomInventory) {
        this.roomInventory = roomInventory;
    }

    // Validate booking request
    public void validate(String roomType) throws InvalidBookingException {

        // Validate room type
        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }

        // Validate availability
        if (roomInventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }

    // Reduce inventory after successful booking
    public void allocateRoom(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " : " + roomInventory.get(type));
        }
    }
}

public class BookMyStayApp{

    public static void main(String[] args) {

        Map<String, Integer> roomInventory = new HashMap<>();

        // Initial inventory
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 1);
        roomInventory.put("Suite", 0);

        InvalidBookingValidator validator = new InvalidBookingValidator(roomInventory);

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Guest Name:");
        String guestName = sc.nextLine();

        System.out.println("Enter Room Type (Standard / Deluxe / Suite):");
        String roomType = sc.nextLine();

        try {

            // Validate booking
            validator.validate(roomType);

            // If valid, allocate room
            validator.allocateRoom(roomType);

            Reservation reservation = new Reservation("RES1001", guestName, roomType);

            System.out.println("\nBooking Confirmed!");
            System.out.println(reservation);

        } catch (InvalidBookingException e) {

            // Graceful error handling
            System.out.println("\nBooking Failed: " + e.getMessage());

        }

        // System continues running safely
        validator.displayInventory();

        sc.close();
    }
}