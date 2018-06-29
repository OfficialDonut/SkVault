package us._donut_.skvault.events;

public class BooleanRequestEvent extends ValueRequestEvent {

    public boolean getBooleanValue() {
        verifyImplemented();
        try {
            return (boolean) getReturnValue();
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }
}
