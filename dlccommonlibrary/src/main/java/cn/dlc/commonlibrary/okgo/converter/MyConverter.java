package cn.dlc.commonlibrary.okgo.converter;

/**
 * 网络访问响应结果转换器，一般是把json转程bean
 */

public abstract class MyConverter<T> {

    protected Class<T> mClazz;

    /**
     * 网络访问响应结果转换器，一般是把json转程bean
     *
     * @param clazz
     */
    public MyConverter(Class<T> clazz) {
        mClazz = clazz;
    }

    public void setClass(Class<T> clazz) {
        mClazz = clazz;
    }

    public Class<T> getToConvertClass() {
        return mClazz;
    }

    public abstract T convert(String stringResponse) throws Throwable;
}
