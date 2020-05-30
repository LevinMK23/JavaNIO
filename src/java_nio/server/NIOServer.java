package java_nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer implements Runnable{

    ServerSocketChannel srv;
    Selector selector;
    ByteBuffer buffer = ByteBuffer.allocate(256);
    static int cnt = 1;

    @Override
    public void run() {
        try {
            srv = ServerSocketChannel.open();
            srv.socket().bind(new InetSocketAddress(8189));
            selector = Selector.open();
            srv.configureBlocking(false);
            srv.register(selector, SelectionKey.OP_ACCEPT);
            SelectionKey key;
            Iterator<SelectionKey> iterator;
            while (srv.isOpen()) {
                selector.select();
                iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isReadable()) {
                        handleRead(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        StringBuilder message = new StringBuilder();
        int readed = 0;
        while (true) {
            buffer.clear();
            buffer.flip();
            readed = channel.read(buffer);
            buffer.flip();
            byte [] data = new byte[buffer.limit()];
            for (int i = 0; i < buffer.limit(); i++) {
                data[i] = buffer.get();
            }
            message.append(new String(data));
            buffer.flip();
            buffer.rewind();
            if (readed == -1) {
                break;
            }
        }
        broadCastMessage(message.toString());
    }

    private void broadCastMessage(String message) throws IOException {
        for (SelectionKey key : selector.keys()) {
            if (key.isValid() && key.channel() instanceof SocketChannel) {
                ((SocketChannel) key.channel()).
                        write(ByteBuffer.wrap(message.getBytes()));
            }
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel)key.channel()).accept();
        channel.configureBlocking(false);
        String userName = "Client#" + cnt;
        cnt++;
        channel.register(selector, SelectionKey.OP_READ, userName);
    }

    public static void main(String[] args) {
        new Thread(new NIOServer()).start();
    }
}
