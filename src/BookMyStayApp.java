public class BookMyStayApp {
        public static void main(String[] args) {

            System.out.println("==================================");
            System.out.println(" Hotel Booking System ");
            System.out.println(" Version: 2.1 ");
            System.out.println("==================================");

            // Creating room objects using polymorphism
            Room singleRoom = new SingleRoom();
            Room doubleRoom = new DoubleRoom();
            Room suiteRoom = new SuiteRoom();

            // Static availability variables
            int singleRoomAvailability = 10;
            int doubleRoomAvailability = 5;
            int suiteRoomAvailability = 2;

            // Display Single Room details
            System.out.println("\n--- Single Room Details ---");
            singleRoom.displayRoomDetails();
            System.out.println("Available Rooms: " + singleRoomAvailability);

            // Display Double Room details
            System.out.println("\n--- Double Room Details ---");
            doubleRoom.displayRoomDetails();
            System.out.println("Available Rooms: " + doubleRoomAvailability);

            // Display Suite Room details
            System.out.println("\n--- Suite Room Details ---");
            suiteRoom.displayRoomDetails();
            System.out.println("Available Rooms: " + suiteRoomAvailability);

            System.out.println("\nApplication execution completed.");
        }
    }

    abstract class Room {

        private String roomType;
        private int numberOfBeds;
        private int roomSize;
        private double pricePerNight;

        public Room(String roomType, int numberOfBeds, int roomSize, double pricePerNight) {
            this.roomType = roomType;
            this.numberOfBeds = numberOfBeds;
            this.roomSize = roomSize;
            this.pricePerNight = pricePerNight;
        }

        // Method to display room details
        public void displayRoomDetails() {
            System.out.println("Room Type: " + roomType);
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Room Size: " + roomSize + " sq ft");
            System.out.println("Price per Night: $" + pricePerNight);
        }
    }

    class SingleRoom extends Room {

        public SingleRoom() {
            super("Single Room", 1, 200, 100.0);
        }
    }

    class DoubleRoom extends Room {

        public DoubleRoom() {
            super("Double Room", 2, 350, 180.0);
        }
    }

    class SuiteRoom extends Room {

        public SuiteRoom() {
            super("Suite Room", 3, 600, 350.0);
        }
    }
