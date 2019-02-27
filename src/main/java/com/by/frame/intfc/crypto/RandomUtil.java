package com.by.frame.intfc.crypto;

import java.util.Random;

/**
 * 随机工具类
 * @author hejian
 *
 */
public class RandomUtil {

	
	/**
     * @param charCount
     *            字符串数量
     * @return 键盘上字符产生数量为charCount的随机字符串
     */
    public static String randChar(int charCount) {
           String charValue = "";
           // 生成随机字母串
           for (int i = 0; i < charCount; i++) {
                  // 键盘上字符产生随机数
                  char c = (char) (randomInt(33, 128));
                  charValue += String.valueOf(c);
           }
           return charValue;
    }

    /**
     * 返回[from,to)之间的一个随机整数
     * 
     * @param from
     *            起始值
     * @param to
     *            结束值
     * @return [from,to)之间的一个随机整数
     */
    public static int randomInt(int from, int to) {
           // Random r = new Random();
           return from + new Random().nextInt(to - from);
    }
	
}
