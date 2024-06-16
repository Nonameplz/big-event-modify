package nonameplz.bigEventServer.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class randomStringGetter {
    private static final String[] GENERATE_SOURCE = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z"};
    private static final int STR_LEN = GENERATE_SOURCE.length;

    public static String generateTokenByShuffle() {
        List<String> list = Arrays.asList(GENERATE_SOURCE);
        //打乱元素排序，增加反推难度
        Collections.shuffle(list);
        StringBuilder randomStr = new StringBuilder();
        for (int i = 0; i < STR_LEN; i++) {
            randomStr.append(list.get(i));
        }
        //更改下面两个数字可以取到不同位数的随机数哦
        return randomStr.substring(2, 34);
    }

    public static String generateUUIDRandom() {
        long time = System.currentTimeMillis();
        int random = (int) (Math.random() * Integer.MAX_VALUE);
        UUID uuid = new UUID(time, random);
        return uuid.toString();
    }

}
