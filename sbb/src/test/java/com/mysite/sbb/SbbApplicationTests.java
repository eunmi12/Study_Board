package com.mysite.sbb;

import java.util.List;
import java.util.Optional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

//	@Transactional //이게 있다면 DB와 테스트 서버 데이터 결과값이 다시 롤백된다.. 그래서 나는 데이터를 아무리 넣어도 로컬서버를 켜면 2개만 들어간 것
	@Test
	void testJpa() {

		//대량의 테스트 데이터
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content, null); // create 매개변수가 추가되었기 때문에 null 값을 넣어주면 해결
		}

//		//질문 데이터를 통해 답변 데이터 찾기
//		Optional<Question> optionalQuestion = this.questionRepository.findById(2);
//		assertTrue(optionalQuestion.isPresent());
//		Question question = optionalQuestion.get();
//
//		List<Answer> answerList = question.getAnswerList();
//
//		assertEquals(1, answerList.size());
//		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
//


//		//답변 데이터를 조회하는 테스트
//		Optional<Answer> optionalAnswer = this.answerRepository.findById(1);
//		assertTrue(optionalAnswer.isPresent());
//		Answer answer = optionalAnswer.get();
//		assertEquals(2,answer.getQuestion().getId());


//		//답변 데이터를 저장하는 테스트
//		Optional<Question> optionalQuestion = this.questionRepository.findById(2);
//		assertTrue(optionalQuestion.isPresent());
//		Question question = optionalQuestion.get();
//
//		Answer answer = new Answer();
//		answer.setContent("네 자동으로 생성됩니다.");
//		answer.setQuestion(question);
//		answer.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(answer);


//		//질문 데이터를 삭제하는 테스트
//		assertEquals(2, this.questionRepository.count());//리포지터리의 count 메서드는 테이블 행의 개수를 리턴한다.
//		//assertEquals에서 2는 테스트에서 기대되는 questionRespository의 행의 개수가 2임을 나타낸다.
//		Optional<Question> optionalQuestion = this.questionRepository.findById(1);
//		assertTrue(optionalQuestion.isPresent());
//		Question question = optionalQuestion.get();
//		this.questionRepository.delete(question); //리포지터리의 delete 메서드를 이용해서 데이터를 삭제한다.
//		assertEquals(1, this.questionRepository.count());



//		//질문 데이터를 수정하는 테스트
//		Optional<Question> optionalQuestion = this.questionRepository.findById(1);
//		assertTrue(optionalQuestion.isPresent());
//		Question question = optionalQuestion.get();
//		question.setSubject("수정된 제목2");
//		this.questionRepository.save(question);

//		//List를 사용하여 여러 값을 조회한다.
//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
//		Question q = qList.get(0);
//		assertEquals("sbb가 무엇인가요?" , q.getSubject());

//		//findBySubjectAndContent를 활용하여 subject값과 content 값을 조회하는 메서드
//		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
//		assertEquals(1, q.getId());

//		//findBySubject를 활용하여 subject로 값을 조회하는 메서드
//		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
//		assertEquals(1,q.getId());


//		//id 값을 활용한 데이터 조회 테스트
//		//Optional은 null값을 유연하게 처리하기 위한 클래스이다.
//		Optional<Question> oq = this.questionRepository.findById(1); //findById의 리턴 타입은 Question이 아니라 Optional이다.
//		if(oq.isPresent()){ //isPresent로 값이 존재한다면 다음과 같은 코드를 수행한다.
//			Question q = oq.get(); //존재하면 get 메서드를 통해 실제 Question 객체의 값을 얻는다.
//			assertEquals("sbb가 무엇인가요?", q.getSubject());
//		}


//		//질문 데이터 조회 테스트
//		List<Question> all = this.questionRepository.findAll();
//		assertEquals(2, all.size());
//
//		Question q = all.get(0);
//		assertEquals("sbb가 무엇인가요?", q.getSubject());


		//값 넣는 테스트
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해서 알고 싶습니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1);  // 첫번째 질문 저장
//
//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2);  // 두번째 질문 저장
	}
}