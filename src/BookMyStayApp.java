import java.util.*;

// Booking Request
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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

// Shared Booking Processor
class ConcurrentBookingProcessor {

    private Queue<BookingRequest> bookingQueue = new LinkedList<>();
    private Map<String, Integer> roomInventory;

    public ConcurrentBookingProcessor(Map<String, Integer> roomInventory) {
        this.roomInventory = roomInventory;
    }

    // Add booking request (synchronized)
    public synchronized void addBookingRequest(BookingRequest request) {
        bookingQueue.add(request);
        notifyAll();
    }

    // Process booking request
    public synchronized BookingRequest getBookingRequest() {
        if (bookingQueue.isEmpty()) {
            return null;
        }
        return bookingQueue.poll();
    }

    // Allocate room safely
    public synchronized void allocateRoom(BookingRequest request) {

        String roomType = request.getRoomType();

        if (!roomInventory.containsKey(roomType)) {
            System.out.println("Invalid room type for " + request.getGuestName());
            return;
        }

        int available = roomInventory.get(roomType);

        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            System.out.println(Thread.currentThread().getName() +
                    " booked " + roomType + " for " + request.getGuestName());
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " booking failed for " + request.getGuestName() +
                    " (No " + roomType + " rooms available)");
        }
    }
}

// Worker Thread
class BookingWorker extends Thread {

    private ConcurrentBookingProcessor processor;

    public BookingWorker(ConcurrentBookingProcessor processor, String name) {
        super(name);
        this.processor = processor;
    }

    public void run() {
        while (true) {

            BookingRequest request = processor.getBookingRequest();

            if (request == null) {
                break;
            }

            processor.allocateRoom(request);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Map<String, Integer> roomInventory = new HashMap<>();

        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 1);
        roomInventory.put("Suite", 1);

        ConcurrentBookingProcessor processor =
                new ConcurrentBookingProcessor(roomInventory);

        // Simulated booking requests
        processor.addBookingRequest(new BookingRequest("Arun", "Standard"));
        processor.addBookingRequest(new BookingRequest("Priya", "Standard"));
        processor.addBookingRequest(new BookingRequest("Rahul", "Standard"));
        processor.addBookingRequest(new BookingRequest("Meena", "Deluxe"));
        processor.addBookingRequest(new BookingRequest("Kiran", "Suite"));

        // Multiple threads processing requests
        BookingWorker t1 = new BookingWorker(processor, "Thread-1");
        BookingWorker t2 = new BookingWorker(processor, "Thread-2");
        BookingWorker t3 = new BookingWorker(processor, "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}