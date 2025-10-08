package amnesia.util;

import java.util.ArrayList;
import java.util.List;

public class Util {

    /**
     * <p>引数のリストの要素を新しいインスタンスのリストに格納して部分リストを取得する。
     * 部分リストへの操作は引数のリストに影響を与えない。</p>
     * 
     * @param srcList 部分リストを取得するリスト
     * @param start 開始インデックス(0から)
     * @return 新しいインスタンスの部分リスト
     */
    public static <T> List<T> subList(List<T> srcList, int start) {
        if ((start < 0) || ((srcList.size() - 1) < start)) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add("start is out of range.");
            errorMessage.add("srcList.size()[" + srcList.size() + "]");
            errorMessage.add("start[" + start + "]");
            throw new IllegalArgumentException(String.join(" ", errorMessage));
        }

        List<T> subList = new ArrayList<>();
        for (int i = start; i < srcList.size(); i++) {
            subList.add(srcList.get(i));
        }

        return subList;
    }

    /**
     * <p>引数のリストの要素を新しいインスタンスのリストに格納して部分リストを取得する。
     * 部分リストへの操作は引数のリストに影響を与えない。</p>
     * 
     * <ul>
     *     <li><code>srcList.size() &lt; end</code>の場合はリストの最後まで格納して返す。</li>
     *     <li><code>start == end</code>の場合は空のリストを返す。</li>
     * </ul>
     * 
     * @param srcList 部分リストを取得するリスト
     * @param start 開始インデックス(0から)
     * @param end 終了インデックス(0から)(このインデックスを含まない)
     * @return 新しいインスタンスの部分リスト
     */
    public static <T> List<T> subList(List<T> srcList, int start, int end) {
        if (
            ((start < 0) || ((srcList.size() - 1) < start)) ||
            (end < 0) ||
            (end < start)
        ) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add("start or end is out of range.");
            errorMessage.add("srcList.size()[" + srcList.size() + "]");
            errorMessage.add("start[" + start + "]");
            errorMessage.add("end[" + end + "]");
            throw new IllegalArgumentException(String.join(" ", errorMessage));
        }

        if (srcList.size() < end) {
            end = srcList.size();
        }

        List<T> subList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            subList.add(srcList.get(i));
        }

        return subList;
    }

}
