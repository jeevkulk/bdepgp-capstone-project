package org.upgrad.capstone.domain;

import com.google.gson.annotations.SerializedName;

public class CardTransaction {
    @SerializedName("card_id")
    private String cardId;

    @SerializedName("member_id")
    private String memberId;

    private int amount;

    @SerializedName("postcode")
    private String postCode;

    @SerializedName("pos_id")
    private String posId;

    @SerializedName("transaction_dt")
    private String transactionDateStr;

    private long transactionDate;

    private String status;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getTransactionDateStr() {
        return transactionDateStr;
    }

    public void setTransactionDateStr(String transactionDateStr) {
        this.transactionDateStr = transactionDateStr;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CardTransaction{" +
                "cardId='" + cardId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", amount=" + amount +
                ", postCode='" + postCode + '\'' +
                ", posId='" + posId + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
