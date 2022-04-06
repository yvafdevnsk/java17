package mizuki.sample;

public class StringBuilderSubstring {

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append("0123456789");

        // StringBuilderから部分文字列を取得する。
        //
        // StringBuilder#substring(int start, int end)
        // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/StringBuilder.html#substring(int,int)

        // インデックス3以上8未満 => "34567"
        System.out.println(builder.substring(3, 8));
    }

}
