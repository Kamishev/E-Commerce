package com.example.emtsem.service_or_business.impl;

import com.example.emtsem.model.Category;
import com.example.emtsem.model.Shoe;
import com.example.emtsem.model.exception.ShoeIsAlreadyInShoppingCartException;
import com.example.emtsem.model.exception.ShoeNotFoundException;
import com.example.emtsem.persistence_or_repository.ShoeRepository;
import com.example.emtsem.service_or_business.CategoryService;
import com.example.emtsem.service_or_business.ShoeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;
    private final CategoryService categoryService;

    public ShoeServiceImpl(ShoeRepository shoeRepository,
                              CategoryService categoryService) {
        this.shoeRepository = shoeRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Shoe> findAll() {
        return this.shoeRepository.findAll();
    }

    @Override
    public List<Shoe> findAllByCategoryId(Long categoryId) {
        return this.shoeRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Shoe> findAllSortedByPrice(boolean asc) {
        if (asc) {
            return this.shoeRepository.findAllByOrderByPriceAsc();
        }
        return this.shoeRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public Shoe findById(Long id) {
        return this.shoeRepository.findById(id)
                .orElseThrow(() -> new ShoeNotFoundException(id));
    }

    @Override
    public Shoe saveShoe(String name, Float price, Integer quantity, Long categoryId) {
        Category category = this.categoryService.findById(categoryId);
        Shoe shoe = new Shoe(null, name, price, quantity, category);
        return this.shoeRepository.save(shoe);
    }

    @Override
    public Shoe saveShoe(Shoe shoe, MultipartFile image) throws IOException {
        Category category = this.categoryService.findById(shoe.getCategory().getId());
        shoe.setCategory(category);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
//            product.setImage(image);
            shoe.setImageBase64(base64Image);
        }
        return this.shoeRepository.save(shoe);
    }

    @Override
    public Shoe updateShoe(Long id, Shoe shoe, MultipartFile image) throws IOException {
        Shoe s = this.findById(id);
        Category category = this.categoryService.findById(shoe.getCategory().getId());
        s.setCategory(category);
        s.setPrice(shoe.getPrice());
        s.setQuantity(shoe.getQuantity());
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            s.setImageBase64(base64Image);
        }
        return this.shoeRepository.save(s);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Shoe shoe = this.findById(id);

        this.shoeRepository.deleteById(id);
    }
}
