package com.nb20.statistics.engine.day;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nb20.statistics.R;
import com.nb20.statistics.bean.StatisticsBean;
import com.nb20.statistics.helper.DateHourMinHelper;
import com.nb20.statistics.utils.ScreenSize;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

/*
 * Created by qianli.ma on 2018/11/9 0009.
 */
public class StatisticsDayWidget extends PercentRelativeLayout {

    private Context context;
    private View inflate;
    private RecyclerView rcvStatistics;
    private RelativeLayout rlPlayWalkPanel;
    private TextView tvSportDate;
    private TextView tvPlayWalkTime;
    private RelativeLayout rlGoalline;
    private TextView tvGoalline;
    private TextView tvInfoPlayTime;
    private TextView tvInfoWalkTime;
    private TextView tvInfoRestTime;
    private final int UNIT = 60;

    public StatisticsDayWidget(Context context) {
        this(context, null, 0);
    }

    public StatisticsDayWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsDayWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        inflate = View.inflate(context, R.layout.widget_statistics, this);
        rcvStatistics = findViewById(R.id.rcv_widget_statistics);
        findViewById(R.id.ll_widget_min_coodinary).setVisibility(VISIBLE);// 垂直分坐标
        findViewById(R.id.ll_widget_hour_coodinary).setVisibility(INVISIBLE);// 垂直时坐标
        findViewById(R.id.tv_widget_month).setVisibility(INVISIBLE);// 左下角月份描述(DAY WEEK无需显示)
        // banner
        TextView tvExceriseMinHour = findViewById(R.id.tv_widget_excerise_min_hour);// Excerise / mins
        tvExceriseMinHour.setText(context.getString(R.string.banner_ExceriseMin));
        // timer
        rlPlayWalkPanel = findViewById(R.id.rl_play_walk_time);
        tvSportDate = findViewById(R.id.tv_sport_date);
        tvPlayWalkTime = findViewById(R.id.tv_play_walk_time);
        // goal line
        rlGoalline = findViewById(R.id.rl_goal_line);
        tvGoalline = findViewById(R.id.tv_goal_line);
        setGoal(0);
        // info
        tvInfoPlayTime = findViewById(R.id.tv_info_play_time);
        tvInfoWalkTime = findViewById(R.id.tv_info_walk_time);
        tvInfoRestTime = findViewById(R.id.tv_info_rest_time);
    }

    /**
     * 设置数据
     *
     * @param beans 数据
     */
    public void setStatistics(final List<StatisticsBean> beans) {
        rcvStatistics.post(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager lm = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {
                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }
                };
                rcvStatistics.setLayoutManager(lm);
                StatisticsDayAdapter adapter = new StatisticsDayAdapter(context, beans, rcvStatistics);
                adapter.setOnClickItemListener(new StatisticsDayAdapter.OnClickItemListener() {
                    @Override
                    public void clickItem(StatisticsBean statisticsBean) {
                        // 运动信息显隐
                        rlPlayWalkPanel.setVisibility(statisticsBean.isClick() ? VISIBLE : INVISIBLE);
                        // 运动年月
                        tvSportDate.setText(DateHourMinHelper.turnDateTimeBanner(context, statisticsBean.getSportDate()));
                        // 运动子信息
                        String playText = DateHourMinHelper.turnPlayTimeBanner(context, statisticsBean.getPlayTime());
                        String walkText = DateHourMinHelper.turnWalkTimeBanner(context, statisticsBean.getWalkTime());
                        tvPlayWalkTime.setText(String.valueOf(playText + " " + walkText));
                        // info面板显示
                        int playTime = statisticsBean.getPlayTime();
                        int walkTime = statisticsBean.getWalkTime();
                        int restTime = UNIT - playTime - walkTime;
                        String playtext = DateHourMinHelper.turnXTimeInfo(context, playTime);
                        String walktext = DateHourMinHelper.turnXTimeInfo(context, walkTime);
                        String resttext = DateHourMinHelper.turnXTimeInfo(context, restTime);
                        String defaultTime = DateHourMinHelper.defaultTimeInfo(context);
                        tvInfoPlayTime.setText(statisticsBean.isClick() ? playtext : defaultTime);
                        tvInfoWalkTime.setText(statisticsBean.isClick() ? walktext : defaultTime);
                        tvInfoRestTime.setText(statisticsBean.isClick() ? resttext : defaultTime);
                    }
                });
                rcvStatistics.setAdapter(adapter);
            }
        });
    }

    /**
     * 计算目标线Y位移
     *
     * @param mins 0 ~ 60 mins
     */
    public void setGoal(@IntRange(from = 0, to = UNIT) final int mins) {

        rcvStatistics.post(new Runnable() {
            @Override
            public void run() {
                // 获取目标顶部最大Y偏移(Y1)
                float lineTopY = getGoalCurrentMarginTop();
                // 获取目标底部最小Y偏移(Y0)
                float lineBottomY = getGoalCurrentMarginBottom();
                // 计算可变化范围(Y1 - Y0)
                float delY = Math.abs(lineTopY - lineBottomY);
                // 得到Y单位
                float averageY = delY / UNIT;
                // 总增量 = 单位 * 数量 
                float totalY = averageY * mins;
                // 总偏移量 = 初始Y偏移 + 增量
                float offsetY = totalY + lineBottomY;
                // 赋值绘制
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rlGoalline.getLayoutParams();
                lp.setMargins(0, 0, 0, (int) offsetY);
            }
        });

        // * 设置goal line文字显示
        String goalLineText = String.format(context.getString(R.string.goal_mins), mins);
        tvGoalline.setText(goalLineText);
    }


    /**
     * 计算目标线的最大顶部位移
     *
     * @return 目标线的最大顶部位移
     */
    private float getGoalCurrentMarginTop() {

        float recWidthPercent = Float.valueOf(context.getString(R.string.day_rec_widthPercent).split("%")[0]) / 100;
        float rlWidthPercent = Float.valueOf(context.getString(R.string.day_rlBlue_widthPercent).split("%")[0]) * 1f / 100;

        // 获取设备宽高
        int ww = ScreenSize.getSize(context).width;
        int wh = ScreenSize.getSize(context).height;
        // RCV高度
        float rcvHeight = rcvStatistics.getHeight();
        // 目标线所在父控件的高度 / 2
        float goallineHalfHeight = rlGoalline.getHeight() * 0.5f;
        // 计算得出条目顶部坐标
        float itemTop = rcvHeight - goallineHalfHeight;
        // 偏差0.5个条目宽度作为误差余量
        float recWidth = (ww * recWidthPercent);
        float rlWidth = (recWidth * rlWidthPercent);
        return itemTop - 0.5f * rlWidth;
    }

    /**
     * 计算目标线的起始底部位移
     *
     * @return 目标线的起始底部位移
     */
    private float getGoalCurrentMarginBottom() {

        float tvSizePercent = Float.valueOf(context.getString(R.string.day_tv_sizePercent).split("%")[0]) / 100;
        float recWidthPercent = Float.valueOf(context.getString(R.string.day_rec_widthPercent).split("%")[0]) / 100;
        float rlWidthPercent = Float.valueOf(context.getString(R.string.day_rlBlue_widthPercent).split("%")[0]) * 1f / 100;
        float rlBlueMarginBottomPercent = Float.valueOf(context.getString(R.string.day_rlBlue_marginBottomPercent).split("%")[0]) * 1f / 100;
        float goallineHeightPercent = Float.valueOf(context.getString(R.string.goalline_heightPercent).split("%")[0]) * 1f / 100;

        // 获取设备宽高
        int ww = ScreenSize.getSize(context).width;
        int wh = ScreenSize.getSize(context).height;
        // 列名文本高度
        float tvHeight = ww * tvSizePercent;
        // 进度条距离列名文本的底部距离
        float marginbottom = (ww * recWidthPercent) * rlBlueMarginBottomPercent;
        // 目标线所在父控件的高度 / 2
        float lineHeight = wh * goallineHeightPercent * 0.5f;
        // 增加余量(由于精度的转换, 多少会有些误差, 因此以条目宽度作为余量增量)
        float recWidth = (ww * recWidthPercent);
        float rlWidth = (recWidth * rlWidthPercent);
        // 计算出: 目标线还需要向Y轴移动的距离(0.5个条宽作为误差余量--> 数值越小, 起始线越低)
        return tvHeight + marginbottom - lineHeight + 0.5f * rlWidth;
    }
}
