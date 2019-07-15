package cn.dlc.commonlibrary.okgo.exception;

import cn.dlc.commonlibrary.okgo.translator.ErrorTranslator;

/**
 * 网络接口异常，一般在调用后台接口成功且有返回，但返回的code不为1时抛出
 */

public class ApiException extends Exception {

    private int code;

    /**
     * 网络接口异常，一般在调用后台接口成功且有返回，但返回的code不为1时抛出
     *
     * @param message 错误信息
     * @param code 错误代码，一般不为1
     * @see ErrorTranslator
     */
    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     * 获取错误代码
     *
     * @return
     */
    public int getCode() {
        return code;
    }
}
