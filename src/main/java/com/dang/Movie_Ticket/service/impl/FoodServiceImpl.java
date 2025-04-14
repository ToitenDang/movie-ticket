package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.FoodCreationRequest;
import com.dang.Movie_Ticket.dto.request.FoodUpdateRequest;
import com.dang.Movie_Ticket.entity.Food;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.repository.FoodRepository;
import com.dang.Movie_Ticket.service.FoodService;
import com.dang.Movie_Ticket.service.UploadImageFile;
import com.dang.Movie_Ticket.util.enums.FoodStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public  class FoodServiceImpl implements FoodService {
    private final UploadImageFile uploadImageFile;
    private final FoodRepository foodRepository;

    public FoodServiceImpl(UploadImageFile uploadImageFile, FoodRepository foodRepository) {
        this.uploadImageFile = uploadImageFile;
        this.foodRepository = foodRepository;
    }

    @Override
    public String addFood(FoodCreationRequest request, MultipartFile file) throws IOException {
        Food food = Food.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .image(file != null ? uploadImageFile.uploadImage(file) : "khong co anh")
                .status(FoodStatus.AVAILABLE)
                .build();
        this.foodRepository.save(food);
        log.info("Add food");
        return food.getId();
    }

    @Override
    public void updateFood(String foodId, FoodUpdateRequest request) {
        Food food = getFoodById(foodId);
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        if (food.getQuantity() == 0 && request.getQuantity() != 0)
            changeFoodStatus(foodId, FoodStatus.AVAILABLE);
        food.setQuantity(request.getQuantity());
        food.setSold(request.getSold());
        this.foodRepository.save(food);
    }

    @Override
    public void updateQuantityAndSold(String foodId, int quantity) {
        Food food = getFoodById(foodId);

        if (food.getQuantity() < quantity)
            throw new AppException(ErrorCode.FOOD_QUANTITY_INVALID);
        food.setQuantity(food.getQuantity() - quantity);

        if (food.getQuantity() == 0)
            food.setStatus(FoodStatus.SOLD_OUT);

        food.setSold(food.getSold() + quantity);
        this.foodRepository.save(food);
        log.info("update quantity after order");
    }

    @Override
    public void changeFoodStatus(String foodId, FoodStatus status) {
        Food food = getFoodById(foodId);
        food.setStatus(status);
        this.foodRepository.save(food);
    }

    @Override
    public void deleteFood(String foodId) {
        this.foodRepository.deleteById(foodId);
    }

    @Override
    public Food getFood(String foodId) {
        return this.getFoodById(foodId);
    }

    @Override
    public List<Food> getFoods() {

        return this.foodRepository.findAll();
    }

    private Food getFoodById(String foodId){
        return this.foodRepository.findById(foodId)
                .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOT_EXISTED));
    }
}
