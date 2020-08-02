package cn.canye365.code.demo.utils;

import java.util.UUID;

/**
 * uuid是根据机器、时间等多个维度生成的32位的16进制数，有生之年不会重复。
 * 这里uuid的基础上，封装了8位短uuid。
 * 短ID是根据将32位ID，转为62进制8位ID，减少存储空间。
 * 原理是将uuid转为10进制，再对62取余。也可以再添加两个符号，转成64进制。
 */
public class UuidUtil {

    /**
     * 62个数字+字母
     */
    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    /**
     * 获取短UUID
     * @return
     */
    public static String getShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UuidUtil.getUuid();
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]); // 对62取余
        }
        return shortBuffer.toString();

    }

    /**
     * 获得32位UUID
     */
    public static String getUuid(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(getShortUuid());
    }
}
