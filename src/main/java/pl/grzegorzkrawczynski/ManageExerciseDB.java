package pl.grzegorzkrawczynski;

import pl.grzegorzkrawczynski.model.Exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageExerciseDB {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/programming_school?useSSL=false", "root", "coderslab")) {
            while (true) {

                System.out.println("All exercises: ");
                Exercise.loadAll(connection);
                Scanner sc = new Scanner(System.in);
                System.out.println("What would you like to do: add/edit/delete/quit?");
                String selectedOption = sc.nextLine();
                Exercise exercise = new Exercise();

                switch (selectedOption) {
                    case "add":
                        System.out.println("Title?");
                        exercise.setTitle(sc.nextLine());
                        System.out.println("Description?");
                        exercise.setDescription(sc.nextLine());
                        exercise.save(connection);
                        break;
                }

                switch (selectedOption) {
                    case "edit":
                        System.out.println("Id?");
                        exercise = exercise.findById(connection, Integer.parseInt(sc.nextLine()));
                        System.out.println("Title?");
                        exercise.setTitle(sc.nextLine());
                        System.out.println("Description?");
                        exercise.setDescription(sc.nextLine());
                        exercise.save(connection);
                        break;

                    case "delete":
                        System.out.println("Id?");
                        int id = Integer.parseInt(sc.nextLine());
                        Exercise.delete(connection, id);
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
