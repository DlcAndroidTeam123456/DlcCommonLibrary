package cn.dlc.commonlibrary.okgo.callback;

import cn.dlc.commonlibrary.okgo.converter.RawBeanConvert;

/**
 * 普通的json bean回调，我们后台发挥的json千奇百怪，基本没啥用
 */

public abstract class RawBeanCallback<T> extends MyCallback<T> {

    public RawBeanCallback(Class<T> clazz) {
        setClass(clazz);
    }

    /**
     * 普通的json bean回调，我们后台发挥的json千奇百怪，基本没啥用
     */
    public RawBeanCallback() {
    }

    @Override
    protected T convert(String response) throws Throwable {
        return new RawBeanConvert<>(mClazz).convert(response);
    }
}
