package com.xiuxiuing.testing.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wang on 16/6/13.
 */
public class PasswordGetter {
    private String password;

    private BufferedReader br;

    public PasswordGetter(InputStream in){
        br = new BufferedReader(new InputStreamReader(in));
    }

    public void reSet(InputStream in){
        try {
            br.close();
            br = new BufferedReader(new InputStreamReader(in));
        } catch (IOException e) {
            e.printStackTrace();
            password = null;
        }
    }

    public String getPassword(){
        try {
            password = br.readLine();
            System.out.println("test password:" + password);
        } catch (IOException e) {
            e.printStackTrace();
            password = null;
        }
        return password;
    }

    public void Clean(){
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
