package entityClasses;

// Event Class
public class Event {
    Packet packet;
    String eventType;
    double eventTimestamp;

    public Event(){

    }

    public Event(Packet packet, String eventType, double eventTimestamp) {
        this.packet=packet;
        this.eventType = eventType;
        this.eventTimestamp = eventTimestamp;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public double getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(double eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "packet=" + packet +
                ", eventType='" + eventType + '\'' +
                ", eventTimestamp=" + eventTimestamp +
                '}';
    }
}
