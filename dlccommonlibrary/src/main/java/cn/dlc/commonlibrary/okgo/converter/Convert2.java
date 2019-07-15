package cn.dlc.commonlibrary.okgo.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cn.dlc.commonlibrary.okgo.exception.ApiException;
import java.io.StringReader;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 转换工具类
 */

public class Convert2 {

    public static final Gson sGson = new GsonBuilder().serializeNulls().create();

    /**
     * 响应转String
     *
     * @param response
     * @return
     * @throws Throwable
     */
    public static String toString(Response response) throws Throwable {
        ResponseBody body = response.body();
        String string = null;
        if (body != null) {
            string = body.string();
        }
        return string == null ? "" : string;
    }

    /**
     * Json转JsonElement
     *
     * @param json
     * @return
     * @throws Throwable
     */
    public static JsonElement toJsonElement(String json) throws Throwable {
        JsonReader jsonReader = new JsonReader(new StringReader(json));
        JsonElement jsonElement = new JsonParser().parse(jsonReader);
        return jsonElement;
    }

    /**
     * json转bean
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws Throwable
     */
    public static <T> T toRawBean(String json, Class<T> clazz) throws Throwable {
        JsonElement jsonElement = toJsonElement(json);
        return sGson.fromJson(jsonElement, clazz);
    }

    /**
     * json转处理完code=0，code=1情况的bean
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws Throwable
     */
    public static <T> T toBean01(String json, Class<T> clazz) throws Throwable {

        JsonElement jsonElement = toJsonElement(json);

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int code = jsonObject.get("code").getAsInt();
        String msg = jsonObject.get("msg").getAsString();

        if (code == 1) {
            T t = sGson.fromJson(jsonElement, clazz);
            return t;
        } else {
            throw new ApiException(msg, code);
        }
    }
}
