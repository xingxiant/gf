package com.learn.utils;

import java.io.*;

public class TXTUtil {
    public static void main(String[] args) {
        read("E:\\其他\\1.txt");
    }

    public static String read(String file1) {
        File file = new File(file1);
        String temp = "";
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "gbk");
            BufferedReader reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {
                temp += line;
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println(temp);
        return temp;
    }
}
