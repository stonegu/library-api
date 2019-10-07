package com.ultimatesoftware.libraryapi.domain.rental;

import java.time.LocalDate;
import java.util.Collection;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;

public interface RentalService {

	Rental getRentalById(Long id);

	Collection<Rental> getRentalsByBook(Book book);

	Collection<Rental> getRentalsByCardholder(CardHolder cardHolder);

	Collection<Rental> getRentalsDueAfter(LocalDate date);

	Collection<Rental> getAllRentals();

	Rental save(Rental rental);

	void delete(Rental rental);
}
