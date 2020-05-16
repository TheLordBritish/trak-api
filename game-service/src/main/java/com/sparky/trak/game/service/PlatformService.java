package com.sparky.trak.game.service;

import com.sparky.trak.game.domain.Platform;
import com.sparky.trak.game.repository.specification.PlatformSpecification;
import com.sparky.trak.game.service.dto.PlatformDto;
import com.sparky.trak.game.service.dto.GameDto;
import org.springframework.data.domain.Pageable;

import javax.json.JsonMergePatch;

/**
 * The {@link PlatformService} follows the basic CRUD principle for interaction with {@link Platform} entities on the persistence layer.
 * However, the {@link PlatformService} builds an additional layer of abstraction, with the primary purpose being checking the validity of
 * {@link Platform} data being requested from the persistence layer, as well as encapsulating any domain-based objects into {@link PlatformDto}
 * transfer objects, for additional validation and protection.
 *
 * The {@link PlatformService} still follows the practise in that it will not catch or handle exceptions thrown by the persistence layer,
 * instead it will wrap them in a more reasonable response and propagate the exception to the callee.
 *
 * @since 1.0.0
 * @author Sparky Studios
 */
public interface PlatformService {

    /**
     * Given a {@link PlatformDto} instance, the service will attempt to persist the data with the underlying persistence layer.
     * If the {@link PlatformDto} supplied contains an Id that matches an existing entity, insertion to the persistence layer will
     * fail and a {@link javax.persistence.EntityExistsException} will be thrown. If persistence succeeds, the data is saved and the
     * saved entity is returned as a {@link PlatformDto}.
     *
     * The method does not allow <code>null</code> entities to be inserted, if null is provided, a {@link NullPointerException}
     * will be thrown.
     *
     * @param platformDto The {@link PlatformDto} instance to persist.
     *
     * @return The updated persisted entity as a {@link PlatformDto}.
     */
    PlatformDto save(PlatformDto platformDto);

    /**
     * Given an ID of a {@link Platform} entity, this service method will query the underlying persistence layer and try and
     * retrieve the {@link Platform} entity that matches the given ID and map it to a {@link PlatformDto}. If the Id provided does not
     * map to any known {@link Platform} entity, then an exception will be thrown specifying that it can't be found.
     *
     * @param id The ID of the {@link Platform} entity to try and retrieve.
     *
     * @return The {@link Platform} entity matching the ID mapped to a {@link PlatformDto}.
     */
    PlatformDto findById(long id);

    /**
     * Retrieves all of the {@link Platform} entities stored within the persistence layer. This method should not be used within a live
     * environment, as the amount of data may cause buffer overflows and bring down the server. It should only be used within a test
     * environment and even then, with hesitancy.
     *
     * @return All of the {@link Platform} entities contained within the persistence layer, wrapped as {@link PlatformDto}s.
     */
    Iterable<PlatformDto> findAll();

    /**
     * This method will retrieve an {@link Iterable} of {@link PlatformDto} with a response size specified by the {@link Pageable}
     * that are referenced by the specified {@link GameDto} ID.
     *
     * @param gameId The ID of the {@link GameDto} to retrieve paged platform data for.
     * @param pageable The size and page of data to return.
     *
     * @return An {@link Iterable} of relevant queried {@link PlatformDto} instances.
     */
    Iterable<PlatformDto> findPlatformsByGameId(long gameId, Pageable pageable);

    /**
     * This method will retrieve an {@link Iterable} of {@link PlatformDto} with a response size specified by the {@link Pageable}. The
     * results can be queried and filtered by utilising the exposed specifications on the {@link PlatformSpecification} object. If the response
     * from the specifications is that none match, an empty {@link Iterable} will be returned.
     *
     * The {@link PlatformSpecification} argument can be omitted and is optional, however if the callee provides <code>null</code> for the
     * {@link Pageable}, an exception will be thrown.
     *
     * @param platformSpecification The {@link PlatformSpecification} to filter the query by.
     * @param pageable The size and page of data to return.
     *
     * @return An {@link Iterable} of relevant queried {@link PlatformDto} instances.
     */
    Iterable<PlatformDto> findAll(PlatformSpecification platformSpecification, Pageable pageable);

    /**
     * Given a {@link PlatformDto} instance, the service will attempt to the update the persisted data which matches the given {@link PlatformDto}
     * in the underlying persistence layer. If the {@link PlatformDto} supplied contains an ID that doesn't match any existing entities, then
     * the update will fail and a {@link javax.persistence.EntityNotFoundException} will be thrown. If persistence succeeds, the relevant
     * record is updated and the updated entity is returned as a {@link PlatformDto}.
     *
     * The method does not allow <code>null</code> entities to be inserted, if null is provided, a {@link NullPointerException}
     * will be thrown.
     *
     * @param platformDto The {@link PlatformDto} instance to update.
     *
     * @return The updated persisted entity as a {@link PlatformDto}.
     */
    PlatformDto update(PlatformDto platformDto);

    /**
     * Given a {@link JsonMergePatch} which will contain JSON information pertaining to a {@link PlatformDto}, this method will attempt to retrieve
     * the {@link PlatformDto} that matches the given ID and apply the new JSON on top of it. If the ID provided doesn't match any existing entities,
     * then the patch will fail and a {@link javax.persistence.EntityNotFoundException} will be thrown. If the {@link JsonMergePatch} contains any
     * JSON data not contained on the {@link PlatformDto}, it'll be ignored. If the patching succeeds, the patched record will be updated and the updated
     * entity returned as a {@link PlatformDto}.
     *
     * The method does not allow a null {@link JsonMergePatch} to be provided, if null is provided, a {@link NullPointerException} will be thrown.
     *
     * @param id The ID of the {@link PlatformDto} to patch.
     * @param jsonMergePatch The {@link JsonMergePatch} containing the JSON data to patch.
     *
     * @return The patched persisted entity as a {@link PlatformDto}.
     */
    PlatformDto patch(long id, JsonMergePatch jsonMergePatch);

    /**
     * Deletes the persisted entity that is mapped to the given ID. If the service cannot find a {@link PlatformDto} that is mapped to the ID,
     * then deletion will not occur and a {@link javax.persistence.EntityNotFoundException} exception will be thrown. Deletion can be
     * classes as successful if the method completes without throwing additional errors.
     *
     * @param id The ID of the {@link PlatformDto} to delete.
     */
    void deleteById(long id);
}
