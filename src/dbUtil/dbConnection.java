package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * dbConnection class, class that help us connect with the database .
 */

public class dbConnection {

    private static final String USERNAME = "dbUser";
    private static final String PASSWORD = "dbPassword";
    private static final String CONN = "jdbc:mysql://localhost/school";

    private static final String SQCONN = "jdbc:sqlite:school.sqlite";

        public static Connection getConnection() throws SQLException{

                try {

                    Class.forName("org.sqlite.JDBC");
                    return DriverManager.getConnection(SQCONN);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                return null;
        }
}
