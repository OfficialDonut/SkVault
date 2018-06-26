package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import us._donut_.skvault.events.SkVaultEvent;
import us._donut_.skvault.events.ValueRequestEvent;

public class EnabledRequestEvent extends ValueRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Economy Enabled Status (Return: boolean)", SkVaultEvent.class, EnabledRequestEvent.class,
                "[vault] [eco[nomy]] enabled [status] request")
                .description("Called when Vault requests the enabled status of the economy")
                .requiredPlugins("Vault")
                .since("1.0")
                .examples("on vault enabled status request:\n    return true");
    }
}
