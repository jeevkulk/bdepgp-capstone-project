package org.upgrad.capstone.processor;

import org.upgrad.capstone.domain.CardLookup;
import org.upgrad.capstone.domain.CardTransaction;
import org.upgrad.capstone.hbase.HBaseDAO;

public class TransactionProcessor {

    private CardTransaction cardTransaction;

    public TransactionProcessor(CardTransaction cardTransaction) {
        this.cardTransaction = cardTransaction;
    }

    public void doProcess() throws Exception {
        HBaseDAO hBaseDAO = new HBaseDAO();
        System.out.println(cardTransaction);

        CardLookup cardLookup = hBaseDAO.lookupCardDetails(cardTransaction);
        System.out.println(cardLookup);

        /**
         * Task 7: Update the transactions data along with the status (Fraud/Genuine)
         * in the card_transactions table.
         */
        TransactionEvaluator transactionEvaluator = new TransactionEvaluator();
        transactionEvaluator.evaluate(cardTransaction);
        hBaseDAO.saveCardTransaction(cardTransaction);

        /**
         * Task 8: Store the ‘postcode’ and ‘transaction_dt’ of the current transaction
         * in the look-up table in the NoSQL database.
         */
        hBaseDAO.saveCardLookupDetails(cardTransaction);
    }
}
