package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.TransactionRequest;
import com.github.yildizmy.model.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-11T20:14:44+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class TransactionRequestMapperImpl extends TransactionRequestMapper {

    @Override
    public Transaction toEntity(TransactionRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        if ( dto.getCreatedAt() != null ) {
            transaction.setCreatedAt( dto.getCreatedAt() );
        }
        else {
            transaction.setCreatedAt( java.time.Instant.now() );
        }
        transaction.setId( dto.getId() );
        transaction.setAmount( dto.getAmount() );
        transaction.setDescription( dto.getDescription() );

        transaction.setStatus( com.github.yildizmy.model.Status.SUCCESS );
        transaction.setReferenceNumber( java.util.UUID.randomUUID() );

        setToEntityFields( transaction, dto );

        return transaction;
    }

    @Override
    public TransactionRequest toDto(Transaction entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionRequest transactionRequest = new TransactionRequest();

        transactionRequest.setId( entity.getId() );
        transactionRequest.setAmount( entity.getAmount() );
        transactionRequest.setDescription( entity.getDescription() );
        transactionRequest.setCreatedAt( entity.getCreatedAt() );
        transactionRequest.setReferenceNumber( entity.getReferenceNumber() );
        transactionRequest.setStatus( entity.getStatus() );

        return transactionRequest;
    }
}
