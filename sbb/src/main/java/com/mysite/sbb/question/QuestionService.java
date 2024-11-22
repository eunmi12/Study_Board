package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service //@Service가 붙은 클래스들은 스프링부트가 알아서 구별함
public class QuestionService {
    private final QuestionRepository questionRepository;

    //최신순으로 게시글 정렬하기 + search 를 수정해서 검색까지 되도록 함
    public Page<Question> getList(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAll(spec,pageable);
        //만약 쿼리를 사용한다면 다음과 같이 하면 된다.
//        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

    //게시글 페이지 보어주기
//    public Page<Question> getList(int page){
//        Pageable pageable = PageRequest.of(page, 10); //page 는 조회할 번호, 10은 한 페이지에 보여줄 개수
//        return this.questionRepository.findAll(pageable);
//    }
//
    public List<Question> getList(){ //QuestionController에서 사용한 getList 부분을 그대로 옮겼다.
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    //제목과 내용을 입력받아 이를 질문으로 저장하는 create메서드를 만든다.
    public void create(String subject, String content, SiteUser user){
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(user);
        this.questionRepository.save(question);
    }

    //질문 수정하기
    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    //질문 삭제하기
    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    //질문 추천하기
    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }

    //검색 기능 구현용
    private Specification<Question> search(String kw){
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); //중복 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" +kw + "%"), //제목
                        cb.like(q.get("content"), "%" +kw + "%"), //내용
                        cb.like(u1.get("username"), "%" +kw + "%"), //질문 작성자
                        cb.like(a.get("content"), "%" +kw + "%"), //답변 내용
                        cb.like(u2.get("username"), "%" +kw + "%"));  //답변 작성자
            }
        };
    }

}
