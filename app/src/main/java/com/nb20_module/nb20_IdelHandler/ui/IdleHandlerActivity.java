package com.nb20_module.nb20_IdelHandler.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.widget.Button;

import com.nb20_statistics.nb20_statistics.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdleHandlerActivity extends Activity {

    @BindView(R.id.bt_idlehandler)
    Button btIdlehandler;
    private IdleHandlerImpl handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle_handler);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // ** 此处在onResume()执行时马上获取btn的宽高, 是获取不到的, 因为视图还没有绘制完毕
        Log.e("idles", "onResume no used idlehandler");
        int btnWidth = btIdlehandler.getWidth();
        int btnHeight = btIdlehandler.getHeight();
        Log.e("idles", "btnWidth: " + btnWidth + ";btnHeight: " + btnHeight);

        // ** IdleHandler是在Looper空闲时执行, 因此此处调用后便可以得到控件大小
        // 而无需再采用addGloalLayoutListener的方式, 简化操作流程
        // 因此获取控件大小后的逻辑可以写在实现类里边(如下方的todo)
        if (handler == null) {
            handler = new IdleHandlerImpl();
        }
        Looper.myQueue().addIdleHandler(handler);
    }

    /**
     * IdleHandler 实现类
     */
    class IdleHandlerImpl implements MessageQueue.IdleHandler {
        /**
         * 返回值为true，则保持此Idle一直在Handler中，否则，执行一次后就从Handler线程中remove掉。
         */
        @Override
        public boolean queueIdle() {
            // todo 这里执行你的业务逻辑
            Log.i("idles", "IdleHandlerImpl running");
            int btnWidth = btIdlehandler.getWidth();
            int btnHeight = btIdlehandler.getHeight();
            Log.i("idles", "btnWidth: " + btnWidth + ";btnHeight: " + btnHeight);
            return false;// F:只执行1次 T:循环执行
        }
    }
}
