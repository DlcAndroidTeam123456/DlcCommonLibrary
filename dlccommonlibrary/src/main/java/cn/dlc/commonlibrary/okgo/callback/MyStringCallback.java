package cn.dlc.commonlibrary.okgo.callback;

/**
 * 直接返回string的回调
 */

public abstract class MyStringCallback extends MyCallback<String> {

    /**
     * 直接返回string的回调
     */
    public MyStringCallback() {
        setClass(String.class);
    }

    @Override
    protected String convert(String response) throws Throwable {
        return response;
    }
}
