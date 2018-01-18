package entityClasses;

public class Switch {
    String switchId;
    long maxPacketCapacity;
    long numberOfPacketsHeld;

    public Switch(){

    }

    public Switch(String switchId, long numberOfPacketsHeld,long maxPacketCapacity) {
        this.switchId = switchId;
        this.numberOfPacketsHeld = numberOfPacketsHeld;
        this.maxPacketCapacity=maxPacketCapacity;
    }

    public String getSwitchId() {
        return switchId;
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }

    public long getMaxPacketCapacity() {
        return maxPacketCapacity;
    }

    public void setMaxPacketCapacity(long maxPacketCapacity) {
        this.maxPacketCapacity = maxPacketCapacity;
    }

    public void setNumberOfPacketsHeld(long numberOfPacketsHeld) {
        this.numberOfPacketsHeld = numberOfPacketsHeld;
    }

    public long getNumberOfPacketsHeld() {
        return numberOfPacketsHeld;
    }

    public void setNumberOfPacketsHeld(int numberOfPacketsHeld) {
        this.numberOfPacketsHeld = numberOfPacketsHeld;
    }
}
