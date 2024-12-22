package com.cmyk;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName StreamDemo
 * @Description TODO
 * @Author 86152
 * @Date 2024/6/1 11:10
 * @Version 1.0
 */
public class StreamDemo {
    public static void main(String[] args) {
        test16();
        
    }
    private static void test29() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = stream.parallel()
                .peek(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer num) {
                        System.out.println(num+Thread.currentThread().getName());
                    }
                })
                .filter(num -> num > 5)
                .reduce((result, ele) -> result + ele)
                .get();
        System.out.println(sum);
    }

    private static void test28() {
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

    private static void test27() {
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

    private static void testAnd() {
        List<Author> authors = getAuthors();
        authors.stream()
                .filter(((Predicate<Author>) author -> author.getAge() > 17).and(author -> author.getName().length() > 1))
                .forEach(author -> System.out.println(author.getName() +": "+author.getAge()));
    }

    private static void test26() {
        //​	使用reduce求所有作者中年龄的最小值
        List<Author> authors = getAuthors();
        Optional<Integer> minAge = authors.stream()
                .map(author -> author.getAge())
                .reduce((result, element) -> result < element ? result :element);
        System.out.println(minAge.get());
    }

    private static void test25() {
        //​	使用reduce求所有作者中年龄的最小值
        List<Author> authors = getAuthors();
        Integer minAge = authors.stream()
                .map(author -> author.getAge())
                .reduce(Integer.MAX_VALUE, (result, element) -> result > element ? element : result);
        System.out.println(minAge);
    }

    private static void test24() {
        //​	使用reduce求所有作者中年龄的最大值
        List<Author> authors = getAuthors();
        Integer maxAge = authors.stream()
                .map(author -> author.getAge())
                .reduce(Integer.MIN_VALUE, (max, element) -> max > element ? max : element);
        System.out.println(maxAge);
    }

    private static void test23() {
        //使用reduce求所有作者年龄的和
        List<Author> authors = getAuthors();
        Integer ageSum = authors.stream()
                .distinct()
                .map(author -> author.getAge())
                .peek(System.out::println)
                //初始result值为0，两者相同加，再赋值给result，最后返回结果为result
                .reduce(0, (result, element) -> result + element);
        System.out.println(ageSum);
    }

    private static void test22() {
        //获取一个年龄最小的作家，并输出他的姓名
        List<Author> authors = getAuthors();
        Optional<Author> first = authors.stream()
                .sorted()
                .findFirst();
        first.ifPresent(author -> System.out.println(author.getName()));

    }

    private static void test21() {
        //获取任意一个年龄大于18的作家，如果存在就输出他的名字
        List<Author> authors = getAuthors();
        Optional<Author> optional = authors.stream()
                .filter(author -> author.getAge() > 18)
                .findAny();

        optional.ifPresent(author -> System.out.println(author.getName()));
    }

    private static void test20() {
        //​	判断作家是否都没有超过100岁的。
        List<Author> authors = getAuthors();
        boolean flag = authors.stream()
                .noneMatch(author -> author.getAge() > 100);
        System.out.println(flag);
    }

    private static void test19() {
//        判断是否所有的作家都是成年人
        List<Author> authors = getAuthors();
        boolean flag = authors.stream()
                .allMatch(author -> author.getAge() >= 18);
        System.out.println(flag);
    }


    private static void test18() {

    }

    private static void test17() {
//        判断是否有年龄在29以上的作家
        List<Author> authors = getAuthors();
        boolean flag = authors.stream()
                .anyMatch(author -> author.getAge() > 29);
        System.out.println(flag);
    }


    private static void test16() {
        //获取一个Map集合，map的key为作者名，value为List<Book>
        List<Author> authors = getAuthors();
//        Map<String, List<Book>> map = authors.stream()
//                .distinct()
//                .collect(Collectors.toMap(new Function<Author, String>() {
//                    @Override
//                    public String apply(Author author) { //用Author对象的什么属性作为key
//                        return author.getName();
//                    }
//                }, new Function<Author, List<Book>>() {//用Author对象的什么属性作为value
//                    @Override
//                    public List<Book> apply(Author author) {
//                        return author.getBooks();
//                    }
//                }));
        Map<String, List<Book>> map = authors.stream()
                .distinct()
                .collect(Collectors.toMap(author -> author.getName(), author -> author.getBooks()));
        System.out.println(map);
    }

    private static void test15() {
        // 获取一个所有书名的Set集合。
        List<Author> authors = getAuthors();
        Set<String> bookSet = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> book.getName())
                .collect(Collectors.toSet());
        System.out.println(bookSet);
    }

    private static void test14() {
        //​	获取一个存放所有作者名字的List集合。
        List<Author> authors = getAuthors();
        List<String> nameList = authors.stream()
                .map(Author::getName)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(nameList);
    }

    private static void test13() {
        //        分别获取这些作家的所出书籍的最高分和最低分并打印。
        //Stream<Author>  -> Stream<Book> ->Stream<Integer>  ->求值

        List<Author> authors = getAuthors();
        Optional<Integer> max = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> book.getScore())
                .max((o1, o2) -> o1 - o2);

        Optional<Integer> min = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> book.getScore())
                .min((score1, score2) -> score1 - score2);
        System.out.println(max.get());
        System.out.println(min.get());
    }

    private static void test12() {
    //        打印这些作家的所出书籍的数目，注意删除重复元素。
        List<Author> authors = getAuthors();
        long count = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .count();
        System.out.println(count);
    }

    private static void test11() {
        //打印现有数据的所有分类。要求对分类进行去重。不能出现这种格式：哲学,爱情
        List<Author> authors = getAuthors();
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .flatMap(book -> Arrays.stream(book.getCategory().split(",")))
                .distinct()
                .forEach(category -> System.out.println(category ));
    }

    private static void test10() {
        //打印所有书籍的名字。要求对重复的元素进行去重。
        List<Author> authors = getAuthors();
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .forEach(book -> System.out.println(book.getName()));
    }

    private static void test09() {
        //打印除了年龄最大的作家外的其他作家，要求不能有重复元素，并且按照年龄降序排序。
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted()
                .skip(1)
                .forEach(author -> System.out.println(author.getName()+": "+author.getAge()));
    }

    private static void test08() {
        //对流中的元素按照年龄进行降序排序，并且要求不能有重复的元素,然后打印其中年龄最大的两个作家的姓名。
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge()) //按照年龄降序排序
                .limit(2)
                .forEach(author -> System.out.println(author.getAge()));
    }

    private static void test07() {
        List<Author> authors = getAuthors();
        //对流中的元素按照年龄进行降序排序，并且要求不能有重复的元素。
//        authors.stream()
//                .distinct()
//                .sorted()
//                .forEach(author -> System.out.println(author.getAge()));
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge()) //设置比较器，按升序排序
                .forEach(author -> System.out.println(author.getAge()));
    }

    private static void test06() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .forEach(author -> System.out.println(author.getName()));
    }

    private static void test05() {
        //打印所有作家的姓名
        List<Author> authors = getAuthors();
//        authors.stream()
//                //传入的是Function接口的实现类，第一个泛型是流中对象的类型，第二个泛型是目的类型
//                .map(author -> author.getName()) //将流中的Author类型的对象，转化为String对象)
//                .forEach(s -> System.out.println(s));
        authors.stream()
                .map(new Function<Author, Integer>() {
                    @Override
                    public Integer apply(Author author) {
                        return author.getAge();
                    }
                })
                .map(age->age+10) //运算
                .forEach(age-> System.out.println(age));
    }

    private static void test04() {
        //打印所有姓名长度大于1的作家的姓名
        List<Author> authors = getAuthors();
        //返回一个布尔值，对流中的每个对象按条件进行筛选
        authors.stream()
                .filter(author -> author.getName().length() > 1)
                .forEach(author -> System.out.println(author.getName()));
    }

    private static void  test03() {
        Map<String,Integer> map = new HashMap<>();
        map.put("蜡笔小新",19);
        map.put("黑子",17);
        map.put("日向翔阳",16);
        //将map中的每个键值对封装在一个entry对象中，将一个个entry对象保存在一个set中
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        entries.stream()
                .filter(entry -> entry.getValue() > 16)
                .forEach(entry -> System.out.println(entry.getKey()+" ---"+entry.getValue()));
    }

    private static void test02() {
        Integer[] arr = {1,2,3,4,5};
        //Stream<Integer> stream = Arrays.stream(arr);
        Stream<Integer> stream = Stream.of(arr);
        stream.filter(integer -> integer > 2)
                .forEach(integer -> System.out.println(integer));
    }
    private static void test01() {
        List<Author> authors = getAuthors();
        authors.stream()//将集合转化为流
                .distinct() //去重
                .filter(author -> author.getAge()  < 18) //筛选，将age<18的留着流里
                .forEach(author -> System.out.println(author.getName()));//遍历
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
