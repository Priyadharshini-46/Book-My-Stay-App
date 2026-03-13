import java.util.*;

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

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// Manages booking cancellations and inventory rollback
class CancellationService {

    private Map<String, Reservation> reservations;
    private Map<String, Integer> roomInventory;

    public CancellationService(Map<String, Reservation> reservations, Map<String, Integer> roomInventory) {
        this.reservations = reservations;
        this.roomInventory = roomInventory;
    }

    public void cancelReservation(String reservationId) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation ID not found.");
            return;
        }

        Reservation reservation = reservations.get(reservationId);
        String roomType = reservation.getRoomType();

        // Rollback inventory
        roomInventory.put(roomType, roomInventory.get(roomType) + 1);

        // Remove reservation
        reservations.remove(reservationId);

        System.out.println("Reservation " + reservationId + " cancelled successfully.");
        System.out.println("Inventory restored for room type: " + roomType);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " : " + roomInventory.get(type));
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Map<String, Integer> roomInventory = new HashMap<>();
        Map<String, Reservation> reservations = new HashMap<>();

        // Initial inventory
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 1);
        roomInventory.put("Suite", 1);

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("RES101", "Arun", "Standard");
        Reservation r2 = new Reservation("RES102", "Priya", "Deluxe");

        reservations.put(r1.getReservationId(), r1);
        reservations.put(r2.getReservationId(), r2);

        // Reduce inventory because rooms were booked
        roomInventory.put("Standard", roomInventory.get("Standard") - 1);
        roomInventory.put("Deluxe", roomInventory.get("Deluxe") - 1);

        CancellationService cancellationService =
                new CancellationService(reservations, roomInventory);

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Reservation ID to Cancel:");
        String cancelId = sc.nextLine();

        cancellationService.cancelReservation(cancelId);

        cancellationService.displayInventory();

        sc.close();
    }
}