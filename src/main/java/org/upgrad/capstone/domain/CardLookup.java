package org.upgrad.capstone.domain;

public class CardLookup {
    private String cardId;
    private double ucl;
    private String postCode;
    private long transactionDate;
    private int score;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public double getUcl() {
        return ucl;
    }

    public void setUcl(double ucl) {
        this.ucl = ucl;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "CardLookup{" +
                "cardId='" + cardId + '\'' +
                ", ucl=" + ucl +
                ", postCode='" + postCode + '\'' +
                ", transactionDate=" + transactionDate +
                ", score=" + score +
                '}';
    }
}
