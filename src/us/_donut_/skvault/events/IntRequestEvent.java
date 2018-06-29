package us._donut_.skvault.events;

public class IntRequestEvent extends ValueRequestEvent {

    public int getIntValue() {
        verifyImplemented();
        try {
            return ((Number) getReturnValue()).intValue();
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }
}
