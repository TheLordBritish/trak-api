package com.sparkystudios.traklibrary.game.repository;

import com.sparkystudios.traklibrary.game.domain.Platform;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformRepository extends PagingAndSortingRepository<Platform, Long>, JpaSpecificationExecutor<Platform> {

    Optional<Platform> findBySlug(String slug);
}
