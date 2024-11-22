package com.mysite.sbb.user;

import lombok.Getter;

@Getter //UserRole은 상수 값을 변경할 필요가 없기 때문에 Getter 만 사용한다.
public enum UserRole {

    //두 개의 상수 선언
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value){
        this.value = value;
    }

    private String value;
}
