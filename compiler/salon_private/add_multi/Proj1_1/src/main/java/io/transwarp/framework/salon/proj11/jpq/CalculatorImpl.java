package io.transwarp.framework.salon.proj11.jpq;

import io.transwarp.framework.salon.proj11.Calculator;
import io.transwarp.framework.salon.proj11.Result;
import io.transwarp.framework.salon.proj11.jpq.err.ErrorSets;
import io.transwarp.framework.salon.proj11.jpq.rule.SemanticParser;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.parser.TokenParser;

import java.io.Serializable;
import java.util.Deque;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class CalculatorImpl extends Calculator implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public Result calculate(String exp) {
        ErrorSets.clear();
        Deque<Token> tokens = TokenParser.tokenize(exp);
        if (ErrorSets.hasError()) {
            return new Result(Integer.MIN_VALUE, true);
        }
        SemanticParser semanticParser = new SemanticParser();
        Result result = semanticParser.execute(tokens);
        if (ErrorSets.hasError()){
            result = new Result(Integer.MIN_VALUE, true);
        }
        return result;
    }

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();
        String[] exps = {
        "(1+(22))*3"
        };
        for (String exp : exps) {
            System.out.println(exp);
            Result res = calculator.calculate(exp);
            if (!res.hasCompileError()) {
                System.out.println(res.getValue());
            } else {
                ErrorSets.printErrors();
            }
            ErrorSets.clear();
        }
    }
}
