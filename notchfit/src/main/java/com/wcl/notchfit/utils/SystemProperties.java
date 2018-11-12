package com.wcl.notchfit.utils;

import java.lang.reflect.Method;

public final class SystemProperties {
       public static String get(String key) { 
              String value = ""; 
              Class<?> cls = null; 
              try { 
                   cls = Class.forName("android.os.SystemProperties"); 
                   Method hideMethod = cls.getMethod("get", String.class);
                   Object object = cls.newInstance(); 
                   value = (String) hideMethod.invoke(object, key); 
            } catch (Exception e) {
                  LogUtils.e("get SystemProperties error: " + e.getMessage());
            }
            return value; 
      } 
}