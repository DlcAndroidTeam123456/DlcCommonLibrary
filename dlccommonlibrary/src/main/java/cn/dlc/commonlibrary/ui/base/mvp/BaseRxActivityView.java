package cn.dlc.commonlibrary.ui.base.mvp;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * Created by John on 2017/8/28.
 */

public interface BaseRxActivityView extends UiView, LifecycleProvider<ActivityEvent> {

}
