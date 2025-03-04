# Java17(LTS)でApache Log4j 2.24.3を使う

## 環境

- VirtualBox 7.1.4 (2024/10/15)
- Debian 12.9 (2025/01/11)
- Linux Kernel 6.1.0-30-amd64
- Eclipse IDE for Enterprise Java and Web Developers 2024-12 (4.34.0)

## 全体概要

```
Java17Log4j
    +-src
    |   +-main
    |       +-java
    |       |   +-amnesia
    |       |   |   |-Main.java (アプリケーションのエントリーポイント)
    |       |   |
    |       |   |-module-info.java (JPMS)(Java Platform Module System)
    |       |
    |       +-resources
    |           +-bin
    |           |   |-Java17Log4j.sh (アプリケーション起動用のシェルスクリプト)
    |           |
    |           |-json-template-layout-log4j2.json (ログ出力の設定)(JSON形式)
    |           |-log4j2.xml (ログ出力の設定)
    |
    +-target
        +-release
            +-bin
            |   |-Java17Log4j.sh
            |
            +-lib
            |   |-Java17Log4j-0.0.1-SNAPSHOT.jar
            |   |-log4j-api-2.24.3.jar
            |   |-log4j-core-2.24.3.jar
            |   |-log4j-layout-template-json-2.24.3.jar
            |
            +-log
            |   |-debug.log
            |   |-main.log
            |
            +-resource
                |-json-template-layout-log4j2.json
                |-log4j2.xml
```

## 1. EclipseのInstalled JREsにJava17を追加する

```
Window >> Preferences
    Java
        Installed JREs
            Addボタン
                Standard VM
                    JRE home: /usr/local/jdk-17.0.13+11

            [x]jdk-17.0.13+11 (default) | /usr/local/jdk-17.0.13+11 | Standard VM
```

## 2. EclipseでMavenプロジェクトを新規作成する

### 2-1. プロジェクトを新規作成する

```
File >> New >> Other
    Maven
        Maven Project

[x]Create a simple project (skip archetype selection)
[x]Use default Workspace location

Artifact
    Group Id: amnesia
    Artifact Id: Java17Log4j
    Version: 0.0.1-SNAPSHOT
    Packaging: jar
    Name: Java17(LTS) Log4j
    Description: Java17(LTS)でApache Log4j 2.24.3を使う
```

### 2-2. プロジェクトのJRE System LibraryをJava17に変更する

```
Java17Log4j
    右クリック >> Properties
        Java Build Path
            Libraries
                JRE System Library [JavaSE-1.8]
                    Editボタン
                        System Library
                            [x]Execution environment: JavaSE-17
```

## 3. プロジェクトのpom.xmlファイルにLog4jを追加する

### 3-1. pom.xmlファイルを開いたエディタのエラーを修正する

pom.xmlファイルをエディタで開くと"Downloading external resources is disabled."というエラーが表示される。このエラーは設定で回避できる。

```
Window >> Preferences
    XML (Wild Web Developer)
        [x]Downloading external resources like referenced DTD, XSD
```

### 3-2. プロパティの定義を追加する

```
<!-- プロパティの定義 -->
<properties>
  <!-- プロジェクトのディレクトリを定義する。 -->
  <project.basedir>/home/mizuki/eclipse-workspace/Java17Log4j</project.basedir>
  <!-- 入力ファイルのエンコーディングを定義する。 -->
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <!-- 出力ファイルのエンコーディングを定義する。 -->
  <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
  <!-- コンパイラの出力ターゲット(javacのreleaseオプション)となるJavaのバージョンを指定する。 -->
  <maven.compiler.release>17</maven.compiler.release>
</properties>
```

### 3-3. 依存関係の定義を追加する

Log4jで使用するライブラリのバージョンを一括指定する。

