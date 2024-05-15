package com.github.ewallet.dto.mapper;

import com.github.ewallet.dto.request.CardRequest;
import com.github.ewallet.model.Card;
import com.github.ewallet.service.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper used for mapping CardRequest fields
 */
@Mapper(componentModel = "spring",
        uses = {UserService.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CardRequestMapper {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Mapping(target = "credentials", expression = "java(org.apache.commons.lang3.StringUtils.upperCase(dto.getCredentials()))")
    @Mapping(target = "user", ignore = true)
    public abstract Card toEntity(CardRequest dto);

    public abstract CardRequest toDto(Card entity);

    @AfterMapping
    void setToEntityFields(@MappingTarget Card entity, CardRequest dto) {
        entity.setUser(userService.getReferenceById(dto.getUserId()));
    }
}
