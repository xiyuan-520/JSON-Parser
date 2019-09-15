package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.JsonUtil;
import com.xiyuan.util.json.Token;

/**
 * 日期解析器：
 * 1、优先sql.Date,sql.Time,sql.Timestamp
 * 2、其次Date,Calendar
 *
 * @version v1.0.0 @author lgz 2019年8月31日 新建与整理
 *
 */
public final class DateParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public String toString(Object obj)
    {
        if (obj == null)
            return null;
        
        if (obj instanceof java.sql.Date)
        {
            java.sql.Date date = (java.sql.Date) obj;
            return JsonUtil.DB_QUOTE + toDateString(date) + JsonUtil.DB_QUOTE;
        }
        else if (obj instanceof java.sql.Time)
        {
            java.sql.Time time = (java.sql.Time) obj;
            return JsonUtil.DB_QUOTE + toTimeString(time) + JsonUtil.DB_QUOTE;
        }
        else if (obj instanceof java.sql.Timestamp)
        {
            java.sql.Timestamp time = (java.sql.Timestamp) obj;
            return JsonUtil.DB_QUOTE + toDateTimeString(time) + JsonUtil.DB_QUOTE;
        }
        else if (obj instanceof Date)
        {
            Date date = (Date) obj;
            return JsonUtil.DB_QUOTE + toDateTimeString(date) + JsonUtil.DB_QUOTE;
        }
        else if (obj instanceof Calendar)
        {
            Calendar calendar = (Calendar) obj;
            return JsonUtil.DB_QUOTE + toDateTimeString(calendar) + JsonUtil.DB_QUOTE;
        }
        
        return null;
    }
    
    @Override
    public Object toObject(String json, Token token, Class<?> cls)
    {
        if (token == null || token.type() != JsonUtil.T_VALUE)
            return null;
        
        String value = JsonUtil.removeStartEndQuotation(token.toString(json));
        if (cls == java.sql.Date.class)
            return toDate(value);
        else if (cls == java.sql.Time.class)
            return toTime(value);
        else if (cls == java.sql.Timestamp.class)
            return toTimestamp(value);
        else if (cls == Date.class)
            return toDate(value);
        else if (cls == Calendar.class)
            return toCalendar(value);
        return null;
    }
    
}
