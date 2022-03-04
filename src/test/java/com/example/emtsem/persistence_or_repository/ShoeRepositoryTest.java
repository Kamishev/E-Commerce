package com.example.emtsem.persistence_or_repository;

import com.example.emtsem.model.Category;
import com.example.emtsem.model.Shoe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShoeRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ShoeRepository shoeRepository;

    @BeforeAll
    public void init() {
        Category c = new Category();
        c.setId(1l);
        c.setName("name1");
        c = categoryRepository.save(c);

        Shoe s1,s2,s3;
        s1 = new Shoe();
        s1.setQuantity(5);
        s1.setPrice(5f);
        s1.setCategory(c);

        s2 = new Shoe();
        s2.setQuantity(2);
        s2.setPrice(2f);
        s2.setCategory(c);

        s3 = new Shoe();
        s3.setQuantity(3);
        s3.setPrice(3f);
        s3.setCategory(c);
        this.shoeRepository.save(s1);
        this.shoeRepository.save(s2);
        this.shoeRepository.save(s3);
    }

    @Test
    void findAllByOrderByPriceAsc() {
        List<Shoe> shoes = this.shoeRepository.findAllByOrderByPriceAsc();
        assert shoes.get(0).getPrice().equals(2f);
        assert shoes.get(1).getPrice().equals(3f);
        assert shoes.get(2).getPrice().equals(5f);
    }

    @Test
    void findAllByOrderByPriceDesc() {
        List<Shoe> shoes = this.shoeRepository.findAllByOrderByPriceDesc();
        assert shoes.get(0).getPrice().equals(5f);
        assert shoes.get(1).getPrice().equals(3f);
        assert shoes.get(2).getPrice().equals(2f);
    }

    @Test
    void countAllByPriceGreaterThan() {
        long count = this.shoeRepository.countAllByPriceGreaterThan(4f);
        assert count == 1;
    }

    @Test
    void findAllByCategoryId() {
        List<Shoe> shoes = this.shoeRepository.findAllByCategoryId(1l);
        assert shoes.size() == 3;
    }

    @Test
    void findAllByNameLikeAndCategoryIdOrderByPriceDesc() {
    }
}