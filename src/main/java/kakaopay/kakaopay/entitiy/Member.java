package kakaopay.kakaopay.entitiy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(nullable = false)
    private int age;
    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;

    public static Member initData(List<String> records) {
        Member member = new Member();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String userName = records.get(1).trim();
        int age = Integer.parseInt(records.get(2).trim());
        LocalDate regDate = LocalDate.parse(records.get(3).trim(), formatter);
        member.changeMember(userName, age, regDate);

        return member;
    }

    public static Member createMember(String userName, int age) {
        Member member = new Member();
        member.changeMember(userName, age, LocalDate.now());
        return member;
    }

    public String changeName(String userName) {
        this.userName = userName;
        return this.userName;
    }

    public void changeMember(String userName, int age, LocalDate regDate) {
        this.userName = userName;
        this.age = age;
        this.regDate = regDate;
    }

    public void changeMember(String userName, int age) {
        this.userName = userName;
        this.age = age;
        this.regDate = regDate;
    }

}
