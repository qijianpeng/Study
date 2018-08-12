package io.transwarp.framework.salon.proj11.jpq.err;

import java.io.Serializable;
import java.util.*;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class ErrorSets implements Serializable {
    private static final long serialVersionUID = 1L;
    private static List<Exception> exceptionSet = new ArrayList<>();

    public static boolean hasError(){
        return exceptionSet.size() != 0;
    }
    public static void putError(Exception exception){
        exceptionSet.add(exception);
    }
    public static void printErrors(){
        if (hasError()){
            Iterator<Exception> iterator = exceptionSet.iterator();
            while (iterator.hasNext()){
                Exception exception = iterator.next();
                System.err.println(exception.getMessage());
            }
        }
    }
    public static void clear(){
        exceptionSet.clear();
    }
}
