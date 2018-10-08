package pl.grzegorzkrawczynski;

import pl.grzegorzkrawczynski.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageUsersDB {
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/programming_school?useSSL=false", "root", "coderslab")){
            while(true){

                System.out.println("All users: ");
                User.loadAllUsers(connection);
                Scanner sc = new Scanner(System.in);
                System.out.println("What would you like to do: add/edit/delete/quit?");
                String selectedOption = sc.nextLine();
                User user = new User();

                switch (selectedOption){
                    case "add":
                        System.out.println("Name?");
                        user.setUsername(sc.nextLine());
                        System.out.println("Email?");
                        user.setEmail(sc.nextLine());
                        System.out.println("Password?");
                        user.setPassword(sc.nextLine());
                        System.out.println("User groupd id?");
                        user.setUser_group_id(Integer.parseInt(sc.nextLine()));
                        user.save(connection);
                        break;

                    case "edit":
                        System.out.println("Id?");
                        user = User.findById(connection, Integer.parseInt(sc.nextLine()));
                        System.out.println("Name?");
                        user.setUsername(sc.nextLine());
                        System.out.println("Email?");
                        user.setEmail(sc.nextLine());
                        System.out.println("Password?");
                        user.setPassword(sc.nextLine());
                        System.out.println("User groupd id?");
                        user.setUser_group_id(Integer.parseInt(sc.nextLine()));
                        user.save(connection);
                        break;

                    case "delete":
                        System.out.println("Id?");
                        int id = Integer.parseInt(sc.nextLine());
                        User.delete(connection, id);
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
