package com.cmyk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode//重写Object的equals方法
public class Author implements Comparable<Author> {
    //id
    private Long id;
    //姓名
    private String name;
    //年龄
    private Integer age;
    //简介
    private String intro;
    //作品
    private List<Book> books;

    @Override
    public int compareTo(Author o) {
        return this.getAge() - o.getAge(); //按age升序排序
//        return o.getAge() -this.getAge(); //按age降序排序
    }
}