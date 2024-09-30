package KSTU;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBC {

    public static Connection DBConnect(){
            Connection con = null;   // 리턴값 먼저 선언

            String user = "ICIA";      // 아이디
            String password = "1111";  // 비밀번호
            String url = "jdbc:oracle:thin:@localhost:1521:XE";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection(url, user, password);   // 메소드 오버로딩

            System.out.println("접속 성공!");

        } catch (ClassNotFoundException e) {
            System.out.println("DB접속 실패 : 드라이버 로딩 실패");
            throw new RuntimeException(e);                            // ExceptionType1
        } catch (SQLException e) {                                    // ExceptionType2
            System.out.println("DB접속 실패 : 접속 정보 오류");
            throw new RuntimeException(e);
        }
        return con;   // 데이터타입과 동일한 타입(Connection)
    }
}
