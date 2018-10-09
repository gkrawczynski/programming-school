package pl.grzegorzkrawczynski;

import pl.grzegorzkrawczynski.model.Solution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageSolutionDB {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/programming_school?useSSL=false", "root", "coderslab")) {
            while (true) {

                System.out.println("All exercises: ");
                Solution.loadAll(connection);
                Scanner sc = new Scanner(System.in);
                System.out.println("What would you like to do: add/edit/delete/quit?");
                String selectedOption = sc.nextLine();
                Solution solution = new Solution();

                switch (selectedOption) {
                    case "add":
                        System.out.println("Created?");
                        solution.setCreated(sc.nextLine());
                        System.out.println("Updated?");
                        solution.setUpdated(sc.nextLine());
                        System.out.println("Description?");
                        solution.setDescription(sc.nextLine());
                        System.out.println("User id?");
                        solution.setUsers_id(Integer.parseInt(sc.nextLine()));
                        System.out.println("Exercise id?");
                        solution.setExercise_id(Integer.parseInt(sc.nextLine()));
                        solution.save(connection);
                        break;
                }

                switch (selectedOption) {
                    case "edit":
                        System.out.println("Id?");
                        solution = solution.findById(connection, Integer.parseInt(sc.nextLine()));
                        System.out.println("Created?");
                        solution.setCreated(sc.nextLine());
                        System.out.println("Updated?");
                        solution.setUpdated(sc.nextLine());
                        System.out.println("Description?");
                        solution.setDescription(sc.nextLine());
                        System.out.println("User id?");
                        solution.setUsers_id(Integer.parseInt(sc.nextLine()));
                        System.out.println("Exercise id?");
                        solution.setExercise_id(Integer.parseInt(sc.nextLine()));
                        solution.save(connection);
                        break;

                    case "delete":
                        System.out.println("Id?");
                        int id = Integer.parseInt(sc.nextLine());
                        Solution.delete(connection, id);
                        break;

                    case "quit":
                        System.out.println("Ending program...");
                        return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
