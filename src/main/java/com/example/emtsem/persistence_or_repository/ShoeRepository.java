package com.example.emtsem.persistence_or_repository;

import com.example.emtsem.model.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Long> {

    List<Shoe> findAllByOrderByPriceAsc();



    List<Shoe> findAllByOrderByPriceDesc();


    long countAllByPriceGreaterThan(@Param("price") Float price);

    List<Shoe> findAllByPriceGreaterThan(@Param("price") Float price);



    List<Shoe> findAllByCategoryId(@Param("id") Long id);
    List<Shoe> findAllByNameLikeAndCategoryIdOrderByPriceDesc(String name, Long categoryId);
}
