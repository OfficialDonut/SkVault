package us._donut_.skvault.events;

public class DoubleRequestEvent extends ValueRequestEvent {

    public double getDoubleValue() {
        verifyImplemented();
        try {
            return ((Number) getReturnValue()).doubleValue();
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }
}
