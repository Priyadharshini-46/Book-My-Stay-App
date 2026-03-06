import java.util.HashMap;
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println(" Hotel Booking System ");
        System.out.println(" Version: 4.1 ");
        System.out.println("==================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room domain objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Perform search
        SearchService searchService = new SearchService(inventory);

        System.out.println("\n--- Available Rooms ---");

        searchService.displayAvailableRoom(single);
        searchService.displayAvailableRoom(doubleRoom);
        searchService.displayAvailableRoom(suite);

        System.out.println("\nSearch completed. Inventory state unchanged.");
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 10);
        inventory.put("Double", 5);
        inventory.put("Suite", 0); // Example unavailable room
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRoom(Room room) {

        int available = inventory.getAvailability(room.getRoomType());

        // Defensive check
        if (available > 0) {
            room.displayRoomDetails();
            System.out.println("Available Rooms: " + available);
            System.out.println("--------------------------------");
        }
    }
}

abstract class Room {

    private String roomType;
    private int beds;
    private int size;
    private double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Room Size: " + size + " sq ft");
        System.out.println("Price per Night: $" + price);
    }
}

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single", 1, 200, 100.0);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double", 2, 350, 180.0);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite", 3, 600, 350.0);
    }
}