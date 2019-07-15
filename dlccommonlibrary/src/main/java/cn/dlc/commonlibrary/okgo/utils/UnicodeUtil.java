package cn.dlc.commonlibrary.okgo.utils;

/**
 * unicode处理器，用来将json中的unicode字符串（\\u那些）解码
 */
public class UnicodeUtil {

    /**
     * 解密json中的unicode字符串（\\u那些）
     *
     * @param unicodeStr unicode字符串
     * @return 解码后的字符串
     */
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }

        //LogPlus.e(unicodeStr);

        StringBuilder retBuf = new StringBuilder();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(
                    i + 1) == 'U'))) {
                    try {
                        retBuf.append(
                            (char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                } else if (i < maxLoop - 1 && unicodeStr.charAt(i + 1) == '/') {
                    retBuf.append(unicodeStr.charAt(++i));
                } else {
                    retBuf.append(unicodeStr.charAt(i));
                }
            }/* else if (unicodeStr.charAt(i) == '/' && i > 0 && unicodeStr.charAt(i - 1) == '\\') {
                // 用来排除掉http斜杆的转义
                // 什么都不干
            }*/ else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
}
