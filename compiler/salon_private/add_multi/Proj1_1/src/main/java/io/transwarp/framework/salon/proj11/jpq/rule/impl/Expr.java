package io.transwarp.framework.salon.proj11.jpq.rule.impl;

import io.transwarp.framework.salon.proj11.jpq.err.SemanticException;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.Expression;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.TerminalExpression;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.parser.token.impl.AddToken;
import io.transwarp.framework.salon.proj11.jpq.rule.Rule;

import java.util.Deque;

/**
 * Created by jianpeng.qi on 18-8-13.
 * e: jianpeng.qi@transwarp.io
 *
 *
 *
 * EXP: TERM (TOK_ADD EXP)*
 */
public class Expr implements Rule {
    Term termRule = null;
    public void setTermRule(Term termRule){
        this.termRule = termRule;
    }
    public Term getTermRule(){
        return termRule;
    }
    @Override
    public Expression execute(Deque<Token> tokensQueue) throws SemanticException {
        TerminalExpression term = (TerminalExpression) termRule.execute(tokensQueue);
        while ( !tokensQueue.isEmpty()) {//TOK_ADD EXP
            Token token = tokensQueue.peek();
            if (token instanceof AddToken) {
                tokensQueue.pop();
                Expression right = execute(tokensQueue);
                term = (TerminalExpression) (((AddToken) token).getOperator()).execute(term, right);
            }else{
                break;
            }
        }
        return term;
    }
}
