package com.nb20.statistics.engine.week;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nb20.statistics.R;
import com.nb20.statistics.bean.StatisticsBean;
import com.nb20.statistics.utils.ScreenSize;

import java.util.List;


/*
 * Created by qianli.ma on 2018/11/8 0008.
 */
public class StatisticsWeekAdapter extends RecyclerView.Adapter<StatisticsWeekHolder> {

    private Context context;
    private List<StatisticsBean> beans;
    private RecyclerView rcv;
    private int progressHeight;// 进度条总高
    private final int UNIT = 60;

    public StatisticsWeekAdapter(Context context, @NonNull List<StatisticsBean> beans, RecyclerView rcv) {
        this.context = context;
        this.beans = beans;
        this.rcv = rcv;
        getSizeFromAttr();
    }

    /**
     * 刷新
     *
     * @param beans 数据
     */
    public void notifys(List<StatisticsBean> beans) {
        this.beans = beans;
        notifyDataSetChanged();
    }

    /**
     * 计算进度条可允许高度
     */
    private void getSizeFromAttr() {

        // 属性占比
        float recWidthPercent = (Float.valueOf(context.getString(R.string.week_rec_widthPercent).split("%")[0]) * 1f / 100);
        float rlHeightPercent = (Float.valueOf(context.getString(R.string.week_rlBlue_heightPercent).split("%")[0]) * 1f / 100);
        float rlWidthPercent = (Float.valueOf(context.getString(R.string.week_rlBlue_widthPercent).split("%")[0]) * 1f / 100);
        float rlMarginBottomPercent = (Float.valueOf(context.getString(R.string.week_rlBlue_marginBottomPercent).split("%")[0]) * 1f / 100);
        float ivHeightPercent = (Float.valueOf(context.getString(R.string.week_ivBlue_heightPercent).split("%")[0]) * 1f / 100);
        float swHeightPercent = (Float.valueOf(context.getString(R.string.all_sw_heightPercent).split("%")[0]) * 1f / 100);
        float swWidthPercent = (Float.valueOf(context.getString(R.string.all_sw_widthPercent).split("%")[0]) * 1f / 100);
        float tvSizePercent = (Float.valueOf(context.getString(R.string.week_tv_sizePercent).split("%")[0]) * 1f / 100);
        float remainedRate = (Float.valueOf(context.getString(R.string.remained_rate)));
        
        // RCV宽高
        float rcvHeight = rcv.getHeight();
        float rcvWidth = rcv.getWidth();

        // RCV父布局宽高
        float rcvParentWidth = (rcvWidth * 1f / swWidthPercent);
        float rcvParentHeight = (rcvHeight * 1f / swHeightPercent);

        // 屏幕宽高
        float windowWidth = ScreenSize.getSize(context).width;
        float windowHeight = ScreenSize.getSize(context).height;

        // 计算
        float tvHeight = (windowWidth * tvSizePercent);// 4%sw
        float recWidth = (windowWidth * recWidthPercent);// 6%sw
        float rlHeight = ((rcvHeight - tvHeight) * rlHeightPercent);
        float rlWidth = (recWidth * rlWidthPercent);// 60%w
        float ivHeight = (rlWidth * ivHeightPercent);
        float rlBottom = (recWidth * rlMarginBottomPercent);
        progressHeight = (int) (rlHeight - remainedRate * rlWidth - rlBottom);// 这里减1.2个width是为了留出余量
        Log.i("blue", "progressHeight: " + progressHeight);
    }

