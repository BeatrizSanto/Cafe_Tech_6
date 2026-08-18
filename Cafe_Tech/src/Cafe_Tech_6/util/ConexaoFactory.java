
package Cafe_Tech_6.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class ConexaoFactory {

    private static final String URL =
        "jdbc:mysql://localhost:3306/cafe_tech"
      + "?useSSL=false"
      + "&serverTimezone=UTC"
      + "&allowPublicKeyRetrieval=true";

    private static final String USER = "cafe";
    private static final String PASSWORD = "cafe123";

    private ConexaoFactory() {
     
    }

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

