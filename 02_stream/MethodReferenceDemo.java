package com.cmyk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @ClassName MethodReferenceDemo
 * @Description 方法引用测试
 * @Author 86152
 * @Date 2024/6/2 0:53
 * @Version 1.0
 */
public class MethodReferenceDemo {

    interface UseString{
        String use(String str,int start,int length);
    }

    public static String subAuthorName(String str, UseString useString){
        int start = 0;
        int length = 1;
        return useString.use(str,start,length);
    }


    public static void main(String[] args) {

        test04();

    }

    private static void test04() {
        List<Author> authors = getAuthors();
        authors.stream()
                .map(Author::getName)
                .map(StringBuilder::new)
                .map(sb->sb.append("-三更").toString())
                .forEach(System.out::println);
    }

    private static void test03() {
        subAuthorName("三更草堂", new UseString() {
            @Override
            public String use(String str, int start, int length) {
                return str.substring(start,length);
            }
        });
    }

    private static void test2() {
        List<Author> authors = getAuthors();

        Stream<Author> authorStream = authors.stream();
        StringBuilder sb = new StringBuilder();
        authorStream
                .map(new Function<Author, String>() {
                    @Override
                    public String apply(Author author) {
                        return author.getName();  //调用了author对象的成员方法，但没有传递抽象方法的参数，不能简化
                    }
                })
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String name) {
                        sb.append(name);//调用了sb对象的成员方法，且将抽象方法的参数传递进去
                    }
                });
    }

    private static void test1() {
        List<Author> authors = getAuthors();

        authors.stream()
                .map(new Function<Author, Integer>() {
                    @Override
                    public Integer apply(Author author) {
                        return author.getAge(); //并没有调用静态方法，也没有传递抽象方法的参数
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer age) {
                        return String.valueOf(age); //调用String的静态方法，且将抽象方法的参数传递进去
                    }
                });
    }
    private static List<Author> getAuthors() {
        //数据初始化
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        Author author2 = new Author(2L,"亚拉索",15,"狂风也追逐不上他的思考速度",null);
        Author author3 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);
        Author author4 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);

        //书籍列表
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L,"刀的两侧是光明与黑暗","哲学,爱情",88,"用一把刀划分了爱恨"));
        books1.add(new Book(2L,"一个人不能死在同一把刀下","个人成长,爱情",99,"讲述如何从失败中明悟真理"));

        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(4L,"吹或不吹","爱情,个人传记",56,"一个哲学家的恋爱观注定很难把他所在的时代理解"));

        books3.add(new Book(5L,"你的剑就是我的剑","爱情",56,"无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));

        author.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        List<Author> authorList = new ArrayList<>(Arrays.asList(author2,author,author3,author4));
        return authorList;
    }
}
