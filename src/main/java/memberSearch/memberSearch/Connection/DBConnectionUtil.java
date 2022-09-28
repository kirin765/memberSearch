package memberSearch.memberSearch.Connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DBConnectionUtil {

    private static final String URL = "jdbc:h2:tcp://localhost/~/test2";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

    public static Connection getConnection(){
        try {
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection={}, class={}", con, con.getClass());
            return con;
        }catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
}
