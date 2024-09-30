package Naver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NaverSQL_3 {

    // DB연동 3객체
    Connection con;             // 접속
    PreparedStatement pstmt;    // SQL문 작성
    ResultSet rs;               // 결과
    String id;

    // 연결(DB접속 메소드)
    public void connect(){
        con = DBC.DBConnect();
    }

    // 연결해제(DB접속 해제 메소드)
    public void conClose(){
        try {
            con.close();
            System.out.println("프로그램을 종료합니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 회원가입 메소드 : NaverMain에서 입력받은 정보(member)를 매개변수로 사용
    public void join(NaverMember nMem) {
        try {
            // (1) sql문 작성
            String sql = "INSERT INTO NAVER VALUES(?,?,?,?,?,?,?)";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) ?에 데이터 입력
            pstmt.setString(1,nMem.getnId());
            pstmt.setString(2,nMem.getnPw());
            pstmt.setString(3,nMem.getnName());
            pstmt.setString(4,nMem.getnBirth());
            pstmt.setString(5,nMem.getnGender());
            pstmt.setString(6,nMem.getnEmail());
            pstmt.setString(7,nMem.getnPhone());

            // (4) 실행 insert,update,delete => int result
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("회원가입 성공!");
            } else {
                System.out.println("회원가입 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public boolean login(String nId, String nPw) {
        boolean result = false;

        try {
            // (1) sql문 작성
            String sql = "SELECT * FROM NAVER WHERE NID =? AND NPW = ?";

            // (2)
            pstmt = con.prepareStatement(sql);

            // (3)
            pstmt.setString(1, nId);
            pstmt.setString(2, nPw);

            // (4) select => rs
            rs = pstmt.executeQuery();

            // (5)
            result = rs.next();
            if (result){
                id = nId;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void memberList() {

        // 회원 한명의 정보를 담을 객체
        NaverMember member;

        // 회원목록을 만들 수 있는 객체
        ArrayList<NaverMember> memberList = new ArrayList<>();

        try {
            // (1) sql문 작성
            String sql = "SELECT * FROM NAVER";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력 ('?' 없으면 패스)

            // (4) 실행 : select => rs
            rs = pstmt.executeQuery();

            // (5) 결과
            while (rs.next()){

                member = new NaverMember();

                member.setnId(rs.getString(1));
                member.setnPw(rs.getString(2));
                member.setnName(rs.getString(3));
                member.setnBirth(rs.getString(4));
                member.setnGender(rs.getString(5));
                member.setnEmail(rs.getString(6));
                member.setnPhone(rs.getString(7));

                memberList.add(member);
            }

//            for (int i=0; i<memberList.size(); i++){
//                System.out.print("아이디 : " + memberList.get(i).getnId());
//                System.out.print("이름 : " + memberList.get(i).getnName());
//                System.out.print("연락처 : " + memberList.get(i).getnPhone());
//            }
            
            // 향상된 for문 : for-each문
            for (NaverMember list : memberList){
                System.out.print("아이디 : " + list.getnId());
                System.out.print(" | 이름 : " + list.getnName());
                System.out.println(" | 연락처 : " + list.getnPhone());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public NaverMember memberDetail(String id) {

        NaverMember nMem = null; // 처음에 null로 초기화

        try {
            // (1) sql문 작성
            String sql = "SELECT * FROM NAVER WHERE NID = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력
            pstmt.setString(1, id);

            // (4) 실행 : select => rs
            rs = pstmt.executeQuery();

            // (5) 결과
            if (rs.next()) {
                nMem = new NaverMember(); // nMem 객체 생성 (결과가 있을 때만)
                nMem.setnId(rs.getString("NID")); // 컬럼 이름으로 가져오는 게 더 안전
                nMem.setnPw(rs.getString("NPW"));
                nMem.setnName(rs.getString("NNAME"));
                nMem.setnBirth(rs.getString("NBIRTH"));
                nMem.setnGender(rs.getString("NGENDER"));
                nMem.setnEmail(rs.getString("NEMAIL"));
                nMem.setnPhone(rs.getString("NPHONE"));
            } else {
                System.out.println("ID에 해당하는 회원을 찾을 수 없습니다: " + id);
            }
        } catch (SQLException e) {
            System.err.println("SQL 실행 중 오류 발생: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return nMem; // null 또는 결과 객체 리턴
    }


    public void memberUpdate(NaverMember nMem) {
        try {
            // (1) sql문 작성
            String sql = "UPDATE NAVER SET NPW =?,NNAME = ?,NBIRTH=?,NGENDER=?,NEMAIL=?,NPHONE=? WHERE NID = ?";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) ?에 데이터 입력
            pstmt.setString(1,nMem.getnPw());
            pstmt.setString(2,nMem.getnName());
            pstmt.setString(3,nMem.getnBirth());
            pstmt.setString(4,nMem.getnGender());
            pstmt.setString(5,nMem.getnEmail());
            pstmt.setString(6,nMem.getnPhone());
            pstmt.setString(7, id);

            // (4) 실행 insert,update,delete => int result
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("회원수정 성공!");
            } else {
                System.out.println("회원수정 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void memberDelete(String nId) {
        NaverMember nMem = new NaverMember();

        try {
            // (1) sql문 작성
            String sql = "DELETE FROM NAVER WHERE NID = ? ";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력 ('?' 없으면 패스)
            pstmt.setString(1, nId);

            // (4) 실행 : select => rs
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("회원삭제 성공");
            } else {
                System.out.println("회원삭제 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
