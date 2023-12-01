package com.rhb.scaffold.annotation;

import com.rhb.scaffold.imp.MultiRabbitImport;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: rhb
 * @date: 2023/11/2 20:53
 * @description:
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value={MultiRabbitImport.class})
public @interface EnableMultiRabbit {

    boolean isOpen() default false;

    String[] basePackages();

}
