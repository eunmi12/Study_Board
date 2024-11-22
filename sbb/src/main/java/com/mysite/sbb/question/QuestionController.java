package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/question")//이렇게 되면 프리픽스가 설정되기때문에 여기있는 url들은 항상 /question이 기본값으로 박혀있음.
@RequiredArgsConstructor //생성자 방식으로 questionRepository 객체를 주입함. 롬복이 제공, final 이 붙은 속성을 포함하는 생성자를 자동으로 만들어줌
@Controller
public class QuestionController {

    //Controller가 Repository 대신에 Service를 사용하도록 한다.
//    private final QuestionRepository questionRepository;
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
//    @ResponseBody
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) { //매개 변수로 Model을 지정하면 객체가 자동으로 생성 된다.

        Page<Question> paging = this.questionService.getList(page,kw);
//        List<Question> questionList = this.questionService.getList();
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}") //여기서 mapping의 value 값으로 사용되는 값과 밑의 PathVariable의 매개변수 이름이 같아야한다.
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){ //PathVariable은 변하는 값을 표현할 때 사용한다.
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    //질문 답변하기
    @PreAuthorize("isAuthenticated()") //principal 객체를 사용하는 메서드에 붙여줘야 한다. 이 객체를 붙이면 해당 메서드는 로그인 한 사용자만 호출함
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){//Get방식에도 th:object의 요청에 의해 QuestionForm이 필요하다.
        return "question_form";
    }

    //PostMapping을 해서 사용하는데, GetMapping때 썻던 메서드 명을 그대로 사용 가능함. 단, 매개변수의 형태가 다를 경우에만 가능하다.
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal
//                                  @RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content)
    ){//이때 BindingResult 매개변수는 항상 @Valid 뒤에 위치하여야 한다.
        //Todo 질문을 저장한다.
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }

    //게시글 수정하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal){
        Question question = this.questionService.getQuestion(id); //여기서 id로 객체를 조회했었음!

        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다."); //로그인 한 사용자와 질문 작성자가 동일하지 않을 경우
        }
        //수정한 질문의 제목과 내용을 화면에 보여주기 위해 객체의 id 값으로 조회한 질문의 제목과 내용을 questionForm에 담아 템플릿으로 전달했다.
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    //질문 수정하는 화면에서 저장하기 버튼을 누르면 호출되는 것
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){ //작성자와 수정자가 동일한지 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id); //검증이 통과되면 해당 id값의 숫자로 리다이렉트함.
        //String.format은 메서드에 문자열을 삽입하기 위한 서식 지정자이다. %s가 문자열의 자리를 나타냄
    }

    //질문 삭제 버튼을 누렀을 때 호출함
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    //질문 추천버튼을 눌렀을 때 사용되는 메소드
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}


