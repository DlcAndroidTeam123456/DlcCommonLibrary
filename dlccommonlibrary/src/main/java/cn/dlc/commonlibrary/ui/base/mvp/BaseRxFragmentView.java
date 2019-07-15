package cn.dlc.commonlibrary.ui.base.mvp;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * Created by John on 2017/8/28.
 */

public interface BaseRxFragmentView extends UiView, LifecycleProvider<FragmentEvent> {
    
}
