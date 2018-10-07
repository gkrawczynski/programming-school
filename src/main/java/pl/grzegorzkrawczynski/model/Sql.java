package pl.grzegorzkrawczynski.model;

final class Sql {

    private String createUsersTable = "" +
            "CREATE TABLE users (\n"+
            "id INT AUTO_INCREMENT PRIMARY KEY,\n"+
            "username VARCHAR(255) NOT NULL,\n"+
            "email VARCHAR(255) NOT NULL UNIQUE,\n"+
            "password VARCHAR(255) NOT NULL\n"+
            ");";

    private String createExercisesTable = "" +
            "CREATE TABLE exercise (\n" +
            "  id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "  title VARCHAR(255) NOT NULL,\n" +
            "  description TEXT NOT NULL\n" +
            ");";

    private String createSolutionsTable = "" +
            "CREATE TABLE solution (\n" +
            "  id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "  users_id INT,\n" +
            "  exercise_id INT(11),\n" +
            "  username VARCHAR(255) NOT NULL,\n" +
            "  email VARCHAR(255) NOT NULL UNIQUE,\n" +
            "  password VARCHAR(255) NOT NULL,\n" +
            "  FOREIGN KEY (users_id) REFERENCES users(id),\n" +
            "  FOREIGN KEY (exercise_id) REFERENCES exercise(id)\n" +
            ");";

    private String createUserGroupTable = "" +
            "CREATE TABLE user_group (\n" +
            "  id INT(11) AUTO_INCREMENT PRIMARY KEY,\n" +
            "  name VARCHAR(255) NOT NULL\n" +
            ");";

    private Sql(){

    }


}