```
<!-- 依存関係の定義 -->
<dependencyManagement>
  <dependencies>
    <!-- ログ出力に使用するLog4jのバージョンを一括指定する。 -->
    <!-- Log4j BOM (Bill-of-Materials) -->
    <!-- https://logging.apache.org/log4j/2.x/manual/getting-started.html#prerequisites -->
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-bom</artifactId>
      <version>2.24.3</version>
      <scope>import</scope>
      <type>pom</type>
    </dependency>
  </dependencies>
</dependencyManagement>
```

### 3-4. 依存ライブラリの定義を追加する

API(呼び出しインターフェース), Core(ログ出力の実装), テンプレートレイアウト(JSON形式のログ出力)の3つを追加する。

```
<!-- 依存ライブラリの定義 -->
<dependencies>
  <!-- Logging API (Log4j API) -->
  <!-- https://logging.apache.org/log4j/2.x/manual/getting-started.html#using-api -->
  <!-- バージョンはdependencyManagementで指定しているのでここには記述しない。 -->
  <!-- コンパイル時に参照する。 -->
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
  </dependency>
  <!-- Logging implementation (Log4j Core) -->
  <!-- https://logging.apache.org/log4j/2.x/manual/getting-started.html#install-app -->
  <!-- 実行時に参照する。 -->
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <scope>runtime</scope>
  </dependency>
  <!-- Log4j JSON-encoding support -->
  <!-- https://logging.apache.org/log4j/2.x/manual/getting-started.html#install-app -->
  <!-- 実行時に参照する。 -->
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-layout-template-json</artifactId>
    <scope>runtime</scope>
  </dependency>
</dependencies>
```

## 4. プロジェクトにLog4jの設定ファイルを追加する

### 4-1. ログ出力の設定ファイルを追加する

Java17Log4j/src/main/resources/log4j2.xml  
Log4jのWebサイトから設定ファイルのサンプルを取得して書き換える。

```
<!-- 出力先の定義 -->
<Appenders>
  <!-- コンソール出力 -->
  <!-- エンコーディングをUTF-8にする。EclipseのXMLエディタ上ではエラーになるが動作する。 -->
  <!--   issueが登録されている。 -->
  <!--     Log4J2: PatternLayout "charset" in documentation but forbidden by xml schema #3423 -->
  <!--     https://github.com/apache/logging-log4j2/issues/3423 -->
  <!-- エンコーディングのデフォルト値はプラットフォームのデフォルトになっている。 -->
  <!-- https://logging.apache.org/log4j/2.x/manual/pattern-layout.html#plugin-attr-charset -->
  <Console name="CONSOLE">
    <PatternLayout pattern="%p - %m%n" charset="UTF-8"/>
  </Console>
  <!-- ファイル出力 -->
  <File name="MAIN" fileName="/home/mizuki/eclipse-workspace/Java17Log4j/target/release/log/main.log">
    <!-- JSON Template Layoutのエンコーディングのデフォルト値はUTF-8になっている。 -->
    <!-- https://logging.apache.org/log4j/2.x/manual/json-template-layout.html#plugin-attr-charset -->
    <!-- 設定ファイルはクラスパスに配置してeventTemplateUri属性で指定する。 -->
    <!-- 設定ファイルはサンプルを部分的に置き換えるのが簡単で良い。 -->
    <!-- https://logging.apache.org/log4j/2.x/manual/json-template-layout.html#event-templates -->
    <!--   EcsLayout.json -->
    <!-- 設定ファイルのタイムスタンプのタイムゾーンをUTCから日本(Asia/Tokyo)にする。 -->
    <!-- https://logging.apache.org/log4j/2.x/manual/json-template-layout.html#event-template-resolver-timestamp -->
    <JsonTemplateLayout eventTemplateUri="classpath:json-template-layout-log4j2.json"/>
  </File>
  <!-- ファイル出力 -->
  <!-- エンコーディングをUTF-8にする。EclipseのXMLエディタ上ではエラーになるが動作する。 -->
  <!--   issueが登録されている。 -->
  <!--     Log4J2: PatternLayout "charset" in documentation but forbidden by xml schema #3423 -->
  <!--     https://github.com/apache/logging-log4j2/issues/3423 -->
  <!-- エンコーディングのデフォルト値はプラットフォームのデフォルトになっている。 -->
  <!-- https://logging.apache.org/log4j/2.x/manual/pattern-layout.html#plugin-attr-charset -->
  <File name="DEBUG_LOG" fileName="/home/mizuki/eclipse-workspace/Java17Log4j/target/release/log/debug.log">
    <PatternLayout pattern="%d [%t] %p %c - %m%n" charset="UTF-8"/>
  </File>
</Appenders>
```

