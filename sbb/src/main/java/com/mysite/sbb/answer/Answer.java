package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    //답변을 통해 질문의 제목을 알고 싶다면 answer.getQuestion().getSubject()를 사용해서 접근 가능하지만
    //단순히 question 속성만 추가하면 안되고 질문 엔티티와 연결된 속성이라는 것을 답변 엔티티에 표시해야 한다.
    //ManyToOne은 N:1 관계를 나타낼 수 있다.
    @ManyToOne
    private Question question;

    @ManyToOne //ManyToOne 어노테이션으로 Question과 SiteUser와의 관계를 정의하고 있음. author 필드는 실제로 SiteUser 엔티티에 대한 참조를 가진다.
    private SiteUser author; //그래서 answer.getAuthor().getUsername()과 같이 작성자의 username을 조회할 수 있음.

    //수정 일시를 의미한다.
    private LocalDateTime modifyDate;

    //추천을 위한
    @ManyToMany
    Set<SiteUser> voter;

}
