package com.hd.util;

/**
 * @author HuaDong
 * @date 2019/11/7 13:53
 */
class PinYin {

    /**
     * 拼音首字母串
     */
    public String firstPinYinString;

    /**
     * 全拼
     */
    public String fullPinYin;


    @Override
    public String toString() {
        return "PinYin{" +
                "firstPinYinChar='" + firstPinYinString + '\'' +
                ", fullPinYin='" + fullPinYin + '\'' +
                '}';
    }
}
