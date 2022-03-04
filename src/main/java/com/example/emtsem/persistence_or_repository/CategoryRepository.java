package com.example.emtsem.persistence_or_repository;

import com.example.emtsem.model.Category;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Profile("!in-memory")
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
