package pl.grzegorzkrawczynski.model;

final class Sql {

    private String createUsersTable = "" +
            "CREATE TABLE users (\n"+
            "id INT AUTO_INCREMENT PRIMARY KEY,\n"+
            "username VARCHAR(255) NOT NULL,\n"+
            "email VARCHAR(255) NOT NULL UNIQUE,\n"+
            "password VARCHAR(255) NOT NULL\n"+
            ");";

    private String createExercisesTable = "";

    private String createSolutionsTable = "";

    private String createUserGroupTable = "";

    private Sql(){

    }


}
