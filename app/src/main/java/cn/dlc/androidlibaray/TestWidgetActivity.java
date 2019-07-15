package cn.dlc.androidlibaray;

import android.os.Bundle;
import butterknife.BindView;
import cn.dlc.androidlibaray.base.BaseActivity;
import cn.dlc.commonlibrary.ui.widget.TitleBar;

public class TestWidgetActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_widget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitleBar.leftExit(this);
    }
}
