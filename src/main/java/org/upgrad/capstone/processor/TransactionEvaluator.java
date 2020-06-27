package org.upgrad.capstone.processor;

import org.upgrad.capstone.constant.Constants;
import org.upgrad.capstone.domain.CardTransaction;

public class TransactionEvaluator {

    public TransactionEvaluator() {
    }

    public CardTransaction evaluate(CardTransaction cardTransaction) {
        //TODO: Evaluate transaction and set transaction status
        cardTransaction.setStatus(Constants.STATUS_GENUINE);
        return cardTransaction;
    }
}
