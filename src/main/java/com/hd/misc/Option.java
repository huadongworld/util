package com.hd.misc;

/**
 * @author HuaDong
 * @date 2019/10/14 9:30
 */
public class Option {

    private String desc;

    private Object value;

    public Option() {
    }

    public Option(String desc, Object value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Option{" +
                "desc='" + desc + '\'' +
                ", value=" + value +
                '}';
    }
}
