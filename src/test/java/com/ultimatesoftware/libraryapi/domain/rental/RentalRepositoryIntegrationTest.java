package com.ultimatesoftware.libraryapi.domain.rental;

import com.ultimatesoftware.libraryapi.domain.book.Book;
import com.ultimatesoftware.libraryapi.domain.cardholder.CardHolder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collection;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RentalRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RentalRepository rentalRepository;

    @Test
    public void whenFindRentalsByBookId_thenReturnRentals() {
        Book book = new Book("title", "isbn", "author");
        entityManager.persist(book);
        CardHolder cardHolder = new CardHolder("lastname", "firstname", "cardnumber");
        entityManager.persist(cardHolder);

        Rental rental = new Rental();
        rental.setBook(book);
        rental.setCardHolder(cardHolder);
        rental.setDueDate(LocalDate.now().plusDays(30l));
        entityManager.persist(rental);
        entityManager.flush();

        Collection<Rental> rentalsByBookId = rentalRepository.findRentalsByBookId(book.getId());
        Assert.assertTrue(rentalsByBookId.size() == 1);

    }


}
