package com.ultimatesoftware.libraryapi.domain.book;

import java.util.Collection;
import java.util.Optional;

public interface BookService {

	Optional<Book> getBookById(Long id);

	Collection<Book> getAllBooks();

	Collection<Book> getAllBooksOnLoan();

	Collection<Book> getBooksOnLoanByBookId(Long id);

	Collection<Book> getAllBooksOverdue();

	Collection<Book> getBooksByAuthor(String author);

	Book save(Book book);

	void delete(Book book);
}
