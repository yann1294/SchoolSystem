package loginApp;

import dbUtil.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    Connection connection;

    public LoginModel() {
        try {

            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (this.connection == null){
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String user, String pass, String opt) throws SQLException {

        PreparedStatement pr = null;
        ResultSet rs = null;

            // define a sql query

        String Sql = " SELECT * FROM login where username == ? and password == ? and division == ?  ";

        try {
            pr = this.connection.prepareStatement(Sql);
            pr.setString(1,user);
            pr.setString(2,pass);
            pr.setString(3,opt);

            rs = pr.executeQuery();

            boolean ball1;

                if (rs.next()){
                    return  true;
                }
            return false;
        } catch (SQLException e) {
            return false;
        }
        finally {
            pr.close();
            rs.close();
        }
    }



}
