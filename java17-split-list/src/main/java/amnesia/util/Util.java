package amnesia.util;

import java.util.ArrayList;
import java.util.List;

public class Util {

    /**
     * リストを指定された件数ごとに分割する。
     * 
     * srcListが10,500件のリストの場合、
     * countに2,000を指定すると2,000件のリストが5個、500件のリストが1個の合計6個のリストに分割される。
     * 
     * srcList[10500]
     * splitedList(0)=subList[2000]
     * splitedList(1)=subList[2000]
     * splitedList(2)=subList[2000]
     * splitedList(3)=subList[2000]
     * splitedList(4)=subList[2000]
     * splitedList(5)=subList[500]
     * 
     * @param srcList 分割するリスト
     * @param count 何件ずつ分割するか
     * @return 指定された件数ごとに分割されたリスト
     */
    public static <T> List<List<T>> splitList(List<T> srcList, int count) {
        if ((srcList == null) || srcList.isEmpty() || (count <= 0)) {
            return new ArrayList<>();
        }
        List<List<T>> splitedList = new ArrayList<>();

        int itemTotalIndex = 0;
        List<T> subList = new ArrayList<>();

        while (itemTotalIndex < srcList.size()) {
            subList.add(srcList.get(itemTotalIndex++));

            // 分割単位を満たしたsubListをここで回収する。
            if (count <= subList.size()) {
                splitedList.add(subList);
                subList = new ArrayList<>();
            }
        }

        // 分割単位を満たさない端数のsubListをここで回収する。
        if (!subList.isEmpty()) {
            splitedList.add(subList);
        }

        return splitedList;
    }

}
