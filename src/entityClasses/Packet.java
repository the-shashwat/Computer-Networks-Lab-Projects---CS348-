package entityClasses;

// Packet Class
public class Packet {
    String packetId;
    String sourceId;
    int sizeOfPacket;
    double creationTimestamp;

    public Packet(){

    }

    public Packet(String packetId, String sourceId, int sizeOfPacket, double creationTimestamp) {
        this.packetId = packetId;
        this.sourceId = sourceId;
        this.sizeOfPacket = sizeOfPacket;
        this.creationTimestamp = creationTimestamp;
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getSizeOfPacket() {
        return sizeOfPacket;
    }

    public void setSizeOfPacket(int sizeOfPacket) {
        this.sizeOfPacket = sizeOfPacket;
    }

    public double getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(double creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "packetId=" + packetId +
                ", sourceId=" + sourceId +
                ", sizeOfPacket=" + sizeOfPacket +
                ", creationTimestamp=" + creationTimestamp +
                '}';
    }
}
