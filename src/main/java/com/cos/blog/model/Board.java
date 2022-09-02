package com.cos.blog.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량데이터
    private String content; // 섬머노트 라이브러리 <html> 가 섞여 디자인 됨

    private int count; //조회수

    @ManyToOne(fetch = FetchType.EAGER) //Board 가 Many , User가 One  한명의 유저가 여러개의 게시글을 쓸 수 있다.
    @JoinColumn(name="userId")
    private User user; // 누가 적었는지
    //DB 에선 오브젝트를 저장할 수 없지만 (FK 사용) 자바는 오브젝트를 사용한다
    //fetch eager 무조건 들고와야 된다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 하나의 게시글은 여러개의 답글을 가질 수 있다.
    //mappedBy 연관관계에 주인이아니다. (FK가 아니다) DB에 칼럼을 만들지 말아 주세요.
    //fetch.lazy 필요할때 챙겨온다. 댓글창 펼치기를 사용한다면
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;

}