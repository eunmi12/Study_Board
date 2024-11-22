package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    //NotEmpty: Null 또는 빈 무자열 허용x
    //Size: 문자 길이 제한
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;

}
