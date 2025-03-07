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
