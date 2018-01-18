package entityClasses;

// Link Class
public class Link {
    String linkId;
    double bandwidth;
    String sourceId;

    public Link()
    {

    }

    public Link(String linkId, double bandwidth,String sourceId) {
        this.linkId = linkId;
        this.bandwidth = bandwidth;
        this.sourceId=sourceId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }

}
