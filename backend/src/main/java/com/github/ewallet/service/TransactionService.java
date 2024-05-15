package com.github.ewallet.service;

import com.github.ewallet.dto.mapper.TransactionRequestMapper;
import com.github.ewallet.dto.mapper.TransactionResponseMapper;
import com.github.ewallet.dto.request.TransactionRequest;
import com.github.ewallet.dto.response.CommandResponse;
import com.github.ewallet.dto.response.TransactionResponse;
import com.github.ewallet.exception.NoSuchElementFoundException;
import com.github.ewallet.model.Transaction;
import com.github.ewallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.github.ewallet.common.Constants.*;

/**
 * Service used for Transaction related operations
 */
@Slf4j(topic = "TransactionService")
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionRequestMapper transactionRequestMapper;
    private final TransactionResponseMapper transactionResponseMapper;

    /**
     * Fetches a single transaction by the given id
     *
     * @param id
     * @return TransactionResponse
     */
    @Transactional(readOnly = true)
    public TransactionResponse findById(long id) {
        return transactionRepository.findById(id)
                .map(transactionResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_TRANSACTION));
    }

    /**
     * Fetches a single transaction by the given referenceNumber
     *
     * @param referenceNumber
     * @return TransactionResponse
     */
    @Transactional(readOnly = true)
    public TransactionResponse findByReferenceNumber(UUID referenceNumber) {
        return transactionRepository.findByReferenceNumber(referenceNumber)
                .map(transactionResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_TRANSACTION));
    }

    /**
     * Fetches all transaction by the given userId
     *
     * @param userId
     * @return List of TransactionResponse
     */
    @Transactional(readOnly = true)
    public List<TransactionResponse> findAllByUserId(Long userId) {
        final List<TransactionResponse> transactions = transactionRepository.findAllByUserId(userId).stream()
                .map(transactionResponseMapper::toDto)
                .toList();

        if (transactions.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return transactions;
    }

    /**
     * Fetches all transactions based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of TransactionResponse
     */
    @Transactional(readOnly = true)
    public Page<TransactionResponse> findAll(Pageable pageable) {
        final Page<TransactionResponse> transactions = transactionRepository.findAll(pageable)
                .map(transactionResponseMapper::toDto);
        if (transactions.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return transactions;
    }

    /**
     * Creates a new transaction using the given request parameters
     *
     * @param request
     * @return id of the created transaction
     */
    public CommandResponse create(TransactionRequest request) {
        final Transaction transaction = transactionRequestMapper.toEntity(request);
        transactionRepository.save(transaction);
        log.info(CREATED_TRANSACTION, new Object[]{transaction.getFromWallet().getIban(), transaction.getToWallet().getIban(), transaction.getAmount()});
        return CommandResponse.builder().id(transaction.getId()).build();
    }
}
