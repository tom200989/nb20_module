package com.nb20.statistics.helper;

import android.annotation.SuppressLint;
import android.content.Context;

import com.nb20.statistics.R;
import com.nb20.statistics.utils.StatisticsUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * Created by qianli.ma on 2018/11/16 0016.
 */
public class DateHourMinHelper {

    /**
     * 转换年月日(banner)
     *
     * @param context 上下文
     * @param date    日期
     * @return ex:9 May 2018
     */
    @SuppressLint("SimpleDateFormat")
    public static String turnDateTimeBanner(Context context, Date date) {
        // 获取月份集合
        List<String> months = StatisticsUtils.getMonthEnglish(context);
        // Date --> d-M-yyyy
        SimpleDateFormat sm = new SimpleDateFormat("d-M-yyyy");
        String format = sm.format(date);
        String[] split = format.split("-");
        int day = Integer.valueOf(split[0]);
        int month = Integer.valueOf(split[1]);
        int year = Integer.valueOf(split[2]);
        // 根据参数获取月份字符
        String monthText = months.get(month - 1);
        return (day + " ") + (monthText + " ") + year;
    }

    /**
     * 转换play时间(banner)
     *
     * @param mins 总分钟
     */
    public static String turnPlayTimeBanner(Context context, int mins) {
        // 1.准备材料
        String playText = context.getString(R.string.banner_play);
        String hText = context.getString(R.string.h);
        String minsText = context.getString(R.string.mins);
        // 2.转换分钟
        StatisticsUtils.Times times = StatisticsUtils.makeTime(mins);
        int hour = times.hour;
        int min = times.min;
        // 3.条件判断
        if (hour > 0) {
            return (playText + ": ") + (hour + " " + hText + " ") + (min + " " + minsText);
        } else {
            return (playText + ": ") + (min + " " + minsText);
        }
    }

    /**
     * 转换Walk时间(banner)
     *
     * @param mins 总分钟
     */
    public static String turnWalkTimeBanner(Context context, int mins) {
        // 1.准备材料
        String walkText = context.getString(R.string.banner_walk);
        String hText = context.getString(R.string.h);
        String minsText = context.getString(R.string.mins);
        // 2.转换分钟
        StatisticsUtils.Times times = StatisticsUtils.makeTime(mins);
        int hour = times.hour;
        int min = times.min;
        // 3.条件判断
        if (hour > 0) {
            return (walkText + ": ") + (hour + " " + hText + " ") + (min + " " + minsText);
        } else {
            return (walkText + ": ") + (min + " " + minsText);
        }
    }

    /**
     * 转换X时间( play | walk | rest )
     *
     * @param mins 总分钟
     */
    public static String turnXTimeInfo(Context context, int mins) {
        // 1.准备材料
        String hText = context.getString(R.string.h);
        String minsText = context.getString(R.string.mins);
        String mText = context.getString(R.string.m);
        // 2.转换分钟
        StatisticsUtils.Times times = StatisticsUtils.makeTime(mins);
        int hour = times.hour;
        int min = times.min;
        // 3.条件判断
        if (hour > 0) {
            if (min > 0) {
                return (hour + hText + min + mText);
            } else {
                return (hour + hText);
            }

        } else {
            return (min + minsText);
        }
    }

    /**
     * 默认时间 (0.0h)
     *
     * @param context 上下文
     * @return 默认时间 (0.0h)
     */
    public static String defaultTimeInfo(Context context) {
        return "0.0" + context.getString(R.string.h);
    }

}
