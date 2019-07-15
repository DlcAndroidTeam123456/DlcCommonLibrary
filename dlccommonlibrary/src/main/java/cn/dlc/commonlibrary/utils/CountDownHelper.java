package cn.dlc.commonlibrary.utils;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yz on 2018/3/10.
 */

public abstract class CountDownHelper {

    private final long mInitialPeriod;//初始值
    private final long mInitialInterval;//初始值
    private final TimeUnit mTimeUnit;
    private CountDownTimer mTimer;
    private long mUntilFinished;
    private long mInterval;

    /**
     * @param period 总时间
     * @param interval 间隔
     * @param unit 单位
     * @return
     */
    public CountDownHelper(long period, long interval, TimeUnit unit) {
        mUntilFinished = period;
        mInitialPeriod = period;

        mInterval = interval;
        mInitialInterval = interval;

        mTimeUnit = unit;
    }

    @NonNull
    private CountDownTimer newCountDownTimer(long millisInFuture, long countDownInterval) {
        return new CountDownTimer(mTimeUnit.toMillis(millisInFuture),
            mTimeUnit.toMillis(countDownInterval)) {
            @Override
            public void onTick(long millisUntilFinished) {
                mUntilFinished = millisUntilFinished;
                CountDownHelper.this.onTick(mUntilFinished);
            }

            @Override
            public void onFinish() {
                CountDownHelper.this.onFinish();
            }
        };
    }

    /**
     * 暂停
     */
    public void pause() {
        mTimer.cancel();
        mTimer = null;
    }

    /**
     * 开始
     */
    public void start() {
        if (mTimer == null) {
            mTimer = newCountDownTimer(mInitialPeriod, mInitialInterval).start();
        } else {
            throw new IllegalArgumentException("请问你是怎么来到这里的");
        }
    }

    /**
     * 继续
     */
    public void resume() {
        mTimer = newCountDownTimer(mUntilFinished, mInitialPeriod).start();
    }

    public abstract void onTick(long millisUntilFinished);

    public abstract void onFinish();
}
