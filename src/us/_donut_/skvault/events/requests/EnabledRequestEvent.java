package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.SkriptEventInfo;
import us._donut_.skvault.Util;
import us._donut_.skvault.events.BooleanRequestEvent;
import us._donut_.skvault.events.SkVaultEvent;

public class EnabledRequestEvent extends BooleanRequestEvent {

    static {
        SkriptEventInfo skriptEventInfo = Skript.registerEvent("Vault Request - Economy Enabled Status (Return: boolean)", SkVaultEvent.class, EnabledRequestEvent.class,
                "[vault] [eco[nomy]] enabled [status] request")
                .description("Called when Vault requests the enabled status of the economy")
                .since("1.0")
                .examples("on vault enabled status request:\n    return true");

        Util.setRequiredPlugins(skriptEventInfo);
    }
}
