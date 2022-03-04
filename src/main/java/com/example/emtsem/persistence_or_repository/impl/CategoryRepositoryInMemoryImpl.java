package com.example.emtsem.persistence_or_repository.impl;

import com.example.emtsem.model.Category;
import com.example.emtsem.persistence_or_repository.CategoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("in-memory")
public class CategoryRepositoryInMemoryImpl implements CategoryRepository {

    private HashMap<Long, Category> categories;
    private AtomicLong counter;

    public CategoryRepositoryInMemoryImpl() {
        this.categories = new HashMap<>();
        this.counter = new AtomicLong(0);
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(this.categories.values());
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Category> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends Category> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Category> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Category> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Category getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Category> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Category> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Category> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Category> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Category> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Category> boolean exists(Example<S> example) {
        return false;
    }


    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(this.categories.get(id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(this.counter.getAndIncrement());
        }
        this.categories.put(category.getId(), category);
        return category;
    }

    @Override
    public void deleteById(Long id) {
        this.categories.remove(id);
    }

    @Override
    public void delete(Category category) {

    }

    @Override
    public void deleteAll(Iterable<? extends Category> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
