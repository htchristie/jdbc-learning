package examples.example4;

import db.DB;
import db.DbIntegrityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DB.getConnection();
            statement = connection.prepareStatement(
                "DELETE FROM department "
                    + "WHERE "
                    + "Id = ?"
            );

            statement.setInt(1, 5);

            int rowsAffected = statement.executeUpdate();

            System.out.println("Update completed. Rows affected: " + rowsAffected);
        }
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
