package com.morrystore.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class Beans {

    /**
     * 将整个 list 中对象属性复制到另一个对象
     * @param <T>
     * @param <K>
     * @param source
     * @param target
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T,K> List<K> copy(List<T> source, Class<K> target) throws InstantiationException, IllegalAccessException {
        if(ArrayUtils.isEmpty(source)) {
            return new ArrayList<>();
        }
        List<K> result = new ArrayList<>();
        K entity = null;
        for(T record: source) {
            entity = target.newInstance();
            BeanUtils.copyProperties(record, entity);
            result.add(entity);
        }
        return result;
    }
}