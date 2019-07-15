package cn.dlc.androidlibaray;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.androidlibaray.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_widget)
    Button mBtnWidget;
    @BindView(R.id.btn_player)
    Button mBtnPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({ R.id.btn_widget, R.id.btn_player })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_widget:
                startActivity(TestWidgetActivity.class);
                break;
            case R.id.btn_player:
                //startActivity(PlayerActivity.class);
                break;
        }
    }

    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
        // 设置深色的状态栏
        setDarkStatus();
    }
}
