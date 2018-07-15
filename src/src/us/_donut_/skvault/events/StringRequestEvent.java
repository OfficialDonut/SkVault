package us._donut_.skvault.events;

public class StringRequestEvent extends ValueRequestEvent {

    public String getStringValue() {
        verifyImplemented();
        try {
            return ((String) getReturnValue()).toString();
        } catch (ClassCastException | NullPointerException e) {
            return "";
        }
    }
}
