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
