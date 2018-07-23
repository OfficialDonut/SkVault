package us._donut_.skvault;

import java.util.stream.Stream;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.*;

public class Util {

    public static void setRequiredPlugins(SkriptEventInfo skriptEventInfo) {
        if (Skript.methodExists(SkriptEventInfo.class, "requiredPlugins"))
            skriptEventInfo.requiredPlugins("Vault");
    }


    //Skript literal utils
    @SuppressWarnings("unchecked")
    public static <T> Expression<T> defendExpression(Expression<?> expr) {
        if (expr instanceof ExpressionList) {
            Expression<?>[] exprs = ((ExpressionList) expr).getExpressions();
            for (int i = 0; i < exprs.length; i++)
                exprs[i] = defendExpression(exprs[i]);
        } else if (expr instanceof UnparsedLiteral) {
            Literal<?> parsedLiteral = ((UnparsedLiteral) expr).getConvertedExpression(Object.class);
            return (Expression<T>) (parsedLiteral == null ? expr : parsedLiteral);
        }
        return (Expression<T>) expr;
    }

    private static boolean hasUnparsedLiteral(Expression<?> expr) {
        if (expr instanceof UnparsedLiteral) {
            return true;
        } else if (expr instanceof ExpressionList) {
            return Stream.of(((ExpressionList) expr).getExpressions())
                    .anyMatch(UnparsedLiteral.class::isInstance);
        }
        return false;
    }

    public static boolean canInitSafely(Expression<?>... expressions) {
        for (int i = 0; i < expressions.length; i++) {
            if (expressions[i] == null || hasUnparsedLiteral(expressions[i])) {
                return false;
            }
        }
        return true;
    }
}