package pl.grzegorzkrawczynski.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserGroup {
    private int id;
    private String name;

    public UserGroup() {
    }

    public UserGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
        PreparedStatement sql = connection.prepareStatement("INSERT INTO user_group (name) VALUES (?)", new String[] {"id"});
        sql.setString(1, name);
        sql.executeUpdate();
        ResultSet rs = sql.getGeneratedKeys();
        if(rs.next()) {
            id = rs.getInt(1);
        }
    }

    private void update(Connection connection) throws  SQLException{
        PreparedStatement sql = connection.prepareStatement("UPDATE user_group SET name = ? WHERE  id = ?)");
        sql.setString(1, name);
        sql.setInt(2, id);
        sql.executeUpdate();
    }

    public static void delete(Connection connection, int id) throws SQLException{
        if(id != 0){
            String query = "DELETE FROM user_group WHERE id=?";
            PreparedStatement sql = connection.prepareStatement(query);
            sql.setInt(1, id);
            sql.executeUpdate();
            id = 0;
        }
    }

    public static UserGroup findById(Connection connection, int id) throws SQLException {
        String query = "SELECT name FROM user_group WHERE id = ?";
        PreparedStatement sql = connection.prepareStatement(query);
        sql.setInt(1, id);
        ResultSet rs = sql.executeQuery();
        if(rs.next()){
            UserGroup userGroup = new UserGroup();
            userGroup.id = id;
            userGroup.name = rs.getString("name");
            return userGroup;
        }else{
            return null;
        }
    }

    public static UserGroup[] loadAll(Connection connection) throws SQLException{
        ArrayList<UserGroup> userGroup= new ArrayList<UserGroup>();
        String query = "SELECT * FROM user_group";
        PreparedStatement sql = connection.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        while(rs.next()){
            UserGroup loadedUserGroup = new UserGroup();
            loadedUserGroup.id = rs.getInt("id");
            loadedUserGroup.name = connection.prepareStatement("SELECT * FROM user_group").executeQuery().getString("name");
            userGroup.add(loadedUserGroup);
        }
        UserGroup[] userGroupsArray = new UserGroup[userGroup.size()];
        userGroupsArray = userGroup.toArray(userGroupsArray);
        return userGroupsArray;
    }
}
