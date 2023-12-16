import java.sql.*;
import java.util.Scanner;

public class DocumentRoutingSystemCRUD {

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cse7l";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
                Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {

            while (true) {
                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        addDocument(connection, scanner);
                        break;
                    case 2:
                        viewDocuments(connection);
                        break;
                    case 3:
                        updateDocument(connection, scanner);
                        break;
                    case 4:
                        deleteDocument(connection, scanner);
                        break;
                    case 5:
                        addOffice(connection, scanner);
                        break;
                    case 6:
                        viewOffices(connection);
                        break;
                    case 7:
                        addRoute(connection, scanner);
                        break;
                    case 8:
                        viewRoutes(connection);
                        break;
                    case 9:
                        updateOffice(connection, scanner);
                        break;
                    case 10:
                        deleteOffice(connection, scanner);
                        break;
                    case 11:
                        updateRoute(connection, scanner);
                        break;
                    case 12:
                        deleteRoute(connection, scanner);
                        break;
                    case 13:
                        System.out.println("Exiting the program. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("Document Routing System");
        System.out.println("1. Add Document");
        System.out.println("2. View Documents");
        System.out.println("3. Update Document");
        System.out.println("4. Delete Document");
        System.out.println("5. Add Office");
        System.out.println("6. View Offices");
        System.out.println("7. Add Route");
        System.out.println("8. View Routes");
        System.out.println("9. Update Office");
        System.out.println("10. Delete Office");
        System.out.println("11. Update Route");
        System.out.println("12. Delete Route");
        System.out.println("13. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addDocument(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter tracking number: ");
        String trackingNumber = scanner.nextLine();

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter content: ");
        String content = scanner.nextLine();

        System.out.print("Enter termination status: ");
        String terminationStatus = scanner.nextLine();

        String sql = "INSERT INTO document (TrackingNumber, Title, Content, TerminationStatus) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, trackingNumber);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, terminationStatus);

            preparedStatement.executeUpdate();

            System.out.println("Document added successfully!");
        }
    }

    private static void viewDocuments(Connection connection) throws SQLException {
        String sql = "SELECT * FROM document";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("List of Documents:");
            System.out.printf("%-15s %-20s %-30s %-15s%n", "DocumentID", "TrackingNumber", "Title",
                    "TerminationStatus");

            while (resultSet.next()) {
                int documentId = resultSet.getInt("DocumentID");
                String trackingNumber = resultSet.getString("TrackingNumber");
                String title = resultSet.getString("Title");
                String terminationStatus = resultSet.getString("TerminationStatus");

                System.out.printf("%-15d %-20s %-30s %-15s%n", documentId, trackingNumber, title,
                        terminationStatus);
            }
        }
    }

