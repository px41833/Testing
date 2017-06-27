package com.xiuxiuing.testing.adapter;

import java.util.ArrayList;
import java.util.List;

import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.utils.AppinfoUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wang on 16/11/2.
 */
public class AppinfoRecyclerAdapter extends RecyclerView.Adapter<AppinfoRecyclerAdapter.MainViewHolder> {

    private Context mContext;
    private List<AppInfo> appInfos = new ArrayList<AppInfo>();

    public AppinfoRecyclerAdapter(Context context) {
        this.mContext = context;
        PackageManager pm = mContext.getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            if (true || (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                AppInfo info = new AppInfo();
                info.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
                info.setPkgName(packageInfo.packageName);
                info.setVersionName(packageInfo.versionName);
                info.setVersionCode(packageInfo.versionCode);
                info.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
                info.setSign(AppinfoUtils.getPkgSignature(mContext, packageInfo.packageName));
                appInfos.add(info);
            }

        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_appinfo_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        holder.tvAppName.setText(appInfos.get(position).getAppName());
        holder.tvAppVersion.setText(appInfos.get(position).getVersionName());
        holder.tvAppPkg.setText(appInfos.get(position).getPkgName());
        holder.tvAppSign.setText(appInfos.get(position).getSign());
        holder.ivAppIcon.setImageDrawable(appInfos.get(position).getAppIcon());

        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText(appInfos.get(position).toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    private void shareText(String text) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        mContext.startActivity(intent);
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        CardView cvItem;
        TextView tvAppName;
        TextView tvAppPkg;
        TextView tvAppSign;
        TextView tvAppVersion;
        ImageView ivAppIcon;

        public MainViewHolder(View itemView) {
            super(itemView);
            cvItem = (CardView) itemView.findViewById(R.id.cv_appinfo_item);
            tvAppName = (TextView) itemView.findViewById(R.id.tv_appname);
            tvAppVersion = (TextView) itemView.findViewById(R.id.tv_appversion);
            tvAppPkg = (TextView) itemView.findViewById(R.id.tv_apppkg);
            tvAppSign = (TextView) itemView.findViewById(R.id.tv_appsign);
            ivAppIcon = (ImageView) itemView.findViewById(R.id.iv_appicon);
        }
    }
    private static class AppInfo {
        private String appName;
        private String pkgName;
        private String versionName;
        private int versionCode;
        private Drawable appIcon;
        private String sign;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getPkgName() {
            return pkgName;
        }

        public void setPkgName(String pkgName) {
            this.pkgName = pkgName;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public Drawable getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(Drawable appIcon) {
            this.appIcon = appIcon;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return "AppInfo{" + "appName='" + appName + '\'' + ", pkgName='" + pkgName + '\'' + ", versionName='" + versionName + '\''
                    + ", versionCode=" + versionCode + ", sign='" + sign + '\'' + '}';
        }
    }
}
