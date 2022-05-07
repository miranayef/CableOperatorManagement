package com.example.SAPproject2.Services;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Entities.Enums.Type;
import com.example.SAPproject2.Exceptions.NotFoundException;
import com.example.SAPproject2.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories()
    {
        return (List<Category>) categoryRepository.findAll();
    }


    public Category getCategoryById(int id) {
        boolean exists = categoryRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("category with id "+ id + " doesn't exist");
        }
        return categoryRepository.findById(id).get();
    }

    public void delete(int id) {
        boolean exists = categoryRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("customer with id "+ id + " doesn't exist");

        }
        categoryRepository.deleteById(id);
    }

    public void saveOrUpdate(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(int id, Type type, BigDecimal price, List<TVchannels> tVchannels, List<Packets> packets){
        Category category  = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "category with id " + id + " doesn't exist"));
        if (type != null && !Objects.equals(category.getType(), type)) {
            category.setType(type);
        }
        if (price != null &&  !Objects.equals(category.getPrice(), price)) {
            category.setPrice(price);
        }
        if (tVchannels != null && !Objects.equals(category.getTVchannels(), tVchannels)) {
            category.setTVchannels(tVchannels);
        }
        if (packets != null && !Objects.equals(category.getPackets(), packets)) {
            category.setPackets(packets);
        }

    }
}
