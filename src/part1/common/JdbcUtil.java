package part1.common;

import java.sql.*;
import java.util.Scanner;

public class JdbcUtil {     // 필수적으로 사용할 것들 모아주기
    // 공용객체
    public static final Scanner SC = new Scanner(System.in);    // final

    static{
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(ClassNotFoundException cne){
            System.out.println("드라이버 로딩 실패");
            cne.printStackTrace();
        }
    }

    // JdbcUtil.getConnection()
    public static Connection getConnection(){    // 메소드(함수)
        Connection con = null;
        try{
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
                    "icia", "1111");
            // 수동 커밋으로 변경
            // con.setAutoCommit(false);   // 기본값 : 자동커밋(true)
            System.out.println("Connection Success!");
            // select, update
        } catch(SQLException se){
            System.out.println("DB 접속 실패");
            se.printStackTrace();
        }
        finally{    // 어떤 경우에도 실행됨(생략가능) => 접속을 끝내줌

        }
        return con;
    } // connect end

    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            //System.out.println("close OK!");
        } catch (Exception e) {
            System.out.println("resultSet 예외 발생");
            e.printStackTrace();
        }
    }
    public static void close(PreparedStatement statement) {
        try {
            if (statement != null) statement.close();
            //System.out.println("close OK!");
        } catch (Exception e) {
            System.out.println("pstmt close 예외 발생");
            e.printStackTrace();
        }
    }
    public static void close(Connection con) {
        try {
            if (con != null) con.close(); //DB접속 종료
            //System.out.println("close OK!");
        } catch (Exception e) {
            System.out.println("con close 예외 발생");
            e.printStackTrace();
        }
    }

    public static void commit(Connection con) {
        try {
            con.commit();
        } catch (SQLException e) {
            System.out.println("commit 예외 발생");
        }
    }

    public static void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            System.out.println("rollback 예외 발생");
        }
    }
}   // JDBC 공통 메소드

