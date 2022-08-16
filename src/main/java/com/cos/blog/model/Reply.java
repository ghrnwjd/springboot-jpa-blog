package com.cos.blog.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Reply {

    @Id // Primary Key
    //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    // MySQL <== auto_increment
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne // 여러개의 답변은 하나에 게시글에 존재할 수 있다.
    @JoinColumn(name="boardId")
    private Board board;

    @ManyToOne // 하나에 유저는 여러개의 답글을 달 수 있다.
    @JoinColumn(name="userId")
    private User user;

    private Timestamp createDate;
}