    private static void updateDocument(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the DocumentID to update: ");
        int documentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter the new tracking number: ");
        String newTrackingNumber = scanner.nextLine();

        System.out.print("Enter the new title: ");
        String newTitle = scanner.nextLine();

        System.out.print("Enter the new content: ");
        String newContent = scanner.nextLine();

        System.out.print("Enter the new termination status: ");
        String newTerminationStatus = scanner.nextLine();

        String sql = "UPDATE document SET TrackingNumber=?, Title=?, Content=?, TerminationStatus=? WHERE DocumentID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newTrackingNumber);
            preparedStatement.setString(2, newTitle);
            preparedStatement.setString(3, newContent);
            preparedStatement.setString(4, newTerminationStatus);
            preparedStatement.setInt(5, documentId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Document updated successfully!");
            } else {
                System.out.println("No document found with the given DocumentID.");
            }
        }
    }

    private static void deleteDocument(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the DocumentID to delete: ");
        int documentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        String sql = "DELETE FROM document WHERE DocumentID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, documentId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Document deleted successfully!");
            } else {
                System.out.println("No document found with the given DocumentID.");
            }
        }
    }

    private static void addOffice(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter office name: ");
        String officeName = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        String sql = "INSERT INTO office (OfficeName, Location) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, officeName);
            preparedStatement.setString(2, location);

            preparedStatement.executeUpdate();

            System.out.println("Office added successfully!");
        }
    }

    private static void viewOffices(Connection connection) throws SQLException {
        String sql = "SELECT * FROM office";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("List of Offices:");
            System.out.printf("%-15s %-20s %-30s%n", "OfficeID", "OfficeName", "Location");

            while (resultSet.next()) {
                int officeId = resultSet.getInt("OfficeID");
                String officeName = resultSet.getString("OfficeName");
                String location = resultSet.getString("Location");

                System.out.printf("%-15d %-20s %-30s%n", officeId, officeName, location);
            }
        }
    }

    private static void addRoute(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter DocumentID: ");
        int documentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter FromOfficeID: ");
        int fromOfficeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter ToOfficeID: ");
        int toOfficeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Assuming Timestamp is a date type in your database
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String sql = "INSERT INTO route (DocumentID, FromOfficeID, ToOfficeID, Timestamp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, documentId);
            preparedStatement.setInt(2, fromOfficeId);
            preparedStatement.setInt(3, toOfficeId);
            preparedStatement.setTimestamp(4, timestamp);

            preparedStatement.executeUpdate();

            System.out.println("Route added successfully!");
        }
    }

    private static void viewRoutes(Connection connection) throws SQLException {
        String sql = "SELECT * FROM route";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("List of Routes:");
            System.out.printf("%-15s %-20s %-20s %-30s%n", "RouteID", "DocumentID", "FromOfficeID", "ToOfficeID");

            while (resultSet.next()) {
                int routeId = resultSet.getInt("RouteID");
                int documentId = resultSet.getInt("DocumentID");
                int fromOfficeId = resultSet.getInt("FromOfficeID");
                int toOfficeId = resultSet.getInt("ToOfficeID");

                System.out.printf("%-15d %-20d %-20d %-30d%n", routeId, documentId, fromOfficeId, toOfficeId);
            }
        }
    }

    private static void updateOffice(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the OfficeID to update: ");
        int officeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter the new office name: ");
        String newOfficeName = scanner.nextLine();

        System.out.print("Enter the new location: ");
        String newLocation = scanner.nextLine();

        String sql = "UPDATE office SET OfficeName=?, Location=? WHERE OfficeID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newOfficeName);
            preparedStatement.setString(2, newLocation);
            preparedStatement.setInt(3, officeId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Office updated successfully!");
            } else {
                System.out.println("No office found with the given OfficeID.");
            }
        }
    }

    private static void deleteOffice(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the OfficeID to delete: ");
        int officeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        String sql = "DELETE FROM office WHERE OfficeID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, officeId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Office deleted successfully!");
            } else {
                System.out.println("No office found with the given OfficeID.");
            }
        }
    }

    private static void updateRoute(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the RouteID to update: ");
        int routeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter the new DocumentID: ");
        int newDocumentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter the new FromOfficeID: ");
        int newFromOfficeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter the new ToOfficeID: ");
        int newToOfficeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Assuming Timestamp is a date type in your database
        Timestamp newTimestamp = new Timestamp(System.currentTimeMillis());

        String sql = "UPDATE route SET DocumentID=?, FromOfficeID=?, ToOfficeID=?, Timestamp=? WHERE RouteID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newDocumentId);
            preparedStatement.setInt(2, newFromOfficeId);
            preparedStatement.setInt(3, newToOfficeId);
            preparedStatement.setTimestamp(4, newTimestamp);
            preparedStatement.setInt(5, routeId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Route updated successfully!");
            } else {
                System.out.println("No route found with the given RouteID.");
            }
        }
    }

    private static void deleteRoute(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the RouteID to delete: ");
        int routeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        String sql = "DELETE FROM route WHERE RouteID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, routeId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Route deleted successfully!");
            } else {
                System.out.println("No route found with the given RouteID.");
            }
        }
    }

}
