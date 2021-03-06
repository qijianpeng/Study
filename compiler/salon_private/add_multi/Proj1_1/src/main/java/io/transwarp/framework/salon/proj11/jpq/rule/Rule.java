package io.transwarp.framework.salon.proj11.jpq.rule;

import io.transwarp.framework.salon.proj11.jpq.err.SemanticException;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.Expression;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;

import java.util.Deque;

/**
 * Created by jianpeng.qi on 18-8-13.
 * e: jianpeng.qi@transwarp.io
 */
public interface Rule {
    public int execute(Deque<Token> tokensQueue) throws SemanticException;
}
