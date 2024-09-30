package Naver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NaverSQL {

    // DB연동 3객체
    Connection con;             // 접속
    PreparedStatement pstmt;    // SQL문 작성
    ResultSet rs;               // 결과

    // DB접속 메소드
    public void connect(){
        con = DBC.DBConnect();
    }

    // DB접속 해제 메소드
    public void conClose(){
        try {
            con.close();
            System.out.println("프로그램을 종료합니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // 회원가입 메소드 : NaverMain에서 입력받은 정보(member)를 매개변수로 사용
    public void memberJoin(NaverMember member) {

        try {
            // (1) sql문 작성 : 입력할 데이터 대신 '?' 작성
            String sql = "INSERT INTO NAVER VALUES(?, ?, ?, ?, ?, ?, ?)";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 입력데이터 처리
            pstmt.setString(1, member.getnId());
            pstmt.setString(2, member.getnPw());
            pstmt.setString(3, member.getnName());
            pstmt.setString(4, member.getnBirth());
            pstmt.setString(5, member.getnGender());
            pstmt.setString(6, member.getnEmail());
            pstmt.setString(7, member.getnPhone());
            
            // (4) 실행 : insert, update, delete => int result
            int result = pstmt.executeUpdate();
            
            // (5) 결과
            if(result > 0){
                System.out.println("가입 성공!");
            } else {
                System.out.println("가입 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // (2) 로그인
    public NaverMember memberLogin(String nId, String nPw) {
        NaverMember member = new NaverMember();

        try {
            // (1) sql문 작성
            String sql = "SELECT * FROM NAVER WHERE NID = ? AND NPW = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력 ('?' 없으면 패스)
            pstmt.setString(1, nId);
            pstmt.setString(2, nPw);

            // (4) 실행 : select => rs
            rs = pstmt.executeQuery();

            // (5) 결과
            if(rs.next()){
                member.setnId(rs.getString(1));
                member.setnPw(rs.getString(2));
                member.setnName(rs.getString(3));
                member.setnBirth(rs.getString(4));
                member.setnGender(rs.getString(5));
                member.setnEmail(rs.getString(6));
                member.setnPhone(rs.getString(7));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return member;
    }

    // (2-1) 회원목록
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
//                System.out.println("연락처 : " + memberList.get(i).getnPhone());
//            }

            // 향상된 for문 : for-each문
            for(NaverMember list : memberList){
                System.out.print("아이디 : " + list.getnId());
                System.out.print("\t | 이름 : " + list.getnName());
                System.out.println("\t | 연락처 : " + list.getnPhone());
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // (2-3) 회원정보 수정하기
    public void memberUpdate(NaverMember member) {
        try {
            // (1) sql문 작성 : 입력할 데이터 대신 '?' 작성
            String sql = "UPDATE NAVER SET NPW = ?, NNAME = ?, NBIRTH = ?, NGENDER = ?, NEMAIL = ?, NPHONE = ? WHERE NID = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 입력데이터 처리
            pstmt.setString(1, member.getnPw());
            pstmt.setString(2, member.getnName());
            pstmt.setString(3, member.getnBirth());
            pstmt.setString(4, member.getnGender());
            pstmt.setString(5, member.getnEmail());
            pstmt.setString(6, member.getnPhone());
            pstmt.setString(7, member.getnId());

            // (4) 실행 : insert, update, delete => int result
            int result = pstmt.executeUpdate();

            // (5) 결과
            if(result > 0){
                System.out.println("수정 성공!");
            } else {
                System.out.println("수정 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // (2-4) 회원 탈퇴하기
    public void memberDelete(String nId) {
        try {
            // (1) sql문 작성 : 입력할 데이터 대신 '?' 작성
            String sql = "DELETE FROM NAVER WHERE NID = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 입력데이터 처리
            pstmt.setString(1, nId);

            // (4) 실행 : insert, update, delete => int result
            int result = pstmt.executeUpdate();

            // (5) 결과
            if(result > 0){
                System.out.println("삭제 성공!");
            } else {
                System.out.println("삭제 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}







