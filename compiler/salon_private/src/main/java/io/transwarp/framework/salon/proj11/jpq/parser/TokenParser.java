package io.transwarp.framework.salon.proj11.jpq.parser;

import io.transwarp.framework.salon.proj11.jpq.err.ErrorSets;
import io.transwarp.framework.salon.proj11.jpq.err.TokenParseException;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.parser.token.impl.*;

import java.io.Serializable;
import java.text.StringCharacterIterator;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class TokenParser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * parse expression into queue
     * @param expression
     * @return
     */
    public static Deque<Token> tokenize(String expression){
        //1st in 1st out.
        Deque<Token> tokens = new ArrayDeque(expression.length());
        StringCharacterIterator itr = new StringCharacterIterator(expression);
        for (char c = itr.first(); c != StringCharacterIterator.DONE; c = itr.next()){
            if (c =='(') {
                tokens.offer(new LpToken(itr.getIndex()));
                continue;
            }
            if (c == ')') {
                tokens.offer(new RpToken(itr.getIndex()));
                continue;
            }
            if(c == '-' && !(tokens.peekLast() instanceof NumberToken)){//negative number
                int index = itr.getIndex();
                itr.next();
                int number = parseNumber(itr) * -1;
                ErrorSets.putError(new TokenParseException("Illegal negative number : " +
                        number + ", Position " + index));
                continue;
             }
             if (c == '+'){
                tokens.offer(new AddToken(itr.getIndex()));
             }
             if (Character.isDigit(c)) {
                int index = itr.getIndex();
                int number = parseNumber(itr);
                if (tokens.peekLast() instanceof NumberToken){
                    //illegal whitespace
                    ErrorSets.putError(new TokenParseException("Illegal whitespace : " +
                            number + ", Position " + index));
                 }
                tokens.offer(new NumberToken(number, index));
                continue;
             }
             if (c == '*') {
                 tokens.offer(new MultiToken(itr.getIndex()));
                 continue;
             }
             if ( c == ' ') {//valid whitespace
                 continue;
             }
             //illegal
            ErrorSets.putError(new TokenParseException("Illegal value " + c + ", Position " + itr.getIndex()));

        }
        return tokens;
    }

    private static int parseNumber(StringCharacterIterator itr) {
        int res = 0;
        for (char c = itr.current(); c != StringCharacterIterator.DONE; c = itr.next()){
            if (!Character.isDigit(c)){
                itr.previous();
                return res;
            }
            res *= 10;
            res = res + (c - '0');
        }
        return res;
    }
}
