package part2;

// 데이터 bean 클래스:
public class Members {
    // 필드명 = 컬럼명
    private String id;
    private String pw;
    private String name;
    private int age;
    private String gender;

    @Override
    public String toString() {
        return "Member [id : " + id + ", name : " + name +
                ", age : " + age + ", gender : " + gender + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void showInfo() {
    }
}
