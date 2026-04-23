package com.preparation.prep.controller;


import com.preparation.prep.dto.LoginRequestDTO;
import com.preparation.prep.dto.ResponseDTO;
import com.preparation.prep.dto.SignUpRequestDTO;
import com.preparation.prep.entity.TaskUser;
import com.preparation.prep.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

@Autowired
    private AuthenticationService authenticationService;

  @PostMapping("/signUp")
    public ResponseDTO createUser( @RequestBody @Valid SignUpRequestDTO requestDTO)
  {
      return authenticationService.createUser(requestDTO);


  }

  @PostMapping("/login")
    public ResponseDTO loginUser(@RequestBody LoginRequestDTO loginRequestDTO)
  {


        return authenticationService.loginUser(loginRequestDTO);

  }


}
