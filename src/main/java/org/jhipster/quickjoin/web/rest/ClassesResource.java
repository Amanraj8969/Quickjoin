package org.jhipster.quickjoin.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.jhipster.quickjoin.domain.Classes;
import org.jhipster.quickjoin.repository.ClassesRepository;
import org.jhipster.quickjoin.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.quickjoin.domain.Classes}.
 */
@RestController
@RequestMapping("/api/classes")
@Transactional
public class ClassesResource {

    private final Logger log = LoggerFactory.getLogger(ClassesResource.class);

    private static final String ENTITY_NAME = "classes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassesRepository classesRepository;

    public ClassesResource(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }

    /**
     * {@code POST  /classes} : Create a new classes.
     *
     * @param classes the classes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classes, or with status {@code 400 (Bad Request)} if the classes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<Classes>> createClasses(@Valid @RequestBody Classes classes) throws URISyntaxException {
        log.debug("REST request to save Classes : {}", classes);
        if (classes.getId() != null) {
            throw new BadRequestAlertException("A new classes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return classesRepository
            .save(classes)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/classes/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /classes/:id} : Updates an existing classes.
     *
     * @param id the id of the classes to save.
     * @param classes the classes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classes,
     * or with status {@code 400 (Bad Request)} if the classes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Classes>> updateClasses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Classes classes
    ) throws URISyntaxException {
        log.debug("REST request to update Classes : {}, {}", id, classes);
        if (classes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return classesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return classesRepository
                    .save(classes)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(
                        result ->
                            ResponseEntity.ok()
                                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                                .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /classes/:id} : Partial updates given fields of an existing classes, field will ignore if it is null
     *
     * @param id the id of the classes to save.
     * @param classes the classes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classes,
     * or with status {@code 400 (Bad Request)} if the classes is not valid,
     * or with status {@code 404 (Not Found)} if the classes is not found,
     * or with status {@code 500 (Internal Server Error)} if the classes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Classes>> partialUpdateClasses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Classes classes
    ) throws URISyntaxException {
        log.debug("REST request to partial update Classes partially : {}, {}", id, classes);
        if (classes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return classesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Classes> result = classesRepository
                    .findById(classes.getId())
                    .map(existingClasses -> {
                        if (classes.getTitle() != null) {
                            existingClasses.setTitle(classes.getTitle());
                        }
                        if (classes.getDescription() != null) {
                            existingClasses.setDescription(classes.getDescription());
                        }
                        if (classes.getTecher_name() != null) {
                            existingClasses.setTecher_name(classes.getTecher_name());
                        }
                        if (classes.getPrice() != null) {
                            existingClasses.setPrice(classes.getPrice());
                        }
                        if (classes.getLocation() != null) {
                            existingClasses.setLocation(classes.getLocation());
                        }
                        if (classes.getDuration() != null) {
                            existingClasses.setDuration(classes.getDuration());
                        }

                        return existingClasses;
                    })
                    .flatMap(classesRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(
                        res ->
                            ResponseEntity.ok()
                                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, res.getId().toString()))
                                .body(res)
                    );
            });
    }

    /**
     * {@code GET  /classes} : get all the classes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classes in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Classes>> getAllClasses() {
        log.debug("REST request to get all Classes");
        return classesRepository.findAll().collectList();
    }

    /**
     * {@code GET  /classes} : get all the classes as a stream.
     * @return the {@link Flux} of classes.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Classes> getAllClassesAsStream() {
        log.debug("REST request to get all Classes as a stream");
        return classesRepository.findAll();
    }

    /**
     * {@code GET  /classes/:id} : get the "id" classes.
     *
     * @param id the id of the classes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Classes>> getClasses(@PathVariable("id") Long id) {
        log.debug("REST request to get Classes : {}", id);
        Mono<Classes> classes = classesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classes);
    }

    /**
     * {@code DELETE  /classes/:id} : delete the "id" classes.
     *
     * @param id the id of the classes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClasses(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classes : {}", id);
        return classesRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
