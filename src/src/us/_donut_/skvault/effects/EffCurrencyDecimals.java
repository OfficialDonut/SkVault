package us._donut_.skvault.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import us._donut_.skvault.CustomEconomy;

import javax.annotation.Nullable;

@Name("Vault Automatic Linking - Currency Decimal Places")
@Description("Sets currency decimal places for automatic Vault linking")
@RequiredPlugins("Vault")
@Since("1.0.5")
@Examples("set automatic vault linking currency decimals to 2")
public class EffCurrencyDecimals extends Effect {

    static {
        Skript.registerEffect(EffCurrencyDecimals.class, "set auto[matic] [vault] (linking|hooking) [currency] decimal[s] [places] to %number%");
    }

    private Expression<Number> currencyDecimals;

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        currencyDecimals = (Expression<Number>) e[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "set automatic vault linking currency decimals to " + currencyDecimals.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        CustomEconomy.currencyDecimals = currencyDecimals.getSingle(e).intValue();
    }
}
