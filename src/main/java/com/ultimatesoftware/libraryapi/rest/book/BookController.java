package com.ultimatesoftware.libraryapi.rest.book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.book.BookService;

@RestController
@RequestMapping("/v1/books")
public class BookController {
	Logger logger = LoggerFactory.getLogger(BookController.class);

	private final BookService bookService;
	private final ModelMapper modelMapper;

	@Autowired
	public BookController(final BookService bookService, final ModelMapper modelMapper) {
		this.bookService = bookService;
		this.modelMapper = modelMapper;
	}


	@GetMapping
	public List<BookDto> getAll() {
		return bookService.getAllBooks().stream()
				.map(book -> modelMapper.map(book, BookDto.class))
				.collect(Collectors.toList());
	}

	@GetMapping(path = "/{id}")
	public BookDto getById(@PathVariable Long id) {
		Optional<Book> book = bookService.getBookById(id);
		if (!book.isPresent()) {
			logger.error("book not found");
			throw new ResponseStatusException (
					HttpStatus.NOT_FOUND, "Book not found"
			);
		}
		return modelMapper.map(book.get(), BookDto.class);
	}

	@PostMapping
	public BookDto addBook(@RequestBody BookDto bookDto) {
		Book newBook = new Book(bookDto.getTitle(), bookDto.getISBN(), bookDto.getAuthor());
		newBook = bookService.save(newBook);
		return modelMapper.map(newBook, BookDto.class);
	}

	@GetMapping(path = "/onloan")
	public List<BookDto> getAllOnLoan() {
		return bookService.getAllBooksOnLoan().stream()
				.map(book -> modelMapper.map(book, BookDto.class))
				.collect(Collectors.toList());
	}

	@GetMapping(path = "/overdue")
	public List<BookDto> getAllOverdue() {
		return bookService.getAllBooksOverdue().stream()
				.map(book -> modelMapper.map(book, BookDto.class))
				.collect(Collectors.toList());
	}


}
