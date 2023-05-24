package com.illuminarean.example.user.api;

import com.illuminarean.example.security.JdbcAuthenticationPrincipal;
import com.illuminarean.example.user.api.dto.UserDto;
import com.illuminarean.example.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")  //FIXME : 경로 수정
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("me")
    public UserDto me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) return null;

        JdbcAuthenticationPrincipal principal = (JdbcAuthenticationPrincipal) authentication.getPrincipal();
        return userService.findById(principal.getId());
    }
}
