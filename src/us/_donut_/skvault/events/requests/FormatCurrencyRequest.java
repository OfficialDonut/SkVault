package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import us._donut_.skvault.events.SkVaultEvent;
import us._donut_.skvault.events.ValueRequestEvent;

public class FormatCurrencyRequest extends ValueRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Format Number As Currency (Return: string)", SkVaultEvent.class, FormatCurrencyRequest.class,
                "[vault] format [[number as] currency] request")
                .description("Called when Vault requests a number to be formatted as currency")
                .requiredPlugins("Vault")
                .since("1.0.5")
                .examples("on vault format currency request:\n    return \"$%event-number%\"");
        EventValues.registerEventValue(FormatCurrencyRequest.class, Number.class, new Getter<Number, FormatCurrencyRequest>() {
            public Number get(FormatCurrencyRequest  e) {
                return e.getRequestedNumber();
            }
        }, 0);
    }

    private boolean gaveSkriptValue = false;
    private double requestedNumber;

    public FormatCurrencyRequest(double amount) {
        this.requestedNumber = amount;
    }

    public double getRequestedNumber() {
        return requestedNumber;
    }

    //Skript uses currency format while parsing the script so an UnsupportedOperationException shouldn't be thrown the first time
    @Override
    public void verifyImplemented() {
        if (!gaveSkriptValue) {
            gaveSkriptValue = true;
        } else {
            if (!isImplemented())
                throw new UnsupportedOperationException(getClass().getSimpleName().replace("Event", ""));
        }
    }
}
