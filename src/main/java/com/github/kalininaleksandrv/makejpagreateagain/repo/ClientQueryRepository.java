package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ClientQueryRepository extends JpaRepository<Client, Integer> {

    /*
    example of sub-query with "exist" keyword according Vlad Mihalcea instead of "distinct":
    Since the projection contains only the Client entity, the JOIN is not needed in this case.
    Instead, a SemiJoin should be used to filter the Post entity records.
    Explanation distinct vs exist on SO: Whenever possible, you should use EXISTS rather than DISTINCT
    because DISTINCT sorts the retrieved rows before suppressing the duplicate rows.
    ---
    this request also possible with jpql and criteria API
    https://vladmihalcea.com/exists-subqueries-jpa-hibernate/
     */
    @Query("select c from Client c where exists ( " +
            "select 1 from Account ac where ac.client = c and ac.amount > :amount) " +
            "order by c.id")
    List<Client> getAllWithAmountMoreThen(@Param("amount") BigDecimal amount);
}
