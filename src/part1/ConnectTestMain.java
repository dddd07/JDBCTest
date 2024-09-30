package part1;

// Transaction(TX) : DB(프로그래밍)에서 논리적인 작업 단위
// 입금 TX, 출금TX, 결제TX
// A계좌 에서 -1000  B계좌에서 update OK
// B계좌는 +1000   A계좌에서 update  ok
// commit

public class ConnectTestMain {
    public static void main(String[] args) {
        ConnectTest crud = new ConnectTest();
        // crud.select();
        // crud.select();
        // crud.close();
        // crud.connect();
        // crud.login("aaaa","1111");
        // crud.insert("eeee", "5555", "이이", 22, "남자");
        // crud.update("aaaa","2222","여자");    // aaaa회원의 비번과 성별을 변경
         crud.select();
        // crud.delete("admin");    // bbbb회원 삭제
        // crud.tXTest();
        // crud.select();
        // crud.close();
    }
}
