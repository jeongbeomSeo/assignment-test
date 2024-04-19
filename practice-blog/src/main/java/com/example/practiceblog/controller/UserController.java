package com.example.practiceblog.controller;

import com.example.practiceblog.dto.AccessTokenResponseDTO;
import com.example.practiceblog.entity.User;
import com.example.practiceblog.global.jwt.TokenProvider;
import com.example.practiceblog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() { return "user/login"; }

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String signupForm() { return "user/signup"; }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String signup(User user) {
        userService.save(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> authenticate(User user) {
        User userInfo = userService.getByCredentials(user.getLoginId());

        if (bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword())) {
            String token = tokenProvider.generateToken(userInfo, Duration.ofHours(2));

            AccessTokenResponseDTO accessTokenResponseDTO = new AccessTokenResponseDTO(token);
            return ResponseEntity.ok(accessTokenResponseDTO);
        } else {
            return ResponseEntity.badRequest().body("login failed");
        }
    }

}
