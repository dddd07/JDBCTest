package part2;

import part1.common.JdbcUtil;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;

public class JdbcCrudTest_1 {
    public static void main(String[] args) {
        JdbcCrud_1 jc = new JdbcCrud_1();
        System.out.println("로그인 될때까지 무한루프 실행");
        while (true) {
            System.out.print("아이디 입력 : ");
            String id = JdbcUtil.SC.next();
            System.out.print("비번 입력 : ");
            String pw = JdbcUtil.SC.next();
            ArrayList<Members> list = jc.login(id, pw);
            if (list != null) {
                // 로그인 성공
                for(Members mb : list){
                    System.out.println(mb);     // Members의 toString
                }
                break;
            } else {
                // 로그인 실패
                System.out.println("로그인 실패");
            }
        } // while end
        System.out.println("프로그램 종료");

        // 실행 예시
        // 아이디 입력 : aaa엔터
        // 비번 입력 : 1233421엔터
        // 로그인 실패시 다시 입력
        // 아이디 입력 : aaa엔터
        // 비번 입력 : 1111엔터
        // 로그인 성공시 회원정보를 출력

        
        
        
        
        
        // Connection con = JdbcUtil.getConnection();
        // 키보드로 회원 아이디, 비번, 이름, 나이, 성별을 입력 후 리턴
//        Members mb = getMemberInfo();
//        boolean result = jc.join(mb);                // insert into
//        if (result){
//            System.out.println("회원가입 성공!");
//        } else {
//            System.out.println("회원가입 실패!");
//        }
//        jc.select();
//        ArrayList<Members> mbList = jc.select();
//        for (Members mb : mbList) {
//            // mb.showInfo();                      // 아이디, 이름, 나이, 성별
//            System.out.println(mb);                // toString 재정의
//        }
//        for (int i = 0; i < mbList.size(); i++) {
//            System.out.println(mbList.get(i));       // toString
//        }

//        ArrayList<String> idList = jc.select();    // DB 연결 ---> select ---> DB 종료
//        for(String id: idList){
//            System.out.println("아이디 : " + id);
//        }
//        for(int i = 0; i < idList.size(); i++){
//            System.out.println("아이디 : " + idList.get(i));
//        }
        // c.insert();
        // jc.select();
        // JdbcUtil.close();

    }

    private static Members getMemberInfo() {
        Members mb = new Members();
        System.out.print("아이디 입력 : ");
        mb.setId(JdbcUtil.SC.next());
        System.out.print("비번 입력 : ");
        mb.setPw(JdbcUtil.SC.next());
        System.out.print("이름 입력 : ");
        mb.setName(JdbcUtil.SC.next());
        System.out.print("나이 입력 : ");
        mb.setAge(JdbcUtil.SC.nextInt());
        System.out.print("성별 입력 : ");
        mb.setGender(JdbcUtil.SC.next());
        return mb;
    }
}
