package io.transwarp.framework.salon.proj11.jpq;
import io.transwarp.framework.salon.proj11.Calculator;
import io.transwarp.framework.salon.proj11.Result;
import io.transwarp.framework.salon.proj11.jpq.err.ErrorSets;
import io.transwarp.framework.salon.proj11.jpq.err.SemanticException;
import io.transwarp.framework.salon.proj11.jpq.err.TokenParseException;
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
    SemanticParser semanticParser = new SemanticParser();

    @Override
    public Result calculate(String exp) {

        try {
            Deque<Token> tokens = TokenParser.tokenize(exp);
            Result result = semanticParser.execute(tokens);
            return result;
        }catch (SemanticException semanticException){
            return new Result(Integer.MIN_VALUE, true);
        }catch (TokenParseException tokenParseException){
            return new Result(Integer.MIN_VALUE, true);
        }catch (NullPointerException nullPointer){
            return new Result(Integer.MIN_VALUE, true);
        }

    }

    public static void main(String[] args) {
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