```
<!-- ログの定義 -->
<!--   ログのレベル -->
<!--   https://logging.apache.org/log4j/2.x/javadoc/log4j-api/org/apache/logging/log4j/Level.html -->
<!--     [低] ALL, TRACE, DEBUG, INFO, WARN, ERROR, FATAL [高] -->
<!--   デフォルトのログのレベルはERROR -->
<!--   https://logging.apache.org/log4j/2.x/manual/systemproperties.html#log4j2.level -->
<Loggers>
  <!-- DEBUG以上のレベルを受け入れる。 -->
  <Root level="DEBUG">
    <!-- WARN以上のレベルをコンソールに出力する。 -->
    <AppenderRef ref="CONSOLE" level="WARN"/>
    <!-- INFO以上のレベルをMAINファイルに出力する。 -->
    <AppenderRef ref="MAIN" level="INFO"/>
    <!-- DEBUG以上のレベルをDEBUG_LOGファイルに出力する。 -->
    <AppenderRef ref="DEBUG_LOG" level="DEBUG"/>
  </Root>
</Loggers>
```

### 4-2. JSON Template Layoutの設定ファイルを追加する

Java17Log4j/src/main/resources/json-template-layout-log4j2.json  
設定ファイルのサンプルのタイムスタンプのタイムゾーンをUTCから日本(Asia/Tokyo)にする。

```
{
  "@timestamp": {
    "$resolver": "timestamp",
    "pattern": {
      "format": "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
      "timeZone": "Asia/Tokyo"
    }
  },
}
```

## 5. プロジェクトにJavaのソースコードを追加する

### 5-1. Main.javaを追加する

Java17Log4j/src/main/java/amnesia/Main.java

```
package amnesia;

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

### 5-2. module-info.javaを追加する

Java17Log4j/src/main/java/module-info.java

```
module amnesia {
    requires java.xml;
    requires org.apache.logging.log4j;
}
```

## 6. プロジェクトにアプリケーション起動用のシェルスクリプトを追加する

### 6-1. Java17Log4j.shを追加する

Java17Log4j/src/main/resources/bin/Java17Log4j.sh

```
#!/bin/bash

JAVA_HOME="/usr/local/jdk-17.0.13+11"
CLASS_PATH_JAR="/home/mizuki/eclipse-workspace/Java17Log4j/target/release/lib/*"
CLASS_PATH_RESOURCE="/home/mizuki/eclipse-workspace/Java17Log4j/target/release/resource"
CLASS_PATH=$CLASS_PATH_JAR:$CLASS_PATH_RESOURCE
MAIN_CLASS=amnesia.Main

$JAVA_HOME/bin/java -cp $CLASS_PATH $MAIN_CLASS
```

### 6-2. シェルスクリプトを実行可能にする

```
cd /home/mizuki/eclipse-workspace/Java17Log4j/src/main/resources/bin
chmod +x Java17Log4j.sh
```

## 7. プロジェクトのpom.xmlファイルにビルドの定義を追加する

### 7-1. コンパイルの定義を追加する

```
<!-- ビルドの定義 -->
<build>
    <plugins>
        <!-- コンパイル -->
        <!-- https://maven.apache.org/plugins/maven-compiler-plugin/usage.html -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.14.0</version>
            <configuration>
                <!-- ソースコードのディレクトリを指定する。 -->
                <!-- https://maven.apache.org/plugins/maven-compiler-plugin/compile-mojo.html#compileSourceRoots -->
                <compileSourceRoots>
                    <compileSourceRoot>${project.basedir}/src/main/java</compileSourceRoot>
                </compileSourceRoots>
                <!-- クラスファイルを出力するディレクトリを指定する。 -->
                <!-- https://maven.apache.org/plugins/maven-compiler-plugin/compile-mojo.html#outputDirectory -->
                <outputDirectory>${project.basedir}/target/classes</outputDirectory>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 7-2. JARファイルの作成の定義を追加する

