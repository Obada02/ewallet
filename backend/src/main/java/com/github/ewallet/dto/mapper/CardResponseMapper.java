package com.github.ewallet.dto.mapper;

import com.github.ewallet.dto.response.CardResponse;
import com.github.ewallet.dto.response.UserResponse;
import com.github.ewallet.model.Card;
import com.github.ewallet.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.text.MessageFormat;

/**
 * Mapper used for mapping CardResponse fields
 */
@Mapper(componentModel = "spring")
public interface CardResponseMapper {

    Card toEntity(CardResponse dto);

    CardResponse toDto(Card entity);

    @AfterMapping
    default void setFullName(@MappingTarget UserResponse dto, User entity) {
        dto.setFullName(MessageFormat.format("{0} {1}", entity.getFirstName(), entity.getLastName()));
    }
}
