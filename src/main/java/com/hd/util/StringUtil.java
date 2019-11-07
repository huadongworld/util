package com.hd.util;

import com.google.common.base.CharMatcher;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author HuaDong
 * @date 2019/10/18 18:14
 */
public class StringUtil {

    public static final String PHONE_PATTERN_STRING = "^[1][\\d]{10}$";

    public static final String TELEPHONE_PATTERN_STRING = "^((\\d{3,4})|\\d{3,4}-)?\\d{7,8}$";

    private static final String DATE_WITHOUT_YEAR_AND_DAY_PATTERN_STRING = "[0-2][0-9][:][0-5][0-9][:][0-5][0-9]";

    public static final String PHONE_OR_TELEPHONE_STRING = "(" + PHONE_PATTERN_STRING + ")|(" + TELEPHONE_PATTERN_STRING + ")";

    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_PATTERN_STRING);

    private static final Pattern TELEPHONE_PATTERN = Pattern.compile(TELEPHONE_PATTERN_STRING);

    private static final Pattern DATE_WITHOUT_YEAR_AND_DAY_PATTERN = Pattern.compile(DATE_WITHOUT_YEAR_AND_DAY_PATTERN_STRING);

    private static final Pattern PHONE_OR_TELEPHONE_PATTERN = Pattern.compile(PHONE_OR_TELEPHONE_STRING);

    private static final String ALL_LETTER_AND_NUMBER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final HanyuPinyinOutputFormat pinyinOutputFormat;

    static {
        pinyinOutputFormat = new HanyuPinyinOutputFormat();
        pinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param s
     * @return
     */
    public static boolean containsChinese(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (isChinese(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 15634211184 -> 156********
     *
     * @param phone
     * @return
     */
    public static String coverPhone(String phone){
        if (StringUtils.isNotBlank(phone) && phone.length() > 3) {
            return phone.substring(0, 3) + "********";
        }
        return "";
    }

    /**
     * 判断字符是否是中文
     *
     * @param v
     * @return
     */
    public static boolean isChinese(char v) {
        return (v >= 19968) && (v <= 171941);
    }

    /**
     * 生成len长度的随机数字串
     *
     * @param len
     * @return
     */
    public static String generateRandomNumber(int len) {
        String value = "";
        for (int i = 0; i < len; i++) {
            value += (int) (Math.random() * 10);
        }
        return value;
    }

    /**
     * 判断是否是手机号格式
     *
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number) {
        if (StringUtils.isBlank(number)) {
            return false;
        }
        return PHONE_PATTERN.matcher(number)
                .matches();
    }

    /**
     * 判断是否是手机号或者座机格式
     *
     * @param number
     * @return
     */
    public static boolean isPhoneOrTelephoneNumber(String number) {
        if (StringUtils.isBlank(number)) {
            return false;
        }
        return PHONE_OR_TELEPHONE_PATTERN.matcher(number)
                .matches();
    }

    /**
     * 判断是否是座机格式
     *
     * @param number
     * @return
     */
    public static boolean isTelephoneNumber(String number) {
        if (StringUtils.isBlank(number)) {
            return false;
        }
        return TELEPHONE_PATTERN.matcher(number)
                .matches();
    }

    /**
     * 判断日期是否只包含时分秒格式，不含年月日
     *
     * @param date
     * @return
     */
    public static boolean isDateWithoutDayAndYear(String date) {
        if (StringUtils.isBlank(date)) {
            return false;
        }
        return DATE_WITHOUT_YEAR_AND_DAY_PATTERN.matcher(date)
                .matches();
    }

    /**
     * 获取随机串
     *
     * @return
     */
    public static String getShortUUID() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-",
                        "");
    }

    /**
     * 生成Sid
     *
     * @return
     */
    public static String generateSessionId() {
        String uuid = getShortUUID();
        String random = generateRandomNumber(6);
        long time = System.currentTimeMillis();
        return Base64.encode((uuid + random + time).getBytes());
    }

    /**
     * 获取随机62位串
     *
     * @return
     */
    public static String random62Char() {

        char[] cc = ALL_LETTER_AND_NUMBER.toCharArray();
        StringBuilder sb = new StringBuilder();
        int len = cc.length;
        for (int i = 0; i < cc.length; i++, len--) {
            int p = (int) (Math.random() * len);
            sb.append(cc[p]);
            cc[p] = cc[len - 1];
        }
        return sb.toString();
    }

    public static String getFullPinYinName(String name) {
        StringBuilder pinYinName = new StringBuilder();
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                String[] pinYin = new String[0];
                try {
                    pinYin = PinyinHelper.toHanyuPinyinStringArray(c,
                            pinyinOutputFormat);
                    if (pinYin != null)
                        pinYinName.append(pinYin[0]);
                    else
                        pinYinName.append(c);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    throw new RuntimeException(badHanyuPinyinOutputFormatCombination);
                }
            }
        }
        return pinYinName.toString();

    }

    public static PinYin getPinYinInfo(String name) {
        StringBuilder pinYinName = new StringBuilder();
        StringBuilder firstPin = new StringBuilder();
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                String[] pinYin;
                try {
                    pinYin = PinyinHelper.toHanyuPinyinStringArray(c,
                            pinyinOutputFormat);
                    if (pinYin != null) {
                        pinYinName.append(pinYin[0]);
                        firstPin.append(pinYin[0].charAt(0));
                    } else {
                        pinYinName.append(c);
                        firstPin.append(c);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    throw new RuntimeException(badHanyuPinyinOutputFormatCombination);
                }
            }
        }

        PinYin pinYin = new PinYin();
        pinYin.firstPinYinString = firstPin.toString();
        pinYin.fullPinYin = pinYinName.toString();

        return pinYin;
    }

    /**
     * 取大写首字母
     *
     * @param name
     * @return
     */
    public static String getFirstCapitalPin(String name) {
        StringBuilder firstPin = new StringBuilder();
        if (name != null) {
            char c = name.charAt(0);
            String[] pinYin;
            try {
                pinYin = PinyinHelper.toHanyuPinyinStringArray(c,
                        pinyinOutputFormat);
                if (pinYin != null) {
                    firstPin.append(pinYin[0].charAt(0));
                } else {
                    firstPin.append(c);
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                throw new RuntimeException(badHanyuPinyinOutputFormatCombination);
            }
        }

        return firstPin.toString()
                .toUpperCase();
    }

    /**
     * separateThenJoinBy(11532,%) -> 1%1%5%3%2
     *
     * @param key       要间隔的字符串
     * @param character 字符
     * @return
     * @since v3.2.4
     */
    public static String separateThenJoinBy(String key, String character) {

        if (StringUtils.isBlank(key)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            sb.append(key.toCharArray()[i]);
            if (i < key.length() - 1) {
                sb.append(character);
            }
        }

        return sb.toString();
    }

    public static String randomPwd(int len) {
        String pwd = "";
        for (int i = 0; i < len; i++) {
            pwd += (int) (Math.random() * 10);
        }
        return pwd;
    }


    public static String replaceSpecialCharWithUnderline(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters  must be replace
            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&' || c == ';' || c == '/'
                    || c == '$' || c == '%'
                    || Character.isWhitespace(c)) {
                sb.append("_");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 高亮命中的关键字
     *
     * @param content
     * @param key
     * @param color
     * @return
     * @since v4.2.0
     */
    public static String  highlightKeyWords(String content, String key, String color) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(content))
            return content;

        if (content.contains(key))
            return StringUtils.replace(content,
                    key,
                    getHtmlContent(key,
                            color));

        return content;
    }

    /**
     * 高亮命中的关键字，不区分大小写
     *
     * @param content
     * @param key
     * @param color
     * @return
     */
    public static String highlightKeyWordsIgnoreCase(String content, String key, String color) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(content))
            return content;

        //TODO 正则表达式改造，目前只支持标红第一个匹配中的串
        Integer start = content.toLowerCase().indexOf(key.toLowerCase());
        Integer keyLength = key.length();
        String heightKey = content.substring(start, start + keyLength);

        if (content.toLowerCase().contains(key.toLowerCase()))
            return StringUtils.replace(content,
                    heightKey,
                    getHtmlContent(heightKey,
                            color));

        return content;
    }

    /**
     * @param content
     * @param color
     * @return
     * @since v3.4.0
     */
    public static String getHtmlContent(String content, String color) {
        return "<font color='" + color + "'>" + content + "</font>";
    }

    /**
     * 将驼峰式命名的字符串转换为下划线小写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->hello_world
     * 例如：ABS->abs
     *
     * @param camelName 转换前的驼峰式命名的字符串
     * @return 转换后下划线小写方式命名的字符串
     * @since v6.0.0
     */
    public static String underscoreName(String camelName) {
        StringBuilder result = new StringBuilder();
        // 无内容
        if (StringUtils.isBlank(camelName)) {
            return result.toString();
        }

        // 特殊处理全大写
        if (StringUtils.isAllUpperCase(camelName)) {
            return StringUtils.lowerCase(camelName);
        }

        // 将第一个字符处理成大写
        result.append(camelName.substring(0,
                1)
                .toLowerCase());
        // 循环处理其余字符
        for (int i = 1; i < camelName.length(); i++) {
            String s = camelName.substring(i,
                    i + 1);
            // 在大写字母前添加下划线
            if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                result.append("_");
            }
            // 其他字符直接转成小写
            result.append(s.toLowerCase());
        }

        return result.toString();
    }

    /**
     * 将下划线小写方式命名的字符串转换为驼峰式。如果转换前的下划线小写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world->helloWorld
     *
     * @param underscoreName 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     * @since v6.0.0
     */
    public static String camelName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (underscoreName == null || underscoreName.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!underscoreName.contains("_")) {
            // 不含下划线，仅将首字母小写
            return underscoreName.substring(0, 1)
                    .toLowerCase() + underscoreName.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = underscoreName.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0,
                        1)
                        .toUpperCase());
                result.append(camel.substring(1)
                        .toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 将以英文逗号分隔的字符串（数字）转为Set集合
     *
     * @param string
     * @return
     */
    public static Set<Long> toSet(String string){
        Set<Long> set = null;
        if(StringUtils.isNotBlank(string)){
            set = Stream.of(string.split(","))
                    .map(s -> Long.parseLong(s.trim()))
                    .collect(Collectors.toSet());
        }else {
            set = new HashSet<>();
        }
        return set;
    }

    /**
     * 如果原始字符串（以英文逗号分隔的）中没有指定的字符则附加上
     *
     * @param originalStr
     * @param appendedStr
     * @return
     */
    public static String appendIfAbsent(String originalStr, String appendedStr){
        String string = originalStr;
        if (StringUtils.isNotBlank(originalStr)) {
            List<String> list = Arrays.asList(originalStr.split(","));
            if (!list.contains(appendedStr)) {
                string += "," + appendedStr;
            }
        }
        return string;
    }

    /**
     * 随机生成22位字符串，用于oss文件命名
     *
     * @return
     */
    public static String getRandomStr() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 22; i++) {
            int number = random.nextInt(ALL_LETTER_AND_NUMBER.length());
            sb.append(ALL_LETTER_AND_NUMBER.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 去掉字符串前后空格，含半角/全角 空格
     * @param str
     * @return
     */
    public static String trim(String str){
        if (StringUtils.isBlank(str)){
            return null;
        }

        return CharMatcher.WHITESPACE.trimFrom(str);
    }

    public static String toString(Object object) {
        return object == null ? null : object.toString();
    }
}
