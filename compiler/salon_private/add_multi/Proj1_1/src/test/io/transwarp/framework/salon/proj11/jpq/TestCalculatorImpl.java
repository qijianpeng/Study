package io.transwarp.framework.salon.proj11.jpq;

import io.transwarp.framework.salon.proj11.Calculator;
import io.transwarp.framework.salon.proj11.Result;
import org.junit.Test;

import java.io.*;

/**
 * Created by jianpeng.qi on 18-8-16.
 * e: jianpeng.qi@transwarp.io
 */
public class TestCalculatorImpl {

    @Test
    public void testCalculator(){
        try {
            Calculator calculator = new CalculatorImpl();
            File file = new File("./data/huge.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String exp = reader.readLine();
            System.out.println(exp);
            Result res = calculator.calculate(exp);
            System.out.println(res);
        }catch (Exception e){

        }
    }
}
