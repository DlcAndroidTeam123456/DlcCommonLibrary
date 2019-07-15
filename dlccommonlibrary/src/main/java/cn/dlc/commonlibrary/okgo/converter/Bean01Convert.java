package cn.dlc.commonlibrary.okgo.converter;

/**
 * 处理了我们接口那种code=0，code=1情况的转换器
 */
public class Bean01Convert<T> extends MyConverter<T> {

    /**
     * 处理了我们接口那种code=0，code=1情况的转换器
     *
     * @param clazz
     */
    public Bean01Convert(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T convert(String json) throws Throwable {
        return Convert2.toBean01(json, mClazz);
    }
}
