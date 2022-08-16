package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
// @DynamicInsert insert 시 null인 필드를 제외시킨다.
// ORM -> Java(다른언어) Object -> 테이블로 매핑해준다.
public class User {
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Id // Primary Key
    //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    // MySQL <== auto_increment
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length =  30, unique = true)  // null 값이 될 수 없다.
    private String username; //아이디

    @Column(nullable = false, length =  100)  // 123456 ==> 해쉬(비밀번호 암호화)
    private String password; // 패스워드

    public RoleType getRole() {
        return role;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Column(nullable = false, length =  50)
    private String email; // 이메일

    //DB 는 RoleType 이 없다.
    // @ColumnDefault("'user")
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum 을 쓰는게 좋다. (domain 설정 가능, 범위가 정해져있다.) => Admin, user, manager
    // String으로 입력시 오타 역시 허용된다.

    @CreationTimestamp // 시간이 자동으로 입력 된다.
    private Timestamp createDate;

}
