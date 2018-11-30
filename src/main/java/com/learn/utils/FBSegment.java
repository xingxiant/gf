package com.learn.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;


public class FBSegment {
    public static String message;
    private static Set<String> seg_dict;
    private static int max = 0;

    private static void append(String str) {
        message += str + "<br/>";
    }

    //加载词典
    public static void Init() {
        message = "";
        seg_dict = new HashSet<String>();
        String dicpath = "C:\\Users\\lenovo\\Desktop\\program\\src\\main\\resources\\word.dic";
        String line = null;

        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(dicpath),"UTF-8"));
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                if(line.length() > max)
                    max = line.length();
                seg_dict.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 正向匹配
     * 前向算法分词
     *
     * @param phrase 待分词句子
     * @return 前向分词结果
     */
    public static Vector<String> FMM2(String phrase) {
        append("开始正向匹配-----------------------------");


        int maxlen = max;
        Vector<String> fmm_list = new Vector<String>();
        int len_phrase = phrase.length();
        int i = 0, j = 0;

        while (i < len_phrase) {

            int end = i + maxlen;
            if (end >= len_phrase) {
            }
            end = len_phrase;
            String phrase_sub = phrase.substring(i, end);
            if (i > 0) {
                append("切除左边【" + i + "】个词，现在剩余：" + phrase_sub);
            }
            for (j = phrase_sub.length(); j >= 0; j--) {
                if (j == 1)
                    break;
                String key = phrase_sub.substring(0, j);
                append("现在用【" + key + "】匹配");
                if (seg_dict.contains(key)) {
                    append("<span style='color:red;'>匹配成功:【" + key + "】</span>");
                    append("把左边【" + key + "】切去");
                    fmm_list.add(key);
                    i += key.length() - 1;
                    break;
                }
            }
            append("");
            if (j == 1)
                fmm_list.add("" + phrase_sub.charAt(0));
            i += 1;
        }
        append("正向匹配完毕-----------------------------");
        append("");
        return fmm_list;
    }

    /**
     * 逆向匹配
     * 后向算法分词
     *
     * @param phrase 待分词句子
     * @return 后向分词结果
     */
    public static Vector<String> BMM2(String phrase) {

        append("开始逆向匹配-----------------------------");
        int maxlen = max;
        Vector<String> bmm_list = new Vector<String>();
        int len_phrase = phrase.length();
        int i = len_phrase, j = 0;

        while (i > 0) {
            int start = i - maxlen;
            if (start < 0)
                start = 0;

            String phrase_sub = phrase.substring(start, i);
            if (i >= 0) {
                append("切除最右边的一个词，现在剩余：【" + phrase_sub + "】");
            }
            for (j = 0; j < phrase_sub.length(); j++) {
                if (j == phrase_sub.length() - 1)
                    break;
                String key = phrase_sub.substring(j);
                append("左边切除一个词进行匹配:【" + key + "】");
                if (seg_dict.contains(key)) {
                    append("<span style='color:red;'>匹配成功:【" + key + "】</span>");
                    append("把【" + key + "】切去");
                    bmm_list.insertElementAt(key, 0);
                    i -= key.length() - 1;
                    break;
                }
            }
            append("");
            if (j == phrase_sub.length() - 1)
                bmm_list.insertElementAt("" + phrase_sub.charAt(j), 0);
            i -= 1;
        }
        append("逆向匹配完毕-----------------------------");
        append("");
        return bmm_list;
    }

    /**
     * 该方法结合正向匹配和逆向匹配的结果，得到分词的最终结果
     *
     * @param phrase FMM2   正向匹配的分词结果
     * @param phrase BMM2   逆向匹配的分词结果
     * @param phrase return 分词的最终结果
     */
    public static Vector<String> segment(String phrase) {
        Vector<String> fmm_list = FMM2(phrase);
        Vector<String> bmm_list = BMM2(phrase);

        append("-------------------------------------------------");
        append("-------------------------------------------------");
        append("正向匹配结果："+fmm_list.toString());
        append("逆向匹配结果："+bmm_list.toString());

        append("开始把正向和逆向的结果进行双向匹配");
        append("-------------------------------------------------");
        append("两次匹配结果判断 ");


        //如果正反向分词结果词数不同，则取分词数量较少的那个
        if (fmm_list.size() != bmm_list.size()) {
            append("两次匹配结果词数不同");
            append("返回分词数量较少的那个");
            if (fmm_list.size() > bmm_list.size())
                return bmm_list;
            else return fmm_list;
        }
        //如果分词结果词数相同
        else {
            append("两次匹配结果词数相同");
            append("开始判断内容");
            //如果正反向的分词结果相同，就说明没有歧义，可返回任意一个
            int i, FSingle = 0, BSingle = 0;
            boolean isSame = true;
            for (i = 0; i < fmm_list.size(); i++) {
                if (!fmm_list.get(i).equals(bmm_list.get(i)))
                    isSame = false;
                if (fmm_list.get(i).length() == 1)
                    FSingle += 1;
                if (bmm_list.get(i).length() == 1)
                    BSingle += 1;
            }
            if (isSame) {
                append("两次匹配的分词结果相同，随意返回一个");
                return fmm_list;
            } else {
                append("两次匹配的分词结果相同，返回其中单字较少的那个");
                //分词结果不同，返回其中单字较少的那个
                if (BSingle > FSingle)
                    return fmm_list;
                else return bmm_list;
            }
        }
    }

}
