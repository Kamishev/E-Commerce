package com.example.emtsem.web_or_presentation.rest_controller;

import com.example.emtsem.model.Shoe;
import com.example.emtsem.service_or_business.ShoeService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/shoes")
public class ShoeRestController {
    private final ShoeService shoeService;

    public ShoeRestController(ShoeService shoeService) {
        this.shoeService = shoeService;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<Shoe> findAll() {
        return this.shoeService.findAll();
    }

    @GetMapping("/{id}")
    public Shoe findById(@PathVariable Long id) {
        return this.shoeService.findById(id);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<Shoe> findAllByCategoryId(@PathVariable Long categoryId) {
        return this.shoeService.findAllByCategoryId(categoryId);
    }

    @GetMapping("/by-price")
    public List<Shoe> findAllSortedByPrice(
            @RequestParam (required = false, defaultValue = "true") Boolean asc) {
        return this.shoeService.findAllSortedByPrice(asc);
    }


    @PostMapping
    public Shoe save(@Valid Shoe shoe, @RequestParam(required = false) MultipartFile image) throws IOException {
        return this.shoeService.saveShoe(shoe, image);
    }


    @PutMapping("/{id}")
    public Shoe update(@PathVariable Long id,
                          @Valid Shoe shoe,
                          @RequestParam(required = false) MultipartFile image) throws IOException {
        return this.shoeService.updateShoe(id, shoe, image);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.shoeService.deleteById(id);
    }

}
