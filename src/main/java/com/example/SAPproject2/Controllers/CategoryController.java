package com.example.SAPproject2.Controllers;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Entities.Enums.Type;
import com.example.SAPproject2.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "{id}")
    public Category getCategory(@PathVariable("id") int id){
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping(path = "{id}")
    private void deleteCategory(@PathVariable("id") int id)
    {
        categoryService.delete(id);
    }

    @PostMapping
    private ResponseEntity<String> addNewCategory(@RequestBody Category category)
    {
        categoryService.saveOrUpdate(category);
        return ResponseEntity.ok("Added category successfully");

    }

    @PutMapping(path = "{id}")
    public void updateCategory(@PathVariable("id") int id,
                               @RequestParam(required = false) Type type,
                               @RequestParam(required = false) BigDecimal price,
                               @RequestParam(required = false) List<TVchannels> tVchannels,
                               @RequestParam(required = false)  List<Packets> packets){
        categoryService.updateCategory(id, type, price,tVchannels,packets);
    }
}