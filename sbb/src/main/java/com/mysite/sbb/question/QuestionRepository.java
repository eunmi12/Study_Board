package com.mysite.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    //JpaRepository<Question, Integer>는 Question 엔티티로 리포지터리를 생성한다는 의미이다. 또한 Question
    //엔티티의 기본키가 Integer임을 추가로 지정해야한다.

    //findBySubject 라는 메서드는 리포지터리가 기본적으로 제공하고있지 않기 때문에 여기서 만들어줘야한다.
    Question findBySubject(String subject);

    //subject와 content를 한번에 조회하는 메서드
    Question findBySubjectAndContent(String subject, String content);

    //여러개의 응답을 조회할때는 List를 사용한다.
    List<Question> findBySubjectLike(String subject);

    //페이징을 위한 클래스
    Page<Question> findAll(Pageable pageable);

    //검색을 위한 클래스 DB에서 Question엔티티를 조회한 결과를 페이징하여 반환한다.
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    //만약에 JPA의 Specification 을 사용하지 않고 Query를 쓴다면
    @Query("select "
        + "distinct q "
        + "from Question q "
        + "left outer join SiteUser u1 on q.author=u1 "
        + "left outer join Answer a on a.question=q "
        + "left outer join SiteUser u2 on a.author=u2 "
        + "where "
        + "     q.subject like %:kw% "
        + "     or q.content like %:kw "
        + "     or u1.username like %:kw "
        + "     or a.content like %:kw "
        + "     or u2.username like %:kw ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
