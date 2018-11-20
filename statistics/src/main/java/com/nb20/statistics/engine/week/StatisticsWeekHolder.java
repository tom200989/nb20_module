package com.nb20.statistics.engine.week;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nb20.statistics.R;


/*
 * Created by qianli.ma on 2018/11/8 0008.
 */
public class StatisticsWeekHolder extends RecyclerView.ViewHolder {

    public RelativeLayout rlStatistics;
    public TextView tvNum;

    public RelativeLayout rlSmall;
    public RelativeLayout rlGreen;
    public RelativeLayout rlBlue;
    public RelativeLayout rlOrange;
    public ImageView ivPgGray;
    public ImageView ivPgBlue;
    public ImageView ivPgOrange;

    public RelativeLayout rlBig;
    public RelativeLayout rlGreebBig;
    public RelativeLayout rlBlueBig;
    public RelativeLayout rlOrangeBig;
    public ImageView ivPgGreenBig;
    public ImageView ivPgBlueBig;
    public ImageView ivPgOrangeBig;

    public StatisticsWeekHolder(View itemView) {
        super(itemView);
        rlStatistics = itemView.findViewById(R.id.week_rl_Statistics);
        tvNum = itemView.findViewById(R.id.week_tv_statistics);

        rlSmall = itemView.findViewById(R.id.week_rl_small);
        rlGreen = itemView.findViewById(R.id.week_rl_green);
        rlBlue = itemView.findViewById(R.id.week_rl_blue);
        rlOrange = itemView.findViewById(R.id.week_rl_orange);
        ivPgGray = itemView.findViewById(R.id.week_iv_progress_gray);
        ivPgBlue = itemView.findViewById(R.id.week_iv_progress_blue);
        ivPgOrange = itemView.findViewById(R.id.week_iv_progress_orange);

        rlBig = itemView.findViewById(R.id.week_rl_big);
        rlGreebBig = itemView.findViewById(R.id.week_rl_green_big);
        rlBlueBig = itemView.findViewById(R.id.week_rl_blue_big);
        rlOrangeBig = itemView.findViewById(R.id.week_rl_orange_big);
        ivPgGreenBig = itemView.findViewById(R.id.week_iv_progress_green_big);
        ivPgBlueBig = itemView.findViewById(R.id.week_iv_progress_blue_big);
        ivPgOrangeBig = itemView.findViewById(R.id.week_iv_progress_orange_big);
    }
}
