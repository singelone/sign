package com.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UTCUtil {
	// 取得本地时间：
    private Calendar cal = Calendar.getInstance();
    // 取得时间偏移量：
    private int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
    // 取得夏令时差：
    private int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

    public static void main(String[] args) {
    	Long utc = 1498181958L*1000;
    	Date udate = new Date(utc);
    	Long beijing = 1498181958L*1000+8*60*60*1000L;
    	Date bdate = new Date(beijing);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time = sdf.format(udate);
    	String times = sdf.format(bdate);
    	System.out.println(time);
    	System.out.println(times);

    }

    public long getUTCTimeStr(Long mils) {

        System.out.println("local millis = " + cal.getTimeInMillis()); // 等效System.currentTimeMillis() , 统一值，不分时区

        // 从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        long mills = cal.getTimeInMillis();
        System.out.println("UTC = " + mills);

        return mills;
    }

    public void setUTCTime(long millis) {

        cal.setTimeInMillis(millis);

        SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = foo.format(cal.getTime());
        System.out.println("GMT time= " + time);

        // 从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, (zoneOffset + dstOffset));
        time = foo.format(cal.getTime());
        System.out.println("Local time = " + time);

    }

    public void getGMTTime() {
        //mothed 2
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(gmtTime);
        System.out.println("GMT Time: " + format.format(date));
        
        //method 2
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeZone(gmtTime);
        //System.out.println(calendar1.getTime());    //时区无效
        //System.out.println(calendar1.getTimeInMillis()); //时区无效
        System.out.println("GMT hour = " + calendar1.get(Calendar.HOUR));
    }
}
