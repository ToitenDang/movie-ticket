package com.dang.Movie_Ticket.controller;

import com.dang.Movie_Ticket.dto.request.ResponseData;
import com.dang.Movie_Ticket.dto.request.ResponseError;
import com.dang.Movie_Ticket.dto.request.UserCreateDTO;
import com.dang.Movie_Ticket.dto.response.UserResponseDTO;
import com.dang.Movie_Ticket.exception.ResourceNotFoundException;
import com.dang.Movie_Ticket.repository.UserRepository;
import com.dang.Movie_Ticket.service.UserService;
import com.dang.Movie_Ticket.util.enums.UserStatus;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserRepository userRepository;

    private static final String ERROR_MESSAGE = "errorMessage={}";

    @PostMapping
    public ResponseData<UserResponseDTO> addUser(@RequestBody UserCreateDTO userCreateDTO){
        log.info("Add user");
        try {
            return new ResponseData<>(HttpStatus.CREATED.value(), "Create user succeed",
                    userService.addUser(userCreateDTO));
        } catch (ResourceNotFoundException e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create user failed");
        }
    }

    @PutMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable String userId, @RequestBody UserCreateDTO request){
        log.info("Update user");
        try {
            userService.updateUser(userId, request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User update succeed");
        } catch (ResourceNotFoundException e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "User update failed");
        }
    }

    @PatchMapping("/{userId}")
    public ResponseData<?> updateUserStatus(@PathVariable String userId, @RequestParam UserStatus status){
        log.info("Change user status");
        try {
            userService.changeUserStatus(userId, status);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User change status succeed");
        } catch (ResourceNotFoundException e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Change user status failed");
        }
    }

    @GetMapping("/{userId}")
    public ResponseData<UserResponseDTO> getuser(@PathVariable String userId){
        log.info("Get user by {}", userId);
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "User", userService.getUser(userId));
        } catch (ResourceNotFoundException e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.NOT_FOUND.value(), "User not found");
        }
    }

    @GetMapping()
    public ResponseData<?> getUsers(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                   @Min(1) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                    @RequestParam(required = false) String sortBy){
        log.info("Get users");
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "users", userService.getUsers(pageNo, pageSize, sortBy));
        } catch (ResourceNotFoundException e){
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.NOT_FOUND.value(), "User empty");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseData<?> deleteUser(@PathVariable String userId){
        log.info("Delete user");
        try {
            userService.deleteUser(userId);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete user succeed");
        } catch (ResourceNotFoundException e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete user failed");
        }
    }

}
