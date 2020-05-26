package cn.canye365.socket.demo02;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author CanYe
 */
public class FileUploadServer {
    public static void main(String[] args) throws IOException {
        // 1. 创建服务端套接字
        ServerSocket serverSocket = new ServerSocket(8888);
        // 2. 获取发送请求的客户端的Socket
        Socket socket = serverSocket.accept();
        // 3. 获取网络字节输入流
        InputStream in = socket.getInputStream();
        // 4. 准备本地字节输出流以保存文件,参数标明保存位置
        String fileName = "E:\\code\\3.jpg";
        FileOutputStream fos = new FileOutputStream(fileName);
        // 5. 写入文件
        byte[] bytes = new byte[1024];
        int len = -1;
        while((len = in.read(bytes)) != -1){
            fos.write(bytes,0, len);
        }
        // 6. 保存成功
        OutputStream os = socket.getOutputStream();
        os.write("保存成功".getBytes());

        // 7. 关闭
        os.close();
        fos.close();
        in.close();
        socket.close();
        serverSocket.close();
    }
}
