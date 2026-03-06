import java.util.HashMap;
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println(" Hotel Booking System ");
        System.out.println(" Version: 3.1 ");
        System.out.println("==================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        System.out.println("\n--- Initial Room Inventory ---");
        inventory.displayInventory();

        // Example availability check
        System.out.println("\nAvailable Single Rooms: "
                + inventory.getAvailability("Single"));

        // Example update
        System.out.println("\nBooking 1 Single Room...");
        inventory.updateAvailability("Single", -1);

        // Display updated inventory
        System.out.println("\n--- Updated Room Inventory ---");
        inventory.displayInventory();

        System.out.println("\nApplication execution completed.");
    }
}

class RoomInventory {

    // HashMap storing room type -> availability
    private HashMap<String, Integer> inventory;
    public RoomInventory() {

        inventory = new HashMap<>();

        // Register room types with counts
        inventory.put("Single", 10);
        inventory.put("Double", 5);
        inventory.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int change) {

        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    public void displayInventory() {

        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Rooms Available: "
                    + inventory.get(roomType));
        }
    }
}