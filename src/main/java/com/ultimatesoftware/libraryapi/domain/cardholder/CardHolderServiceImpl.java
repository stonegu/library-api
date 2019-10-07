package com.ultimatesoftware.libraryapi.domain.cardholder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class CardHolderServiceImpl implements CardHolderService{

    @Autowired
    private CardHolderRepository cardHolderRepository;


    @Transactional(readOnly = true)
    public Optional<CardHolder> getCardHolderById(Long id) {
        return cardHolderRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Collection<CardHolder> getAllCardHolders() {
        return cardHolderRepository.findAll();
    }

    @Transactional
    public CardHolder save(CardHolder cardHolder) {
        return cardHolderRepository.save(cardHolder);
    }

    @Transactional
    public void delete(CardHolder cardHolder) {
        cardHolderRepository.delete(cardHolder);
    }

}
