package com.sparky.maidcafe.game.service.impl;

import com.sparky.maidcafe.game.repository.GameUserEntryRepository;
import com.sparky.maidcafe.game.repository.specification.GameUserEntrySpecification;
import com.sparky.maidcafe.game.service.GameUserEntryService;
import com.sparky.maidcafe.game.service.PatchService;
import com.sparky.maidcafe.game.service.dto.GameUserEntryDto;
import com.sparky.maidcafe.game.service.mapper.GameUserEntryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.json.JsonMergePatch;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class GameUserEntryServiceImpl implements GameUserEntryService {

    private final GameUserEntryRepository gameUserEntryRepository;
    private final GameUserEntryMapper gameUserEntryMapper;
    private final MessageSource messageSource;
    private final PatchService patchService;

    @Override
    public GameUserEntryDto save(GameUserEntryDto gameUserEntryDto) {
        Objects.requireNonNull(gameUserEntryDto);

        if (gameUserEntryRepository.existsById(gameUserEntryDto.getId())) {
            String errorMessage = messageSource
                    .getMessage("game-user-entry.exception.entity-exists", new Object[] { gameUserEntryDto.getId() }, LocaleContextHolder.getLocale());

            throw new EntityExistsException(errorMessage);
        }

        return gameUserEntryMapper.gameUserEntryToGameUserEntryDto(gameUserEntryRepository
                .save(gameUserEntryMapper.gameUserEntryDtoToGameUserEntry(gameUserEntryDto)));
    }

    @Override
    public GameUserEntryDto findById(long id) {
        String errorMessage = messageSource
                .getMessage("game-user-entry.exception.not-found", new Object[] { id }, LocaleContextHolder.getLocale());

        return gameUserEntryMapper.gameUserEntryToGameUserEntryDto(gameUserEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage)));
    }

    @Override
    public Iterable<GameUserEntryDto> findAll(GameUserEntrySpecification gameUserEntrySpecification, Pageable pageable) {
        return StreamSupport.stream(gameUserEntryRepository.findAll(gameUserEntrySpecification, pageable).spliterator(), false)
                .map(gameUserEntryMapper::gameUserEntryToGameUserEntryDto)
                .collect(Collectors.toList());
    }

    @Override
    public GameUserEntryDto update(GameUserEntryDto gameUserEntryDto) {
        Objects.requireNonNull(gameUserEntryDto);

        if (!gameUserEntryRepository.existsById(gameUserEntryDto.getId())) {
            String errorMessage = messageSource
                    .getMessage("game-user-entry.exception.not-found", new Object[] { gameUserEntryDto.getId() }, LocaleContextHolder.getLocale());

            throw new EntityNotFoundException(errorMessage);
        }

        return gameUserEntryMapper.gameUserEntryToGameUserEntryDto(gameUserEntryRepository
                .save(gameUserEntryMapper.gameUserEntryDtoToGameUserEntry(gameUserEntryDto)));
    }

    @Override
    public GameUserEntryDto patch(long id, JsonMergePatch jsonMergePatch) {
        // Set the new Java object with the patch information.
        GameUserEntryDto patched = patchService.patch(jsonMergePatch, findById(id), GameUserEntryDto.class);
        // Save to the repository and convert it back to a GameDto.
        return gameUserEntryMapper.gameUserEntryToGameUserEntryDto(gameUserEntryRepository
                .save(gameUserEntryMapper.gameUserEntryDtoToGameUserEntry(patched)));
    }

    @Override
    public void deleteById(long id) {
        if (!gameUserEntryRepository.existsById(id)) {
            String errorMessage = messageSource
                    .getMessage("game-user-entry.exception.not-found", new Object[] { id }, LocaleContextHolder.getLocale());

            throw new EntityNotFoundException(errorMessage);
        }

        gameUserEntryRepository.deleteById(id);
    }
}
