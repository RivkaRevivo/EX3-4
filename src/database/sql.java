package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sql
{
    public static void PrintFromDatabase()
    {
        double[] game = new double[11];
        game[1] = 2.12825983E9;
        String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop";
        String jdbcUser="student";
        String jdbcPassword="student";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

            Statement statement = connection.createStatement();

            String allCustomersQuery = "SELECT * FROM logs where FirstID = 204375455 and SecondID = 312531031;";
            ResultSet resultSet = statement.executeQuery(allCustomersQuery);
            resultSet.last();
            double LastGameScore = resultSet.getDouble("Point");
            double GameID = resultSet.getDouble("SomeDouble");

            allCustomersQuery = "SELECT * FROM logs where FirstID = 204375455 and SecondID = 312531031 and SomeDouble = "
                                       + GameID + " ORDER BY point DESC;";
            resultSet = statement.executeQuery(allCustomersQuery);
            int i = 1;
            while (resultSet.next())
            {
                if(resultSet.getDouble("Point") > LastGameScore)
                    i++;
                else
                    break;
            }

            System.out.println("In the Last Game We reach the " + i + " place compere the other game we played in Game number " + GetGameNumByID(GameID));

            allCustomersQuery = "SELECT * FROM logs where SomeDouble = " + GameID + " ORDER BY point DESC;";
            resultSet = statement.executeQuery(allCustomersQuery);
            i = 1;
            while (resultSet.next())
            {
                if(resultSet.getDouble("Point") > LastGameScore)
                    i++;
                else
                    break;
            }

            System.out.println("In the Last Game We reach the " + i + " place compere the other game the entire class played in Game number " + GetGameNumByID(GameID));


            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("Vendor Error: " + sqle.getErrorCode());
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        PrintFromDatabase();
    }

    private static int GetGameNumByID(double GameID)
    {
        if(GameID == 2.12825983E9)
            return 1;
        if(GameID == 1.149748017E9)
            return 2;
        if(GameID == -6.8331707E8)
            return 3;
        if(GameID == 1.193961129E9)
            return 4;
        if(GameID == 1.577914705E9)
            return 5;
        if(GameID == -1.315066918E9)
            return 6;
        if(GameID == -1.377331871E9)
            return 7;
        if(GameID == 3.06711633E9)
            return 8;
        if(GameID == 2.12825983E9)
            return 9;
        else return -1;

    }
}
