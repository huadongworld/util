package com.hd.util;

import com.hd.enums.TimeUnit;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author HuaDong
 * @date 2019/10/12 11:34
 */
public class DateUtil {

    public static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_PATTERN_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT_PATTERN = "HH:mm:ss";
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String SHORT_TIME_FORMAT_PATTERN = "HHmmss";
    public static final String SHORT_DATE_FORMAT_PATTERN = "yyyyMMdd";
    public static final String SQL_DATE_FORMAT_PATTERN = "yyyy/MM/dd";
    public static final String SQL_DATE_EX_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_BACKUP_FORMAT_PATTERN = "yyyyMMddHHmmss";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String SHORT_YEAR_DATE_FORMAT_PATTERN = "yyMMdd";
    public static final String DATE_DETAIL_FORMAT_PATTERN = "yyyyMMddHHmmssSSS";

    public static final DateFormat DATE_DETAIL_FORMAT = new SimpleDateFormat(DATE_DETAIL_FORMAT_PATTERN);
    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);
    public static final DateFormat DATE_TIME_FORMAT_NO_SECOND = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN_NO_SECOND);
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat(TIME_FORMAT_PATTERN);
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN);
    public static final DateFormat MONTH_FORMAT = new SimpleDateFormat(MONTH_PATTERN);
    public static final DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(SHORT_DATE_FORMAT_PATTERN);
    public static final DateFormat SHORT_TIME_FORMAT = new SimpleDateFormat(SHORT_TIME_FORMAT_PATTERN);
    public static final DateFormat SQL_DATE_FORMAT = new SimpleDateFormat(SQL_DATE_FORMAT_PATTERN);
    public static final DateFormat SQL_DATE_EX_FORMAT = new SimpleDateFormat(SQL_DATE_EX_FORMAT_PATTERN);
    public static final DateFormat DATE_BACKUP_FORMAT = new SimpleDateFormat(DATE_BACKUP_FORMAT_PATTERN);
    public static final DateFormat SHORT_YEAR_DATE_FORMAT = new SimpleDateFormat(SHORT_YEAR_DATE_FORMAT_PATTERN);

    private static final double DAY_DIVISOR = 1000 * 60 * 60 * 24 * 1.0;
    private static final double HOUR_DIVISOR = 1000 * 60 * 60 * 1.0;
    private static final double MINUTE_DIVISOR = 1000 * 60 * 1.0;
    private static final double SECOND_DIVISOR = 1000 * 1.0;

    /**
     * 获取时间戳，格式 yyyyMMddHHmmssSSS
     *
     * @return
     */
    public static String getTimeStamp(Date date) {
        return DATE_DETAIL_FORMAT.format(date);
    }

    /**
     * 获取时间戳，格式 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getSimpleTimeStamp(Date date) {
        return DATE_BACKUP_FORMAT.format(date);
    }

    /**
     * 获取当天所需格式的时间
     *
     * @param dateFormat
     * @return
     */
    public static String currentDateFormat(DateFormat dateFormat) {
        return dateFormat.format(new Date());
    }

    /**
     * 格式化所需时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {

        if (date != null && StringUtils.isNotBlank(format)) {
            try {
                return formatDate(date, new SimpleDateFormat(format));
            } catch (Exception e) {
                //ignore
            }
        }

        return null;
    }

    /**
     * 格式化所需时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, DateFormat format) {

        if (date != null && format != null) {
            try {
                return format.format(date);
            } catch (Exception e) {
                //ignore
            }
        }

        return null;
    }

    /**
     * 2019-12-01 23:43:98、2019/12/01 23:43:98、20191201234398格式日期转成date
     *
     * @param dateTimeString
     * @return
     */
    public static Date tryParseDateTime(String dateTimeString) {

        if (StringUtils.isBlank(dateTimeString)) {
            return null;
        }
        try {
            return DATE_TIME_FORMAT.parse(dateTimeString);
        } catch (ParseException e) {
            try {
                return SQL_DATE_EX_FORMAT.parse(dateTimeString);
            } catch (ParseException e2) {
                try {
                    return DATE_BACKUP_FORMAT.parse(dateTimeString);
                } catch (ParseException e1) {
                    //ignore
                }
            }
            return null;
        }
    }

    /**
     * 2019-12-01、2019/12/01、20191201格式日期转成date
     *
     * @param dateString
     * @return
     */
    public static Date tryParseDate(String dateString) {

        if (StringUtils.isBlank(dateString)) {
            return null;
        }

        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            try {
                return SQL_DATE_FORMAT.parse(dateString);
            } catch (ParseException e2) {
                try {
                    return SHORT_DATE_FORMAT.parse(dateString);
                } catch (ParseException e1) {
                    //ignore
                }
            }
            return null;
        }
    }

    /**
     * 2019-12-01、2019/12/01、20191201格式日期转成sql.date
     *
     * @param dateString
     * @return
     */
    public static java.sql.Date tryParseDateToSqlDate(String dateString) {

        Date date = tryParseDate(dateString);
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(
                Calendar.MILLISECOND,
                0
        );
        c.set(
                Calendar.SECOND,
                0
        );
        c.set(
                Calendar.MINUTE,
                0
        );
        c.set(
                Calendar.HOUR_OF_DAY,
                0
        );

        return new java.sql.Date(c.getTimeInMillis());
    }

    public static java.sql.Date parseSqlDate(
            String format,
            String dateString
    ) {
        try {
            Date date = new SimpleDateFormat(format).parse(dateString);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(
                    Calendar.MILLISECOND,
                    0
            );
            c.set(
                    Calendar.SECOND,
                    0
            );
            c.set(
                    Calendar.MINUTE,
                    0
            );
            c.set(
                    Calendar.HOUR_OF_DAY,
                    0
            );

            return new java.sql.Date(c.getTimeInMillis());

        } catch (Exception e) {
            return null;
        }
    }

    public static java.sql.Date parseSqlDate(String dateString) {
        java.sql.Date date = parseSqlDate(
                SQL_DATE_FORMAT_PATTERN,
                dateString
        );
        if (date == null) {
            try {
                return new java.sql.Date(
                        SQL_DATE_EX_FORMAT.parse(dateString).getTime());
            } catch (Exception e) {
                //ignore
            }
        }
        return date;
    }

    public static java.sql.Date getTodaySqlDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     * @param timeStr e.g. 1:0:5
     * @return if timeStr is 1:0:5,then retun 01:00:05
     */
    public static String formatAs24HoursFormat(String timeStr) {
        try {
            TIME_FORMAT.parse(timeStr);
            String[] timeArr = timeStr.split(":");
            Integer hour = Integer.parseInt(timeArr[0]);
            Integer min = Integer.parseInt(timeArr[1]);
            Integer sec = Integer.parseInt(timeArr[2]);
            if (hour < 0 || hour > 23) {
                return "";
            }

            if (min < 0 || min > 60) {
                return "";
            }

            if (sec < 0 || sec > 60) {
                return "";
            }

            return String.format(
                    "%s:%s:%s",
                    StringUtils.leftPad(
                            timeArr[0],
                            2,
                            '0'
                    ),
                    StringUtils.leftPad(
                            timeArr[1],
                            2,
                            '0'
                    ),
                    StringUtils.leftPad(
                            timeArr[2],
                            2,
                            '0'
                    )
            );
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param dateString e.g. 2015-1-1
     * @return 20150101
     */
    public static String format2ShortDate(String dateString) {
        try {
            Date date = DATE_FORMAT.parse(dateString);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            String year = c.get(Calendar.YEAR) + "";
            String month = (c.get(Calendar.MONTH) + 1) + "";
            String day = c.get(Calendar.DAY_OF_MONTH) + "";

            return StringUtils.leftPad(
                    year,
                    4,
                    '0'
            ) + StringUtils.leftPad(
                    month,
                    2,
                    '0'
            ) + StringUtils.leftPad(
                    day,
                    2,
                    '0'
            );
        } catch (Exception e) {
            return "";
        }

    }

    public static java.sql.Date toSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 指定日期所在月份的第一天：YYYY-mm-01 00:00:00:000
     *
     * @param date
     * @return
     */
    public static Date getBeginDayOfMonth(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(
                Calendar.DAY_OF_MONTH,
                c.getActualMinimum(Calendar.DAY_OF_MONTH)
        );
        c.set(
                Calendar.HOUR_OF_DAY,
                0
        );
        c.set(
                Calendar.MINUTE,
                0
        );
        c.set(
                Calendar.SECOND,
                0
        );
        c.set(
                Calendar.MILLISECOND,
                0
        );
        return c.getTime();
    }

    /**
     * 指定日期所在月份的最后一天：YYYY-mm-dd 23:59:59:999
     *
     * @param date
     * @return
     */
    public static Date getEndDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(
                Calendar.DAY_OF_MONTH,
                c.getActualMaximum(Calendar.DAY_OF_MONTH)
        );
        c.set(
                Calendar.HOUR_OF_DAY,
                23
        );
        c.set(
                Calendar.MINUTE,
                59
        );
        c.set(
                Calendar.SECOND,
                59
        );
        c.set(
                Calendar.MILLISECOND,
                999
        );
        return c.getTime();
    }

    /**
     * 指定日期开始时间：00:00:00:000
     *
     * @param date
     * @return
     */
    public static Date getBeginOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(
                Calendar.HOUR_OF_DAY,
                0
        );
        c.set(
                Calendar.MINUTE,
                0
        );
        c.set(
                Calendar.SECOND,
                0
        );
        c.set(
                Calendar.MILLISECOND,
                0
        );

        return c.getTime();
    }

    /**
     * 指定日期结束时间：23:59:59:999
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(
                Calendar.HOUR_OF_DAY,
                23
        );
        c.set(
                Calendar.MINUTE,
                59
        );
        c.set(
                Calendar.SECOND,
                59
        );
        c.set(
                Calendar.MILLISECOND,
                999
        );

        return c.getTime();
    }

    /**
     * 指定日期结束时间：23:59:59:000
     *
     * @param date
     * @return
     */
    public static Date getTheEndOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(
                Calendar.HOUR_OF_DAY,
                23
        );
        c.set(
                Calendar.MINUTE,
                59
        );
        c.set(
                Calendar.SECOND,
                59
        );
        c.set(
                Calendar.MILLISECOND,
                0
        );

        return c.getTime();
    }

    /**
     * 日期的加法
     *
     * @param date
     * @param unit
     * @return
     */
    public static Date plusDate(Date date, TimeUnit unit, int value) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (unit) {
            case DAYS:
                c.add(Calendar.DAY_OF_MONTH, value);
                break;
            case YEAR:
                c.add(Calendar.YEAR, value);
                break;
            case MONTH:
                c.add(Calendar.MONTH, value);
                break;
            default:
                break;
        }
        return c.getTime();
    }

    /**
     * 日期的加法
     *
     * @param date
     * @param unit
     * @return
     */
    public static Date minusDate(Date date, TimeUnit unit, int value) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (unit) {
            case DAYS:
                c.add(Calendar.DAY_OF_MONTH, -value);
                break;
            case YEAR:
                c.add(Calendar.YEAR, -value);
                break;
            case MONTH:
                c.add(Calendar.MONTH, -value);
                break;
            default:
                break;
        }
        return c.getTime();
    }

    /**
     * 按指定规则进行日期比较，只要格式化后的字符串相等则认为日期相等
     *
     * @param one
     * @param two
     * @param format 指定日期格式
     * @return
     */
    public static boolean equals(Date one, Date two, String format) {

        if (one == null && two == null) {
            return true;
        }

        if (one != null && two != null) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

            return simpleDateFormat.format(one).equals(simpleDateFormat.format(two));
        }

        return false;
    }

    /**
     * 计算两个时间相差的 年/月/日/时/分/秒数
     *
     * @param firstDate
     * @param secondDate
     * @param unit       枚举类 TimeUnit 对应的值 (无法依赖TimeUnit所在模块)
     * @return
     */
    public static Integer differ(Date firstDate, Date secondDate, TimeUnit unit) {

        Long firstTime = firstDate.getTime();
        Long secondTime = secondDate.getTime();

        switch (unit) {
            // 天
            case DAYS:
                return (int) (Math.floor(firstTime / DAY_DIVISOR) - Math.floor(secondTime / DAY_DIVISOR));
            // 时
            case HOUR:
                return (int) (Math.floor(firstTime / HOUR_DIVISOR) - Math.floor(secondTime / HOUR_DIVISOR));
            // 分
            case MINUTE:
                return (int) (Math.floor(firstTime / MINUTE_DIVISOR) - Math.floor(secondTime / MINUTE_DIVISOR));
            // 秒
            case SECOND:
                return (int) (Math.floor(firstTime / SECOND_DIVISOR) - Math.floor(secondTime / SECOND_DIVISOR));
            default:
                throw new RuntimeException("不支持的时间单位");
        }
    }
}
