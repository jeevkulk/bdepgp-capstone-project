package org.upgrad.capstone.processor;

import org.upgrad.capstone.constant.Constants;
import org.upgrad.capstone.domain.CardLookup;
import org.upgrad.capstone.domain.CardTransaction;
import org.upgrad.capstone.util.DistanceUtility;

/**
 * Applies rules for the transaction - so as to identify fraudulent transactions
 */
public class TransactionEvaluator {
    public CardTransaction evaluate(CardTransaction cardTransaction, CardLookup cardLookup) throws Exception {
        String transactionStatus = null;
        if (validUCL(cardTransaction, cardLookup)
                && validMemberScore(cardLookup)
                && validDistanceTime(cardTransaction, cardLookup)) {
            transactionStatus = Constants.STATUS_GENUINE;
        } else {
            transactionStatus = Constants.STATUS_FRAUD;
        }
        cardTransaction.setStatus(transactionStatus);
        return cardTransaction;
    }

    /**
     * Checks if the transaction amount is less than Upper Control Limit
     * @param cardTransaction
     * @param cardLookup
     * @return
     */
    private boolean validUCL(CardTransaction cardTransaction, CardLookup cardLookup) {
        return cardLookup.getUcl() > cardTransaction.getAmount();
    }

    /**
     * Checks if the member has good credit score
     * @param cardLookup
     * @return
     */
    private boolean validMemberScore(CardLookup cardLookup) {
        return cardLookup.getScore() > 200;
    }

    /**
     * Checks if the transaction done is not too early from time and distance perspective
     * with reference to previous transaction
     * @param cardTransaction
     * @param cardLookup
     * @return
     */
    private boolean validDistanceTime(CardTransaction cardTransaction, CardLookup cardLookup) throws Exception {
        DistanceUtility distanceUtility = new DistanceUtility();

        String currentTransactionPostCode = cardTransaction.getPostCode();
        String lastTransactionPostCode = cardLookup.getPostCode();
        double distance = distanceUtility.getDistanceViaZipCode(currentTransactionPostCode, lastTransactionPostCode);

        long currentTransactionTransactionDate = cardTransaction.getTransactionDate();
        long lastTransactionTransactionDate = cardLookup.getTransactionDate();
        long timeBetweenTransactionsInHours = (currentTransactionTransactionDate - lastTransactionTransactionDate) / (60 * 60);

        double calculatedSpeed = distance / timeBetweenTransactionsInHours;

        if (calculatedSpeed > 900) {
            return false;
        } else {
            return true;
        }
    }
}
