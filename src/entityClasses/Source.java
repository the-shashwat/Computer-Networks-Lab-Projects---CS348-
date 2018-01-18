package entityClasses;
// Source class
public class Source {
    String sourceId;
    double packetGenerationRate;

    public Source(){

    }
    public Source(String sourceId,double packetGenerationRate)
    {
        this.sourceId=sourceId;
        this.packetGenerationRate=packetGenerationRate;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setpacketGenerationRate(double packetGenerationRate) {
        this.packetGenerationRate = packetGenerationRate;
    }


    public double getpacketGenerationRate() {
        return packetGenerationRate;

    }

    @Override
    public String toString() {
        return "Source{" +
                "sourceId=" + sourceId +
                ", packetGenerationRate=" + packetGenerationRate +
                '}';
    }
}