    @NonNull
    @Override
    public StatisticsWeekHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatisticsWeekHolder(LayoutInflater.from(context).inflate(R.layout.item_statistics_week, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsWeekHolder holder, int position) {
        // 处理高度
        float eachHeight = progressHeight * 1f / 100;// 得到单位高度
        StatisticsBean bean = beans.get(position);
        bean = turnTimeToProgress(bean);// 处理进度显示逻辑
        int progressBlue = (int) (bean.getProgressBlue() * eachHeight);
        int progressOrange = (int) (bean.getProgressOrange() * eachHeight);
        if (progressBlue >= progressHeight) {
            progressBlue = progressHeight;
        }
        if (progressOrange >= progressHeight) {
            progressOrange = progressHeight;
        }
        // 设置参数属性
        setHolderAttr(holder, position, progressBlue, progressOrange);
    }

    /**
     * 设置参数属性
     *
     * @param holder         holder
     * @param position       position
     * @param progressBlue   blue progress
     * @param progressOrange orange progress
     */
    private void setHolderAttr(final StatisticsWeekHolder holder, final int position, int progressBlue, int progressOrange) {
        StatisticsBean statistics = beans.get(position);

        // 总布局点击事件
        holder.rlStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                 * 点击某个条目时
                 * 如果当前被选中, 则取反设置为「不选中」
                 * 如果当前未选中, 则取反设置为「选中」
                 * 点击其他条目是, 当前条目恢复为「不选中」
                 */
                for (int i = 0; i < beans.size(); i++) {
                    if (i == position) {
                        if (beans.get(i).isClick()) {
                            beans.get(i).setClick(false);
                        } else {
                            beans.get(i).setClick(true);
                        }
                        clickItemNext(beans.get(i));
                    } else {
                        beans.get(i).setClick(false);
                    }
                }
                notifys(beans);
            }
        });


        // 大小布局
        holder.rlSmall.setVisibility(statistics.isClick() ? View.GONE : View.VISIBLE);
        holder.rlBig.setVisibility(statistics.isClick() ? View.VISIBLE : View.GONE);
        // 文字描述\颜色\加粗
        holder.tvNum.setText(statistics.getColValue());
        int colorBlack = context.getResources().getColor(R.color.colorBlack);
        int colorGray = context.getResources().getColor(R.color.colorGray);
        holder.tvNum.setTextColor(statistics.isClick() ? colorBlack : colorGray);
        holder.tvNum.getPaint().setFakeBoldText(statistics.isClick());
        // 小布局参数设置
        ViewGroup.LayoutParams ivpGreen = holder.ivPgGray.getLayoutParams();
        ViewGroup.LayoutParams ivpBlue = holder.ivPgBlue.getLayoutParams();
        ViewGroup.LayoutParams ivpOrange = holder.ivPgOrange.getLayoutParams();
        ivpGreen.height = progressHeight;// 总是满格
        ivpBlue.height = progressBlue;
        ivpOrange.height = progressOrange;
        holder.ivPgBlue.setLayoutParams(ivpBlue);
        holder.ivPgOrange.setLayoutParams(ivpOrange);
        // 大布局参数设置
        ViewGroup.LayoutParams ivpGreenbig = holder.ivPgGreenBig.getLayoutParams();
        ViewGroup.LayoutParams ivpBluebig = holder.ivPgBlueBig.getLayoutParams();
        ViewGroup.LayoutParams ivpOrangebig = holder.ivPgOrangeBig.getLayoutParams();
        ivpGreenbig.height = progressHeight;// 总是满格
        ivpBluebig.height = progressBlue;
        ivpOrangebig.height = progressOrange;
        holder.ivPgBlueBig.setLayoutParams(ivpBluebig);
        holder.ivPgOrangeBig.setLayoutParams(ivpOrangebig);
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    /**
     * 把 play & walk 时间转换为实际进度
     *
     * @param bean 当前对象
     * @return 处理后的对象
     */
    private StatisticsBean turnTimeToProgress(StatisticsBean bean) {
        float playTime = bean.getPlayTime();
        float walkTime = bean.getWalkTime();
        int playProgress = Math.round((playTime / UNIT) * 100);
        bean.setProgressBlue(playProgress);
        int walkProgress = Math.round((walkTime / UNIT) * 100);
        // 此处注意: 由于play 和 walk是层叠关系
        // walk显示的进度要比Play要高, 即在play的显示高度上去增加walk的实际高度
        walkProgress += playProgress;
        bean.setProgressOrange(walkProgress);
        return bean;
    }

    /* -------------------------------------------- impl -------------------------------------------- */

    private OnClickItemListener onClickItemListener;

    // Inteerface--> 接口OnClickItemListener
    public interface OnClickItemListener {
        void clickItem(StatisticsBean statisticsBean);
    }

    // 对外方式setOnClickItemListener
    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    // 封装方法clickItemNext
    private void clickItemNext(StatisticsBean statisticsBean) {
        if (onClickItemListener != null) {
            onClickItemListener.clickItem(statisticsBean);
        }
    }
}
