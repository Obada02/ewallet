package com.github.ewallet.service;

import com.github.ewallet.dto.mapper.CardRequestMapper;
import com.github.ewallet.dto.mapper.CardResponseMapper;
import com.github.ewallet.dto.request.CardRequest;
import com.github.ewallet.dto.response.CommandResponse;
import com.github.ewallet.dto.response.CardResponse;
import com.github.ewallet.exception.ElementAlreadyExistsException;
import com.github.ewallet.exception.NoSuchElementFoundException;
import com.github.ewallet.model.Card;
import com.github.ewallet.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.ewallet.common.Constants.*;

/**
 * Service used for Card related operations
 */
@Slf4j(topic = "CardService")
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardRequestMapper cardRequestMapper;
    private final CardResponseMapper cardResponseMapper;

    /**
     * Fetches a single card by the given id
     *
     * @param id
     * @return CardResponse
     */
    @Transactional(readOnly = true)
    public CardResponse findById(long id) {
        return cardRepository.findById(id)
                .map(cardResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_CARD));
    }

    /**
     * Fetches a single card by the given userId
     *
     * @param userId
     * @return List of CardResponse
     */
    @Transactional(readOnly = true)
    public List<CardResponse> findByUserId(long userId) {
        return cardRepository.findByUserId(userId).stream()
                .map(cardResponseMapper::toDto)
                .toList();
    }

    /**
     * Fetches all cards based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of CardResponse
     */
    @Transactional(readOnly = true)
    public Page<CardResponse> findAll(Pageable pageable) {
        final Page<CardResponse> cards = cardRepository.findAll(pageable)
                .map(cardResponseMapper::toDto);
        if (cards.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return cards;
    }

    /**
     * Creates a new card using the given request parameters
     *
     * @param request
     * @return id of the created card
     */
    @Transactional
    public CommandResponse create(CardRequest request) {
        if (cardRepository.existsByUserIdAndCredentialsIgnoreCase(request.getUserId(), request.getCredentials()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_CARD);

        final Card card = cardRequestMapper.toEntity(request);
        cardRepository.save(card);
        log.info(CREATED_CARD, new Object[]{card.getCredentials(), card.getExpiryDate()});

        return CommandResponse.builder().id(card.getId()).build();
    }

    /**
     * Updates card using the given request parameters
     *
     * @param request
     * @return id of the updated card
     */
    public CommandResponse update(CardRequest request) {
        final Card foundCard = cardRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_CARD));

        // check if the credentials are changed and new credentials already exist for the user
        if (!request.getCredentials().equalsIgnoreCase(foundCard.getCredentials()) &&
                cardRepository.existsByUserIdAndCredentialsIgnoreCase(request.getUserId(), request.getCredentials()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_CARD);

        final Card card = cardRequestMapper.toEntity(request);
        cardRepository.save(card);
        log.info(UPDATED_CARD, new Object[]{card.getCredentials(), card.getExpiryDate()});
        return CommandResponse.builder().id(card.getId()).build();
    }

    /**
     * Deletes card by the given id
     *
     * @param id
     */
    public void deleteById(long id) {
        final Card card = cardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_CARD));
        cardRepository.delete(card);
        log.info(DELETED_CARD, new Object[]{card.getCredentials(), card.getExpiryDate()});
    }
}
