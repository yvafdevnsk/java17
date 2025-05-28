package amnesia.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import amnesia.util.Util;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        List<String> srcList = new ArrayList<>(Arrays.asList(new String[10500]));
        int count = 2000;
        List<List<String>> splitList = Util.splitList(srcList, count);
        for (int i = 0; i < splitList.size(); i++) {
            System.out.println(String.format("分割リスト[%d]=[%d]件", i, splitList.get(i).size()));
        }
    }
}
