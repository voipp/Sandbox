import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * NIOServer.
 *
 * @author Aleksey_Kuznetsov
 */
public class NIOServer implements Runnable {

    public static void main(String[] args) {
        NIOServer nioServer = new NIOServer();

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<?> future = service.submit(nioServer);
        service.shutdown();

        try {
            Thread.sleep(2_000);

            clientCommunicateWithServer();

            Thread.sleep(12_000);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        nioServer.active = false;

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void clientCommunicateWithServer() throws IOException, InterruptedException {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress(8081));
        System.out.println("Client channel opened");

        channel.write(ByteBuffer.wrap("Hello! It's a message from client".getBytes()));
        System.out.println("First message written to channel");

        Thread.sleep(12_000);

        channel.write(ByteBuffer.wrap("The last message from client".getBytes()));
        System.out.println("Second message is written to channel");

        Thread.sleep(3_000);

        channel.close();
        System.out.println("Channel is closing");
    }

    public void run() {
        try {
            System.out.println("Server is about to start working");

            work();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Boolean active = Boolean.TRUE;

    private void work() throws IOException {
        Selector selector = Selector.open();
        System.out.println("Server opens selector");

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress(8081));
        System.out.println("Server channel opened");

        serverSocketChannel.configureBlocking(false);
        System.out.println("Server channel is configured blocked");

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server selector is registered");


        while (active) {
            selector.select();
            System.out.println("Server selector selects");

            processDataFromChannels(selector);
        }
    }

    private void processDataFromChannels(Selector selector) throws IOException {
        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

        while (iter.hasNext()) {
            SelectionKey selectedKey = iter.next();

            //established connection with client
            if (selectedKey.isAcceptable()) {
                SocketConnection connection = new SocketConnection(((ServerSocketChannel) selectedKey.channel()).accept()
                        , selector, SelectionKey.OP_READ);
            } else if (selectedKey.isReadable()) {
                SocketConnection connection = (SocketConnection) selectedKey.attachment();

                connection.read();
            } else if (selectedKey.isWritable()) {

            }

            iter.remove();
        }
    }

    static class SocketConnection {
        private SocketChannel channel;
        private Selector selector;
        ByteBuffer cb = ByteBuffer.allocate(100);

        public SocketConnection(SocketChannel channel, Selector selector, int ops) throws IOException {
            this.channel = channel;
            this.selector = selector;

            this.channel.configureBlocking(false);

            this.channel.register(selector, ops).attach(this);
        }

        public void read() throws IOException {
            getStringFromInputStream(channel);
        }

        private void getStringFromInputStream(SocketChannel sc) {

            try {
                if (sc.read(cb) == -1) {
                    cb.flip();

                    System.out.println("STRING RECEIVED FROM CLIENT: " + new String(cb.array(), StandardCharsets.UTF_8));

                    cb.clear();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
