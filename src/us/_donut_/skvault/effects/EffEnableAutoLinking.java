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

@Name("Vault Automatic Linking - Enable")
@Description("Enables automatic Vault linking")
@RequiredPlugins("Vault")
@Since("1.0.5")
@Examples("enable automatic vault linking with balance variable \"{balances::%%player%%}\"")
public class EffEnableAutoLinking extends Effect {

    static {
        Skript.registerEffect(EffEnableAutoLinking.class, "enable auto[matic] [vault] (linking|hooking) (with|using) [bal[ance]] var[iable] %string%");
    }

    private Expression<String> balanceVariable;

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        balanceVariable = (Expression<String>) e[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "enable automatic vault linking with balance variable " + balanceVariable.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        CustomEconomy.automaticLinking = true;
        CustomEconomy.linkVariable = balanceVariable.getSingle(e).replace("{", "").replace("}", "");
    }
}
