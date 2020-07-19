package org.upgrad.capstone;

import com.google.gson.Gson;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.upgrad.capstone.config.Options;
import org.upgrad.capstone.domain.CardTransaction;
import org.upgrad.capstone.kafka.KafkaSparkStreaming;
import org.upgrad.capstone.processor.TransactionProcessor;

/**
 * Main program which invokes kafka spark stream code to get new transactions
 */
public class Executor {

    /**
     * Main starting point of the program
     * Accepts kafka group id as parameter, so it can be re-run a number of times,
     * and ec2-public-dns (if not running from ec2 instance
     * @param args
     */
    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.OFF);
        try {
            Options options = Options.getInstance();
            if (args.length > 0) {
                options.setKAFKA_GROUP_ID(args[0]);
            }
            if (args.length > 1) {
                options.setEC2_PUBLIC_DNS(args[1]);
            } else {
                options.setEC2_PUBLIC_DNS("localhost");
            }
            Executor executor = new Executor();
            executor.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Execute method called by main
     * @throws Exception
     */
    public void execute() throws Exception {
        /**
         * Task 6: Create a streaming data processing framework which ingests real-time
         * POS transaction data from Kafka. The transaction data is then validated based
         * on the three rules’ parameters (stored in the NoSQL database) discussed above.
         */
        KafkaSparkStreaming kafkaSparkStreaming = new KafkaSparkStreaming();
        kafkaSparkStreaming.getTransactionsDataStream();

        /*String jsonString = "{\"card_id\":5189563368503974,\"member_id\":117826301530,\"amount\":4133848,\"postcode\":26346,\"pos_id\":182031383443115,\"transaction_dt\":\"09-09-2018 01:52:32\"}";
        Gson gson = new Gson();
        CardTransaction cardTransaction = gson.fromJson(jsonString, CardTransaction.class);
        TransactionProcessor processor = new TransactionProcessor(cardTransaction);
        processor.doProcess();*/
    }
}
