package com.cmyk;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ClassName OptionalDemo
 * @Description TODO
 * @Author 86152
 * @Date 2024/6/1 22:50
 * @Version 1.0
 */
public class OptionalDemo {

    public static void main(String[] args) {
//        testFilter();
        testMap();
    }

    private static void testMap() {
        Optional<Author> authorOptional = getAuthor();
        authorOptional
                .map(author -> author.getBooks())
                .ifPresent(books -> System.out.println(books));
        }

    private static void testFilter() {
        Optional<Author> authorOptional = getAuthor();
        authorOptional
                .filter(author -> author.getAge() > 18) //对optional内封装的数据进行过滤
                .ifPresent(author -> System.out.println(author));//过滤后再消费
    }

    private static Optional<Author> getAuthor() {
        //数据初始化
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        List<Book> books1 = new ArrayList<>();

        books1.add(new Book(1L,"刀的两侧是光明与黑暗","哲学,爱情",88,"用一把刀划分了爱恨"));
        books1.add(new Book(2L,"一个人不能死在同一把刀下","个人成长,爱情",99,"讲述如何从失败中明悟真理"));
        author.setBooks(books1);
        return Optional.ofNullable(author);
    }
}
