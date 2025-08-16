import java.sql.*;
import java.util.Scanner;

public class jdbclibrarycrud{

    static final String DB_URL = "jdbc:mysql://localhost:3306/jdbcexample";
    static final String USER = "root";
    static final String PASS = "Swara@123";

    static Connection conn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish Connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database!");

            while (true) {
                System.out.println("\n--- Library Management System ---");
                System.out.println("1. Insert Book");
                System.out.println("2. Display All Books");
                System.out.println("3. Update Book");
                System.out.println("4. Delete Book");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: insertBook(); break;
                    case 2: displayBooks(); break;
                    case 3: updateBook(); break;
                    case 4: deleteBook(); break;
                    case 5:
                        System.out.println("Exiting...");
                        conn.close();
                        sc.close();
                        return;
                    default: System.out.println("Invalid option.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void insertBook() {
        try {
            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter Title: ");
            String title = sc.nextLine();
            System.out.print("Enter Author: ");
            String author = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();

            String query = "INSERT INTO books (id, title, author, price) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setDouble(4, price);
            ps.executeUpdate();
            System.out.println("Book inserted successfully.");
        } catch (Exception e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    static void displayBooks() {
        try {
            String query = "SELECT * FROM books";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("\n--- Book Records ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Title: " + rs.getString("title") +
                                   ", Author: " + rs.getString("author") +
                                   ", Price: " + rs.getDouble("price"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }
    }

    static void updateBook() {
        try {
            System.out.print("Enter Book ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter New Title: ");
            String title = sc.nextLine();
            System.out.print("Enter New Author: ");
            String author = sc.nextLine();
            System.out.print("Enter New Price: ");
            double price = sc.nextDouble();

            String query = "UPDATE books SET title=?, author=?, price=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setDouble(3, price);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Book not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    static void deleteBook() {
        try {
            System.out.print("Enter Book ID to delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM books WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book not found.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }
}
