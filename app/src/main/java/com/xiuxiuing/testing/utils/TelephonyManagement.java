package com.xiuxiuing.testing.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build.VERSION;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.util.List;

/* compiled from: UMCTelephonyManagement */
public class TelephonyManagement {
    private static TelephonyManagement a;
    private b b = null;

    /* compiled from: UMCTelephonyManagement */
    public static class a extends Exception {
        public a(String str) {
            super(str);
        }
    }

    /* compiled from: UMCTelephonyManagement */
    public static class b {
        private String a = "";
        private String b = "";
        private String c = "";
        private String d = "";
        private boolean e = false;
        private boolean f = false;
        private int g = -1;
        private int h = -1;
        private int i = -1;
        private int j = -1;
        private String k = "";
        private String l = "";
        private int m = -1;
        private int n = -1;

        protected void a(String str) {
            if (str != null) {
                this.a = str;
            }
        }

        public String a() {
            return this.b;
        }

        protected void b(String str) {
            if (str != null) {
                this.b = str;
            }
        }

        public String b() {
            return this.c;
        }

        protected void c(String str) {
            if (str != null) {
                this.c = str;
            }
        }

        public String c() {
            return this.d;
        }

        protected void d(String str) {
            if (str != null) {
                this.d = str;
            }
        }

        protected void e(String str) {
            if (str != null) {
                this.k = str;
            }
        }

        public String d() {
            return this.l;
        }

        protected void f(String str) {
            this.l = str;
        }

        protected void a(boolean z) {
            this.e = z;
        }

        public boolean e() {
            return this.f;
        }

        protected void b(boolean z) {
            this.f = z;
        }

        public int f() {
            return this.m;
        }

        protected void a(int i) {
            this.m = i;
        }

        public int g() {
            return this.g;
        }

        protected void b(int i) {
            this.g = i;
        }

        public int h() {
            return this.h;
        }

        protected void c(int i) {
            this.h = i;
        }

        protected void d(int i) {
            this.i = i;
        }

        protected void e(int i) {
            this.j = i;
        }

        public String f(int i) {
            if (this.g == i) {
                return this.c;
            }
            if (this.h == i) {
                return this.d;
            }
            return "";
        }

        public String g(int i) {
            if (this.g == i) {
                return this.a;
            }
            if (this.h == i) {
                return this.b;
            }
            return "";
        }

        public String h(int i) {
            if (this.g == i) {
                return this.k;
            }
            if (this.h == i) {
                return this.l;
            }
            return "";
        }

        public int i(int i) {
            if (this.g == i) {
                return this.i;
            }
            if (this.h == i) {
                return this.j;
            }
            return -1;
        }
    }

    private TelephonyManagement() {}

    public static TelephonyManagement a() {
        if (a == null) {
            a = new TelephonyManagement();
        }
        return a;
    }

    public b a(Context context) {
        if (this.b == null) {
            b(context);
        }
        return this.b;
    }

    public TelephonyManagement b(Context context) {
        this.b = new b();
        if (VERSION.SDK_INT >= 22) {
            e(context);
        } else {
            f(context);
        }
        c(context);
        d(context);
        return this;
    }

    @SuppressLint({"NewApi"})
    private void c(Context context) {
        if (VERSION.SDK_INT >= 22) {
            Object from = SubscriptionManager.from(context.getApplicationContext());
            if (from != null) {
                try {
                    SubscriptionInfo a = a(from, "getDefaultDataSubscriptionInfo", null);
                    if (a != null) {
                        this.b.m = a.getSimSlotIndex();
                        return;
                    }
                    return;
                } catch (a e) {
                    return;
                }
            }
            return;
        }
        this.b.m = -1;
    }

