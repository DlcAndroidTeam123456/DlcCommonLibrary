package cn.dlc.commonlibrary.okgo.translator;

/**
 * 异常信息格式化器
 */
public interface ErrorTranslator {

    /**
     * 格式化异常信息
     *
     * @param throwable 需要处理的异常
     * @return 处理后的异常信息
     */
    String translate(Throwable throwable);
}
