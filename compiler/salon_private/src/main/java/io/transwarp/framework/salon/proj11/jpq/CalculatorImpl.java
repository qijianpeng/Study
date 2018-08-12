package io.transwarp.framework.salon.proj11.jpq;

import io.transwarp.framework.salon.proj11.jpq.err.ErrorSets;
import io.transwarp.framework.salon.proj11.jpq.operator.ExpressionExecutor;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.parser.TokenParser;

import java.io.Serializable;
import java.util.Collection;
import java.util.Deque;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class CalculatorImpl implements Calculator, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public Result calculate(String exp) {
        ErrorSets.clear();
        Deque<Token> tokens = TokenParser.tokenize(exp);
        if (ErrorSets.hasError()) {
            return new Result(Integer.MIN_VALUE, false);
        }
        ExpressionExecutor expressionExecutor = new ExpressionExecutor();
        Result result = expressionExecutor.execute(tokens);
        if (ErrorSets.hasError()){
            result.status = false;
        }
        return result;
    }

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();
        String[] exps = {
        "ฅ'ω'ฅ + 3 * test"
        };
        for (String exp : exps) {
            System.out.println(exp);
            Result res = calculator.calculate(exp);
            if (res.status) {
                System.out.println(res.value);
            } else {
                ErrorSets.printErrors();
            }
            ErrorSets.clear();
        }
    }
}
