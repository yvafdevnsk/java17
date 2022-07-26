package mizuki.java17;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriteTextFile {

    public static void main(String[] args) {
        WriteTextFile.writeWindows31J();
        WriteTextFile.writeUTF16LE();
    }

    private static void writeWindows31J() {
        Path outputFilePath = Paths.get("/home/mizuki/tmp", "write-text-file-windows31j.txt");
        Charset charset = Charset.forName("Windows-31J");

        String outputString = "あいうえお赤青黄野菜肉魚ビタミンカルシウム鉄";

        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath, charset)) {
            writer.write(outputString);
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    private static void writeUTF16LE() {
        Path outputFilePath = Paths.get("/home/mizuki/tmp", "write-text-file-utf16le.txt");
        Charset charset = StandardCharsets.UTF_16LE;

        String outputString = "あいうえお赤青黄野菜肉魚ビタミンカルシウム鉄";

        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath, charset)) {
            writer.write(outputString);
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
}
