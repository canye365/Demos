package cn.canye365.socket.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author CanYe
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        // 1. 创建Socket, 输入端口号
        ServerSocket serverSocket = new ServerSocket(8888);
        // 2. 获取连接的socket
        Socket socket = serverSocket.accept();
        // 3. 获取输入流，读取内容
        InputStream is = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        System.out.println(new String(bytes, 0, len));
        // 4. 获取输出流，写入返回内容
        OutputStream os = socket.getOutputStream();
        os.write("accepted, thanks".getBytes());
        // 5. 关闭
        socket.close();
        serverSocket.close();
    }
}
