package amnesia.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import amnesia.util.Util;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        List<Integer> srcList = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(i -> srcList.add(i));

        //srcList=1,2,3,4,5,6,7,8,9,10
        //subList=6,7,8,9,10
        List<Integer> subList = Util.subList(srcList, 5);
        System.out.println("srcList=1,2,3,4,5,6,7,8,9,10");
        System.out.println("subList=6,7,8,9,10");
        System.out.println(subList.stream().map(String::valueOf).collect(Collectors.joining(",")));

        //srcList=1,2,3,4,5,6,7,8,9,10
        //subList=4,5,6
        subList = Util.subList(srcList, 3, 6);
        System.out.println("srcList=1,2,3,4,5,6,7,8,9,10");
        System.out.println("subList=4,5,6");
        System.out.println(subList.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }
}
