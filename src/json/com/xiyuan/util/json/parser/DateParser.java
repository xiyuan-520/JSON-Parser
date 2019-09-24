package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;

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
    public DateParser(JsonLexer lexer)
    {
        super(lexer);
    }
    
    @Override
    public String toString(Object obj)
    {
        if (obj == null)
            return null;
        
        switch (obj.getClass().getName().hashCode())
        {
            case JsonLexer.SQL_DATE_CLS_HASH:
                return JsonLexer.DB_QUOTE + toDateString((java.sql.Date) obj) + JsonLexer.DB_QUOTE;
            case JsonLexer.SQL_TIME_CLS_HASH:
                return JsonLexer.DB_QUOTE + toTimeString((java.sql.Time) obj) + JsonLexer.DB_QUOTE;
            case JsonLexer.SQL_TIMESTAMP_CLS_HASH:
                return JsonLexer.DB_QUOTE + toDateTimeString((java.sql.Timestamp) obj) + JsonLexer.DB_QUOTE;
            case JsonLexer.DATE_CLS_HASH:
                return JsonLexer.DB_QUOTE + toDateTimeString((Date) obj) + JsonLexer.DB_QUOTE;
            case JsonLexer.CALENDAR_CLS_HASH:
                return JsonLexer.DB_QUOTE + toDateTimeString((Calendar) obj) + JsonLexer.DB_QUOTE;
            default:
                return null;
        }
    }
    
    @Override
    public Object toObject(Class<?> cls)
    {
//        if (!lexer.isString())
//            return null;
        
        String value = JsonLexer.removeStartEndQuotation(lexer.value());
        switch (cls.getName().hashCode())
        {
            case JsonLexer.SQL_DATE_CLS_HASH:
                return toDate(value);
            case JsonLexer.SQL_TIME_CLS_HASH:
                return toTime(value);
            case JsonLexer.SQL_TIMESTAMP_CLS_HASH:
                return  toTimestamp(value);
            case JsonLexer.DATE_CLS_HASH:
                return toDate(value);
            case JsonLexer.CALENDAR_CLS_HASH:
                return  toCalendar(value);
            default:
                return null;
        }
    }
    
}
