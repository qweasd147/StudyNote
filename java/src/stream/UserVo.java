package stream;

import java.util.List;
import java.util.Map;

public class UserVo {

    private String name;
    private int age;
    private String emali;
    private List<String> auth;


    public UserVo(String name, int age, String emali, List<String> auth) {
        this.name = name;
        this.age = age;
        this.emali = emali;
        this.auth = auth;
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

    public String getEmali() {
        return emali;
    }

    public void setEmali(String emali) {
        this.emali = emali;
    }

    public List<String> getAuth() {
        return auth;
    }

    public void setAuth(List<String> auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", emali='" + emali + '\'' +
                ", auth=" + auth +
                '}';
    }
}
