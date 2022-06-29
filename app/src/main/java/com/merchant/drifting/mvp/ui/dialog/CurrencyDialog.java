package com.merchant.drifting.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseDialog;
import com.merchant.drifting.R;


/**
 * 系统弹框
 *
 * @author wjq
 * @date 2021/8/27
 */

public class CurrencyDialog extends BaseDialog implements View.OnClickListener {
    private TextView mTvCofim, mTvCancel, mTvTitle, mTvSmallTitle,mTvContent;
    private ImageView mImgTop;
    private Context mContext;
    private float mDialogWith = 0.8f;
    public static final int SELECT_FINISH = 0x01;
    public static final int SELECT_CANCEL = 0x02;


    public CurrencyDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public CurrencyDialog(@NonNull Context context, float dialogWith) {
        super(context);
        this.mContext = context;
        this.mDialogWith = dialogWith;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_currency;
    }

    @Override
    protected float getDialogWith() {
        return mDialogWith;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvCofim = findViewById(R.id.tv_cofim);
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvContent = findViewById(R.id.tv_content);
        mTvTitle = findViewById(R.id.tv_title);
        mTvSmallTitle = findViewById(R.id.tv_small_title);
        mImgTop = findViewById(R.id.img_top);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvCancel.setOnClickListener(this);
        mTvCofim.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_CANCEL);
                }
                break;
            case R.id.tv_cofim:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }
    }

    /**
     * 大标题文案设置
     *
     * @param title
     */
    public CurrencyDialog setTitleText(String title) {
        mTvTitle.setText(title);
        return this;
    }

    /**
     * 隐藏内容
     * @return
     */
    public CurrencyDialog setContentVisible() {
        mTvContent.setVisibility(View.GONE);
        return this;
    }

    /**
     * 内容
     *
     * @param content
     */
    public CurrencyDialog setContentText(String content) {
        mTvContent.setText(content);
        return this;
    }

    public CurrencyDialog setContentTextSize(float titleSize) {
        mTvContent.setTextSize(titleSize);
        return this;
    }

    public  CurrencyDialog setContentTextColor(int cancelColor) {
        mTvContent.setTextColor(mContext.getResources().getColor(cancelColor));
        return this;
    }




    /**
     * 大小标题文案设置
     *
     * @param title
     * @param smallTitle
     */
    public CurrencyDialog setTitleText(String title, String smallTitle) {
        mTvTitle.setText(title);
        mTvContent.setText(smallTitle);
        return this;
    }

    public CurrencyDialog setSmallTitleText(String smallTitle) {
        mTvSmallTitle.setText(smallTitle);
        return this;
    }


    /**
     * 大标题文案颜色设置
     *
     * @param titleColor
     */
    public CurrencyDialog setTitleColor(int titleColor) {
        mTvTitle.setTextColor(mContext.getResources().getColor(titleColor));
        return this;
    }

    /**
     * 大小标题文案颜色设置
     *
     * @param titleColor
     * @param smallTitleColor
     */
    public CurrencyDialog setTitleColor(int titleColor, int smallTitleColor) {
        mTvTitle.setTextColor(mContext.getResources().getColor(titleColor));
        mTvSmallTitle.setTextColor(mContext.getResources().getColor(smallTitleColor));
        return this;
    }

    /**
     * 大小标题文案大小设置
     *
     * @param titleSize
     * @param smallTitleSize
     */
    public CurrencyDialog setTitleSize(float titleSize, float smallTitleSize) {
        mTvTitle.setTextSize(titleSize);
        mTvSmallTitle.setTextSize(smallTitleSize);
        return this;
    }

    /**
     * 确定文案设置
     *
     * @param finish
     */
    public CurrencyDialog setText(String finish) {
        mTvCofim.setText(finish);
        return this;
    }

    /**
     * 取消和确定文案设置
     *
     * @param cancel
     * @param finish
     */
    public CurrencyDialog setText(String cancel, String finish) {
        mTvCancel.setText(cancel);
        mTvCofim.setText(finish);
        return this;
    }

    public CurrencyDialog setTextSize(float titleSize) {
        mTvCancel.setTextSize(titleSize);
        mTvCofim.setTextSize(titleSize);
        return this;
    }


    /**
     * 取消和确定文案颜色设置
     *
     * @param cancelColor
     * @param finishColor
     */
    public CurrencyDialog setTextColor(int cancelColor, int finishColor) {
        mTvCancel.setTextColor(mContext.getResources().getColor(cancelColor));
        mTvCofim.setTextColor(mContext.getResources().getColor(finishColor));
        return this;
    }

    /**
     * 顶部图片设置
     *
     * @param imgResources
     */
    public CurrencyDialog setImageTop(int imgResources) {
        mImgTop.setImageResource(imgResources);
        return this;
    }

    /**
     * 设置显示类型
     *
     * @param type 1 : 只显示大标题  2 : 大小标题显示，图标隐藏
     */
    public CurrencyDialog setType(int type) {
        switch (type) {
            case 1:
                mImgTop.setVisibility(View.GONE);
                mTvTitle.setVisibility(View.VISIBLE);
                mTvSmallTitle.setVisibility(View.GONE);
                break;
            case 2:
                mImgTop.setVisibility(View.GONE);
                mTvTitle.setVisibility(View.VISIBLE);
                mTvSmallTitle.setVisibility(View.GONE);
                mTvContent.setVisibility(View.VISIBLE);
                break;
            case 3:
                mImgTop.setVisibility(View.GONE);
                mTvTitle.setVisibility(View.GONE);
                mTvContent.setVisibility(View.GONE);
                mTvSmallTitle.setVisibility(View.VISIBLE);
                break;
            case 4:
                mImgTop.setVisibility(View.GONE);
                mTvTitle.setVisibility(View.VISIBLE);
                mTvSmallTitle.setVisibility(View.GONE);
                mTvContent.setVisibility(View.GONE);
                break;
        }
        return this;
    }

}








