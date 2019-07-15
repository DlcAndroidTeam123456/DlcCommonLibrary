package cn.dlc.androidlibaray.base;

import android.os.Bundle;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

/**
 * 备用的
 * Created by John on 2018/3/1.
 */

public abstract class BaseActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在这里进行初始化
    }

    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
        // 沉浸状态栏
        setTranslucentStatus();
    }
}
