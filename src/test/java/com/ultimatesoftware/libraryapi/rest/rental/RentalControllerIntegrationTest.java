package com.ultimatesoftware.libraryapi.rest.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.book.BookService;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolderService;
import com.ultimatesoftware.libraryapi.domain.rental.Rental;
import com.ultimatesoftware.libraryapi.domain.rental.RentalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RentalController.class)
public class RentalControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RentalService rentalService;

    @MockBean
    private BookService bookService;

    @MockBean
    private CardHolderService cardHolderService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void rentPost_success() throws Exception {

        RentalRequest request = new RentalRequest();
        request.setBookId(1l);
        request.setCardHoldId(2l);

        Book book = new Book("title", "isbn", "author");
        CardHolder cardHolder = new CardHolder("lastname", "firstname", "cardnumber");
        LocalDate date = LocalDate.now();
        Rental rental = new Rental(cardHolder, book, date);

        given(bookService.getBookById(request.getBookId())).willReturn(Optional.of(book));
        given(cardHolderService.getCardHolderById(request.getCardHoldId())).willReturn(Optional.of(cardHolder));
        given(bookService.getBooksOnLoanByBookId(request.getBookId())).willReturn(Collections.EMPTY_LIST);
        given(rentalService.save(any(Rental.class))).willReturn(rental);

        String json = mapper.writeValueAsString(request);

        mvc.perform(post("/v1/rental")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void rentPost_notfound() throws Exception {

        RentalRequest request = new RentalRequest();
        request.setBookId(1l);
        request.setCardHoldId(2l);

        Book book = new Book("title", "isbn", "author");
        CardHolder cardHolder = new CardHolder("lastname", "firstname", "cardnumber");
        LocalDate date = LocalDate.now();
        Rental rental = new Rental(cardHolder, book, date);

        given(bookService.getBookById(request.getBookId())).willReturn(Optional.empty());
        given(cardHolderService.getCardHolderById(request.getCardHoldId())).willReturn(Optional.of(cardHolder));
        given(bookService.getBooksOnLoanByBookId(request.getBookId())).willReturn(Collections.EMPTY_LIST);
        given(rentalService.save(any(Rental.class))).willReturn(rental);

        String json = mapper.writeValueAsString(request);

        mvc.perform(post("/v1/rental")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

}
