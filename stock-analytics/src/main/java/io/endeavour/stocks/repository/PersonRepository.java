package io.endeavour.stocks.repository;

import io.endeavour.stocks.entity.PersonEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
    @Override
    @EntityGraph(attributePaths = "addresses")
    List<PersonEntity> findAll();
}
