package com.ultimatesoftware.libraryapi.graphql.query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.book.BookService;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolderService;
import com.ultimatesoftware.libraryapi.domain.rental.Rental;
import com.ultimatesoftware.libraryapi.domain.rental.RentalService;
import com.ultimatesoftware.libraryapi.rest.rental.RentalDto;
import com.ultimatesoftware.libraryapi.rest.rental.RentalRequest;

@Component
public class BookMutation implements GraphQLMutationResolver {

	public static final long BOOK_HOLDING_DAY = 30;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CardHolderService cardHolderService;

    @Autowired
    private ModelMapper modelMapper;
	
	public RentalDto rentbook(RentalRequest request) {

        // request validation
        if (request.getBookId() == null || request.getCardHoldId() == null) {
        	return null;
        }

        // check book and cardholder available
        Optional<Book> book = bookService.getBookById(request.getBookId());
        Optional<CardHolder> cardHolder = cardHolderService.getCardHolderById(request.getCardHoldId());

        if (!book.isPresent() || !cardHolder.isPresent()) {
            return null;
        }

        // check if the book is rent
        Collection<Book> booksOnloan = bookService.getBooksOnLoanByBookId(request.getBookId());
        if (!CollectionUtils.isEmpty(booksOnloan)) {
        	return null;
        }

        Rental savedRental = rentalService.save(
                new Rental(cardHolder.get(), book.get(), LocalDate.now().plusDays(BOOK_HOLDING_DAY)));

        return modelMapper.map(savedRental, RentalDto.class);

	}
	
	public Collection<RentalDto> bookReturn(Long bookId) {
        // check has book
        Optional<Book> book = bookService.getBookById(bookId);
        if(!book.isPresent()) {
        	return null;
        }

        // check book rent
        Collection<Rental> rentals = rentalService.getRentalsByBook(book.get());
        if (CollectionUtils.isEmpty(rentals)) {
        	return null;
        }

        Collection<RentalDto> rentalDtos = rentals.stream().map(rental -> modelMapper.map(rental, RentalDto.class)).collect(Collectors.toList());
        // delete rentals
        for (Rental rental: rentals) {
            rentalService.delete(rental);
        }

        return rentalDtos;
		
	}

}
