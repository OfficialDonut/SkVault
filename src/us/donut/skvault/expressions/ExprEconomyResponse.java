package us.donut.skvault.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprEconomyResponse extends SimpleExpression<Object> {

    static {
        Skript.registerExpression(ExprEconomyResponse.class, Object.class, ExpressionType.COMBINED,
                "[a] [new] eco[nomy] response [with] [amount [modified]] %number%, [new] [bal[ance]] %number%, [[response] type] (0¦success|1¦failure|2¦not implemented), [and] [error [message]] %string% [to [the] [vault] request]");
    }

    private Expression<Number> amountModified;
    private Expression<Number> newBalance;
    private Expression<String> errorMessage;
    private EconomyResponse.ResponseType responseType;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        amountModified = (Expression<Number>) exprs[0];
        newBalance = (Expression<Number>) exprs[1];
        errorMessage = (Expression<String>) exprs[2];
        switch (parseResult.mark) {
            case 0:
                responseType = EconomyResponse.ResponseType.SUCCESS;
                break;
            case 1:
                responseType = EconomyResponse.ResponseType.FAILURE;
                break;
            case 2:
                responseType = EconomyResponse.ResponseType.NOT_IMPLEMENTED;
                break;
        }
        return true;
    }

    @Override
    protected Object[] get(Event e) {
        Number amountModified = this.amountModified.getSingle(e);
        Number newBalance = this.newBalance.getSingle(e);
        if (amountModified == null || newBalance == null) {
            return null;
        }
        return new Object[]{new EconomyResponse(amountModified.doubleValue(), newBalance.doubleValue(), responseType, errorMessage.getSingle(e))};
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "economy response";
    }
}
