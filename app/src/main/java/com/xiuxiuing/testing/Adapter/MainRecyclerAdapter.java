package com.xiuxiuing.testing.adapter;

import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.activity.AppinfoActivity;
import com.xiuxiuing.testing.activity.CustomViewActivity;
import com.xiuxiuing.testing.activity.IntentOpenApp;
import com.xiuxiuing.testing.activity.LocationActivity;
import com.xiuxiuing.testing.activity.PhoneInfoActivity;
import com.xiuxiuing.testing.activity.WebviewActivity;
import com.xiuxiuing.testing.activity.WiFiSdkActivity;
import com.xiuxiuing.testing.activity.WifiManagerActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wang on 16/5/23.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
    private final ItemInfo[] itemInfos = {new ItemInfo(R.string.title_wifi_manager, R.string.desc_wifi_manager, WifiManagerActivity.class),
            new ItemInfo(R.string.title_wifi_share, R.string.desc_wifi_share, WiFiSdkActivity.class),
            new ItemInfo(R.string.title_android_location, R.string.desc_android_location, LocationActivity.class),
            new ItemInfo(R.string.title_android_webview, R.string.desc_android_webview, WebviewActivity.class),
            new ItemInfo(R.string.title_android_openappstore, R.string.desc_android_openappstore, IntentOpenApp.class),
            new ItemInfo(R.string.title_android_phoneinfo, R.string.desc_android_phoneinfo, PhoneInfoActivity.class),
            new ItemInfo(R.string.title_android_customview, R.string.desc_android_customview, CustomViewActivity.class),
            new ItemInfo(R.string.title_android_appinfo, R.string.desc_android_appinfo, AppinfoActivity.class)};

    private Context mContext;

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_main_item, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        holder.tvTitle.setText(itemInfos[position].title);
        holder.tvDesc.setText(itemInfos[position].desc);
        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, itemInfos[position].itemClass);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemInfos.length;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        CardView cvItem;
        TextView tvTitle;
        TextView tvDesc;

        public MainViewHolder(View itemView) {
            super(itemView);
            cvItem = (CardView) itemView.findViewById(R.id.cv_main_item);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_main_desc);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_main_title);

        }
    }

    private static class ItemInfo {
        private final int title;
        private final int desc;
        private final Class<? extends Activity> itemClass;

        public ItemInfo(int title, int desc, Class<? extends Activity> itemClass) {
            this.title = title;
            this.desc = desc;
            this.itemClass = itemClass;
        }
    }
}
