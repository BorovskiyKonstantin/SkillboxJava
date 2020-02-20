import java.sql.*;

public class DBConnection {
    private static Connection connection;

    private static String dbName = "learn";
    private static String dbUser = "root";
    private static String dbPass = "test";

    private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("SET GLOBAL max_allowed_packet= 100*1024*1024");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id)"
                        + ")");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsert() throws SQLException {
        String sql = "INSERT INTO voter_count(name, birthDate, `count`)" +
                "VALUES " + insertQuery.toString();
        DBConnection.getConnection().createStatement().execute(sql);
        insertQuery = new StringBuilder();
    }

    public static void countVoter(String name, String birthDay) throws SQLException {
        birthDay = birthDay.replace('.', '-');
        insertQuery.append((insertQuery.length() > 0 ? "," : "") + "('" + name + "', '" + birthDay + "', 1)");
        if (insertQuery.length() > 50_000_000) {
            executeMultiInsert();
            insertQuery = new StringBuilder();
        }
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT * FROM (" +
                "SELECT name, birthDate, SUM(count) as counts " +
                "FROM learn.voter_count GROUP BY birthDate,name " +
                "ORDER BY counts DESC) AS new_table " +
                "WHERE counts > 1;";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("counts"));
        }
    }
}