```
<!-- ビルドの定義 -->
<build>
    <plugins>
        <!-- JARファイルの作成 -->
        <!-- https://maven.apache.org/plugins/maven-compiler-plugin/usage.html -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.4.2</version>
            <configuration>
                <!-- JARファイルに入れるクラスファイルのディレクトリを指定する。 -->
                <!-- https://maven.apache.org/plugins/maven-jar-plugin/jar-mojo.html#classesDirectory -->
                <classesDirectory>${project.basedir}/target/classes</classesDirectory>
                <!-- JARファイルに入れるファイルを指定する。 -->
                <!-- https://maven.apache.org/plugins/maven-jar-plugin/jar-mojo.html#includes -->
                <!-- "**"は任意のディレクトリ、"*"は任意のファイル -->
                <!-- ${project.basedir}/target/classesに${project.basedir}/src/main/resources配下のファイルがコピーされるので必要なものだけJARファイルに入れる。 -->
                <includes>
                    <include>module-info.class</include>
                    <include>amnesia/**/*.class</include>
                </includes>
                <!-- JARファイルにpom.xmlを入れない。 -->
                <archive>
                    <addMavenDescriptor>false</addMavenDescriptor>
                </archive>
                <!-- JARファイルを出力するディレクトリを指定する。 -->
                <!-- https://maven.apache.org/plugins/maven-jar-plugin/jar-mojo.html#outputDirectory -->
                <outputDirectory>${project.basedir}/target/release/lib</outputDirectory>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 7-3. リソースファイルのコピーの定義を追加する

```
<!-- ビルドの定義 -->
<build>
    <plugins>
        <!-- リソースファイルのコピー -->
        <!-- https://maven.apache.org/plugins/maven-resources-plugin/index.html -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <version>3.3.1</version>
            <executions>
                <execution>
                    <id>copy-resources-shell</id>
                    <!-- Introduction to the Build Lifecycle -->
                    <!-- https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html -->
                    <phase>validate</phase>
                    <goals>
                        <goal>copy-resources</goal>
                    </goals>
                    <configuration>
                        <!-- リソースファイルを出力するディレクトリを指定する。 -->
                        <!-- https://maven.apache.org/plugins/maven-resources-plugin/resources-mojo.html#outputDirectory -->
                        <outputDirectory>${project.basedir}/target/release/bin</outputDirectory>
                        <!-- コピーするリソースを指定する。 -->
                        <!-- https://maven.apache.org/plugins/maven-resources-plugin/examples/include-exclude.html -->
                        <resources>
                            <resource>
                                <directory>${project.basedir}/src/main/resources/bin</directory>
                                <includes>
                                    <include>Java17Log4j.sh</include>
                                </includes>
                            </resource>
                        </resources>
                    </configuration>
                </execution>
                <execution>
                    <id>copy-resources-log</id>
                    <!-- Introduction to the Build Lifecycle -->
                    <!-- https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html -->
                    <phase>validate</phase>
                    <goals>
                        <goal>copy-resources</goal>
                    </goals>
                    <configuration>
                        <!-- リソースファイルを出力するディレクトリを指定する。 -->
                        <!-- https://maven.apache.org/plugins/maven-resources-plugin/resources-mojo.html#outputDirectory -->
                        <outputDirectory>${project.basedir}/target/release/resource</outputDirectory>
                        <!-- コピーするリソースを指定する。 -->
                        <!-- https://maven.apache.org/plugins/maven-resources-plugin/examples/include-exclude.html -->
                        <resources>
                            <resource>
                                <directory>${project.basedir}/src/main/resources</directory>
                                <includes>
                                    <include>json-template-layout-log4j2.json</include>
                                    <include>log4j2.xml</include>
                                </includes>
                            </resource>
                        </resources>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### 7-4. 依存ライブラリのコピーの定義を追加する

