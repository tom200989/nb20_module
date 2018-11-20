package com.nb20.statistics.bean;

import java.util.Date;

/*
 * Created by qianli.ma on 2018/11/8 0008.
 */
public class StatisticsBean {

    private String colValue;// 列名
    private int progressBlue;// 蓝色进度值
    private int progressOrange;// 橙色进度值
    private boolean isClick;// 是否被点击
    private Date sportDate;// 运动日期
    private int playTime;// play时间
    private int walkTime;// walk时间

    public Date getSportDate() {
        return sportDate;
    }

    public void setSportDate(Date sportDate) {
        this.sportDate = sportDate;
    }

    public int getPlayTime() {
        return playTime;
    }

    /**
     * Play时间(分钟)
     * @param playTime 分钟
     */
    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getWalkTime() {
        return walkTime;
    }

    /**
     * walk时间(分钟)
     * @param walkTime 分钟
     */
    public void setWalkTime(int walkTime) {
        this.walkTime = walkTime;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
    }

    public int getProgressBlue() {
        return progressBlue;
    }

    public void setProgressBlue(int progressBlue) {
        this.progressBlue = progressBlue;
    }

    public int getProgressOrange() {
        return progressOrange;
    }

    public void setProgressOrange(int progressOrange) {
        this.progressOrange = progressOrange;
    }
}
