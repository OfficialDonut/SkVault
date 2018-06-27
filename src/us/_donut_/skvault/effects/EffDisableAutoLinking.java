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

@Name("Vault Automatic Linking - Disable")
@Description("Disables automatic Vault linking")
@RequiredPlugins("Vault")
@Since("1.0.5")
@Examples("disable automatic vault linking")
public class EffDisableAutoLinking extends Effect {

    static {
        Skript.registerEffect(EffDisableAutoLinking.class, "disable auto[matic] [vault] (linking|hooking)");
    }

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "disable automatic vault linking";
    }

    @Override
    protected void execute(Event e) {
        CustomEconomy.automaticLinking = false;
    }
}
