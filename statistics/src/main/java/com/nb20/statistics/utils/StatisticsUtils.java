package com.nb20.statistics.utils;

import android.content.Context;

import com.nb20.statistics.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by qianli.ma on 2018/11/16 0016.
 */
public class StatisticsUtils {

    /**
     * Mins --> m Hour n Min
     *
     * @param mins 总分钟数
     * @return Times
     */
    public static Times makeTime(int mins) {
        int hour = mins / 60;
        int min = mins % 60;
        Times times = new Times();
        times.hour = hour;
        times.min = min;
        return times;
    }

    public static class Times {
        public int hour;
        public int min;
    }

    /**
     * 获取每个月的英文名
     *
     * @return ex:January,February..
     */
    public static List<String> getMonthEnglish(Context context) {
        List<Integer> monthRes = new ArrayList<>();
        List<String> months = new ArrayList<>();
        monthRes.add(R.string.January);
        monthRes.add(R.string.February);
        monthRes.add(R.string.March);
        monthRes.add(R.string.April);
        monthRes.add(R.string.May);
        monthRes.add(R.string.June);
        monthRes.add(R.string.July);
        monthRes.add(R.string.August);
        monthRes.add(R.string.September);
        monthRes.add(R.string.October);
        monthRes.add(R.string.November);
        monthRes.add(R.string.December);
        for (int monthRe : monthRes) {
            months.add(context.getString(monthRe));
        }
        return months;
    }

    /**
     * 获取一个星期每天的简写
     *
     * @param context 上下文
     * @return ex: Mon,Tue..
     */
    public static List<String> getWeekEnglish(Context context) {
        List<Integer> weekRes = new ArrayList<>();
        List<String> weeks = new ArrayList<>();
        weekRes.add(R.string.Sunday);
        weekRes.add(R.string.Monday);
        weekRes.add(R.string.Tuesday);
        weekRes.add(R.string.Wednesday);
        weekRes.add(R.string.Thursday);
        weekRes.add(R.string.Friday);
        weekRes.add(R.string.Saturday);
        for (int weekRe : weekRes) {
            weeks.add(context.getString(weekRe));
        }
        return weeks;
    }
}
