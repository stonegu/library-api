package com.ultimatesoftware.libraryapi.rest.rental;

public class RentalRequest {
    private Long cardHoldId;
    private Long bookId;

    public RentalRequest() {
    }

    public Long getCardHoldId() {
        return cardHoldId;
    }

    public void setCardHoldId(Long cardHoldId) {
        this.cardHoldId = cardHoldId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
