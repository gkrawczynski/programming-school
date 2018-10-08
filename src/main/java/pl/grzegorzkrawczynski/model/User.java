package pl.grzegorzkrawczynski.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private int user_group_id;

    public User() {

    }

    public User(String username, String email, String password, int user_group_id) {
        this.id = id;
        this.username = username;
        this.email = email;
        setPassword(password);
        this.user_group_id = user_group_id;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(int user_group_id) {
        this.user_group_id = user_group_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user_group_id=" + user_group_id +
                '}';
    }

    public void save(Connection connection) throws SQLException{
        if(id == 0){
            insert(connection);
        } else {
            update(connection);
        }
    }

    private void insert(Connection connection) throws SQLException{          //wyrzuca kolumnę o nazwie "id"  z auto. generated nr id
        PreparedStatement sql = connection.prepareStatement("INSERT INTO users (username, email, password, user_group_id) VALUES (?, ?, ?, ?)", new String[] {"id"});
        sql.setString(1, username);
        sql.setString(2, email);
        sql.setString(3, password);
        sql.setInt(4, user_group_id);
        sql.executeUpdate();
        ResultSet rs = sql.getGeneratedKeys();
        if(rs.next()) {
                id = rs.getInt(1);
        }
    }

    private void update(Connection connection) throws  SQLException{
        PreparedStatement sql = connection.prepareStatement("UPDATE users SET username = ?, email = ?, password = ?, user_group_id = ? WHERE  id = ?)");
        sql.setString(1, username);
        sql.setString(2, email);
        sql.setString(3, password);
        sql.setInt(4, user_group_id);
        sql.setInt(5, id);
        sql.executeUpdate();
    }

    public static void delete(Connection connection, int id) throws SQLException{
        if(id != 0){
            String query = "DELETE FROM users WHERE id=?";
            PreparedStatement sql = connection.prepareStatement(query);
            sql.setInt(1, id);
            sql.executeUpdate();
            id = 0;
        }
    }

    public static User findById(Connection connection, int id) throws SQLException {
        String query = "SELECT username, email, password FROM users WHERE id = ?";
        PreparedStatement sql = connection.prepareStatement(query);
        sql.setInt(1, id);
        ResultSet rs = sql.executeQuery();
        if(rs.next()){
            User user = new User();
            user.id = id;
            user.username = rs.getString("username");
            user.email = rs.getString("email");
            user.password = rs.getString("password");
            user.user_group_id = rs.getInt("user_group_id");
            return user;
        }else{
            return null;
        }
    }

    public static User[] loadAllUsers(Connection connection) throws SQLException{
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM users";
        PreparedStatement sql = connection.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        while(rs.next()){
            User loadedUser = new User();
            loadedUser.id = rs.getInt("id");
            loadedUser.username = connection.prepareStatement("SELECT * FROM users").executeQuery().getString("username");
            loadedUser.password = rs.getString("password");
            loadedUser.email = rs.getString("email");
            loadedUser.user_group_id = rs.getInt("user_group_id");
            users.add(loadedUser);
        }
        User[] usersArray = new User[users.size()];
        usersArray = users.toArray(usersArray);
        return usersArray;
    }

    public static User[] loadAllByGroupId(Connection connection, int id) throws SQLException{
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM users LEFT JOIN user_group AS ug ON users.user_group_id = ug.id WHERE user_group_id=?;";
        PreparedStatement sql = connection.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        while(rs.next()){
            User loadedUser = new User();
            loadedUser.id = rs.getInt("id");
            loadedUser.username = rs.getString("username");
            loadedUser.email = rs.getString("email");
            loadedUser.user_group_id = rs.getInt("user_group_id");
            users.add(loadedUser);
        }
        User[] usersArray = new User[users.size()];
        usersArray = users.toArray(usersArray);
        return usersArray;
    }
}
