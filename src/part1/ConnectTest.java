package part1;

import part1.common.JdbcUtil;

import java.sql.*;

public class ConnectTest {
    Connection con;     // null
    PreparedStatement statement;    // null
    ResultSet resultSet;    // null

    public void select() {
        con = JdbcUtil.getConnection(); // DB 접속
        try {
            String sql = "select * from members";
            statement = con.prepareStatement(sql);  // 전달 -> 파싱
            resultSet = statement.executeQuery();    // sql 전달 후 실행(resultSet => 필드 지정)
            while(resultSet.next()){    // next로 실행
                System.out.print("아이디 : " + resultSet.getString("id"));
                System.out.print(", 비밀번호 : " + resultSet.getString("pw"));
                System.out.print(", 이름 : " + resultSet.getString("name"));
                System.out.print(", 나이 : " + resultSet.getInt("age"));
                System.out.print(", 성별 : " + resultSet.getString("gender"));
                System.out.println("");   // 줄바꿈
            }
        } catch (SQLException e) {
            System.out.println("select 예외 발생");
        } finally {
            // JdbcUtil.close(resultSet, statement, con);
            JdbcUtil.close(resultSet);
            JdbcUtil.close(statement);
            JdbcUtil.close(con);
        }

    } // select end

    public void login(String id, String pw) {
        try {
            String sql = "select * from members where id = ? and pw = ?";
            statement = con.prepareStatement(sql);       // sql 전달 -> 파싱(분석)
            statement.setString(1, id);
            statement.setString(2, pw);
            resultSet = statement.executeQuery();       // 실행
            // resultSet = statement.executeQuery(sql);    // sql 전달 후 실행(resultSet => 필드 지정)

            if(resultSet.next()){   // 레코드 하나만 검색한다면 if문 사용
                //while(resultSet.next()){    // next로 실행
                System.out.print("아이디 : " + resultSet.getString("id"));
                System.out.print(", 비밀번호 : " + resultSet.getString("pw"));
                System.out.print(", 이름 : " + resultSet.getString("name"));
                System.out.print(", 나이 : " + resultSet.getInt("age"));
                System.out.print(", 성별 : " + resultSet.getString("gender"));
                System.out.println();   // 줄바꿈
            }
        } catch (SQLException e) {
            System.out.println("select 예외 발생");
        }
    }

    public void insert(String id, String pw, String name, int age, String gender) {
        String sql = "insert into members values(?,?,?,?,?)";
        con = JdbcUtil.getConnection();
        try {
            statement = con.prepareStatement(sql);  // sql 전달, 파싱
            statement.setString(1, id);
            statement.setString(2, pw);
            statement.setString(3, name);
            statement.setInt(4, age);
            statement.setString(5, gender);
            // resultSet = statement.executeQuery();     // select만 ==> excuteQuery()
            int result = statement.executeUpdate(); // insert, update, delete ==> excuteUpdate()

            if (result > 0) {
                System.out.println("insert 성공");
            } else {
                System.out.println("insert 실패");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("insert 예외 발생");
            e.printStackTrace();
        }
    }

    public void update(String id, String pw, String gender) {
        String sql = "update members set pw = ?, gender = ? where id = ?";
        try {
            statement = con.prepareStatement(sql);  // sql 전달, 파싱 = sql 연결
            statement.setString(1, pw);
            statement.setString(2, gender);
            statement.setString(3, id);
            int result = statement.executeUpdate();      // insert, update, delete

            if (result > 0) {
                System.out.println("update 성공");
            } else {
                System.out.println("update 실패");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("update 예외 발생");
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "delete from members where id = ?";
        con = JdbcUtil.getConnection();
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, id);
            int result = statement.executeUpdate(); // insert, update, delete

            if (result > 0) {
                System.out.println("delete 성공");
            } else {
                System.out.println("delete 실패");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("delete 예외 발생");
            e.printStackTrace();
        }
    }

    // 이체TX
    public void tXTest() {
        boolean isTxOk = false;
        String sql = "update account set balance = balance-1000 where hostid = ?";
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1,"A");
            statement.executeUpdate();
            sql = "update account set balance = balance+1000 where hostid = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1,"B");
            statement.executeUpdate();
            isTxOk = true;

        } catch (SQLException e) {
            isTxOk = false;
            System.out.println("이체TX 예외 발생");
        } finally {
            if (isTxOk){
                try {
                    con.commit();
                } catch (SQLException e) {
                    System.out.println("commit 예외 발생");
                }
            } else {
                try {
                    con.rollback();
                } catch (SQLException e) {
                    System.out.println("rollback 예외 발생");
                }
            }
        }
    }

} // class end
