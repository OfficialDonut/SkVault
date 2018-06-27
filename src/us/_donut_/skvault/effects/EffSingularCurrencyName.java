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

@Name("Vault Automatic Linking - Singular Currency Name")
@Description("Sets singular currency name for automatic Vault linking")
@RequiredPlugins("Vault")
@Since("1.0.5")
@Examples("set automatic vault linking singular currency name to \"dollar\"")
public class EffSingularCurrencyName extends Effect {

    static {
        Skript.registerEffect(EffSingularCurrencyName.class, "set auto[matic] [vault] (linking|hooking) singular [currency] name to %string%");
    }

    private Expression<String> singularCurrencyName;

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        singularCurrencyName = (Expression<String>) e[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "set automatic vault linking singular currency name to " + singularCurrencyName.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        CustomEconomy.singularCurrencyName = singularCurrencyName.getSingle(e);
    }
}
