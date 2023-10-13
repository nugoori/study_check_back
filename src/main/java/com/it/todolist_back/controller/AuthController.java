package com.it.todolist_back.controller;

import com.it.todolist_back.dto.SignInReqDto;
import com.it.todolist_back.dto.SignUpReqDto;
import com.it.todolist_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // @Autowired = AuthController에 AuthService 하나를 매개변수로 받는 생성자를 만드는것 == class에 @RequiredArgsConstructor를 사용 하는 것과 같음
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpReqDto signUpReqDto, BindingResult bindingResult) {
        // jackson library
        System.out.println(signUpReqDto);
//        userService.signupUser(signUpReqDto);

        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()) {
                String fieldName = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                errorMap.put(fieldName, message);
            }
            return ResponseEntity.badRequest().body(errorMap);
        }
        if(authService.isDuplicatedEmail(signUpReqDto.getEmail())) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("email", "이미 사용 중인 이메일입니다.");
            return ResponseEntity.badRequest().body(errorMap);
        }

        return ResponseEntity.ok().body(authService.insertUser(signUpReqDto));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInReqDto signInReqDto) {
        System.out.println(signInReqDto);
        String accessToken = authService.signin(signInReqDto);
        return ResponseEntity.ok().body(accessToken);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate() {

        return ResponseEntity.ok().body(null); //authService.authenticate(token)
    }
}
