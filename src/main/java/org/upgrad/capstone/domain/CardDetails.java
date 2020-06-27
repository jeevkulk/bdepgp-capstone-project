package org.upgrad.capstone.domain;

public class CardDetails {
    private String cardId;
    private String memberId;
    private long memberJoiningDate;
    private String cardPurchaseDate;
    private String country;
    private String city;

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

    public long getMemberJoiningDate() {
        return memberJoiningDate;
    }

    public void setMemberJoiningDate(long memberJoiningDate) {
        this.memberJoiningDate = memberJoiningDate;
    }

    public String getCardPurchaseDate() {
        return cardPurchaseDate;
    }

    public void setCardPurchaseDate(String cardPurchaseDate) {
        this.cardPurchaseDate = cardPurchaseDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
