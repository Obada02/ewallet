package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.TransactionRequest;
import com.github.yildizmy.dto.request.WalletRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-11T20:14:44+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class WalletTransactionRequestMapperImpl implements WalletTransactionRequestMapper {

    @Override
    public TransactionRequest toTransactionDto(WalletRequest entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionRequest transactionRequest = new TransactionRequest();

        transactionRequest.setAmount( entity.getBalance() );
        transactionRequest.setFromWalletIban( entity.getIban() );
        transactionRequest.setToWalletIban( entity.getIban() );
        transactionRequest.setId( entity.getId() );

        transactionRequest.setDescription( "Initial balance" );
        transactionRequest.setTypeId( (long) 1L );

        return transactionRequest;
    }

    @Override
    public WalletRequest toWalletDto(TransactionRequest dto) {
        if ( dto == null ) {
            return null;
        }

        WalletRequest walletRequest = new WalletRequest();

        walletRequest.setId( dto.getId() );

        return walletRequest;
    }
}
