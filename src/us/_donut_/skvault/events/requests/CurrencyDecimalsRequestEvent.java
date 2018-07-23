package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.SkriptEventInfo;
import us._donut_.skvault.Util;
import us._donut_.skvault.events.IntRequestEvent;
import us._donut_.skvault.events.SkVaultEvent;

public class CurrencyDecimalsRequestEvent extends IntRequestEvent {

    static {
        SkriptEventInfo skriptEventInfo = Skript.registerEvent("Vault Request - Currency Decimal Places (Return: number)", SkVaultEvent.class, CurrencyDecimalsRequestEvent.class,
                "[vault] [(number|amount) of] [currency] decimal[s] [places] request")
                .description("Called when Vault requests the number of decimals in the currency")
                .since("1.0")
                .examples("on vault currency decimals request:\n    return 2");

        Util.setRequiredPlugins(skriptEventInfo);
    }
}
