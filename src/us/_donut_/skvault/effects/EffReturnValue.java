package us._donut_.skvault.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import us._donut_.skvault.LiteralUtils;
import us._donut_.skvault.events.ValueRequestEvent;

import javax.annotation.Nullable;

@Name("Vault Request - Return Value")
@Description("Returns a value to a request from vault")
@RequiredPlugins("Vault")
@Since("1.0")
public class EffReturnValue extends Effect {

    static {
        Skript.registerEffect(EffReturnValue.class, "return [value] %object% [to [the] [vault] request]");
    }

    private Expression<?> value;

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        for (Class<? extends Event> event : ScriptLoader.getCurrentEvents()) {
            if (event.getSuperclass().getSuperclass().equals(ValueRequestEvent.class)) {
                value = LiteralUtils.defendExpression(e[0]);
                return LiteralUtils.canInitSafely(value);
            }
        }
        Skript.error("You can only use the return value effect in a Vault value request event");
        return false;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "return " + value.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        ((ValueRequestEvent) e).setReturnValue(value.getSingle(e));
    }
}
