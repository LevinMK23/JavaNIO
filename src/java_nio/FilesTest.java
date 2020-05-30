package java_nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesTest {
    public static void main(String[] args) throws IOException {
        //Files.createFile(Paths.get("file.txt"));
//        for (int i = 1; i < 11; i++) {
//            //Files.createDirectory(Paths.get("directory" + i));
//            for (int j = 1; j < 6; j++) {
//                Files.createFile(Paths.get("directory" + i, "file" + j + ".txt"));
//            }
//        }
        Files.list(Paths.get("./")).forEach(System.out :: println);

    }
}
