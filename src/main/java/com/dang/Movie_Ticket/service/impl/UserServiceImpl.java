package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.UserCreateDTO;
import com.dang.Movie_Ticket.dto.response.PageResponse;
import com.dang.Movie_Ticket.dto.response.UserResponseDTO;
import com.dang.Movie_Ticket.entity.User;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.mapper.UserMapper;
import com.dang.Movie_Ticket.repository.UserRepository;
import com.dang.Movie_Ticket.service.UserService;
import com.dang.Movie_Ticket.util.enums.MembershipLevel;
import com.dang.Movie_Ticket.util.enums.Role;
import com.dang.Movie_Ticket.util.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO addUser(UserCreateDTO request) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dob(request.getDob())
                .phone(request.getPhone())
                .membershipLevel(MembershipLevel.BASIC)
                .status(UserStatus.ACTIVE)
                .role(Role.USER)
                .build();

        User userResponse = userRepository.save(user);
        return userMapper.toUserResponse(userResponse);
    }

    @Override
    public void updateUser(String userId, UserCreateDTO request) {
        User user = getUserById(userId);
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
//        user.setDob(request.getDob());
//        user.setPhone(request.getPhone());
        userMapper.updateUser(user, request);

        userRepository.save(user);
        log.info("Update user with {}", userId);
    }

    @Override
    public void changeUserStatus(String userId, UserStatus status) {
        User user = getUserById(userId);
        user.setStatus(status);

        userRepository.save(user);

        log.info("User status changed");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<?> getUsers(int pageNo, int pageSize, String sortBy) {
        int p = 0;
        if(pageNo > 0){
            p = pageNo+1;
        }
        List<Sort.Order> sorts = new ArrayList<>();

        // Nếu có điều kiện để sắp xếp sortBy
        if(StringUtils.hasLength(sortBy)){
            // Một điều kiện có 3 phần là (key)(:)(asc hoặc desc) và dùng với bất kỳ thuộc tính nào
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");

            Matcher matcher = pattern.matcher(sortBy);
            if(matcher.find()){
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                }
                else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(p, pageSize, Sort.by(sorts));

        Page<User> users = userRepository.findAll(pageable);
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(users.getTotalPages())
                .items(users.stream().map(userMapper::toUserResponse))
                .build();
    }

    @Override
    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponseDTO getUser(String userId) {
        User user = getUserById(userId);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponseDTO getMyInfo() {
        var context = SecurityContextHolder.getContext();
        var email = context.getAuthentication().getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                            new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }


    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
        log.info("User deleted has userId = {}", userId);
    }

    private User getUserById(String userId){
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
