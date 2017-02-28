package csr.mo;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {

        System.out.println("The program is started!");

        Testdb db = null;

        try {
            db = new Testdb("myRamDB");
        } catch (Exception ex1) {
            ex1.printStackTrace();

            return;
        }

        try {

            db.update("DROP TABLE human IF EXISTS;");
            db.update("DROP TABLE name IF EXISTS;");
            db.update("DROP TABLE second_name IF EXISTS;");

            db.update("CREATE TABLE human\n" +
                    "(\n" +
                    "  human_id INT PRIMARY KEY NOT NULL IDENTITY,\n" +
                    "  name_id INT NOT NULL,\n" +
                    "  second_id INT NOT NULL,\n" +
                    "  age INT NOT NULL\n" +
                    ");");

            db.update("CREATE TABLE name\n" +
                    "(\n" +
                    "  name_id INT NOT NULL IDENTITY,\n" +
                    "  name VARCHAR(255) NOT NULL\n" +
                    ");");

            db.update("CREATE TABLE second_name\n" +
                    "(\n" +
                    "  second_id INT NOT NULL IDENTITY,\n" +
                    "  second_name VARCHAR(255) NOT NULL\n" +
                    ");");

            String[] user_names = {"Vasya", "Petya", "Sasha"};
            String[] user_second_names = {"Ivanov", "Petrov", "Sidorov"};
            int[] age = {10, 20, 30};

            for (int row = 0; row < user_names.length; row++) {
                db.update("INSERT INTO name(name_id,name) VALUES('" + row + "','" + user_names[row] + "');");

            }
            for (int row = 0; row < user_second_names.length; row++) {
                db.update("INSERT INTO second_name(second_id,second_name) VALUES('" + row + "','" + user_second_names[row] + "');");
            }

            for (int row = 0; row < age.length; row++) {
                for (int row_names = 0; row_names < user_names.length; row_names++) {
                    for (int row_sec_names = 0; row_sec_names < user_second_names.length; row_sec_names++) {
                        db.update("INSERT INTO human(name_id,second_id,age) VALUES('" + row_names + "','" + row_sec_names + "','" + age[row] + "');");
                    }
                }

            }

        } catch (SQLException ex2) {
            ex2.printStackTrace();
        }

        try {
            //   db.query("SELECT * FROM name");
            //   db.query("SELECT * FROM second_name");
            //   db.query("SELECT * FROM human");

            System.out.println("RESULT: name <-> second_name <-> age");

            db.query("SELECT nam.name,sec_nam.SECOND_NAME,hum.AGE FROM human as hum\n" +
                    "LEFT JOIN NAME as nam on hum.NAME_ID = nam.NAME_ID\n" +
                    "LEFT JOIN SECOND_NAME as sec_nam on hum.SECOND_ID = sec_nam.SECOND_ID\n" +
                    "ORDER BY hum.HUMAN_ID\n");

            db.shutdown();
            System.out.println("All done!");

        } catch (SQLException ex3) {
            ex3.printStackTrace();
        }

    }
}
