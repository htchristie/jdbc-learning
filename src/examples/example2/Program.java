package examples.example2;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {

        Connection connection;
        PreparedStatement statement = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            connection = DB.getConnection();
            statement = connection.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, Birthdate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)"
            );

            statement.setString(1, "Carl Purple");
            statement.setString(2, "carl@gmail.com");
            statement.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
            statement.setDouble(4, 3000.0);
            statement.setInt(5, 4);

            int rowsAffected = statement.executeUpdate();
            //updates data and returns an integer indicating how many lines were altered

            System.out.println("Rows affected: " + rowsAffected);
        }
        catch (SQLException | ParseException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
