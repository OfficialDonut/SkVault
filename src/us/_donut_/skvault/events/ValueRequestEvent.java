package us._donut_.skvault.events;

public class ValueRequestEvent extends VaultEvent {

    private Object returnValue;

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
        setImplemented(true);
    }

    public double getDoubleValue() {
        verifyImplemented();
        try {
            return ((Number) returnValue).doubleValue();
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }

    public int getIntValue() {
        verifyImplemented();
        try {
            return ((Number) returnValue).intValue();
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }

    public boolean getBooleanValue() {
        verifyImplemented();
        try {
            return (boolean) returnValue;
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }

    public String getStringValue() {
        verifyImplemented();
        try {
            return (String) returnValue;
        } catch (ClassCastException | NullPointerException e) {
            return "";
        }
    }
}
