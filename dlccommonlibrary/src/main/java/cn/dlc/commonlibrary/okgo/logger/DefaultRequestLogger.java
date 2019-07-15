package cn.dlc.commonlibrary.okgo.logger;

import cn.dlc.commonlibrary.okgo.utils.UnicodeUtil;
import com.licheedev.myutils.LogPlus;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

/**
 * 默认提供的日志打印
 */
public class DefaultRequestLogger implements RequestLogger {

    private final boolean mLogRequest;

    public DefaultRequestLogger(boolean logRequest) {
        mLogRequest = logRequest;
    }

    @Override
    public void logRequest(String url, HttpHeaders headers, HttpParams params, String response,
        Throwable tr) {

        if (!mLogRequest) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n【HTTP】url==>")
            .append(url)
            .append("\n【HTTP】request==>")
            .append(String.valueOf(params));

        if (tr != null) {
            sb.append("\n【HTTP】throwable==>").append(tr);
        }

        if (response != null) {
            sb.append("\n【HTTP】response==>").append(UnicodeUtil.decode(response));
        }

        //LogPlus.i(TAG, sb.toString(), tr);
        LogPlus.i(TAG, sb.toString());
    }
}
