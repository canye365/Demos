package cn.canye365.websocket.api;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author CanYe
 */
@ServerEndpoint("/ws/{uid}")
public class MyWebSocket {

    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) throws IOException {
        System.out.println(uid + "已建立websocket连接");
        session.getBasicRemote().sendText("服务端：" + uid + "已连接websocket服务端...");
    }

    @OnClose
    public void onClose() {
        System.out.println(this + "关闭连接...");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("接收到消息：" + message);
        session.getBasicRemote().sendText("服务端已收到消息: " + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
}
