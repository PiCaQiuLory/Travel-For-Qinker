package com.ss.utils;

import com.ss.pojo.vo.Message;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by stella on 2016/7/18.
 */
public class Util {


    public static String getFormattedDate(String formmatter, long date){
        Date time = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat(formmatter);
        return sdf.format(time);
    }


    public static Message createErrorMessage(String error){
        Message message = new Message();
        message.setCode(Constant.FAILURE);
        message.setValue(error);
        return message;
    }

    public static boolean isEmpty(String value){
        return value == null || value.trim().equals("");
    }


}

