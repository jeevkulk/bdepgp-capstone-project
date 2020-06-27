package org.upgrad.capstone;

import com.google.gson.Gson;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.upgrad.capstone.domain.CardTransaction;
import org.upgrad.capstone.processor.TransactionProcessor;

public class Executor {

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);
        try {
            Executor executor = new Executor();
            executor.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void execute() throws Exception {
        /**
         * Task 6: Create a streaming data processing framework which ingests real-time
         * POS transaction data from Kafka. The transaction data is then validated based
         * on the three rules’ parameters (stored in the NoSQL database) discussed above.
         */
        //TODO: To uncomment after testing the flow for single hard-coded card transaction
        /*KafkaSparkStreaming kafkaSparkStreaming = new KafkaSparkStreaming();
        kafkaSparkStreaming.getTransactionsDataStream();*/

        //TODO: To delete the below lines of code
        String jsonString = "{\"card_id\":348702330256514,\"member_id\":37495066290,\"amount\":4380912,\"postcode\":96774,\"pos_id\":248063406800722,\"transaction_dt\":\"01-03-2018 08:24:29\"}";
        Gson gson = new Gson();
        CardTransaction cardTransaction = gson.fromJson(jsonString, CardTransaction.class);
        TransactionProcessor processor = new TransactionProcessor(cardTransaction);
        processor.doProcess();
    }
}
