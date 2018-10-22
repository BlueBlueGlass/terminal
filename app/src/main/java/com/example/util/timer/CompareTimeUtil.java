package com.example.util.timer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/9/24.
 */

public class CompareTimeUtil {
    public static final String TIMEPICKER_TAG = "timepicker";
    public static boolean hourMinuteBetween(String nowDate, String startDate, String endDate) {
        long nowTime = 0;
        long startTime = 0;
        long endTime = 0;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        try {
            String tempDataTime = nowDate.substring(nowDate.indexOf(" ")+1,nowDate.lastIndexOf(":"));
            if(tempDataTime.charAt(0)=='0'){
                tempDataTime = tempDataTime.substring(1);
            }
            Date now = format.parse(tempDataTime);
            Log.e("datatime",tempDataTime);
            Date start = format.parse(startDate);
            Date end  = format.parse(endDate);
             nowTime = now.getTime();
            startTime = start.getTime();
            endTime = end.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(nowTime >startTime && nowTime < endTime){
            Log.e("aaa","在时间段里！！");
        }
        return nowTime > startTime && nowTime <endTime;
    }

    public static void  showTimeSelect(final Context context, android.support.v4.app.FragmentManager manager, final OnTimeSelectListenner listenner){
        final Calendar calendar = Calendar.getInstance();
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                listenner.onTimeSelected(hourOfDay + ":" + minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);
        timePickerDialog.setVibrate(true);
        timePickerDialog.setCloseOnSingleTapMinute(false);
        timePickerDialog.show(manager, TIMEPICKER_TAG);
    }

   public interface OnTimeSelectListenner{
        void onTimeSelected(String time);
    }

    public static boolean belongCalendar(String nowDate, String startDate, String endDate) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            String tempDataTime = nowDate.substring(nowDate.indexOf(" ")+1,nowDate.lastIndexOf(":"));
        Date time = null;
        Date from =null;
        Date to = null;
        try {
            time = format.parse(tempDataTime);
            Log.e("datatime",tempDataTime);
            Log.e("datatime",startDate);
            Log.e("datatime",endDate);
            from = format.parse(startDate);
             to  = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar date = Calendar.getInstance();
        date.setTime(time);

        Calendar after = Calendar.getInstance();
        after.setTime(from);

        Calendar before = Calendar.getInstance();
        before.setTime(to);

        if (date.after(after) && date.before(before)) {
            return true;
        } else {
            return false;
        }
    }

}
