package amnesia.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class UtilTest {
    /**
     * srcList=null
     */
    @Test
    void test1() {
        List<List<String>> resultList = Util.splitList(null, 1);
        assertTrue(resultList.isEmpty());
    }

    /**
     * srcList=empty
     */
    @Test
    void test2() {
        List<List<String>> resultList = Util.splitList(new ArrayList<String>(), 1);
        assertTrue(resultList.isEmpty());
    }

    /**
     * count<=0
     */
    @Test
    void test3() {
        List<String> srcList = new ArrayList<>();
        srcList.add("1");

        List<List<String>> resultList = Util.splitList(srcList, 0);
        assertTrue(resultList.isEmpty());
        resultList = Util.splitList(srcList, -1);
        assertTrue(resultList.isEmpty());
    }

    /**
     * srcList.size()=count
     */
    @Test
    void test4() {
        for (int count = 1; count <= 10; count++) {
            List<Integer> srcList = new ArrayList<>();
            for (int k = 0; k < count; k++) {
                srcList.add(k);
            }

            List<List<Integer>> resultList = Util.splitList(srcList, count);
            assertTrue(resultList.size() == 1);
        }
    }

    /**
     * 0 < count <= srcList.size
     * srcList.size < count
     */
    @Test
    void test5() {
        List<Integer> srcList = new ArrayList<>();
        for (int k = 0; k < 100; k++) {
            srcList.add(k);
        }

        for (int count = 1; count <= 200; count++) {
            List<List<Integer>> resultList = Util.splitList(srcList, count);
            System.out.println("srcList.size[" + srcList.size() + "]count[" + count + "]resultList.size[" + resultList.size() + "]");
            
            int subListCount = srcList.size() / count;
            int mod = srcList.size() % count;
            if (mod == 0) {
                assertTrue(resultList.size() == subListCount);
            }
            else {
                assertTrue(resultList.size() == (subListCount + 1));
            }
        }
    }
}
