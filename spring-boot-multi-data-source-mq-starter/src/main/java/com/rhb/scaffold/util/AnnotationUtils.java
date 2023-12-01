package com.rhb.scaffold.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author: rhb
 * @date: 2023/11/7 15:04
 * @description:
 */
public class AnnotationUtils {

    @SuppressWarnings("all")
   public static void setFieldValue(Annotation annotation,String fieldName,Object fieldValue){
       InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
       try {
           Class<? extends InvocationHandler> clz = invocationHandler.getClass();
           Field field = clz.getDeclaredField("memberValues");
           field.setAccessible(true);
           Map memberValues = (Map)field.get(invocationHandler);
           memberValues.put(fieldName,fieldValue);
           field.set(invocationHandler,memberValues);
       } catch (ReflectiveOperationException e) {
           e.printStackTrace();
       }
   }

}
