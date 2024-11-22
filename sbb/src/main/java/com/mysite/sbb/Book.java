package com.mysite.sbb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

    private String title;
    private String author;

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("점프 투 스프링 부트 3");
        book.setAuthor("박응용");

        System.out.println(book.getAuthor());
        System.out.println(book.getTitle());
    }

}
