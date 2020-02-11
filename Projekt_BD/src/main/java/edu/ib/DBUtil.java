package edu.ib;

import javafx.scene.control.TextArea;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;


public class DBUtil {

    private String userName;
    private String userPassword;

    private Connection conn = null;

    public DBUtil(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void dbConnect() throws SQLException, ClassNotFoundException {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(createURL());

    }

    private String createURL() {

        StringBuilder urlSB = new StringBuilder("jdbc:mysql://");
        urlSB.append("localhost:3306/");
        urlSB.append("bank_krwi?");
        urlSB.append("useUnicode=true&characterEncoding=utf-8");
        urlSB.append("&user=");
        urlSB.append(userName);
        urlSB.append("&password=");
        urlSB.append(userPassword);
        urlSB.append("&serverTimezone=CET");
        System.out.println(urlSB.toString());
        return urlSB.toString();
    }

    public ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        CachedRowSet crs;

            dbConnect();
            stmt = conn.prepareStatement(queryStmt);
            resultSet = stmt.executeQuery(queryStmt);
            crs = new CachedRowSetWrapper();
            crs.populate(resultSet);

            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        conn.close();

        return crs;
    }

    public String dbExecuteDownload(String sqlStmt,String nazwa, int id, int ilosc) throws SQLException, ClassNotFoundException {

        CallableStatement cs = null;
        String info = null;

            dbConnect();
            cs = conn.prepareCall(sqlStmt);
            cs.setString(1,nazwa);
            cs.setInt(2,id);
            cs.setInt(3,ilosc);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.executeQuery();
            info = cs.getString(4);

            if (info != null) {
                cs.close();
                conn.close();
            }

        return info;
    }

    public String dbExecuteAdd(String sqlStmt,String imie,String nazwisko,String data, String telefon, double waga, String grupa, int id) throws SQLException, ClassNotFoundException {

        CallableStatement cs = null;
        String info = null;

            dbConnect();
            cs = conn.prepareCall(sqlStmt);
            cs.setString(1,imie);
            cs.setString(2,nazwisko);
            cs.setString(3,data);
            cs.setString(4,telefon);
            cs.setDouble(5,waga);
            cs.setString(6,grupa);
            cs.setInt(7,id);
            cs.registerOutParameter(8, Types.VARCHAR);
            cs.executeQuery();
            info = cs.getString(8);

            if (info != null) {
                cs.close();
                conn.close();
            }

        return info;
    }

    public String dbExecuteUtilization(String sqlStmt) throws SQLException, ClassNotFoundException {

        CallableStatement cs = null;
        String info = null;

            dbConnect();
            cs = conn.prepareCall(sqlStmt);
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.executeQuery();
            info = cs.getString(1);

            if (info != null) {
                cs.close();
                conn.close();
            }
        return info;
    }




}
