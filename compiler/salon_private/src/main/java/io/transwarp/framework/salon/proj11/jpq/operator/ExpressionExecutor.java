package io.transwarp.framework.salon.proj11.jpq.operator;

import io.transwarp.framework.salon.proj11.jpq.Result;
import io.transwarp.framework.salon.proj11.jpq.err.ErrorSets;
import io.transwarp.framework.salon.proj11.jpq.err.SemanticException;
import io.transwarp.framework.salon.proj11.jpq.operator.impl.BinaryOperator;
import io.transwarp.framework.salon.proj11.jpq.operator.impl.Digit;
import io.transwarp.framework.salon.proj11.jpq.operator.impl.MultiOperation;
import io.transwarp.framework.salon.proj11.jpq.operator.impl.TerminalExpression;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.parser.token.impl.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class ExpressionExecutor implements Serializable {
    private static final long serialVersionUID = 1L;
    public Result execute(Deque<Token> tokens){

        Expression result = exp(tokens);
        if (null == result || !tokens.isEmpty() || ErrorSets.hasError()){
            return new Result(Integer.MIN_VALUE, false);
        }
        return new Result(Integer.valueOf(result.execute().toString()), true);
    }


    /**
     * EXP: TERM (TOK_ADD EXP)*
     TERM: FACTOR (TOK_MUL TERM)*
     FACTOR: TOK_EOF | TOK_NUMBER | TOK_LP EXP TOKRP
     */
    public Expression exp(Deque<Token>  tokens){
        TerminalExpression term = (TerminalExpression) term(tokens);
        while ( !tokens.isEmpty()) {//TOK_ADD EXP
            Token token = tokens.peek();
            if (token instanceof AddToken) {
                tokens.pop();
                Expression right = exp(tokens);
                term = (TerminalExpression) (((AddToken) token).getOperator()).execute(term, right);
            }else{
                break;
            }
        }
        return term;
    }

    //TERM: FACTOR (TOK_MUL TERM)*
    private Expression term(Deque<Token>  tokens){
        TerminalExpression factor = (TerminalExpression)factor(tokens);
        while (null != factor && !tokens.isEmpty()) {
            Token token = tokens.peek();
            if (token instanceof MultiToken) { //TOK_MUL TERM
                tokens.pop();
                TerminalExpression term = (TerminalExpression) term(tokens);
                factor = (TerminalExpression) ((MultiToken) token).getOperator().execute(factor, term);
            }else {
                break;
            }
        }
        return factor;
    }

    //FACTOR: TOK_EOF | TOK_NUMBER | TOK_LP EXP TOKRP
    private Expression factor(Deque<Token> tokens){
        //TOK_EOF
        Token token = tokens.peek();
        //TOK_NUMBER
        if (token instanceof NumberToken){
            tokens.pop();
            return new Digit(((NumberToken) token).getNumber());
        }else if (token instanceof LpToken){ //TOK_LP EXP TOK_RP
            //TOK_LP
            tokens.pop();
            Expression expression = exp(tokens);//EXP
            if (tokens.isEmpty()) {
                ErrorSets.putError(new SemanticException("Missing ')'."));
                return null;
            }
            Token rp = tokens.peek();
            if (rp instanceof RpToken) {//TOK_RP
                tokens.pop();
                return expression;
            }else {
                //error
                ErrorSets.putError(new SemanticException("Redundant value after " + tokens.peek().toString()));
            }
        }else {
            ErrorSets.putError(new SemanticException("Redundant value after " + tokens.peek().toString()));
            return null;
        }
        return null;
    }
}
