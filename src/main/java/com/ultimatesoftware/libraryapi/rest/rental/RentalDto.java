package com.ultimatesoftware.libraryapi.rest.rental;

import com.ultimatesoftware.libraryapi.rest.book.BookDto;
import com.ultimatesoftware.libraryapi.rest.cardholder.CardHolderDto;

import java.time.LocalDate;

public class RentalDto {
    private Long id;
    private CardHolderDto cardHolder;
    private BookDto book;
    private LocalDate dueDate;

    public RentalDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardHolderDto getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolderDto cardHolder) {
        this.cardHolder = cardHolder;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
