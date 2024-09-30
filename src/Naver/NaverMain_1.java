package Naver;

import java.util.Scanner;

public class NaverMain_1 {
    public static void main(String[] args) {
        // 프로그램 실행에 필요한 객체, 변수    
        Scanner sc = new Scanner(System.in);

        // 객체생성
        NaverMember nMem = new NaverMember();

        // NaverSQL 객체 생성
        NaverSQL_3 sql = new NaverSQL_3();

        // 초기메뉴
        boolean run1 = true;
        int menu1 = 0;

        // 로그인 성공시 메뉴
        boolean run2 = true;
        int menu2 = 0;

        // 프로그램 실행 시 DB접속
        sql.connect();

        // DB접속 해제
        // sql.close();

        String nId;
        String nPw;

        while (run1) {
            // 메인 메뉴
            // [1] 회원가입 [2] 로그인
            System.out.println("======================================");
            System.out.println("[1]회원가입\t\t[2]로그인\t\t[3]종료");
            System.out.println("======================================");
            System.out.print("선택 >> ");
            menu1 = sc.nextInt();

            sql.connect();

            switch (menu1) {
                case 1:
                    // [1] 회원 가입
                    // 회원 등록하는 작업 실행
                    System.out.println("회원정보를 입력하세요.");

                    // 회원정보를 담을 객체
                    nMem = new NaverMember();

                    System.out.print("아이디 >> ");
                    nMem.setnId(sc.next());

                    System.out.print("비밀번호 >> ");
                    nMem.setnPw(sc.next());

                    System.out.print("이름 >> ");
                    nMem.setnName(sc.next());

                    System.out.print("생년월일 >> ");
                    nMem.setnBirth(sc.next());

                    System.out.print("성별 >> ");
                    nMem.setnGender(sc.next());

                    System.out.print("이메일 >> ");
                    nMem.setnEmail(sc.next());

                    System.out.print("연락처 >> ");
                    nMem.setnPhone(sc.next());

                    sql.join(nMem);
                    break;
                case 2:
                    // [2] 로그인
                    // 아이디와 비밀번호를 입력받아서 일치여부 확인
                    System.out.print("아이디 >> ");
                    nId = sc.next();

                    System.out.print("비밀번호 >> ");
                    nPw = sc.next();

                    boolean login = sql.login(nId, nPw);
                    // nMem = sql.login(nId, nPw);

                    while (run2) {
                        System.out.println("===========================================");
                        System.out.println("[1]회원목록\t\t[2]내정보보기\t\t[3]내정보수정");
                        System.out.println("[4]탈퇴\t\t\t[5]로그아웃");
                        System.out.println("===========================================");
                        System.out.print("선택 >> ");
                        menu2 = sc.nextInt();

                        switch (menu2) {
                            case 1:
                                sql.memberList();
                                break;
                            case 2:
                                sql.memberDetail(nId);
                                System.out.println(nMem);
                                break;
                            case 3:
                                // 아이디를 제외한 나머지 정보를 덮어쓰기
                                // switch-case 문으로 항목별 수정 가능
                                System.out.println("===========================================");
                                System.out.println("[1]회원목록\t\t[2]이  름\t\t[3]생년월일");
                                System.out.println("[4]이메일\t\t\t[5]연락처");
                                System.out.println("===========================================");
                                System.out.print("선택 >> ");

                                menu2 = sc.nextInt();
                                sql.memberDetail(nId);
                                nMem = new NaverMember();

                                System.out.print("아이디 >> ");
                                nMem.setnId(sc.next());

                                System.out.print("비밀번호 >> ");
                                nMem.setnPw(sc.next());

                                System.out.print("이름 >> ");
                                nMem.setnName(sc.next());

                                System.out.print("생년월일 >> ");
                                nMem.setnBirth(sc.next());

                                System.out.print("성별 >> ");
                                nMem.setnGender(sc.next());

                                System.out.print("이메일 >> ");
                                nMem.setnEmail(sc.next());

                                System.out.print("연락처 >> ");
                                nMem.setnPhone(sc.next());

                                sql.memberUpdate(nMem);
                                break;
                            case 4:
                                System.out.println("정말 탈퇴하시겠습니까? (y/n)");
                                String checkDelete = sc.next();

                                switch (checkDelete){
                                    case "y":
                                    case "Y":
                                        sql.memberDelete(nId);
                                        break;
                                    case "n":
                                    case "N":
                                        System.out.println("삭제를 취소합니다.");
                                        break;
                                    default:
                                        System.out.println("y와 n 중에 입력하세요.");
                                        break;
                                }
                                break;
                            case 5:
                                run2 = false;
                                System.out.println("로그아웃 합니다.");
                                nId = "";   // null 값
                                nPw = "";   // null 값
                                break;
                            default:
                                System.out.println("다시 입력");
                                break;
                        }

                    }

//                    boolean check = true;
//                    if (check != true){
//                        System.out.println();
//                    }

                    break;

                case 3:
                    run1 = false;

                    // DB접속 해제
                    sql.conClose();

                    break;
                default:
                    System.out.println("다시 입력하세요.");
                    break;
            }
        }
    }
}
