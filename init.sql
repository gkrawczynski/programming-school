CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
user_group_id INT,
username VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
FOREIGN KEY (user_group_id) REFERENCES user_group(id)
);

CREATE TABLE solution (
  id INT AUTO_INCREMENT PRIMARY KEY,
  users_id INT(20),
  exercise_id INT(11),
  created DATETIME,
  updated DATETIME,
  description TEXT NOT NULL,
  FOREIGN KEY (users_id) REFERENCES users(id),
  FOREIGN KEY (exercise_id) REFERENCES exercise(id)
);

CREATE TABLE exercise (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT NOT NULL
);

CREATE TABLE user_group (
  id INT(11) AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);