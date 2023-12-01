package com.rhb.shortUrl.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: rhb
 * @date: 2023/12/1 10:29
 * @description:
 */
@Getter
@AllArgsConstructor
public enum SysType {

    ORDER(0,"订单"),
    USER(1,"用户"),
    ;

    private int code;

    private String desc;

    public static SysType codeToEnum(int code){
        for (SysType type : values()){
            if(type.getCode()==code) return type;
        }
        return null;
    }

    public static SysType nameToEnum(String name){
        for (SysType type : values()){
            if(type.name().equals(name)) return type;
        }
        return null;
    }

}
