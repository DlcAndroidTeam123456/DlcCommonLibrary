package cn.dlc.commonlibrary.okgo.converter;

/**
 * 把json直接转成javabean
 */
public class RawBeanConvert<T> extends MyConverter<T> {

    /**
     * 原样输出json文本的转换器
     *
     * @param clazz
     */
    public RawBeanConvert(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T convert(String json) throws Throwable {
        return Convert2.toRawBean(json, mClazz);
    }
}
