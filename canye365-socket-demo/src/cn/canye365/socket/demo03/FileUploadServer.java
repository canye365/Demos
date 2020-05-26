package cn.canye365.socket.demo03;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 优化： 1. 多线程     2. 随机文件名
 * @author CanYe
 */
public class FileUploadServer {
    public static void main(String[] args) throws IOException {
        // 1. 创建服务端套接字
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端启动。");
        // **优化2：死循环监听**
        while(true){
            Socket socket = serverSocket.accept();
            // **优化3：多线程**
            new Thread(() -> {
                try {
                    // 2. 获取发送请求的客户端的Socket
                    // 3. 获取网络字节输入流
                    InputStream in = socket.getInputStream();
                    // 4. 准备本地字节输出流以保存文件,参数标明保存位置
                    // **优化1：随机文件名 = 域名 + 毫秒 + 随机数**
                    File file = new File("E:\\code\\code3\\");
                    if(!file.exists())  file.mkdirs();
                    String fileName = "canye365" + System.currentTimeMillis() + new Random().nextInt(999999);
                    String ext = ".zip";
                    FileOutputStream fos = new FileOutputStream(file + fileName + ext);
                    // 5. 写入文件
                    byte[] bytes = new byte[1024];
                    int len = -1;
                    while((len = in.read(bytes)) != -1){
                        fos.write(bytes,0, len);
                    }
                    // 6. 保存成功
                    OutputStream os = socket.getOutputStream();
                    os.write("保存成功".getBytes());

                    os.close();
                    fos.close();
                    in.close();
                    socket.close();
                } catch(IOException e){
                    System.out.println(e);
                }
            }).start();

        }

        // 7. 关闭
        //serverSocket.close();
    }
}
