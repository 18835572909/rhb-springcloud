package com.rhb.scaffold.imp;

import com.rhb.scaffold.annotation.EnableMultiRabbit;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author: rhb
 * @date: 2023/11/2 20:55
 * @description:
 */
public class MultiRabbitImport implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotationAttributeMap = importingClassMetadata.getAnnotationAttributes(EnableMultiRabbit.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationAttributeMap);

        boolean aBoolean = annotationAttributes.getBoolean("isOpen");
        if(aBoolean){
            return new String[]{ImportPlay.class.getName()};
        }

        return new String[0];
    }


}
