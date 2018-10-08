package pl.grzegorzkrawczynski;

import pl.grzegorzkrawczynski.model.User;

import java.sql.*;
import java.util.Random;

public class Boot {
    public static void main(String[] args) {

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/programming_school?useSSL=false", "root", "coderslab")){
//            PreparedStatement sql = connection.prepareStatement("SELECT  'Hello Database'");
//            ResultSet rs = sql.executeQuery();
           /* while(rs.next()) {
                String hello = rs.getString(1);
                System.out.println(hello);
        }*/

            User user = User.findById(connection, 1);
            user.setPassword("admin" + new Random().nextInt());
            System.out.println(user);

            User anna = new User("anna", "anna" + new Random().nextInt() +"@gamil.com ", "87654321", 1);
            anna.save(connection);
            System.out.println(anna);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

