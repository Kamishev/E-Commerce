package com.example.emtsem.service_or_business;

import com.example.emtsem.model.Shoe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShoeService {
    List<Shoe> findAll();
    List<Shoe> findAllByCategoryId(Long categoryId);
    List<Shoe> findAllSortedByPrice(boolean asc);
    Shoe findById(Long id);
    Shoe saveShoe(String name, Float price, Integer quantity, Long categoryId);
    Shoe saveShoe(Shoe shoe, MultipartFile image) throws IOException;
    Shoe updateShoe(Long id, Shoe shoe, MultipartFile image) throws IOException;
    void deleteById(Long id);
}
