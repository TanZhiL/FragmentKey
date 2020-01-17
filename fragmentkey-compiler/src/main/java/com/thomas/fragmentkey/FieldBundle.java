package com.thomas.fragmentkey;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.TypeElement;

/**
 * Author: Thomas.<br/>
 * Date: 2020/1/17 9:41<br/>
 * GitHub: https://github.com/TanZhiL<br/>
 * CSDN: https://blog.csdn.net/weixin_42703445<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:
 */
public class FieldBundle {
    /**
     * 原始变量名
     */
    private String originName;
    /**
     * 转换成参数名
     */
    private String name;
    /**
     * 变量类型
     */
    private TypeName type;
    /**
     * 宿主类
     */
    private TypeElement clazz;

    public FieldBundle(String originName, String name, TypeName type, TypeElement clazz) {
        this.originName = originName;
        this.name = name;
        this.type = type;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "FieldBundle{" +
                "originName='" + originName + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", clazz=" + clazz +
                '}';
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeName getType() {
        return type;
    }

    public void setType(TypeName type) {
        this.type = type;
    }

    public TypeElement getClazz() {
        return clazz;
    }

    public void setClazz(TypeElement clazz) {
        this.clazz = clazz;
    }
}
