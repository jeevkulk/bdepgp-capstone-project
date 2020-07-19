package org.upgrad.capstone.config;

/**
 * Hold configuration parameters
 */
public class Options {
    //Need to be set to EC2 public DNS
    private String EC2_PUBLIC_DNS;

    //Counter needs to be incremented for every run - so as to get all transactions
    private String KAFKA_GROUP_ID;

    private static Options options;

    private Options() {
    }

    public static Options getInstance() {
        if (options == null) {
            synchronized (Options.class) {
                if (options == null) {
                    options = new Options();
                }
            }
        }
        return options;
    }

    public String getEC2_PUBLIC_DNS() {
        return EC2_PUBLIC_DNS;
    }

    public void setEC2_PUBLIC_DNS(String EC2_PUBLIC_DNS) {
        this.EC2_PUBLIC_DNS = EC2_PUBLIC_DNS;
    }

    public String getKAFKA_GROUP_ID() {
        return KAFKA_GROUP_ID;
    }

    public void setKAFKA_GROUP_ID(String KAFKA_GROUP_ID) {
        this.KAFKA_GROUP_ID = KAFKA_GROUP_ID;
    }
}
