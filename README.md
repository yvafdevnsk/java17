# Java17 サンプルコード

## java17-stringbuilder-substring
StringBuilderから部分文字列を取得する。
```
StringBuilder builder = new StringBuilder();
builder.append("0123456789");

// インデックス3以上8未満 => "34567"
System.out.println(builder.substring(3, 8));
```
