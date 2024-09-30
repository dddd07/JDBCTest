package part2;

import part1.common.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcCrud_1 {
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    public ArrayList<Members> select() {
        con = JdbcUtil.getConnection();
        String sql = "select id, name, age, gender from members order by id";
        //ArrayList<Members> list = new ArrayList<>();
        ArrayList<Members> list = null;           // 초기값
        try {
            pstmt = con.prepareStatement(sql);      // sql문 파싱
            rs = pstmt.executeQuery();              // select문 실행
            list = new ArrayList<>();            // select 성공
            Members mb = null;
            while (rs.next()) {
                mb = new Members();         // 인스턴스 배열은 인스턴스의 참조값을 저장하기 때문에 반복문 안에 넣어줘야 한다.
                mb.setId(rs.getString("id"));
                mb.setName(rs.getString("name"));
                mb.setAge(rs.getInt("age"));
                mb.setGender(rs.getString("gender"));
                list.add(mb);                       // 배열에 회원추가
                // list.add(rs.getString("ID"));
            }
            return list;                            // ArrayList<String> list의 초기값을 null로 두면 return list; 생략하고 마지막에 return 해줌
        } catch (SQLException e) {
            System.out.println(e.getMessage());     // 오류 메세지
            e.printStackTrace();                    // 오류 위치 추적
            System.out.println("select 예외 발생");
        } finally {                                 // 항상 실행
           close();
        }
        return null;                                // 참조형 반환시 에러의 의미로 null(정수형 반환시 -1)
        // return list;
    }

    public boolean join(Members mb) {
        con = JdbcUtil.getConnection();
        String sql = "insert into members values(?,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(sql);       // 파싱 = sql 연결
            pstmt.setString(1,mb.getId());
            pstmt.setString(2,mb.getPw());
            pstmt.setString(3,mb.getName());
            pstmt.setInt(4,mb.getAge());
            pstmt.setString(5,mb.getGender());
            int result = pstmt.executeUpdate();      // insert, update, delete만 사용
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("join 예외 발생");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            close();
        }
        return false;       // 예외 발생
    }

    private void close() {
        JdbcUtil.close(rs);
        JdbcUtil.close(pstmt);
        JdbcUtil.close(con);
    }

    public ArrayList<Members> login(String id, String pw) {
        con = JdbcUtil.getConnection();
        String sql = "select id, pw from members where id = ?";
        ArrayList<Members> list = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            Members mb = null;                 // 참조 변수
            // id  |  pw
            // aaaa   1111
            if (rs.next()) {
                // 아이디가 존재함
                if (rs.getString("id").equals("admin")) {
                    // 관리자인 경우
                    if (rs.getString("pw").equals(pw)) {
                        // 모든 회원정보를 DB에서 읽어옴
                        // ArrayList에 저장후 리턴
                        sql = "select * form members";
                        pstmt = con.prepareStatement(sql);
                        rs = pstmt.executeQuery();
                        while (rs.next()){
                            mb = new Members(); // 인스턴스 : 레코드 수 만큼 생성해야 함
                            mb.setId(rs.getString("id"));
                            mb.setPw(rs.getString("pw"));
                            mb.setName(rs.getString("name"));
                            mb.setAge(rs.getInt("age"));
                            mb.setGender(rs.getString("gender"));
                            list.add(mb);
                        }
                        return list;
                    }
                } else {
                    // 일반인인 경우
                    if (rs.getString("pw").equals(pw)) {
                        // 일반인 로그인 성공
                        // 본인 회원정보를 DB에서 읽어옴
                        // ArrayList에 저장후 리턴
                        sql = "select * form members where id = ?";
                        pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, id);     // 파싱
                        rs = pstmt.executeQuery();
                        if (rs.next()) {     // id는 UNIQUE 이기 때문에 if문 사용
                            mb = new Members();
                            mb.setId(rs.getString("id"));
                            mb.setPw(rs.getString("pw"));
                            mb.setName(rs.getString("name"));
                            mb.setAge(rs.getInt("age"));
                            mb.setGender(rs.getString("gender"));
                            list.add(mb);
                            return list;
                        }
                        // return list;
                    }
                }
            }   // if 아이디 존재여부

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("login 예외 발생");
        } finally {
            close();
        }
        return null;
//                mb = new Members();
//                mb.setId(rs.getString("id"));
//                mb.setPw(rs.getString("pw"));
//                mb.setName(rs.getString("name"));
//                mb.setAge(rs.getInt("age"));
//                mb.setGender(rs.getString("gender"));
//                return mb;
    }
}
