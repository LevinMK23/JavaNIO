package java_nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Iterator;

public class PathTest {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("1.txt");
        //File file = new File("C:\\Users\\user\\IdeaProjects\\JavaNIO\\src\\java_nio\\PathTest.java");
        if (!Files.exists(Paths.get("dir1"))) {
            Files.createDirectory(Paths.get("dir1"));
        }
        Files.write(path, new byte[]{65, 66, 67}, StandardOpenOption.APPEND);
        Files.copy(path, Paths.get("dir1", "copy.txt"), StandardCopyOption.REPLACE_EXISTING);
        //Files.createFile(path, StandardOpenOption.CREATE_NEW);
        System.out.println(path);
        System.out.println(path.startsWith(Paths.get("1", "2")));
        System.out.println(Files.exists(path));
        System.out.println(path.getFileName());
        //System.out.println(path.getFileSystem());
        //System.out.println(path.getParent().getParent().getParent());
        System.out.println(Paths.get("dir1", "1", "1.txt").resolve(Paths.get("dir1", "copy.txt")));
        Path absPath = path.toAbsolutePath();
        System.out.println(path.toRealPath());
        Path p = Paths.get("dir1");
        Iterator<Path> iterator = p.iterator();
        while (iterator.hasNext()) {
            Path tmp = iterator.next();
            System.out.println(tmp);
        }
        while (absPath.getParent() != null) {
            System.out.println(absPath);
            absPath = absPath.getParent();
        }
    }
}
