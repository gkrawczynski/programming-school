package pl.grzegorzkrawczynski.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exercise {
    private int id;
    private String title;
    private String description;

    public Exercise() {
    }

    public Exercise(int id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void save(Connection connection) throws SQLException {
        if(id == 0){
            insert(connection);
        } else {
            update(connection);
        }
    }

    private void insert(Connection connection) throws SQLException{          //wyrzuca kolumnę o nazwie "id"  z auto. generated nr id
        PreparedStatement sql = connection.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)", new String[] {"id"});
        sql.setString(1, username);
        sql.setString(2, email);
        sql.setString(3, password);
        sql.executeUpdate();
        ResultSet rs = sql.getGeneratedKeys();
        if(rs.next()) {
            id = rs.getInt(1);
        }
    }

    private void update(Connection connection) throws  SQLException{
        PreparedStatement sql = connection.prepareStatement("UPDATE users SET username = ?, email = ?, password = ? WHERE  id = ?)");
        sql.setString(1, username);
        sql.setString(2, email);
        sql.setString(3, password);
        sql.setInt(4, id);
        sql.executeUpdate();
    }
}
