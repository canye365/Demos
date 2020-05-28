package cn.canye365.websocket.spring;

import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义WebSockect拦截器
 * @author CanYe
 */
@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {

    /**
     * 握手之前，若返回false，则不建立连接
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> attributes) throws Exception {
        //将用户id放入socket处理器的会话(WebSocketSession)中
        String path = request.getURI().getPath();
        Pattern pattern = Pattern.compile("[0-9]+"); // /ws/[0-9]+
        Matcher m = pattern.matcher(path);
        if(!m.find())  {
            return false;
        }
        Integer uid = Integer.parseInt(m.group());
        // uid = 1002;
        attributes.put("uid", uid);
        System.out.println("开始握手。。。。。。。" + "uid = " + uid);
        return true;
    }

    /**
     * 建立连接之后
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("握手成功啦。。。。。。");
    }
}
