package com.dang.Movie_Ticket.controller;

import com.dang.Movie_Ticket.dto.request.ResponseData;
import com.dang.Movie_Ticket.dto.request.ResponseError;
import com.dang.Movie_Ticket.dto.request.UserCreateDTO;
import com.dang.Movie_Ticket.dto.response.UserResponseDTO;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.repository.UserRepository;
import com.dang.Movie_Ticket.service.UserService;
import com.dang.Movie_Ticket.util.enums.UserStatus;
import jakarta.validation.Valid;
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
    public ResponseData<UserResponseDTO> addUser(@Valid  @RequestBody UserCreateDTO userCreateDTO) {
        log.info("Add user");
        return new ResponseData<>(HttpStatus.CREATED.value(), "Create user succeed",
                userService.addUser(userCreateDTO));
    }

    @PutMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable String userId, @RequestBody UserCreateDTO request) {
        log.info("Update user");
        userService.updateUser(userId, request);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User update succeed");

    }

    @PatchMapping("/{userId}")
    public ResponseData<?> updateUserStatus(@PathVariable String userId, @RequestParam UserStatus status) {
        log.info("Change user status");

        userService.changeUserStatus(userId, status);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User change status succeed");

    }

    @GetMapping("/{userId}")
    public ResponseData<UserResponseDTO> getuser(@PathVariable String userId) {
        log.info("Get user by {}", userId);

        return new ResponseData<>(HttpStatus.OK.value(), "User", userService.getUser(userId));

    }

    @GetMapping()
    public ResponseData<?> getUsers(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                    @Min(1) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                    @RequestParam(required = false) String sortBy) {
        log.info("Get users");

        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.getUsers(pageNo, pageSize, sortBy));

    }

    @DeleteMapping("/{userId}")
    public ResponseData<?> deleteUser(@PathVariable String userId) {
        log.info("Delete user");

        userService.deleteUser(userId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete user succeed");

    }

}
