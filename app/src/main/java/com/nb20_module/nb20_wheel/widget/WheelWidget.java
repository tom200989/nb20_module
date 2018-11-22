package com.nb20_module.nb20_wheel.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.nb20_statistics.nb20_statistics.R;

/*
 * Created by qianli.ma on 2018/11/22 0022.
 */
public class WheelWidget extends RelativeLayout {

    private Context context;
    private View inflate;
    private RecyclerView rcvOne;
    private RecyclerView rcvTwo;
    private RecyclerView rcvThree;
    private RecyclerView rcvFour;

    public WheelWidget(Context context) {
        this(context, null, 0);
    }

    public WheelWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflate = View.inflate(context, R.layout.widget_wheel, this);
        rcvOne = findViewById(R.id.rcv_one);
        rcvTwo = findViewById(R.id.rcv_two);
        rcvThree = findViewById(R.id.rcv_three);
        rcvFour = findViewById(R.id.rcv_four);
    }
}
