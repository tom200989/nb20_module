package com.nb20_module.nb20_statistics.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.nb20.statistics.bean.StatisticsBean;
import com.nb20.statistics.engine.day.StatisticsDayWidget;
import com.nb20.statistics.engine.month.StatisticsMonthWidget;
import com.nb20.statistics.engine.week.StatisticsWeekWidget;
import com.nb20.statistics.utils.StatisticsUtils;
import com.nb20_module.nb20_statistics.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsActivity extends Activity {

    @BindView(R.id.wd_day_statistics)
    StatisticsDayWidget wdDayStatistics;// 日视图
    @BindView(R.id.wd_week_statistics)
    StatisticsWeekWidget wdWeekStatistics;// 周视图
    @BindView(R.id.wd_Month_statistics)
    StatisticsMonthWidget wdMonthStatistics;// 月视图

    private List<View> wds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        wdDayStatistics.setStatistics(getDayStatisitcs());
        wdWeekStatistics.setStatistics(getWeekStatisitcs());
        wdMonthStatistics.setStatistics(getMonthStatisitcs());
        wdMonthStatistics.setMonthDes(7);

        wds.add(wdDayStatistics);
        wds.add(wdWeekStatistics);
        wds.add(wdMonthStatistics);

        wdDayStatistics.setGoal(30);
        wdWeekStatistics.setGoal(45);
        wdMonthStatistics.setGoal(15 * 60);

        transfer(0);
    }
    
    

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * @return 获取日数据
     */
    private List<StatisticsBean> getDayStatisitcs() {
        List<StatisticsBean> dayBeans = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            StatisticsBean bean = new StatisticsBean();
            bean.setColValue(String.valueOf(i * 3));
            bean.setClick(false);
            bean.setSportDate(new Date());
            bean.setPlayTime(30);
            bean.setWalkTime(10);
            dayBeans.add(bean);
        }
        return dayBeans;
    }

    /**
     * @return 获取周数据
     */
    private List<StatisticsBean> getWeekStatisitcs() {
        List<String> weeks = StatisticsUtils.getWeekEnglish(this);
        List<StatisticsBean> weekBeans = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            StatisticsBean bean = new StatisticsBean();
            bean.setColValue(String.valueOf(weeks.get(i)));
            bean.setClick(false);
            bean.setSportDate(new Date());
            bean.setPlayTime(25);
            bean.setWalkTime(5);
            weekBeans.add(bean);
        }
        return weekBeans;
    }

    /**
     * @return 获取月数据
     */
    private List<StatisticsBean> getMonthStatisitcs() {
        List<StatisticsBean> monthBeans = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            StatisticsBean bean = new StatisticsBean();
            bean.setColValue(String.valueOf(i));
            bean.setClick(false);
            bean.setSportDate(new Date());
            bean.setPlayTime(480);
            bean.setWalkTime(84);
            monthBeans.add(bean);
        }
        return monthBeans;
    }

    public void setDay(View view) {
        transfer(0);
    }

    public void setWeek(View view) {
        transfer(1);
    }


    public void setMonth(View view) {
        transfer(2);
    }

    /**
     * 切换视图
     *
     * @param index 索引
     */
    public void transfer(int index) {
        for (int i = 0; i < wds.size(); i++) {
            wds.get(i).setVisibility(index == i ? View.VISIBLE : View.INVISIBLE);
        }
    }


}
