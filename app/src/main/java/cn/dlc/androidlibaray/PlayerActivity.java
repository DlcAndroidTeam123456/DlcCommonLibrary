//package cn.dlc.androidlibaray;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import butterknife.BindView;
//import butterknife.OnClick;
//import cn.dlc.androidlibaray.base.BaseActivity;
//import cn.dlc.commonlibrary.ui.widget.TitleBar;
//import cn.dlc.dlcplayermodule.DlcIjkPlayer;
//import cn.dlc.dlcplayermodule.DlcPlayer;
//import cn.dlc.dlcplayermodule.DlcPlayerExt;
//import cn.dlc.dlcplayermodule.ijk.IjkVideoView;
//import java.util.ArrayList;
//
//public class PlayerActivity extends BaseActivity {
//
//    @BindView(R.id.title_bar)
//    TitleBar mTitleBar;
//    @BindView(R.id.video_view)
//    IjkVideoView mVideoView;
//    @BindView(R.id.btn_play)
//    Button mBtnPlay;
//    @BindView(R.id.btn_stop)
//    Button mBtnStop;
//
//    private DlcPlayerExt mDlcPlayerExt;
//    private DlcPlayer mDlcPlayer;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_player;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mTitleBar.leftExit(this);
//
//        // 播放器
//        mDlcPlayer = new DlcIjkPlayer(mVideoView);
//        // 播放监听
//        mDlcPlayer.addPlayerListener(new DlcPlayer.PlayerListener() {
//            @Override
//            public void onPrepared(DlcPlayer player) {
//                // 准备好可以播放了
//                player.start();
//            }
//
//            @Override
//            public void onError(DlcPlayer player) {
//                // 出错播下一个
//                mDlcPlayerExt.playNext();
//            }
//
//            @Override
//            public void onCompletion(DlcPlayer player) {
//                // 出错播下一个
//                mDlcPlayerExt.playNext();
//            }
//
//            @Override
//            public void onSeekComplete(DlcPlayer player) {
//                // 快进监听，不用管
//            }
//        });
//        // 多视频轮播增强
//        mDlcPlayerExt = new DlcPlayerExt(mDlcPlayer);
//        // 获取视频数据
//        ArrayList<String> videoPaths = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            videoPaths.add(
//                "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear3/prog_index.m3u8");
//        }
//        // 设置视频源
//        mDlcPlayerExt.setPaths(videoPaths);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        mDlcPlayer.removeAllPlayListener();
//        mDlcPlayer.release();
//        super.onDestroy();
//    }
//
//    @OnClick({ R.id.btn_play, R.id.btn_stop })
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_play:
//                // 播放下一个
//                mDlcPlayerExt.playNext();
//                break;
//            case R.id.btn_stop:
//                mDlcPlayer.stop();
//                break;
//        }
//    }
//}
//
