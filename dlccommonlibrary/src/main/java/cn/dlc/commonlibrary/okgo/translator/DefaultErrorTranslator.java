package cn.dlc.commonlibrary.okgo.translator;

import com.google.gson.JsonParseException;
import cn.dlc.commonlibrary.okgo.exception.ApiException;
import com.lzy.okgo.exception.HttpException;

/**
 * 默认提供的异常信息格式化器
 */

public class DefaultErrorTranslator implements ErrorTranslator {

    @Override
    public String translate(Throwable throwable) {

        if (throwable instanceof HttpException) {
            return "服务器异常";
        } else if (throwable instanceof JsonParseException) {
            // json解析错误，一般就是接口改了
            return "网络接口异常";
        } else if (throwable instanceof ApiException) {
            return throwable.getMessage();
        } else {
            return throwable.getMessage();
        }
    }
}
