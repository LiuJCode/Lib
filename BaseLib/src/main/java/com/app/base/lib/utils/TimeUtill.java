package com.app.base.lib.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeUtill {
    private static final String TAG = "TimeUtill";
    public static final String DATETIMENOY = "MM-dd HH:mm:ss";
    public static final String DATETIMENOY2 = "MM-dd HH:mm";

    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME2 = "yyyy-MM-dd HH:mm";
    public static final String DATE = "yyyy-MM-dd";
    public static final String DATEMM = "yyyy-MM-dd HH";
    private static final String TIME = "HH:mm:dd";
    public static final String TIMEHHMM = "HH:mm";
    private static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    private static final String HOUR = "HH";
    private static final String MINUTE = "mm";
    private static final String SEC = "ss";
    public static final String DATETIMECHINESE = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATECHINESE = "yyyy年MM月dd日";
    public static final String SIMPLEDATECHINESE = "MM月dd日";
    public static final String SIMPLEDATECHINESE1 = "MM月dd日 HH:mm";





    /**
     * @param latTime 前一刻的时间戳 单位 秒
     * @return XX前
     */
    public static String getTimeAgo(long latTime) {
        long currentTime = System.currentTimeMillis() / 1000;
        long ago = currentTime - latTime;
        if (ago / 60 < 1) {
            return ago + "秒前";
        }
        if (ago / (60 * 60) < 1) {
            return ago / 60 + "分钟前";
        }
        if (ago / (60 * 60 * 24) < 1) {
            return ago / 3600 + "小时前";
        }
        return ago / (3600 * 21) + "天前";
    }


    /**
     * 格式时间 如61秒—->01:01
     *
     * @param time 秒
     * @return
     */
    public static String formatTime(int time) {
        String minValue;
        String secValue;
        //获取分钟
        int minute = time / 60;
        if (minute < 10) {
            minValue = "0" + minute;
        } else {
            minValue = minute + "";
        }
        //获取秒
        int second = time % 60;
        if (second < 10) {
            secValue = "0" + second;
        } else {
            secValue = second + "";
        }

        return minValue + ":" + secValue;
    }

    /**
     * 返回时间毫秒数指定格式字符串
     * 超出10位数的  不需要乘以1000
     *
     * @param timeLong
     * @param formats
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String TimeStamp2Date(Long timeLong, String formats) {
        Long timestamp = timeLong;// timeLong*1000;
        String date = new SimpleDateFormat(formats).format(new Date(timestamp));
        return date;
    }

    // 获取当前年月日
    public static String getDateTime() {
        return new SimpleDateFormat(DATETIME).format(new Date());
    }

    // 获取当前年月日
    public static String getYearMD() {
        return new SimpleDateFormat(DATE).format(new Date());
    }

    //获取今天 明天 后天 昨天 前天 左右的年与日
    // type 1 为向后推  2为向前推
    public static String getymd(int type, int day) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (type == 1) {
            calendar.add(calendar.DATE, +day);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        } else {
            calendar.add(calendar.DATE, -day);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        }
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE);
        String dateString = formatter.format(date);
        return dateString;
    }

    // 获取当前年
    public static String getYear() {
        return new SimpleDateFormat(YEAR).format(new Date());
    }

    // 获取当前月
    public static String getMonth() {
        return new SimpleDateFormat(MONTH).format(new Date());
    }

    // 获取当前日
    public static String getDay() {
        return new SimpleDateFormat(DAY).format(new Date());
    }

    // 获取当前时
    public static String getHour() {
        return new SimpleDateFormat(HOUR).format(new Date());
    }

    // 获取当前分
    public static String getMinute() {
        return new SimpleDateFormat(MINUTE).format(new Date());
    }

    // 获取当前秒
    public static String getSec() {
        return new SimpleDateFormat(SEC).format(new Date());
    }


    /**
     * 时间戳 转为字符串日期
     *
     * @author LIUJING
     * @created at 2016/11/18 11:23
     */
    public static String getDateFor(String time, String Dateformat) {
        if (ToolsUtil.isNotNull(time)) {
            String timeStr = "";
            if (time.length() > 10) {
                timeStr = TimeStamp(Long.parseLong(time.replace(" ","")), Dateformat);
            } else {
                timeStr = TimeStamptwo(Long.parseLong(time.replace(" ","")), Dateformat);
            }
            return timeStr;
        }
        return "";
    }

    /**
     * @param timeLong
     * @param formats
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String TimeStamp(Long timeLong, String formats) {
        Long timestamp = timeLong;
        String date = new SimpleDateFormat(formats).format(new Date(timestamp));
        return date;
    }

    /**
     * @param timeLong
     * @param formats
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String TimeStamptwo(Long timeLong, String formats) {
        Long timestamp = timeLong * 1000;
        String date = new SimpleDateFormat(formats).format(new Date(timestamp));
        return date;
    }


    /**
     * 时间错转为日期时间
     *
     * @param time
     * @return
     */
    public static String getDAYforDATETIME(String time) {
        String timeStr = "";
        timeStr = TimeUtill.getDateFor(time, DATETIME);
        return timeStr;
    }

    /**
     * 字符串 转为 时间戳
     *
     * @author LIUJING
     * @created at 2016/11/18 11:21
     * DateFormat ："yyyy-MM-dd" 字符串格式
     */
    public static String getStringToDateNoTime(String time, String DateFormat) {
        SimpleDateFormat sdr = new SimpleDateFormat(DateFormat,
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
            LogFactory.createLog(TAG).v(times);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    /**
     * 获取当前时间戳
     *
     * @author LIUJING
     * @created at 2016/8/23 22:55
     */
    public static long getDateint() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前日期 不包含时分秒
     *
     * @author LIUJING
     * @created at 2016/11/18 11:20
     */
    public static String getteday() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当前日期时间 时分秒
     *
     * @author LIUJING
     * @created at 2016/11/18 11:20
     */
    public static String gettedaydetial() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取2个时间差
     *
     * @author LIUJING
     * @created at 2016/11/18 11:19
     * time  时间戳
     */
    public static String getDatebetw(String time) {
        String data;
        if (ToolsUtil.isNotNull(time)) {
            try {
                if(time.equals("0")){
                    return "未知";
                }
                data = getDAYforDATETIME(time);
                DateFormat df = new SimpleDateFormat(DATETIME);
                Date d1 = df.parse(data);
                Date d2 = df.parse(gettedaydetial());
                long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
                long days = diff / (1000 * 60 * 60 * 24);
                long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                if (Math.abs(days) > 0) {
                    return "" + Math.abs(days) + "天前";
                }
                if (Math.abs(hours) > 0) {
                    return Math.abs(hours) + "小时前";
                }
                if (Math.abs(minutes) > 0) {
                    return Math.abs(minutes) + "分钟前";
                } else {
                    return "刚刚";//1 + "分钟前";
                }
            } catch (Exception e) {
                LogFactory.createLog(TAG).v(e.getMessage());
            }
            return time;
        }
        return "";
    }

    /**
     * 2个日期之间间隔
     *
     * @author LIUJING
     * @created at 2016/11/18 11:19
     * time  时间戳
     */
    public static int getGapCount(String tempstartDate, String tempendDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(tempstartDate);
            endDate = sdf.parse(tempendDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }


    /**
     * 获取当天是星期几
     *
     * @author LIUJING
     * @created at 2016/11/18 11:19
     */
    public static String getweek() {
        String week = "";
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                week = "星期日";
                System.out.println("星期日");
                break;
            case 2:
                week = "星期一";
                System.out.println("星期一");
                break;
            case 3:
                week = "星期二";
                System.out.println("星期二");
                break;
            case 4:
                week = "星期三";
                System.out.println("星期三");
                break;
            case 5:
                week = "星期四";
                System.out.println("星期四");
                break;
            case 6:
                week = "星期五";
                System.out.println("星期五");
                break;
            case 7:
                week = "星期六";
                System.out.println("星期六");
                break;
        }
        return week;
    }

    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static String getAstro(Date date) {
        Calendar cal = Calendar.getInstance();
        String[] astro = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座",
                "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
        int[] arr = new int[]{ 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};// 两个星座分割日
                              //20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22
        int month = 0;
        int day = 0;
        cal.setTime(date);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        /*int index = month;
        // 所查询日期在分割日之前，索引-1，否则不变
        if (day < arr[month + 1]) {
            index = index + 1;
        }
        // 返回索引指向的星座string
        return astro[index];*/
        return day < arr[month ] ? astro[month]
                : astro[month+1];
    }

}
