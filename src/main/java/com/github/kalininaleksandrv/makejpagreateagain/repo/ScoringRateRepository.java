package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.ScoringRate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoringRateRepository extends JpaRepository<ScoringRate, Integer> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = ScoringRate.RATE_CLIENT_ENTITY_GRAPH)
    List<ScoringRate> findByRateLessThanEqual(Integer rate);
}
