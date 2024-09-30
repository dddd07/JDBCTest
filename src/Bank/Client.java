package Bank;

public class Client {

    private int cNum;           // 고객번호
    private String cName;       // 고객이름
    private String cAccount;    // 계좌번호
    private int balance;        // 잔액


    // 메소드

    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcAccount() {
        return cAccount;
    }

    public void setcAccount(String cAccount) {
        this.cAccount = cAccount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "고객 [ " +
                "고객번호 : " + cNum +
                ", 고객이름 : " + cName +
                ", 계좌번호 : " + cAccount +
                ", 잔액 : " + balance +
                " ]";
    }
}
