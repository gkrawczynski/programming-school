package pl.grzegorzkrawczynski.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Solution {
    private int id;
    private String created;
    private String updated;
    private String description;

    public Solution() {
    }

    public Solution(int id, String created, String updated, String description) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
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
        PreparedStatement sql = connection.prepareStatement("INSERT INTO solution (created, updated, description ) VALUES (?, ?, ?)", new String[] {"id"});
        sql.setString(1, created);
        sql.setString(2, updated);
        sql.setString(3, description);
        sql.executeUpdate();
        ResultSet rs = sql.getGeneratedKeys();
        if(rs.next()) {
            id = rs.getInt(1);
        }
    }

    private void update(Connection connection) throws  SQLException{
        PreparedStatement sql = connection.prepareStatement("UPDATE solution SET created = ?, updated = ?, description = ? WHERE  id = ?)");
        sql.setString(1, created);
        sql.setString(2, updated);
        sql.setString(3, description);
        sql.setInt(4, id);
        sql.executeUpdate();
    }

    public void delete(Connection connection) throws SQLException{
        if(this.id != 0){
            String query = "DELETE FROM solution WHERE id=?";
            PreparedStatement sql = connection.prepareStatement(query);
            sql.setInt(1, this.id);
            sql.executeUpdate();
            this.id = 0;
        }
    }

    public static Solution findById(Connection connection, int id) throws SQLException {
        String query = "SELECT created, updated, description FROM solution WHERE id = ?";
        PreparedStatement sql = connection.prepareStatement(query);
        sql.setInt(1, id);
        ResultSet rs = sql.executeQuery();
        if(rs.next()){
            Solution solution = new Solution();
            solution.id = id;
            solution.created = rs.getString("created");
            solution.updated = rs.getString("updated");
            solution.description = rs.getString("description");
            return solution;
        }else{
            return null;
        }
    }

    public static Solution[] loadAll(Connection connection) throws SQLException{
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String query = "SELECT * FROM solution";
        PreparedStatement sql = connection.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        while(rs.next()){
            Solution loadedSolution = new Solution();
            loadedSolution.id = rs.getInt("id");
            loadedSolution.created = connection.prepareStatement("SELECT * FROM solution").executeQuery().getString("created");
            loadedSolution.updated = rs.getString("updated");
            loadedSolution.description = rs.getString("description");
            solutions.add(loadedSolution);
        }
        Solution[] solutionsArray = new Solution[solutions.size()];
        solutionsArray = solutions.toArray(solutionsArray);
        return solutionsArray;
    }
}
