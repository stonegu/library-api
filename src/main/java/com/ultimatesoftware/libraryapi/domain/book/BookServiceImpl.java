package com.ultimatesoftware.libraryapi.domain.book;

import com.ultimatesoftware.libraryapi.domain.rental.Rental;
import com.ultimatesoftware.libraryapi.domain.rental.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Collection<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<Book> getAllBooksOnLoan() {
        Collection<Rental> rentals = rentalRepository.findAll();
        if (CollectionUtils.isEmpty(rentals)) {
            return Collections.EMPTY_LIST;
        }
        return rentals.stream().map(rental -> rental.getBook()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<Book> getBooksOnLoanByBookId(Long id) {
        Collection<Rental> rentals = rentalRepository.findRentalsByBookId(id);
        if (CollectionUtils.isEmpty(rentals)) {
            return Collections.emptyList();
        }
        return rentals.stream().map(rental -> rental.getBook()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<Book> getAllBooksOverdue() {
        Collection<Rental> rentals = rentalRepository.findRentalsByDueDateBefore(LocalDate.now());
        if (CollectionUtils.isEmpty(rentals)) {
            return Collections.EMPTY_LIST;
        }
        return rentals.stream().map(rental -> rental.getBook()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<Book> getBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
    }

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