    private void d(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return;
            }
            if (activeNetworkInfo.getType() == 1 && activeNetworkInfo.getState() == State.CONNECTED) {
                this.b.n = 1;
            } else if (activeNetworkInfo.getType() == 0 && activeNetworkInfo.getState() == State.CONNECTED) {
                this.b.n = 0;
            } else {
                this.b.n = -1;
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void e(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
        if (telephonyManager != null) {
            List g = g(context);
            a(g, telephonyManager);
            b(g, telephonyManager);
        }
    }

    private void f(Context context) {
        boolean z = true;
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
        this.b.b(0);
        this.b.c(1);
        this.b.a(-1);
        try {
            this.b.a(a(telephonyManager, "getDeviceId", 0));
            this.b.b(a(telephonyManager, "getDeviceId", 1));
        } catch (a e) {
            try {
                this.b.a(a(telephonyManager, "getDeviceIdGemini", 0));
                this.b.b(a(telephonyManager, "getDeviceIdGemini", 1));
            } catch (a e2) {
                this.b.a(telephonyManager.getDeviceId());
            }
        }
        try {
            this.b.c(a(telephonyManager, "getSubscriberId", 0));
            this.b.d(a(telephonyManager, "getSubscriberId", 1));
        } catch (a e3) {
            try {
                this.b.c(a(telephonyManager, "getSubscriberIdGemini", 0));
                this.b.d(a(telephonyManager, "getSubscriberIdGemini", 1));
            } catch (a e4) {
                this.b.c(telephonyManager.getSubscriberId());
            }
        }
        try {
            this.b.a(b(telephonyManager, "getSimState", 0));
            this.b.b(b(telephonyManager, "getSimState", 1));
        } catch (a e5) {
            try {
                this.b.a(b(telephonyManager, "getSimStateGemini", 0));
                this.b.b(b(telephonyManager, "getSimStateGemini", 1));
            } catch (a e6) {
                if (telephonyManager.getSimState() != 5) {
                    z = false;
                }
                this.b.a(z);
            }
        }
        try {
            this.b.e(a(telephonyManager, "getSimOperator", 0));
            this.b.f(a(telephonyManager, "getSimOperator", 1));
        } catch (a e7) {
            try {
                this.b.e(a(telephonyManager, "getSimOperatorGemini", 0));
                this.b.f(a(telephonyManager, "getSimOperatorGemini", 1));
            } catch (a e8) {
                this.b.e(telephonyManager.getSimOperator());
            }
        }
        if (TextUtils.isEmpty(this.b.b()) && !TextUtils.isEmpty(this.b.c())) {
            this.b.a(this.b.a());
            this.b.b("");
            this.b.c(this.b.c());
            this.b.d("");
            this.b.b(this.b.h());
            this.b.c(-1);
            this.b.a(this.b.e());
            this.b.b(false);
            this.b.e(this.b.d());
            this.b.f("");
            this.b.a(this.b.g());
        } else if (!TextUtils.isEmpty(this.b.b()) && TextUtils.isEmpty(this.b.c())) {
            this.b.b("");
            this.b.b(false);
            this.b.c(-1);
            this.b.a(this.b.g());
        } else if (TextUtils.isEmpty(this.b.b()) && TextUtils.isEmpty(this.b.c())) {
            this.b.a("");
            this.b.b("");
            this.b.b(-1);
            this.b.c(-1);
            this.b.a(false);
            this.b.b(false);
            this.b.a(-1);
        }
    }

    @SuppressLint({"NewApi"})
    private void a(List<SubscriptionInfo> list, TelephonyManager telephonyManager) {
        SubscriptionInfo subscriptionInfo;
        boolean z = true;
        int size = list != null ? list.size() : 0;
        if (size == 1) {
            subscriptionInfo = (SubscriptionInfo) list.get(0);
        } else if (size > 1) {
            subscriptionInfo = a((List) list, 0);
        } else {
            return;
        }
        this.b.b(subscriptionInfo.getSimSlotIndex());
        this.b.d(subscriptionInfo.getSubscriptionId());
        try {
            this.b.a(a(telephonyManager, "getDeviceId", subscriptionInfo.getSimSlotIndex()));
        } catch (a e) {
            try {
                this.b.a(a(telephonyManager, "getDeviceIdGemini", subscriptionInfo.getSimSlotIndex()));
            } catch (a e2) {
                this.b.a(telephonyManager.getDeviceId());
            }
        }
        try {
            this.b.e = b(telephonyManager, "getSimState", subscriptionInfo.getSimSlotIndex());
        } catch (a e3) {
            try {
                this.b.e = b(telephonyManager, "getSimStateGemini", subscriptionInfo.getSimSlotIndex());
            } catch (a e4) {
                b bVar = this.b;
                if (telephonyManager.getSimState() != 5) {
                    z = false;
                }
                bVar.a(z);
            }
        }
        int simSlotIndex = BrandUtil.b() == 0 ? subscriptionInfo.getSimSlotIndex() : subscriptionInfo.getSubscriptionId();
        try {
            this.b.c(a(telephonyManager, "getSubscriberId", subscriptionInfo.getSubscriptionId()));
        } catch (a e5) {
            try {
                this.b.c(a(telephonyManager, "getSubscriberIdGemini", simSlotIndex));
            } catch (a e6) {
                this.b.c(telephonyManager.getSubscriberId());
            }
        }
        try {
            this.b.e(a(telephonyManager, "getSimOperator", subscriptionInfo.getSubscriptionId()));
        } catch (a e7) {
            try {
                this.b.e(a(telephonyManager, "getSimOperatorGemini", simSlotIndex));
            } catch (a e8) {
                this.b.e(telephonyManager.getSimOperator());
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void b(List<SubscriptionInfo> list, TelephonyManager telephonyManager) {
        int i = 1;
        if ((list != null ? list.size() : 0) > 1) {
            try {
                this.b.b(a(telephonyManager, "getDeviceId", 1));
            } catch (a e) {
                try {
                    this.b.b(a(telephonyManager, "getDeviceIdGemini", 1));
                } catch (a e2) {
                }
            }
            try {
                this.b.f = b(telephonyManager, "getSimState", 1);
            } catch (a e3) {
                try {
                    this.b.f = b(telephonyManager, "getSimStateGemini", 1);
                } catch (a e4) {
                }
            }
            SubscriptionInfo a = a((List) list, 1);
            this.b.c(a.getSimSlotIndex());
            this.b.e(a.getSubscriptionId());
            if (BrandUtil.b() != 0) {
                i = a.getSubscriptionId();
            }
            try {
                this.b.d(a(telephonyManager, "getSubscriberId", a.getSubscriptionId()));
            } catch (a e5) {
                try {
                    this.b.d(a(telephonyManager, "getSubscriberIdGemini", i));
                } catch (a e6) {
                }
            }
            try {
                this.b.f(a(telephonyManager, "getSimOperator", a.getSubscriptionId()));
            } catch (a e7) {
                try {
                    this.b.f(a(telephonyManager, "getSimOperatorGemini", i));
                } catch (a e8) {
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    private SubscriptionInfo a(List<SubscriptionInfo> list, int i) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) list.get(0);
        SubscriptionInfo subscriptionInfo2 = subscriptionInfo;
        for (SubscriptionInfo subscriptionInfo3 : list) {
            if (subscriptionInfo3.getSimSlotIndex() != i) {
                subscriptionInfo3 = subscriptionInfo2;
            }
            subscriptionInfo2 = subscriptionInfo3;
        }
        return subscriptionInfo2;
    }

    @SuppressLint({"NewApi"})
    private List<SubscriptionInfo> g(Context context) {
        SubscriptionManager from = SubscriptionManager.from(context.getApplicationContext());
        if (from != null) {
            return from.getActiveSubscriptionInfoList();
        }
        return null;
    }

    private String a(TelephonyManager telephonyManager, String str, int i) throws a {
        Object a = a(telephonyManager, str, new Object[] {Integer.valueOf(i)}, new Class[] {Integer.TYPE});
        if (a != null) {
            return a.toString();
        }
        return null;
    }

    private boolean b(TelephonyManager telephonyManager, String str, int i) throws a {
        Object a = a(telephonyManager, str, new Object[] {Integer.valueOf(i)}, new Class[] {Integer.TYPE});
        if (a == null || Integer.parseInt(a.toString()) != 5) {
            return false;
        }
        return true;
    }

    private SubscriptionInfo a(Object obj, String str, Object[] objArr) throws a {
        return (SubscriptionInfo) a(obj, str, objArr, null);
    }

    private Object a(Object obj, String str, Object[] objArr, Class[] clsArr) throws a {
        try {
            Class cls = Class.forName(obj.getClass().getName());
            if (objArr == null || clsArr == null) {
                return cls.getMethod(str, new Class[0]).invoke(obj, new Object[0]);
            }
            return cls.getMethod(str, clsArr).invoke(obj, objArr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new a(str);
        }
    }
}
