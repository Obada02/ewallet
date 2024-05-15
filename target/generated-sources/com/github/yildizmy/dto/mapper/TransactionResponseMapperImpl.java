package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.response.RoleResponse;
import com.github.yildizmy.dto.response.TransactionResponse;
import com.github.yildizmy.dto.response.TypeResponse;
import com.github.yildizmy.dto.response.UserResponse;
import com.github.yildizmy.dto.response.WalletResponse;
import com.github.yildizmy.model.Role;
import com.github.yildizmy.model.Transaction;
import com.github.yildizmy.model.Type;
import com.github.yildizmy.model.User;
import com.github.yildizmy.model.Wallet;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-11T20:14:44+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class TransactionResponseMapperImpl implements TransactionResponseMapper {

    @Override
    public Transaction toEntity(TransactionResponse dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setId( dto.getId() );
        transaction.setAmount( dto.getAmount() );
        transaction.setDescription( dto.getDescription() );
        if ( dto.getCreatedAt() != null ) {
            transaction.setCreatedAt( Instant.parse( dto.getCreatedAt() ) );
        }
        transaction.setReferenceNumber( dto.getReferenceNumber() );
        transaction.setStatus( dto.getStatus() );
        transaction.setFromWallet( walletResponseToWallet( dto.getFromWallet() ) );
        transaction.setToWallet( walletResponseToWallet( dto.getToWallet() ) );
        transaction.setType( typeResponseToType( dto.getType() ) );

        return transaction;
    }

    @Override
    public TransactionResponse toDto(Transaction entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionResponse transactionResponse = new TransactionResponse();

        transactionResponse.setId( entity.getId() );
        transactionResponse.setAmount( entity.getAmount() );
        transactionResponse.setDescription( entity.getDescription() );
        transactionResponse.setReferenceNumber( entity.getReferenceNumber() );
        transactionResponse.setStatus( entity.getStatus() );
        transactionResponse.setFromWallet( walletToWalletResponse( entity.getFromWallet() ) );
        transactionResponse.setToWallet( walletToWalletResponse( entity.getToWallet() ) );
        transactionResponse.setType( typeToTypeResponse( entity.getType() ) );

        formatCreatedAt( transactionResponse, entity );

        return transactionResponse;
    }

    protected Role roleResponseToRole(RoleResponse roleResponse) {
        if ( roleResponse == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleResponse.getId() );
        role.setType( roleResponse.getType() );

        return role;
    }

    protected Set<Role> roleResponseSetToRoleSet(Set<RoleResponse> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new LinkedHashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleResponse roleResponse : set ) {
            set1.add( roleResponseToRole( roleResponse ) );
        }

        return set1;
    }

    protected User userResponseToUser(UserResponse userResponse) {
        if ( userResponse == null ) {
            return null;
        }

        User user = new User();

        user.setId( userResponse.getId() );
        user.setFirstName( userResponse.getFirstName() );
        user.setLastName( userResponse.getLastName() );
        user.setUsername( userResponse.getUsername() );
        user.setEmail( userResponse.getEmail() );
        user.setRoles( roleResponseSetToRoleSet( userResponse.getRoles() ) );

        return user;
    }

    protected Wallet walletResponseToWallet(WalletResponse walletResponse) {
        if ( walletResponse == null ) {
            return null;
        }

        Wallet wallet = new Wallet();

        wallet.setId( walletResponse.getId() );
        wallet.setIban( walletResponse.getIban() );
        wallet.setName( walletResponse.getName() );
        wallet.setBalance( walletResponse.getBalance() );
        wallet.setUser( userResponseToUser( walletResponse.getUser() ) );

        return wallet;
    }

    protected Type typeResponseToType(TypeResponse typeResponse) {
        if ( typeResponse == null ) {
            return null;
        }

        Type type = new Type();

        type.setId( typeResponse.getId() );
        type.setName( typeResponse.getName() );
        type.setDescription( typeResponse.getDescription() );

        return type;
    }

    protected RoleResponse roleToRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setId( role.getId() );
        roleResponse.setType( role.getType() );

        return roleResponse;
    }

    protected Set<RoleResponse> roleSetToRoleResponseSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponse> set1 = new LinkedHashSet<RoleResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleResponse( role ) );
        }

        return set1;
    }

    protected UserResponse userToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.getId() );
        userResponse.setFirstName( user.getFirstName() );
        userResponse.setLastName( user.getLastName() );
        userResponse.setUsername( user.getUsername() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setRoles( roleSetToRoleResponseSet( user.getRoles() ) );

        return userResponse;
    }

    protected WalletResponse walletToWalletResponse(Wallet wallet) {
        if ( wallet == null ) {
            return null;
        }

        WalletResponse walletResponse = new WalletResponse();

        walletResponse.setId( wallet.getId() );
        walletResponse.setIban( wallet.getIban() );
        walletResponse.setName( wallet.getName() );
        walletResponse.setBalance( wallet.getBalance() );
        walletResponse.setUser( userToUserResponse( wallet.getUser() ) );

        return walletResponse;
    }

    protected TypeResponse typeToTypeResponse(Type type) {
        if ( type == null ) {
            return null;
        }

        TypeResponse typeResponse = new TypeResponse();

        typeResponse.setId( type.getId() );
        typeResponse.setName( type.getName() );
        typeResponse.setDescription( type.getDescription() );

        return typeResponse;
    }
}
