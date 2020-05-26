package cn.canye365.socket.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author CanYe
 */
public class TCPClient {
    public static void main(String[] args) throws IOException {
        // 1. 创建Socket，参数是要连接的服务端的地址+端口
        Socket socket = new Socket("127.0.0.1", 8888);
        // 2. 获取socket的输出流，向服务端写数据
        OutputStream os = socket.getOutputStream();
        // 3. 写入内容
        os.write("Hello! ".getBytes());
        // 4. 获取服务端输入流并读取数据
        InputStream is = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        System.out.println(new String(bytes, 0, len));
        // 5. 关闭
        socket.close();
    }
}
