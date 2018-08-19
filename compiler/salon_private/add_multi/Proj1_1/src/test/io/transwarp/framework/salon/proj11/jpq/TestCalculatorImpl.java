package io.transwarp.framework.salon.proj11.jpq;

import io.transwarp.framework.salon.proj11.Calculator;
import io.transwarp.framework.salon.proj11.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.*;

/**
 * Created by jianpeng.qi on 18-8-16.
 * e: jianpeng.qi@transwarp.io
 */
public class TestCalculatorImpl {

    Calculator calculator;
    @Before
    public void setUp(){
        calculator = new CalculatorImpl();
    }
    @Test
    @Category(Ensure.class)
    public void testCalculatorHuge(){
        try {
            File file = new File("./data/huge.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String exp = reader.readLine();
            Result res = calculator.calculate(exp);
            System.out.println(res);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    @Category(Ensure.class)
    public void testCalculatorSimple(){
        try {
            File file = new File("./data/simple.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String exp;
            while ((exp  = reader.readLine()) != null) {
                Result res = calculator.calculate(exp);
                System.out.println(res);
            }
        }catch (Exception e){

        }
    }

    @Test
    public void testCalcuatorStright(){
        String exp = "(71+(((((21)+ 0   *(21)+ 0)   *1   )+ 0    *    1      *(((21)+ 0   *(21)+ 0)   *1   )+ 0    *    1  *  (((21)+ 0   *(21)+ 0)   *1   )+ 0    *    1      *(((21)+ 0   *(21)+ 0)   *1   )+ 0    *    1  )  *1   + 0*(36)+ 0*   1 )    *   1 + 0)";


        Result res = calculator.calculate(exp);
        System.out.println(res);
    }

    @Test
    public void testError(){
        try {
            File file = new File("./data/error.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String exp;
            while ((exp  = reader.readLine()) != null) {
                Result res = calculator.calculate(exp);
                System.out.println(res);
            }
        }catch (Exception e){

        }
    }
}
