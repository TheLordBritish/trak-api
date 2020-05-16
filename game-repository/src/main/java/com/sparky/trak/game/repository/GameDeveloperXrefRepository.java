package com.sparky.trak.game.repository;

import com.sparky.trak.game.domain.GameDeveloperXref;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameDeveloperXrefRepository extends PagingAndSortingRepository<GameDeveloperXref, Long>, JpaSpecificationExecutor<GameDeveloperXref> {
}
