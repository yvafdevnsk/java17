# Java17 サンプルコード

## java17-stringbuilder-substring
StringBuilderから部分文字列を取得する。
```
StringBuilder builder = new StringBuilder();
builder.append("0123456789");

// インデックス3以上8未満 => "34567"
System.out.println(builder.substring(3, 8));
```

## java17-write-text-file
エンコーディングを指定してテキストファイルに書き込む。
```
Path outputFilePath = Paths.get("/home/mizuki/tmp", "write-text-file-windows31j.txt");
Charset charset = Charset.forName("Windows-31J");

String outputString = "あいうえお赤青黄野菜肉魚ビタミンカルシウム鉄";

try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath, charset)) {
    writer.write(outputString);
}
catch(IOException e) {
    System.err.println(e);
}
```

## java17-split-list
リストを指定された件数ごとに分割する。
```
List<String> srcList = new ArrayList<>(Arrays.asList(new String[10500]));
int count = 2000;

List<List<String>> splitList = Util.splitList(srcList, count);
for (int i = 0; i < splitList.size(); i++) {
    System.out.println(String.format("分割リスト[%d]=[%d]件", i, splitList.get(i).size()));
}
```

## java17-sub-list
リストから部分リストを取得する。
```
List<Integer> srcList = new ArrayList<>();
IntStream.rangeClosed(1, 10).forEach(i -> srcList.add(i));
List<Integer> subList = Util.subList(srcList, 3, 6);

System.out.println("srcList=1,2,3,4,5,6,7,8,9,10");
System.out.println("subList=4,5,6");
System.out.println(subList.stream().map(String::valueOf).collect(Collectors.joining(",")));
```

## Java17Log4j
Java17(LTS)でApache Log4j 2.24.3を使う。

- [Javaプロジェクト](https://github.com/yvafdevnsk/java17/tree/main/Java17Log4j)
- [ドキュメント](https://github.com/yvafdevnsk/java17/blob/main/java17-apache-log4j-2.24.3.md)

```
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.fatal("ログメッセージ `{}`", "fatal");
        LOGGER.error("ログメッセージ `{}`", "error");
        LOGGER.warn("ログメッセージ `{}`", "warn");
        LOGGER.info("ログメッセージ `{}`", "info");
        LOGGER.debug("ログメッセージ `{}`", "debug");
        LOGGER.trace("ログメッセージ `{}`", "trace");
    }
}
```
