package com.ruolan.kotlinserver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    public static void main(String[] args) {

        String str = "孔孟之乡的<em>全聚德</em>有何不<em>一样</em>";

        str =  str.replaceAll("<em>","");
        str =  str.replaceAll("</em>","");


        System.out.println(str);

        //匹配规则
        Pattern p = Pattern.compile("<em[^>]*?>.*?</em>");
        Matcher m = p.matcher(str);

        List<String> strings = new ArrayList<>();

        while (m.find()) {
            String group = m.group();
            String substring = group.substring(4, group.length() - 5);
//            System.out.println(substring);
            strings.add(substring);
        }

        for (String string : strings) {
            System.out.println(string);
        }
    }
}
