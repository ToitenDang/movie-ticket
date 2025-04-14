package com.dang.Movie_Ticket.controller;

import com.dang.Movie_Ticket.dto.request.FoodCreationRequest;
import com.dang.Movie_Ticket.dto.request.FoodUpdateRequest;
import com.dang.Movie_Ticket.dto.request.ResponseData;
import com.dang.Movie_Ticket.service.FoodService;
import com.dang.Movie_Ticket.util.enums.FoodStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/foods")
@Slf4j
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    public ResponseData<?> addFood(@ModelAttribute FoodCreationRequest request,
                                   @RequestParam MultipartFile file) throws IOException {
        log.info("Add food");
        return new ResponseData<>(HttpStatus.CREATED.value(), "Add food succeed",
                                    this.foodService.addFood(request, file));
    }

    @PutMapping("/{foodId}")
    public ResponseData<?> updateFood(@PathVariable String foodId,
                                      @RequestBody FoodUpdateRequest request){
        this.foodService.updateFood(foodId, request);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Update Food succeed");
    }

    @PatchMapping("/{foodId}")
    public ResponseData<?> changeFoodStatus(@PathVariable String foodId, @RequestParam FoodStatus status){
        this.foodService.changeFoodStatus(foodId, status);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Change Food Status succeed");
    }

    @PatchMapping("/quantity-sold/{foodId}")
    public ResponseData<?> updateQuantityAndSold(@PathVariable String foodId, @RequestParam int quantity){
        this.foodService.updateQuantityAndSold(foodId, quantity);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "update quantity and sold of Food succeed");
    }

    @DeleteMapping("/{foodId}")
    public ResponseData<?> deleteFood(@PathVariable String foodId){
        this.foodService.deleteFood(foodId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete Food succeed");
    }

    @GetMapping("/{foodId}")
    public ResponseData<?> getFood(@PathVariable String foodId){
        return new ResponseData<>(HttpStatus.OK.value(), "Food", this.foodService.getFood(foodId));
    }

    @GetMapping
    public ResponseData<?> getFoods(){
        return new ResponseData<>(HttpStatus.OK.value(), "Foods", this.foodService.getFoods());
    }
}
