package cn.canye365.socket.demo03;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 优化。。。。。
 * @author CanYe
 */
public class FileUploadClient {
    public static void main(String[] args) throws IOException {
        // 1. 创建套接字
        Socket socket = new Socket("127.0.0.1", 8888);
        System.out.println("客户端启动。");
        // 2. 获取网络字节输出流
        OutputStream os = socket.getOutputStream();
        // 3. 准备本地字节输入流以写入网络字节输出流
        //String fileName = "E:\\code\\1.jpg";
        String fileName = "E:\\code\\visio.zip";
        FileInputStream fis = new FileInputStream(fileName);
        // 4. 本地字节输入流 -> 写入bytes -> 网络字节输出流
        byte[] bytes = new byte[1024];
        int len = -1;
        while( (len = fis.read(bytes)) != -1 ){
            os.write(bytes, 0, len);
        }
        // **解决阻塞问题： 整一个结束标记 ** 在服务端的read中会读取到-1然后结束
        socket.shutdownOutput();
        // 5. 接收 保存成功/失败的消息
        InputStream in = socket.getInputStream();
        while( (len = in.read(bytes)) != -1 ){
            System.out.println(new String(bytes, 0, len));
        }
        // 6. 关闭套接字
        in.close();
        fis.close();
        os.close();
        socket.close();
    }
}
