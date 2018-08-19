package io.transwarp.framework.salon.proj11.jpq;

import java.io.Serializable;
import java.util.Deque;

/**
 * Created by qijianpeng on 2018/8/19.
 * mail: jianpengqi@126.com
 */
public class Utils implements Serializable {
    private static final long serialVersionUID = 1L;

    public static String queueToString(Deque deque){
        if (null == deque)return "";
        StringBuilder sb = new StringBuilder();
        for (Object obj : deque){
            sb.append(obj.toString());
        }
        return sb.toString();
    }
}
