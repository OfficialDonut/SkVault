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
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.event.Event;
import us._donut_.skvault.events.EcoResponseRequestEvent;

import javax.annotation.Nullable;

@Name("Vault Request - Return Economy Response")
@Description("Returns an economy response to a request from vault")
@RequiredPlugins("Vault")
@Since("1.0")
public class EffReturnEcoResponse extends Effect {

    static {
        Skript.registerEffect(EffReturnEcoResponse.class, "return [a] [new] eco[nomy] response [with] [amount [modified]] %number%, [new] [bal[ance]] %number%, [[response] type] (0¦success|1¦failure|2¦not implemented), [and] [error [message] %string% [to [the] [vault] request]");
    }

    private Expression<Number> amountModified;
    private Expression<Number> newBalance;
    private Expression<String> errorMessage;
    private EconomyResponse.ResponseType responseType;

    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        for (Class<? extends Event> event : ScriptLoader.getCurrentEvents()) {
            if (ScriptLoader.isCurrentEvent(event) && event.getSuperclass().equals(EcoResponseRequestEvent.class)) {
                amountModified = (Expression<Number>) e[0];
                newBalance = (Expression<Number>) e[1];
                errorMessage = (Expression<String>) e[2];
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
        }
        Skript.error("You can only use the return economy response effect in a Vault economy response request event");
        return false;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "return economy response";
    }

    @Override
    protected void execute(Event e) {
        EconomyResponse economyResponse = new EconomyResponse(amountModified.getSingle(e).doubleValue(), newBalance.getSingle(e).doubleValue(), responseType, errorMessage.getSingle(e));
        ((EcoResponseRequestEvent) e).setEconomyResponse(economyResponse);
    }
}
