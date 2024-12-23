package com.cmyk;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

/**
 * @ClassName LambdaDemo
 * @Description TODO
 * @Author 86152
 * @Date 2024/6/1 11:04
 * @Version 1.0
 */
public class LambdaDemo {

    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("新线程中run方法被执行了");
//            }
//        }).start();

//        //使用lambda表达式
        new Thread(()-> System.out.println("新线程中run方法被执行了")).start();

        int i = calculateNum((left, right) -> {return left + right;});
        System.out.println(i);


        printNum(value ->  value % 2 == 0);
        Integer result = typeConver( s ->   Integer.valueOf(s));
        System.out.println(result);

        String s = typeConver( x ->  x + "CMYK" );
        System.out.println(s);
        foreachArr(value -> System.out.println(value));
    }
    public static int calculateNum(IntBinaryOperator operator){
        int a = 10;
        int b = 20;
        return operator.applyAsInt(a, b);
    }
    public static void printNum(IntPredicate predicate){
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
            if(predicate.test(i)){
                System.out.println(i);
            }
        }
    }

    public static <R> R typeConver(Function<String,R> function){
        String str = "1235";
        R result = function.apply(str);
        return result;
    }

    public static void foreachArr(IntConsumer consumer){
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
            consumer.accept(i);
        }
    }
}
