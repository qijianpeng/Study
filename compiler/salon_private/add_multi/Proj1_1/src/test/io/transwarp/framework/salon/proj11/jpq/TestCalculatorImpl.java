package io.transwarp.framework.salon.proj11.jpq;

import io.transwarp.framework.salon.proj11.Calculator;
import io.transwarp.framework.salon.proj11.Result;
import org.junit.Test;

/**
 * Created by jianpeng.qi on 18-8-16.
 * e: jianpeng.qi@transwarp.io
 */
public class TestCalculatorImpl {

    @Test
    public void testCalculator(){
        Calculator calculator = new CalculatorImpl();
        String[] exps = {
                "01  ",
                "01+02",
                "(64 *   1   )   + 0*1",
                "01+02",
                "((1+1))"
        };
        for (String exp : exps) {
            Result res = calculator.calculate(exp);
            System.out.println(res);
        }
    }
}
