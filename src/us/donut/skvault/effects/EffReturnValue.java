package us.donut.skvault.effects;

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
import us.donut.skvault.events.VaultRequestEvent;

import javax.annotation.Nullable;

@Name("Vault Request - Return Value")
@Description("Returns a value to a request from Vault")
@RequiredPlugins("Vault")
@Since("2.0")
public class EffReturnValue extends Effect {

    static {
        Skript.registerEffect(EffReturnValue.class, "return %object% [to [the] [vault] request]");
    }

    private Expression<?> value;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        for (Class<? extends Event> event : ScriptLoader.getCurrentEvents()) {
            if (VaultRequestEvent.class.isAssignableFrom(event)) {
                value = exprs[0];
                return true;
            }
        }
        Skript.error("You can only use the return value effect in a Vault request event");
        return false;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void execute(Event e) {
        Object value = this.value.getSingle(e);
        ((VaultRequestEvent) e).setReturnValue(value instanceof Number ? ((Number) value).doubleValue() : value);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "return " + value.toString(e, debug);
    }
}