```
<!-- ビルドの定義 -->
<build>
    <plugins>
        <!-- 依存ライブラリのコピー -->
        <!-- https://maven.apache.org/components/plugins/maven-dependency-plugin/examples/copying-project-dependencies.html -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            <version>3.8.1</version>
            <executions>
                <execution>
                    <id>copy-dependencies</id>
                    <!-- Introduction to the Build Lifecycle -->
                    <!-- https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html -->
                    <phase>package</phase>
                    <goals>
                        <goal>copy-dependencies</goal>
                    </goals>
                    <configuration>
                        <!-- 依存ライブラリを出力するディレクトリを指定する。 -->
                        <!-- https://maven.apache.org/components/plugins/maven-dependency-plugin/copy-dependencies-mojo.html#outputDirectory -->
                        <outputDirectory>${project.basedir}/target/release/lib</outputDirectory>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## 8. ワークスペースにビルド設定を追加する

### 8-1. Run ConfigurationsのMaven Buildにビルド設定を追加する

pom.xml >> 右クリック >> Run As >> Run Configurations >> Maven Build >> 右クリック >> New Configuration

```
Name: Java17(LTS) Log4j 開発ビルド
Main
    Base directory:
          ${project_loc} //Variablesボタンから指定する。
    Goals: clean package
    [x]Skip Tests
```

### 8-2. プロジェクトをビルドする

1. Java17Log4j >> 右クリック >> Refresh
2. pom.xml >> 右クリック >> Maven >> Update Project
3. pom.xml >> 右クリック >> Run As >> Java17(LTS) Log4j 開発ビルド (Maven Build)

## 9. アプリケーションを実行する

### 9-1. シェルスクリプトを実行してアプリケーションを起動する

```
cd /home/mizuki/eclipse-workspace/Java17Log4j/target/release/bin
./Java17Log4j.sh
```

### 9-2. 出力されたログを確認する

コンソールログにWARN以上のログが出力される。

```
FATAL - ログメッセージ `fatal`
ERROR - ログメッセージ `error`
WARN - ログメッセージ `warn`
```

メインログにINFO以上のログがJSON形式で出力される。  
/home/mizuki/eclipse-workspace/Java17Log4j/target/release/log/main.log

```
{"@timestamp":"2025-03-04T15:37:25.187+09:00","ecs.version":"1.2.0","log.level":"FATAL","message":"ログメッセージ `fatal`","process.thread.name":"main","log.logger":"amnesia.Main"}
{"@timestamp":"2025-03-04T15:37:25.194+09:00","ecs.version":"1.2.0","log.level":"ERROR","message":"ログメッセージ `error`","process.thread.name":"main","log.logger":"amnesia.Main"}
{"@timestamp":"2025-03-04T15:37:25.194+09:00","ecs.version":"1.2.0","log.level":"WARN","message":"ログメッセージ `warn`","process.thread.name":"main","log.logger":"amnesia.Main"}
{"@timestamp":"2025-03-04T15:37:25.194+09:00","ecs.version":"1.2.0","log.level":"INFO","message":"ログメッセージ `info`","process.thread.name":"main","log.logger":"amnesia.Main"}
```

デバッグログにDEBUG以上のログが出力される。  
/home/mizuki/eclipse-workspace/Java17Log4j/target/release/log/debug.log

```
2025-03-04 15:37:25,187 [main] FATAL amnesia.Main - ログメッセージ `fatal`
2025-03-04 15:37:25,194 [main] ERROR amnesia.Main - ログメッセージ `error`
2025-03-04 15:37:25,194 [main] WARN amnesia.Main - ログメッセージ `warn`
2025-03-04 15:37:25,194 [main] INFO amnesia.Main - ログメッセージ `info`
2025-03-04 15:37:25,194 [main] DEBUG amnesia.Main - ログメッセージ `debug`
```
