package com.xiuxiuing.testing.utils;

import java.security.MessageDigest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by wang on 16/11/2.
 */
public class AppinfoUtils {
    public static String getPkgSignature(Context context, String pkgName) {
        String signature = "";

        try {
            char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pkgInfo.signatures[0].toByteArray());
            byte[] mdHash = md.digest();

            char[] charArray = new char[32];
            int j = 0;
            for (int i = 0; i < 16; i++) {
                int k = mdHash[i];
                int m = j + 1;
                charArray[j] = hexDigits[(k >>> 4 & 0xF)];
                j = m + 1;
                charArray[m] = hexDigits[(k & 0xF)];
            }

            signature = new String(charArray);
            return signature;

        } catch (Throwable e) {
            // #debug
            e.printStackTrace();
        }

        return signature;
    }
}
