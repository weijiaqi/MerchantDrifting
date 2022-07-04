package com.merchant.drifting.mvp.ui.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.ShopListEntity;
import com.merchant.drifting.util.DateUtil;
import com.merchant.drifting.util.TextUtil;
import java.util.List;
import butterknife.BindView;

public class SwitchMerchantsHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_call)
    TextView mTvCall;
    @BindView(R.id.tv_open_time)
    TextView mTvOpenTime;
    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;

    public SwitchMerchantsHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<ShopListEntity> listBeanList, int position) {
        TextUtil.setText(mTvTitle, listBeanList.get(position).getShop_name());
        TextUtil.setText(mTvCall, "+86" + listBeanList.get(position).getMobile());
        TextUtil.setText(mTvOpenTime, "开店时间：" + DateUtil.unxiToDateYMD(listBeanList.get(position).getApply_at_int() + ""));
    }
}
