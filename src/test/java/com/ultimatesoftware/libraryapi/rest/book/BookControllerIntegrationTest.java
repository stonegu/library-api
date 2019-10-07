package com.ultimatesoftware.libraryapi.rest.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.book.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getAllOnLoan_success() throws Exception {

        Book book = new Book("title", "isbn", "author");
        given(bookService.getAllBooksOnLoan()).willReturn(Arrays.asList(book));

        mvc.perform(get("/v1/books/onloan")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
