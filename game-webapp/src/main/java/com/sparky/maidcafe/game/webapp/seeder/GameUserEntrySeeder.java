package com.sparky.maidcafe.game.webapp.seeder;

import com.sparky.maidcafe.game.domain.GameConsoleXref;
import com.sparky.maidcafe.game.domain.GameUserEntryStatus;
import com.sparky.maidcafe.game.repository.GameConsoleXrefRepository;
import com.sparky.maidcafe.game.service.GameUserEntryService;
import com.sparky.maidcafe.game.service.dto.GameUserEntryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Component
public class GameUserEntrySeeder implements Runnable {

    @Value("${seeding.game-user-entries.user-count ?: 1}")
    private int userCount;

    private final GameUserEntryService gameUserEntryService;
    private final GameConsoleXrefRepository gameConsoleXrefRepository;

    @Override
    public void run() {
        Random random = new SecureRandom();

        List<GameConsoleXref> gameConsoleXrefs = StreamSupport.stream(gameConsoleXrefRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparingLong(GameConsoleXref::getGameId))
                .collect(Collectors.toList());

        IntStream.range(0, userCount).forEach(i -> {

            for (GameConsoleXref gameConsoleXref : gameConsoleXrefs) {
                GameUserEntryDto gameUserEntryDto = new GameUserEntryDto();
                gameUserEntryDto.setGameId(gameConsoleXref.getGameId());
                gameUserEntryDto.setConsoleId(gameConsoleXref.getConsoleId());
                gameUserEntryDto.setUserId(i);
                gameUserEntryDto.setRating((short)random.nextInt(6));
                gameUserEntryDto.setStatus(GameUserEntryStatus.values()[random.nextInt(GameUserEntryStatus.values().length)]);

                gameUserEntryService.save(gameUserEntryDto);
            }
        });
    }
}
