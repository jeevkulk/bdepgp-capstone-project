package org.upgrad.capstone.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.upgrad.capstone.domain.CardLookup;
import org.upgrad.capstone.domain.CardTransaction;
import org.upgrad.capstone.hbase.HBaseDAO;

import java.text.SimpleDateFormat;

/**
 * Processes a transaction
 */
public class TransactionProcessor {
    private static Logger LOGGER = LoggerFactory.getLogger(TransactionProcessor.class);
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private CardTransaction cardTransaction;

    public TransactionProcessor(CardTransaction cardTransaction) throws Exception {
        cardTransaction.setTransactionDate(dateFormat.parse(cardTransaction.getTransactionDateStr()).getTime() / 1000);
        this.cardTransaction = cardTransaction;
    }

    /**
     * For every transaction passed it does a card details lookup, calls transaction evaluator
     * and updates the record in card_transaction table and card_lookup table.
     * @throws Exception
     */
    public void doProcess() throws Exception {
        HBaseDAO hBaseDAO = new HBaseDAO();
        CardLookup cardLookup = hBaseDAO.lookupCardDetails(cardTransaction);
        System.out.println("Card lookup details: "+cardLookup);

        /**
         * Task 7: Update the transactions data along with the status (Fraud/Genuine)
         * in the card_transactions table.
         */
        TransactionEvaluator transactionEvaluator = new TransactionEvaluator();
        transactionEvaluator.evaluate(cardTransaction, cardLookup);
        hBaseDAO.saveCardTransaction(cardTransaction);

        /**
         * Task 8: Store the ‘postcode’ and ‘transaction_dt’ of the current transaction
         * in the look-up table in the NoSQL database.
         */
        CardLookup cardLookupUpdated = new CardLookup();
        cardLookupUpdated.setCardId(cardTransaction.getCardId());
        cardLookupUpdated.setPostCode(cardTransaction.getPostCode());
        cardLookupUpdated.setTransactionDate(cardTransaction.getTransactionDate());
        hBaseDAO.saveCardLookupDetails(cardLookupUpdated);
        System.out.println("Result after processing: "+cardTransaction);
    }
}
