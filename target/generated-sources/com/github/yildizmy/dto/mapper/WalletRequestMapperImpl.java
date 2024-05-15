package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.WalletRequest;
import com.github.yildizmy.model.Wallet;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-11T20:14:44+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class WalletRequestMapperImpl extends WalletRequestMapper {

    @Override
    public Wallet toEntity(WalletRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Wallet wallet = new Wallet();

        wallet.setId( dto.getId() );
        wallet.setBalance( dto.getBalance() );

        wallet.setName( org.apache.commons.text.WordUtils.capitalizeFully(dto.getName()) );
        wallet.setIban( org.apache.commons.lang3.StringUtils.upperCase(dto.getIban()) );

        setToEntityFields( wallet, dto );

        return wallet;
    }

    @Override
    public WalletRequest toDto(Wallet entity) {
        if ( entity == null ) {
            return null;
        }

        WalletRequest walletRequest = new WalletRequest();

        walletRequest.setId( entity.getId() );
        walletRequest.setIban( entity.getIban() );
        walletRequest.setName( entity.getName() );
        walletRequest.setBalance( entity.getBalance() );

        return walletRequest;
    }
}
