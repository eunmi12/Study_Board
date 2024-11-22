package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Question {

    @Id //Id가 들어간 에너테이션 기본키로 사용한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //sequence 값을 1개씩 증가시켜주기 위함이다. strategy 속성을 달아놓지 않으면
    private Integer id; //@GeneratedValue 에너테이션에 걸려있는 모든 속성의 값을 1개씩 증가시키기 떄문에 해당 속성만 별도로 번호가
                        //차례로 증가시키기 위해 해당 속성을 사용한다.

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne //author 속성에는 사용자 한 명이 질문을 여러 개 작성할 수 있기 때문에 해당 애노테이션이 붙음.
    private SiteUser author;

    //수정 일시를 의미함.
    private LocalDateTime modifyDate;

    //추천을 위한
    @ManyToMany
    Set<SiteUser> voter; //Set 자료형을 사용한 이유는 voter 속성 값이 서로 중복되지 않도록 하기 위해서이다.
}
