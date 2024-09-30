package Naver;

import java.util.Scanner;

public class NaverMain {
    public static void main(String[] args) {

        // NaverMember 객체 생성
        NaverMember member = new NaverMember();

        // NaverSQL 객체 생성
        NaverSQL sql = new NaverSQL();

        // 프로그램 실행에 필요한 객체, 변수
        Scanner sc = new Scanner(System.in);

        // 초기 메뉴
        boolean run1 = true;
        int menu1 = 0;

        // 로그인 성공시 메뉴
        boolean run2 = true;
        int menu2 = 0;

        // 프로그램 실행 시 DB접속
        sql.connect();

        String nId;     // 입력받을 아이디
        String nPw;     // 입력받을 비밀번호

        // 메인 메뉴
        // [1] 회원가입  [2] 로그인
        while(run1) {
            System.out.println("=========================================");
            System.out.println("[1]회원가입\t\t[2]로그인\t\t[3]종료");
            System.out.println("=========================================");
            System.out.print("선택 >> ");
            menu1 = sc.nextInt();

            switch (menu1){
                // [1]회원가입
                case 1:
                    System.out.println("회원정보를 입력하세요");
                    member = new NaverMember();

                    System.out.print("아이디 >> ");
                    member.setnId(sc.next());

                    System.out.print("비밀번호 >> ");
                    member.setnPw(sc.next());

                    System.out.print("이름 >> ");
                    member.setnName(sc.next());

                    System.out.print("생년월일 >> ");
                    member.setnBirth(sc.next());

                    System.out.print("성별 >> ");
                    member.setnGender(sc.next());

                    System.out.print("이메일 >> ");
                    member.setnEmail(sc.next());

                    System.out.print("연락처 >> ");
                    member.setnPhone(sc.next());

                    sql.memberJoin(member);
                    break;
                case 2:
                    run2 = true;    // 로그인 성공시

                    System.out.print("아이디 >> ");
                    nId = sc.next();

                    System.out.print("비밀번호 >> ");
                    nPw = sc.next();

                    // [2] 로그인
                    // 아이디와 비밀번호를 입력받아서 일치여부 확인
                    // boolean login = sql.memberLogin(nId, nPw);   // boolean 타입으로 확인 - (1)
                    member = sql.memberLogin(nId, nPw);             // member 객체에 저장되어 있는 값으로 바로 확인 (2)

                    if(member.getnId()!=null){                      // 아이디 맞는지 확인
                        // 로그인 성공시
                        // [1] 회원목록 [2]내정보보기 [3]내정보수정 [4]탈퇴 [5]로그아웃
                        while (run2){
                            System.out.println("===========================================");
                            System.out.println("[1]회원목록\t\t[2]내정보보기\t\t[3]내정보수정");
                            System.out.println("[4]탈퇴\t\t\t[5]로그아웃");
                            System.out.println("===========================================");
                            System.out.print("선택 >> ");
                            menu2 = sc.nextInt();

                            switch (menu2){
                                case 1:
                                    sql.memberList();
                                    break;
                                case 2:
                                    System.out.println(member);
                                    break;
                                case 3:
                                    // 아이디를 제외한 나머지 정보를 덮어쓰기
                                    // switch-case 문으로 항목별 수정 가능
                                    System.out.println("===========================================");
                                    System.out.println("[1]비밀번호\t\t[2]이  름\t\t[3]생년월일");
                                    System.out.println("[4]성    별\t\t[5]이메일\t\t[6]연락처");
                                    System.out.println("===========================================");
                                    System.out.print("선택 >> ");
                                    int menu3 = sc.nextInt();

                                    switch (menu3){
                                        case 1:
                                            System.out.print("비밀번호 >> ");
                                            member.setnPw(sc.next());
                                            break;
                                        case 2:
                                            System.out.print("이름 >> ");
                                            member.setnName(sc.next());
                                            break;
                                        case 3:
                                            System.out.print("생년월일 >> ");
                                            member.setnBirth(sc.next());
                                            break;
                                        case 4:
                                            System.out.print("성별 >> ");
                                            member.setnGender(sc.next());
                                            break;
                                        case 5:
                                            System.out.print("이메일 >> ");
                                            member.setnEmail(sc.next());
                                            break;
                                        case 6:
                                            System.out.print("연락처 >> ");
                                            member.setnPhone(sc.next());
                                            break;
                                        default:
                                            System.out.println("다시 입력!");
                                            break;
                                    }

                                    sql.memberUpdate(member);
                                    break;
                                case 4:
                                    System.out.print("정말 탈퇴하시겠습니까? (y/n)");
                                    String checkDelete = sc.next();

                                    switch (checkDelete){
                                        case "y" :
                                        case "Y" :
                                            sql.memberDelete(nId);
                                            run2 = false;
                                            nId = "";
                                            nPw = "";
                                            break;

                                        case "n" :
                                        case "N" :
                                            System.out.println("삭제를 취소합니다.");
                                            break;

                                        default:
                                            System.out.println("y와 n중에 입력하세요.");
                                            break;

                                    }


                                    break;
                                case 5:
                                    run2 = false;
                                    System.out.println("로그아웃 합니다.");
                                    nId = "";
                                    nPw = "";
                                    break;
                                default:
                                    System.out.println("다시 입력");
                                    break;
                            }

                        }

                    } else {
                        // 로그인 실패시
                        // 메인 메뉴로 돌아가기
                        System.out.println("아이디와 비밀번호를 확인하세요.");
                    }

                    break;
                case 3:
                    run1 = false;

                    // db접속 해제
                    sql.conClose();

                    break;
                default:
                    System.out.println("다시 입력!");
                    break;
            }
        }
    }
}
