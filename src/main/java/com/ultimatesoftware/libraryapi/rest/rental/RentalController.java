package com.ultimatesoftware.libraryapi.rest.rental;

import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.book.BookService;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolderService;
import com.ultimatesoftware.libraryapi.domain.rental.Rental;
import com.ultimatesoftware.libraryapi.domain.rental.RentalService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/rental")
public class RentalController {
    Logger logger = LoggerFactory.getLogger(RentalController.class);

    public static final long BOOK_HOLDING_DAY = 30;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CardHolderService cardHolderService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping()
    public ResponseEntity<RentalDto> rent(@RequestBody RentalRequest request) {

        // request validation
        if (request.getBookId() == null || request.getCardHoldId() == null) {
            logger.error("Either bookId or cardholdId can not be null");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Either bookId or cardholdId can not be null"
            );
        }

        // check book and cardholder available
        Optional<Book> book = bookService.getBookById(request.getBookId());
        Optional<CardHolder> cardHolder = cardHolderService.getCardHolderById(request.getCardHoldId());

        if (!book.isPresent() || !cardHolder.isPresent()) {
            String error = String.format("Either cardholder is not found for id : %d, or book is not found for id: %d", request.getCardHoldId(), request.getBookId());
            logger.error(error);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    error
            );
        }

        // check if the book is rent
        Collection<Book> booksOnloan = bookService.getBooksOnLoanByBookId(request.getBookId());
        if (!CollectionUtils.isEmpty(booksOnloan)) {
            String error = String.format("book is rent for id: %d", request.getBookId());
            logger.error(error);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    error
            );
        }

        Rental savedRental = rentalService.save(
                new Rental(cardHolder.get(), book.get(), LocalDate.now().plusDays(BOOK_HOLDING_DAY)));

        return new ResponseEntity<>(modelMapper.map(savedRental, RentalDto.class), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<Collection<RentalDto>> bookReturn(@PathVariable Long bookId) {

        // check has book
        Optional<Book> book = bookService.getBookById(bookId);
        if(!book.isPresent()) {
            String error = String.format("book is not found for id: %d", bookId);
            logger.error(error);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    error
            );
        }

        // check book rent
        Collection<Rental> rentals = rentalService.getRentalsByBook(book.get());
        if (CollectionUtils.isEmpty(rentals)) {
            String error = String.format("the book is not rent for bookId: %d", bookId);
            logger.error(error);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    error
            );
        }

        Collection<RentalDto> rentalDtos = rentals.stream().map(rental -> modelMapper.map(rental, RentalDto.class)).collect(Collectors.toList());
        // delete rentals
        for (Rental rental: rentals) {
            rentalService.delete(rental);
        }

        return new ResponseEntity<>(rentalDtos, HttpStatus.OK);
    }


}
