package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import us._donut_.skvault.events.SkVaultEvent;
import us._donut_.skvault.events.ValueRequestEvent;

public class EconomyNameRequestEvent extends ValueRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Economy Name (Return: string)", SkVaultEvent.class, EconomyNameRequestEvent.class,
                "[vault] [eco[nomy]] name request")
                .description("Called when Vault requests the economy name")
                .requiredPlugins("Vault")
                .since("1.0")
                .examples("on vault economy name request:\n    return \"Cool Economy\"");
    }
}
