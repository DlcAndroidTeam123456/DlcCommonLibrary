package cn.dlc.commonlibrary.okgo.logger;

import cn.dlc.commonlibrary.okgo.utils.UnicodeUtil;
import com.licheedev.myutils.LogPlus;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 格式化json数据的日志打印
 */
public class JsonRequestLogger implements RequestLogger {

    private final String mLineSeparator;
    private final boolean mLogRequest;
    private final int mLogJsonLines;

    public JsonRequestLogger(boolean logRequest, int logJsonLines) {
        mLogRequest = logRequest;
        mLogJsonLines = logJsonLines;
        mLineSeparator = System.getProperty("line.separator");
    }

    @Override
    public void logRequest(String url, HttpHeaders headers, HttpParams params, String response,
        Throwable tr) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n【HTTP】url==>")
            .append(url)
            .append("\n【HTTP】request==>")
            .append(String.valueOf(params));

        if (tr != null) {
            sb.append("\n【HTTP】throwable==>").append(tr);
        }

        if (response != null) {

            String formattedJson = null;
            if (mLogJsonLines > 0) {
                try {
                    String json = response.trim();
                    if (json.startsWith("{")) {
                        JSONObject jsonObject = new JSONObject(json);
                        formattedJson = jsonObject.toString(2);
                    } else if (json.startsWith("[")) {
                        JSONArray jsonArray = new JSONArray(json);
                        formattedJson = jsonArray.toString(2);
                        return;
                    }
                } catch (JSONException e) {
                    // 无视
                }
            }

            if (formattedJson != null) {

                String[] lines = formattedJson.split(mLineSeparator);

                if (lines.length <= mLogJsonLines) {
                    sb.append("\n【HTTP】response==>");
                    for (String line : lines) {
                        sb.append(mLineSeparator).append("【HTTP】").append(UnicodeUtil.decode(line));
                    }
                } else {
                    sb.append("\n【HTTP】response==>").append(UnicodeUtil.decode(response));
                }
            } else {
                sb.append("\n【HTTP】response==>").append(UnicodeUtil.decode(response));
            }
        }

        //LogPlus.i(TAG, sb.toString(), tr);
        LogPlus.i(TAG, sb.toString());
    }
}
