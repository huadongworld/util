package com.hd.enums;

import com.hd.misc.Option;

/**
 * @author HuaDong
 * @date 2019/10/14 9:28
 */
public enum TimeUnit {

    /**
     * 时间单位
     */
    YEAR("年"),

    MONTH("月"),

    DAYS("日"),

    HOUR("时"),

    MINUTE("分"),

    SECOND("秒"),

    WEEK("周"),

    QUARTER("季度");

    /**
     * 配合时长的值得到具体时长合理描述
     *
     * @param value 值
     * @param unit 单位
     * @return
     */
    public static String getDetailDesc(Integer value, TimeUnit unit) {
        switch (unit){
            case YEAR:
                return value + "年";
            case MONTH:
                return value + "个月";
            case DAYS:
                return value + "天";
            case HOUR:
                return value + "个小时";
            case MINUTE:
                return value + "分钟";
            case SECOND:
                return value + "秒";
            case WEEK:
                return value + "周";
            case QUARTER:
                return value + "个季度";
            default:
                throw new IllegalArgumentException("不能识别的时间单位:" + unit);
        }
    }

    TimeUnit(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public Option toOption() {
        return new Option(desc, ordinal());
    }
}
