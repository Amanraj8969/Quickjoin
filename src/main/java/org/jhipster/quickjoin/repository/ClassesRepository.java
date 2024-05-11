package org.jhipster.quickjoin.repository;

import org.jhipster.quickjoin.domain.Classes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Classes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassesRepository extends ReactiveCrudRepository<Classes, Long>, ClassesRepositoryInternal {
    @Override
    <S extends Classes> Mono<S> save(S entity);

    @Override
    Flux<Classes> findAll();

    @Override
    Mono<Classes> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ClassesRepositoryInternal {
    <S extends Classes> Mono<S> save(S entity);

    Flux<Classes> findAllBy(Pageable pageable);

    Flux<Classes> findAll();

    Mono<Classes> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Classes> findAllBy(Pageable pageable, Criteria criteria);
}
