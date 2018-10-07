package pl.grzegorzkrawczynski.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        PreparedStatement sql = connection.prepareStatement("INSERT INTO exercise (title, description) VALUES (?, ?)", new String[] {"id"});
        sql.setString(1, title);
        sql.setString(2, description);
        sql.executeUpdate();
        ResultSet rs = sql.getGeneratedKeys();
        if(rs.next()) {
            id = rs.getInt(1);
        }
    }

    private void update(Connection connection) throws  SQLException{
        PreparedStatement sql = connection.prepareStatement("UPDATE exercise SET title = ?, description = ? WHERE  id = ?)");
        sql.setString(1, title);
        sql.setString(2, description);
        sql.setInt(3, id);
        sql.executeUpdate();
    }

    public void delete(Connection connection) throws SQLException{
        if(this.id != 0){
            String query = "DELETE FROM exercise WHERE id=?";
            PreparedStatement sql = connection.prepareStatement(query);
            sql.setInt(1, this.id);
            sql.executeUpdate();
            this.id = 0;
        }
    }

    public static Exercise findById(Connection connection, int id) throws SQLException {
        String query = "SELECT title, description FROM exercise WHERE id = ?";
        PreparedStatement sql = connection.prepareStatement(query);
        sql.setInt(1, id);
        ResultSet rs = sql.executeQuery();
        if(rs.next()){
            Exercise exercise = new Exercise();
            exercise.id = id;
            exercise.title = rs.getString("title");
            exercise.description = rs.getString("description");
            return exercise;
        }else{
            return null;
        }
    }

    public static Exercise[] loadAll(Connection connection) throws SQLException{
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        String query = "SELECT * FROM exercise";
        PreparedStatement sql = connection.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        while(rs.next()){
            Exercise loadedExercise = new Exercise();
            loadedExercise.id = rs.getInt("id");
            loadedExercise.title = connection.prepareStatement("SELECT * FROM exercise").executeQuery().getString("title");
            loadedExercise.description = rs.getString("description");
            exercises.add(loadedExercise);
        }
        Exercise[] exercisesArray = new Exercise[exercises.size()];
        exercisesArray = exercises.toArray(exercisesArray);
        return exercisesArray;
    }
}
