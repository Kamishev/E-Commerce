package com.example.emtsem.web_or_presentation.controller;

import com.example.emtsem.model.Category;
import com.example.emtsem.model.Shoe;
import com.example.emtsem.model.exception.ShoeIsAlreadyInShoppingCartException;
import com.example.emtsem.service_or_business.CategoryService;
import com.example.emtsem.service_or_business.ShoeService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/shoes")
public class ShoeController {


    private ShoeService shoeService;
    private CategoryService categoryService;


    public ShoeController(ShoeService shoeService,
                          CategoryService categoryService) {
        this.shoeService = shoeService;
        this.categoryService = categoryService;
    }


    @GetMapping
    public String getProductPage(Model model) {
        List<Shoe> shoes = this.shoeService.findAll();
        model.addAttribute("shoes", shoes);
        return "shoes";
    }



    @GetMapping("/add-new")
    public String addNewShoePage(Model model) {
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("shoe", new Shoe());

        return "add-shoe";
    }

    @GetMapping("/{id}/edit")
    public String editShoePage(Model model, @PathVariable Long id) {
        try {
            Shoe shoe = this.shoeService.findById(id);
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("shoe", shoe);
            model.addAttribute("categories", categories);
            return "add-shoe";
        } catch (RuntimeException ex) {
            return "redirect:/shoes?error=" + ex.getMessage();
        }
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public String saveShoe(

            @Valid Shoe shoe,
            BindingResult bindingResult,
            @RequestParam MultipartFile image,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            return "add-shoe";
        }
        try {
            this.shoeService.saveShoe(shoe, image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/shoes";
    }

    @PostMapping("/{id}/delete")
    public String deleteProductWithPost(@PathVariable Long id) {
        try {
            this.shoeService.deleteById(id);
        } catch (ShoeIsAlreadyInShoppingCartException ex) {
            return String.format("redirect:/shoes?error=%s", ex.getMessage());
        }
        return "redirect:/shoes";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteShoeWithDelete(@PathVariable Long id) {
        this.shoeService.deleteById(id);
        return "redirect:/shoes";
    }
}
