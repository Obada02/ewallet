package com.github.ewallet.controller;

import com.github.ewallet.dto.request.CardRequest;
import com.github.ewallet.dto.response.ApiResponse;
import com.github.ewallet.dto.response.CommandResponse;
import com.github.ewallet.dto.response.CardResponse;
import com.github.ewallet.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static com.github.ewallet.common.Constants.SUCCESS;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final Clock clock;
    private final CardService cardService;

    /**
     * Fetches a single card by the given id
     *
     * @param id
     * @return CardResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.ewallet.model.RoleType).ROLE_USER)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CardResponse>> findById(@PathVariable long id) {
        final CardResponse response = cardService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single card by the given userId
     *
     * @param userId
     * @return List of CardResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.ewallet.model.RoleType).ROLE_USER)")
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<CardResponse>>> findByUserId(@PathVariable long userId) {
        final List<CardResponse> response = cardService.findByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all cards based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of CardResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.ewallet.model.RoleType).ROLE_USER)")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CardResponse>>> findAll(Pageable pageable) {
        final Page<CardResponse> response = cardService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Creates a new card using the given request parameters
     *
     * @param request
     * @return id of the created card wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.ewallet.model.RoleType).ROLE_USER)")
    @PostMapping
    public ResponseEntity<ApiResponse<CommandResponse>> create(@Valid @RequestBody CardRequest request) {
        final CommandResponse response = cardService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Updates card using the given request parameters
     *
     * @param request
     * @return id of the updated card wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.ewallet.model.RoleType).ROLE_USER)")
    @PutMapping
    public ResponseEntity<ApiResponse<CommandResponse>> update(@Valid @RequestBody CardRequest request) {
        final CommandResponse response = cardService.update(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Deletes card by the given id
     *
     * @param id
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @PreAuthorize("hasRole(T(com.github.ewallet.model.RoleType).ROLE_USER)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable long id) {
        cardService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
