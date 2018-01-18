package MainPackage;

import entityClasses.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class NetworkModel {
    public static final String PACKET_GENERATION_EVENT="PACKET_GENERATION_EVENT";
    public static final String PACKET_ARRIVAL_AT_SWITCH_EVENT="PACKET_ARRIVAL_AT_SWITCH_EVENT";
    public static final String PACKET_DISPATCH_EVENT="PACKET_DISPATCH_EVENT";
    public static final String PACKET_REACHED_AT_SINK_EVENT="PACKET_REACHED_AT_SINK_EVENT";
    private static final int SIMULATION_TIME=100;
    private static int numberOfSources=0;
    private static int packetSize=0;
    private static double bs=0;
    private static double bss=0;
    private static double packetGenerationRate=0;
    private static long packetCount=0;
    private static final String FILENAME1 = "C:\\users\\dell\\IdeaProjects\\CS348_Lab1\\simulation1.txt";
    private static final String FILENAME2 = "C:\\users\\dell\\IdeaProjects\\CS348_Lab1\\simulation2.txt";
    // Comparator for priority queue of events
    private static Comparator<Event> eventComparator = new Comparator<Event>(){
        @Override
        public int compare(Event e1, Event e2) {
            if(e1.getEventTimestamp() < e2.getEventTimestamp())
                return -1;
            else if(e1.getEventTimestamp() > e2.getEventTimestamp())
                return 1;
            else
                return 0;
        }
    };

    // maxLimit is the only parameter as input. It denotes the max limit of the queue at the switch
    private static void simulation(long maxLimit)
    {
        Source sources[] = new Source[numberOfSources];
        Link links[] = new Link[numberOfSources];
        PriorityQueue<Event> queueOfEvents = new PriorityQueue(eventComparator);
        int iter=1;
        for (int i = 0; i < numberOfSources; i++)
        {
            sources[i] = new Source("Source_" + (i + 1), packetGenerationRate);
            links[i] = new Link("Link_" + (i + 1), bs, "Source_" + (i + 1));}

        StringBuilder content=new StringBuilder("");
        while(iter<=5000) {
            long packetsDropped=0;
            queueOfEvents.clear();
            double loadfactor=0;
            packetCount=0;
            Random random=new Random();
            for (int i = 0; i < numberOfSources; i++) {
                if(iter==1){
                    sources[i] = new Source("Source_" + (i + 1), packetGenerationRate);
                    links[i] = new Link("Link_" + (i + 1), bs, "Source_" + (i + 1));}
                double randomTimestamp = random.nextDouble()*2;
                Packet packet = new Packet("Packet_" + (++packetCount), "Source_" + (i + 1), packetSize, randomTimestamp);
                Event event = new Event(packet, PACKET_GENERATION_EVENT, packet.getCreationTimestamp());
                queueOfEvents.add(event);
            }
            Switch connectingSwitch = new Switch("Switch1", 0, (long) maxLimit);
            Link switchSinkLink = new Link("Link_" + (numberOfSources + 1), bss, "Switch1");
            double diff = 0;
            double freetime=0;
            boolean freestate=true;
            while (queueOfEvents.size() > 0) {
                Event event = queueOfEvents.poll();
                if (event.getEventType().equals(PACKET_GENERATION_EVENT)) {
                    Event e1 = new Event(event.getPacket(), PACKET_ARRIVAL_AT_SWITCH_EVENT, (event.getEventTimestamp() + packetSize / bs));
                    Packet packet = new Packet("Packet_" + (++packetCount), event.getPacket().getSourceId(), packetSize,  event.getEventTimestamp() + 1 / packetGenerationRate);
                    Event e2 = new Event(packet, PACKET_GENERATION_EVENT, packet.getCreationTimestamp());
                    queueOfEvents.add(e1);
                    if(e2.getEventTimestamp()<SIMULATION_TIME)
                    {queueOfEvents.add(e2);}
                    else
                        packetCount--;
                } else if (event.getEventType().equals(PACKET_ARRIVAL_AT_SWITCH_EVENT)) {
                    boolean packetWasDropped=false;
                    if(connectingSwitch.getNumberOfPacketsHeld()>=connectingSwitch.getMaxPacketCapacity())
                    {
                        packetWasDropped=true;
                        packetsDropped++;
                    }

                    if(!packetWasDropped)
                    {
                        Event e=null;
                        double nextEventTime=Math.max(event.getEventTimestamp(),freetime)+(connectingSwitch.getNumberOfPacketsHeld()) * (packetSize / bss);
                        if(!freestate)nextEventTime-=packetSize/bss;
                        e = new Event(event.getPacket(), PACKET_DISPATCH_EVENT,nextEventTime);
                        connectingSwitch.setNumberOfPacketsHeld(connectingSwitch.getNumberOfPacketsHeld() + 1);
                        queueOfEvents.add(e);
                    }
                }
                else if (event.getEventType().equals(PACKET_DISPATCH_EVENT)) {
                    Event e = new Event(event.getPacket(), PACKET_REACHED_AT_SINK_EVENT,  (event.getEventTimestamp() + packetSize / bss));
                    queueOfEvents.add(e);
                    freestate=false;
                    freetime=event.getEventTimestamp()+packetSize/bss;
                } else {
                    connectingSwitch.setNumberOfPacketsHeld(connectingSwitch.getNumberOfPacketsHeld() - 1);
                    freestate=true;
                    diff += event.getEventTimestamp() - event.getPacket().getCreationTimestamp();
                }
            }
            loadfactor=numberOfSources*packetGenerationRate*packetSize/bss;
            double dropRate = packetsDropped/SIMULATION_TIME;
            double averageTime = diff / packetCount;
            if(maxLimit==Long.MAX_VALUE)
            content.append(loadfactor+","+averageTime+"\n");
            else
            content.append(loadfactor+","+dropRate+"\n");
            iter++;
            bss+=10;
            System.out.println("iter "+iter+" loadfactor "+loadfactor+" average time "+averageTime+" dropRate "+dropRate);
        }
        if(maxLimit==Long.MAX_VALUE)
        writeToTextFile(content,FILENAME1);
        else
            writeToTextFile(content,FILENAME2);

    }
    // this method is used to write the x,y coordinates to text file which is used by the python script for generating the graphs
    private static void writeToTextFile(StringBuilder content,String filepath)
    {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(filepath);
            bw = new BufferedWriter(fw);
            bw.write(String.valueOf(content));
        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }
    // calling python script to plot the graphs
    private static void generateGraph()
    {
        String[] callAndArgs= {"\"python\"","-u","\"plot.py\""};
        try {
            Process p = Runtime.getRuntime().exec(callAndArgs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[])throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of Sources ");
        numberOfSources = sc.nextInt();

        System.out.println("Enter the size of each packet in bits");
        packetSize = sc.nextInt();

        System.out.println("Enter the source packet generation rate in packets per second (i.e. lamda) ");
        packetGenerationRate = sc.nextDouble();

        System.out.println("Enter the bandwidth of each link from source to switch (i.e. bs value) in bits per second");
        System.out.println("Make sure that packetSize*packetGenerationRate<=bandwidth i.e. bandwidth must be >= " + (packetGenerationRate * packetSize));
        bs = sc.nextDouble();
        while (packetGenerationRate * packetSize > bs) {
            System.out.println("Invalid Input.Ensure that packetSize*packetGenerationRate<=bandwidth i.e. bandwidth must be >= " + (packetGenerationRate * packetSize));
            bs = sc.nextDouble();
        }

        double temp=0;
        System.out.println("Enter the bandwidth of the link from switch to sink (i.e. bss value) ");
        temp= sc.nextDouble();

        System.out.println("Enter the max limit of queue.");
        long maxLimit = sc.nextLong();

        bss=temp;
        simulation(maxLimit);
        maxLimit=Long.MAX_VALUE;
        bss=temp;
        simulation(maxLimit);
        generateGraph();
    }
}
/*
I ran the above file using these values and the plots generated are Figure_1.png, Figure_2.png
simulation time = 100
no of sources = 10
packet generation rate = 100
packet length = 20
bs = 3000
bss = 5000
bss inc = 10
queue size = 200
* */