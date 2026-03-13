import java.io.*;
import java.util.*;

// Reservation class (Serializable for persistence)
class Reservation implements Serializable {

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

// System state containing inventory and booking history
class SystemState implements Serializable {

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    // Save system state
    public void save(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load system state
    public SystemState load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("System state restored from file.");
            return state;

        } catch (FileNotFoundException e) {

            System.out.println("No saved state found. Starting with fresh system.");
            return null;

        } catch (Exception e) {

            System.out.println("Failed to load saved state. Starting fresh.");
            return null;
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        // Try restoring previous state
        SystemState loadedState = persistence.load();

        if (loadedState != null) {
            inventory = loadedState.inventory;
            bookings = loadedState.bookings;
        } else {

            // Fresh system state
            inventory = new HashMap<>();
            bookings = new ArrayList<>();

            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);
            inventory.put("Suite", 1);
        }

        // Simulate booking
        Reservation r1 = new Reservation("RES201", "Arun", "Standard");
        bookings.add(r1);
        inventory.put("Standard", inventory.get("Standard") - 1);

        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        System.out.println("\nCurrent Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, bookings);
        persistence.save(newState);
    }
}