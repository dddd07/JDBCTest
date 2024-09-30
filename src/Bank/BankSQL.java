package Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankSQL {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    public void connect() {
        con = DBC.DBConnect();
    }

    public void conClose() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int clientNumber() {
        int cNum = 0;

        try {
            // BCLIENT 테이블에서 MAX(CNUM)을 조회 : 1~5단계
            String sql = "SELECT MAX(CNUM) FROM BCLIENT";

            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                cNum = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cNum + 1;   // 메소드와 동일한 타입의 변수(int)를 만들고 초기값 0 대신에 변수넣기, + 1:고객 자동추가
    }

    public String accountNumber() {
        String cAccount = null;

        // 카카오뱅크 : 3333-xx(2자리)-xxxxxxx(7자리)
        // Math.random() 메소드를 사용해서 무작위 숫자 생성
        // 0부터 9까지 => (int)(Math.random() * 9 + 0)

        cAccount = "3333-";

        // 3333-xx
        for (int i = 1; i <= 2; i++) {      // i<=2 : 반복문 2번 돌린다(2자리를 만들기 위해)
            cAccount += (int) (Math.random() * 9);
        }

        // 3333-xx-
        cAccount += "-";

        // 3333-xx-xxxxxxx
        for (int i = 1; i <= 7; i++) {      // i<=7 : 반복문 7번 돌린다(7자리를 만들기 위해)
            cAccount += (int) (Math.random() * 9);
        }
        // 우선 중복 걱정x
        return cAccount;    // 메소드와 동일한 타입의 변수(String)를 만들고 초기값 null 대신에 변수넣기
    }


    public void createAccount(Client client) {
        try {
            String sql = "INSERT INTO BCLIENT VALUES(?,?,?,?)";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, client.getcNum());
            pstmt.setString(2, client.getcName());
            pstmt.setString(3, client.getcAccount());
            pstmt.setInt(4, client.getBalance());

            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("계좌생성 성공!");
                System.out.println("고객번호 : " + client.getcNum());
                System.out.println("계좌번호 : " + client.getcAccount());
            } else {
                System.out.println("계좌생성 실패!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkAccount(String cAccount) {
        boolean checked = false;

        try {
            String sql = "SELECT * FROM BCLIENT WHERE CACCOUNT = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, cAccount);

            rs = pstmt.executeQuery();

            checked = rs.next();        // checked 존재 : true, 존재하지 않음 : false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checked;     // 메소드와 동일한 타입의 변수(boolean)를 만들고 초기값 false 대신에 변수넣기
    }

    public void deposit(String cAccount, int amount) {

        try {
            // (1) sql문 작성
            String sql = "UPDATE BCLIENT SET BALANCE = BALANCE + ? WHERE CACCOUNT = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력
            pstmt.setInt(1, amount);
            pstmt.setString(2, cAccount);

            // (4) 실행
            int result = pstmt.executeUpdate();

//            // (5) 결과
//            if (result > 0) {
//                System.out.println("입금 성공!");
//            } else {
//                System.out.println("입금 실패!");
//            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void withdraw(String cAccount, int amount) {

        try {
            // (1) sql문 작성
            String sql = "UPDATE BCLIENT SET BALANCE = BALANCE - ? WHERE CACCOUNT = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력
            pstmt.setInt(1, amount);
            pstmt.setString(2, cAccount);

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과
//            if (result > 0) {
//                System.out.pri ntln("출금 성공!");
//            } else {
//                System.out.println("출금 실패!");
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkBalance(String cAccount) {
        int balance = 0;
        try {
            // (1) sql문 작성
            String sql = "SELECT BALANCE FROM BCLIENT WHERE CACCOUNT = ?";

            // (2) DB준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력
            pstmt.setString(1, cAccount);

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            if (rs.next()) {
                balance = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } // catch end
        return balance;
    } // checkBalance end
} // class end
