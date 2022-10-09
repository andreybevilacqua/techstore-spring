package com.abevilacqua.techstore.repository;

import com.abevilacqua.techstore.model.Product;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import javax.transaction.Transactional;

@Repository
public interface ProductRepoPessimisticLocking extends CrudRepository<Product, Long> {

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findProductForWrite(@Param("id") long id);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findProductForRead(@Param("id") long id);
}
