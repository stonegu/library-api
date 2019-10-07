package com.ultimatesoftware.libraryapi.domain.cardholder;

import java.util.Collection;
import java.util.Optional;

public interface CardHolderService {

	Optional<CardHolder> getCardHolderById(Long id);

	Collection<CardHolder> getAllCardHolders();

	CardHolder save(CardHolder cardHolder);

	void delete(CardHolder cardHolder);

}
