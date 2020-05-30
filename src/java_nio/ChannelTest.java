package java_nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class ChannelTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(5);
        for (int i = 0; i < 3; i++) {
            buffer.put((byte) (65 + i));
        }
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            System.out.print((char)buffer.get());
        }
        System.out.println();
        ByteBuffer dst = ByteBuffer.allocate(15);
        channel.read(dst);
        dst.flip();
        while (dst.hasRemaining()) {
            System.out.print((char)dst.get());
        }
        System.out.println();
    }
}
