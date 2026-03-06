import java.util.*;

public class BookMyStayApp{

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println(" Book My Stay - Hotel Booking ");
        System.out.println(" Version: 6.1 ");
        System.out.println("==================================");

        // Initialize services
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Double"));
        bookingQueue.addRequest(new Reservation("Charlie", "Single"));
        bookingQueue.addRequest(new Reservation("David", "Suite"));

        System.out.println("\n--- Processing Booking Requests ---");

        // Process queue in FIFO order
        while (!bookingQueue.isEmpty()) {
            Reservation request = bookingQueue.getNextRequest();
            bookingService.allocateRoom(request);
        }

        System.out.println("\n--- Final Inventory State ---");
        inventory.displayInventory();

        System.out.println("\nApplication execution completed.");
    }
}

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request received from " + reservation.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
        }
    }

    public void displayInventory() {

        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}

class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs
    private Set<String> allocatedRoomIds;

    // Map room type → assigned room IDs
    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {

        this.inventory = inventory;

        allocatedRoomIds = new HashSet<>();
        allocatedRooms = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation) {

        String roomType = reservation.getRoomType();
        String guest = reservation.getGuestName();

        int available = inventory.getAvailability(roomType);

        if (available <= 0) {
            System.out.println("No " + roomType + " rooms available for " + guest);
            return;
        }

        // Generate unique room ID
        String roomId;
        do {
            roomId = roomType.substring(0,1).toUpperCase() + (100 + allocatedRoomIds.size());
        } while (allocatedRoomIds.contains(roomId));

        // Record unique ID
        allocatedRoomIds.add(roomId);

        // Map room type to allocated room IDs
        allocatedRooms.putIfAbsent(roomType, new HashSet<>());
        allocatedRooms.get(roomType).add(roomId);

        // Update inventory immediately
        inventory.decrementRoom(roomType);

        System.out.println("Reservation Confirmed for " + guest +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}