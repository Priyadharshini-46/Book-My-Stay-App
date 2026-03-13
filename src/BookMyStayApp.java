import java.util.*;

// Represents a Reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String reservationId, String guestName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Nights: " + nights;
    }
}

// Stores confirmed reservations
class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation " + reservation.getReservationId() + " added to history.");
    }

    // Retrieve booking history
    public List<Reservation> getReservations() {
        return reservations;
    }
}

// Generates reports
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("\nBooking History:");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\nBooking Summary Report");

        int totalBookings = reservations.size();
        int totalNights = 0;

        for (Reservation r : reservations) {
            totalNights += r.getNights();
        }

        System.out.println("Total Reservations: " + totalBookings);
        System.out.println("Total Nights Booked: " + totalNights);
    }
}

// Main class
public class BookMyStayApp{

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("RES101", "Arun", "Deluxe", 2);
        Reservation r2 = new Reservation("RES102", "Priya", "Suite", 3);
        Reservation r3 = new Reservation("RES103", "Rahul", "Standard", 1);

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin views booking history
        reportService.displayAllBookings(history.getReservations());

        // Admin generates summary report
        reportService.generateSummary(history.getReservations());
    }
}