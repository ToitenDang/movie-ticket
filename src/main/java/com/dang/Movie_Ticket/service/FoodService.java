package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.FoodCreationRequest;
import com.dang.Movie_Ticket.dto.request.FoodUpdateRequest;
import com.dang.Movie_Ticket.entity.Food;
import com.dang.Movie_Ticket.util.enums.FoodStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FoodService {
    String addFood(FoodCreationRequest request, MultipartFile file) throws IOException;
    void updateFood(String foodId, FoodUpdateRequest request);
    void updateQuantityAndSold(String foodId, int quantity);
    void changeFoodStatus(String foodId, FoodStatus status);
    void deleteFood(String foodId);
    Food getFood(String foodId);
    List<Food> getFoods();
}
