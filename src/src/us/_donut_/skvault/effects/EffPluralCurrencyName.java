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

@Name("Vault Automatic Linking - Plural Currency Name")
@Description("Sets plural currency name for automatic Vault linking")
@RequiredPlugins("Vault")
@Since("1.0.5")
@Examples("set automatic vault linking plural currency name to \"dollars\"")
public class EffPluralCurrencyName extends Effect {

    static {
        Skript.registerEffect(EffPluralCurrencyName.class, "set auto[matic] [vault] (linking|hooking) plural [currency] name to %string%");
    }

    private Expression<String> pluralCurrencyName;

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        pluralCurrencyName = (Expression<String>) e[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "set automatic vault linking plural currency name to " + pluralCurrencyName.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        CustomEconomy.pluralCurrencyName = pluralCurrencyName.getSingle(e);
    }
}
