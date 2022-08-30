package examples.example2;

import db.DB;

import java.sql.*;
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
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, "Lisa Jung");
            statement.setString(2, "lisa@gmail.com");
            statement.setDate(3, new java.sql.Date(sdf.parse("10/09/1995").getTime()));
            statement.setDouble(4, 4500.0);
            statement.setInt(5, 3);

            int rowsAffected = statement.executeUpdate();
            //updates data and returns an integer indicating how many lines were altered

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    System.out.println("Insert completed. Generated ID: " + id);
                }
            } else {
                System.out.println("No rows affected.");
            }
        }
        catch (SQLException | ParseException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
