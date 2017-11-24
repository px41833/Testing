package com.xiuxiuing.testing.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.socks.library.KLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wang on 2017/11/2.
 */

public class UmcSdkUtils {

    public static byte[] hexStr2Bytes(String paramString) {
        if (paramString.length() < 1) {
            return null;
        }
        byte[] arrayOfByte = new byte[paramString.length() / 2];
        for (int i = 0; i < paramString.length() / 2; i++) {
            int j = Integer.parseInt(paramString.substring(i * 2, i * 2 + 1), 16);
            int k = Integer.parseInt(paramString.substring(i * 2 + 1, i * 2 + 2), 16);

            arrayOfByte[i] = ((byte) (j * 16 + k));
        }
        return arrayOfByte;
        // new String(arrayOfByte, "UTF-8")
        // aesCrypt(arrayOfByte, System.currentTimeMillis(), 1)
    }

    public static void requestNetwork(final Context context) {
        KLog.d("requestNetwork");
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);

            NetworkRequest request = builder.build();
            ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(final Network network) {
                    super.onAvailable(network);
                    KLog.d("onAvailable");
                    // 通过network.openConnection 来获取URLConnection
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getmobilekey(network, "http://www.cmpassport.com/openapi/getmobilekey?" + getParams(context, "300008334557"));
                                }
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                }
            };
            // cm.registerNetworkCallback(request, callback);
            cm.requestNetwork(request, callback);
        }



    }

    protected static String getParams(Context context, String appid) {
        try {
            String c = "2.0";
            String d = "999";
            String q = "1.0";
            String b = "umcsdk_outer_v1.4.3.5";
            String A = "1";
            String C = Build.BRAND;
            String D = Build.MODEL;
            String E = "android" + Build.VERSION.RELEASE;
            String t = "0";
            String u = getAir(context);
            String v = getuuid();
            String r = getIMSI(context);
            String s = getIMEI(context);
            String w = "";
            String x = "";
            String y = "";
            String z = getTime();

            StringBuilder sb = new StringBuilder();
            sb.append("ver=");
            sb.append(c);
            sb.append("&sourceid=");
            sb.append(d);
            sb.append("&appid=");
            sb.append(appid);
            sb.append("&clientver=");
            sb.append(URLEncoder.encode(q, "utf-8"));
            sb.append("&sdkver=");
            sb.append(b);
            sb.append("&authtype=");
            sb.append(A);
            sb.append("&smskey=");
            String str = a("", "vy7580");
            sb.append(URLEncoder.encode(str, "UTF-8"));
            sb.append("&imsi=");
            sb.append(r);
            sb.append("&imei=");
            sb.append(s);
            sb.append("&mobilebrand=");
            sb.append(URLEncoder.encode(C, "UTF-8"));
            sb.append("&mobilemodel=");
            sb.append(URLEncoder.encode(D, "UTF-8"));
            sb.append("&mobilesystem=");
            sb.append(URLEncoder.encode(E, "UTF-8"));
            sb.append("&clienttype=");
            sb.append(t);
            sb.append("&operatortype=");
            sb.append(u);
            sb.append("&unikey=");
            sb.append(v);
            sb.append("&redirecturl=");
            sb.append(URLEncoder.encode(w, "UTF-8"));
            sb.append("&state=");
            sb.append(x);
            sb.append("&expandparams=");
            sb.append(y);
            sb.append("&timestamp=");
            sb.append(z);
            sb.append("&code=");
            sb.append(getCode(c + d + appid + q + b + A + str + r + s + C + D + E + t + u + v + w + x + y + z + "12345678"));
            return sb.toString();

        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "";
    }

    public static final String getCode(String paramString) {
        return MD5(paramString).toUpperCase();
    }

    public static final String MD5(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            byte[] arrayOfByte1 = paramString.getBytes();
            byte[] arrayOfByte2 = localMessageDigest.digest(arrayOfByte1);
            StringBuffer localStringBuffer = new StringBuffer();
            for (int i = 0; i < arrayOfByte2.length; i++) {
                if (Integer.toHexString(0xFF & arrayOfByte2[i]).length() == 1) {
                    localStringBuffer.append("0").append(Integer.toHexString(0xFF & arrayOfByte2[i]));
                } else {
                    localStringBuffer.append(Integer.toHexString(0xFF & arrayOfByte2[i]));
                }
            }
            return localStringBuffer.toString();
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
        }
        return null;
    }

    public static String getAir(Context paramContext) {
        int i = Settings.System.getInt(paramContext.getApplicationContext().getContentResolver(), "airplane_mode_on", 0);
        if (i == 1) {
            return "0";
        }
        String str = "";
        try {
            TelephonyManagement.b localb = TelephonyManagement.a().a(paramContext);
            int j = localb.f();
            str = localb.h(j);
            if (TextUtils.isEmpty(str)) {
                TelephonyManager localTelephonyManager = (TelephonyManager) paramContext.getSystemService("phone");
                str = localTelephonyManager.getSimOperator();
            }
            if (!TextUtils.isEmpty(str)) {
                if ((str.equals("46000")) || (str.equals("46002")) || (str.equals("46007")) || (str.equals("46004"))) {

                    return "1";
                }
                if (("46001".equals(str)) || (str.equals("46006")) || (str.equals("46009"))) {

                    return "2";
                }
                if (("46003".equals(str)) || (str.equals("46005")) || (str.equals("46011"))) {

                    return "3";
                }
            }
        } catch (Exception localException) {
        }
        return "0";
    }

    public static String getIMEI(Context context) {
        try {
            TelephonyManagement.b localb = TelephonyManagement.a().a(context);
            int i = localb.f();
            String str = localb.g(i);
            if (TextUtils.isEmpty(str)) {
                TelephonyManager localTelephonyManager = (TelephonyManager) context.getSystemService("phone");
                str = localTelephonyManager.getDeviceId();
                if (TextUtils.isEmpty(str)) {
                    str = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "android_id");
                }
                if (!TextUtils.isEmpty(str)) {
                }
            }
            return "000000000000000";
        } catch (Exception localException) {
        }
        return "000000000000000";
    }

    public static String getIMSI(Context context) {
        try {
            TelephonyManagement.b localb = TelephonyManagement.a().a(context);
            int i = localb.f();
            String str = localb.f(i);
            if (TextUtils.isEmpty(str)) {
                TelephonyManager localTelephonyManager = (TelephonyManager) context.getSystemService("phone");
                str = localTelephonyManager.getSubscriberId();
                if (TextUtils.isEmpty(str)) {
                    str = localTelephonyManager.getSimOperator();
                    if (!TextUtils.isEmpty(str)) {
                        str = str + c().substring(10, 20);
                    }
                }
            }
            if ((str == null) || (!str.startsWith("460"))) {
                str = "";
            }
            return str;
        } catch (Exception localException) {
        }
        return "";
    }

    public static String c() {
        String str1 = "0123456789";
        int i = 0;
        StringBuffer localStringBuffer = new StringBuffer();
        Random localRandom = new Random();
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        localStringBuffer.append(localSimpleDateFormat.format(new Date()));
        for (int j = 0; j < 6; j++) {
            localStringBuffer.append("0123456789".charAt(localRandom.nextInt("0123456789".length())));
        }
        i++;
        if (i > 9999) {
            i = 0;
        }
        String str2 = String.valueOf(i);
        for (int k = 0; k < 4 - str2.length(); k++) {
            localStringBuffer.append("0");
        }
        localStringBuffer.append(str2);
        return localStringBuffer.toString();
    }

    public static String getTime() {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return localSimpleDateFormat.format(new Date());
    }

    public static String getuuid() {
        String str = UUID.randomUUID().toString();
        return str.replaceAll("-", "");
    }

    public static String getmobilekey(Network paramNetwork, String url) {
        HttpURLConnection localHttpURLConnection = null;
        InputStream localInputStream = null;
        OutputStream localOutputStream = null;
        try {
            URL localURL = new URL(url);
            if ((Build.VERSION.SDK_INT >= 21) && (paramNetwork != null)) {
                localHttpURLConnection = (HttpURLConnection) paramNetwork.openConnection(localURL);
            } else {
                localHttpURLConnection = (HttpURLConnection) localURL.openConnection();
            }
            localHttpURLConnection.addRequestProperty("Accept-Encoding", "gzip,deflate");
            localHttpURLConnection.setConnectTimeout(5000);
            localHttpURLConnection.setReadTimeout(5000);
            localHttpURLConnection.setRequestMethod("POST");
            localHttpURLConnection.setDoOutput(true);
            localOutputStream = localHttpURLConnection.getOutputStream();
            int i = localHttpURLConnection.getResponseCode();

            if (i != 200) {

            } else {
                byte[] bytes;
                Map<String, List<String>> n = localHttpURLConnection.getHeaderFields();
                localInputStream = localHttpURLConnection.getInputStream();
                if (n.toString().toUpperCase().contains("CONTENT-ENCODING=[GZIP]")) {
                    bytes = b(localInputStream);
                } else {
                    bytes = a(localInputStream);
                }
                KLog.d(new String(bytes, "UTF-8"));
                return new String(bytes, "UTF-8");

            }
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            try {
                if (localInputStream != null) {
                    localInputStream.close();
                }
            } catch (IOException localIOException5) {
                localIOException5.printStackTrace();
            }
            try {
                if (localOutputStream != null) {
                    localOutputStream.close();
                }
            } catch (IOException localIOException6) {
                localIOException6.printStackTrace();
            }
            if (localHttpURLConnection != null) {
                localHttpURLConnection.disconnect();
            }
        }
        return "";
    }


    public static byte[] b(InputStream paramInputStream) throws IOException {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        GZIPInputStream localGZIPInputStream = new GZIPInputStream(paramInputStream);
        byte[] arrayOfByte1 = new byte[1024];
        int i = -1;
        try {
            while ((i = localGZIPInputStream.read(arrayOfByte1)) != -1) {
                localByteArrayOutputStream.write(arrayOfByte1, 0, i);
            }
            localByteArrayOutputStream.close();
            return localByteArrayOutputStream.toByteArray();
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            if (paramInputStream != null) {
                paramInputStream.close();
            }
        }
        return null;
    }

    public static byte[] a(InputStream paramInputStream) throws IOException {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] arrayOfByte1 = new byte[1024];
            int i;
            while ((i = paramInputStream.read(arrayOfByte1, 0, arrayOfByte1.length)) != -1) {
                localByteArrayOutputStream.write(arrayOfByte1, 0, i);
            }
            localByteArrayOutputStream.close();
            return localByteArrayOutputStream.toByteArray();
        } finally {
            try {
                if (localByteArrayOutputStream != null) {
                    localByteArrayOutputStream.close();
                    localByteArrayOutputStream = null;
                }
            } catch (IOException localIOException2) {
                localIOException2.printStackTrace();
            }
        }
    }

    public static String a(String paramString1, String paramString2) {
        if (TextUtils.isEmpty(paramString1)) {
            return "";
        }
        byte[] arrayOfByte1 = a(paramString2);
        byte[] arrayOfByte2 = paramString1.getBytes();
        try {
            byte[] arrayOfByte3 = a(arrayOfByte2, arrayOfByte1);
            return new String(ba(arrayOfByte3));
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "";
    }

    public static byte[] a(String paramString) {
        String str = md5(paramString);
        if (str != null) {
            return a(str.toCharArray());
        }
        return null;
    }

    public static byte[] a(char[] paramArrayOfChar) {
        char[] arrayOfChar = new char[2];
        byte[] arrayOfByte = new byte[paramArrayOfChar.length / 2];
        int i = 0;
        for (int j = 0; j + 1 < paramArrayOfChar.length; j += 2) {
            arrayOfChar[0] = paramArrayOfChar[j];
            arrayOfChar[1] = paramArrayOfChar[(j + 1)];
            arrayOfByte[(i++)] = b(arrayOfChar);
        }
        return arrayOfByte;
    }

    private static byte b(char[] paramArrayOfChar) {
        char[] arrayOfChar = new char[2];

        arrayOfChar[0] = a(paramArrayOfChar[0]);
        arrayOfChar[1] = a(paramArrayOfChar[1]);

        byte b = (byte) (arrayOfChar[0] << '\004' | arrayOfChar[1]);

        return b;
    }

    private static char a(char paramChar) {
        if ((paramChar >= '0') && (paramChar <= '9')) {
            return (char) (paramChar - '0');
        }
        if ((paramChar >= 'a') && (paramChar <= 'f')) {
            return (char) (paramChar - 'a' + 10);
        }
        if ((paramChar >= 'A') && (paramChar <= 'F')) {
            return (char) (paramChar - 'A' + 10);
        }
        return ' ';
    }

    private static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte2, "AES");
        Cipher localCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        localCipher.init(1, localSecretKeySpec);
        return localCipher.doFinal(paramArrayOfByte1);
    }

    public static byte[] ba(byte[] paramArrayOfByte) {
        final byte[] b = new byte[] {(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74,
                (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86,
                (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103,
                (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114,
                (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50,
                (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
        int i = paramArrayOfByte.length % 3;
        byte[] arrayOfByte;
        if (i == 0) {
            arrayOfByte = new byte[4 * paramArrayOfByte.length / 3];
        } else {
            arrayOfByte = new byte[4 * (paramArrayOfByte.length / 3 + 1)];
        }
        int j = paramArrayOfByte.length - i;

        int i1 = 0;
        int i2;
        for (i2 = 0; i1 < j; i2 += 4) {
            int k = paramArrayOfByte[i1] & 0xFF;
            int m = paramArrayOfByte[(i1 + 1)] & 0xFF;
            int n = paramArrayOfByte[(i1 + 2)] & 0xFF;

            arrayOfByte[i2] = b[(k >>> 2 & 0x3F)];
            arrayOfByte[(i2 + 1)] = b[((k << 4 | m >>> 4) & 0x3F)];
            arrayOfByte[(i2 + 2)] = b[((m << 2 | n >>> 6) & 0x3F)];
            arrayOfByte[(i2 + 3)] = b[(n & 0x3F)];
            i1 += 3;
        }
        int i4;
        switch (i) {
            case 0:
                break;
            case 1:
                i4 = paramArrayOfByte[(paramArrayOfByte.length - 1)] & 0xFF;
                i1 = i4 >>> 2 & 0x3F;
                i2 = i4 << 4 & 0x3F;

                arrayOfByte[(arrayOfByte.length - 4)] = b[i1];
                arrayOfByte[(arrayOfByte.length - 3)] = b[i2];
                arrayOfByte[(arrayOfByte.length - 2)] = 61;
                arrayOfByte[(arrayOfByte.length - 1)] = 61;
                break;
            case 2:
                i4 = paramArrayOfByte[(paramArrayOfByte.length - 2)] & 0xFF;
                int i5 = paramArrayOfByte[(paramArrayOfByte.length - 1)] & 0xFF;

                i1 = i4 >>> 2 & 0x3F;
                i2 = (i4 << 4 | i5 >>> 4) & 0x3F;
                int i3 = i5 << 2 & 0x3F;

                arrayOfByte[(arrayOfByte.length - 4)] = b[i1];
                arrayOfByte[(arrayOfByte.length - 3)] = b[i2];
                arrayOfByte[(arrayOfByte.length - 2)] = b[i3];
                arrayOfByte[(arrayOfByte.length - 1)] = 61;
        }
        return arrayOfByte;
    }

    public static String md5(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            return null;
        }
        MessageDigest localMessageDigest = null;
        try {
            localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            return null;
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
            return null;
        }
        return a(localMessageDigest.digest());
    }

    private static String a(byte[] paramArrayOfByte) {
        char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] arrayOfChar = new char[32];

        int i = 0;
        for (int j = 0; j < 16; j++) {
            int k = paramArrayOfByte[j];
            arrayOfChar[(i++)] = a[(k >>> 4 & 0xF)];

            arrayOfChar[(i++)] = a[(k & 0xF)];
        }
        String str = new String(arrayOfChar);
        return str;
    }
}
