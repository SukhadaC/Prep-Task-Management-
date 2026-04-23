package com.preparation.prep.controller;


import com.preparation.prep.dto.LoginRequestDTO;
import com.preparation.prep.dto.ResponseDTO;
import com.preparation.prep.dto.SignUpRequestDTO;
import com.preparation.prep.entity.TaskUser;
import com.preparation.prep.response.APIResponseWrapper;
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
    public APIResponseWrapper<ResponseDTO> createUser(@RequestBody @Valid SignUpRequestDTO requestDTO)
  {

      ResponseDTO responseDTO=authenticationService.createUser(requestDTO);
      APIResponseWrapper<ResponseDTO>response=new APIResponseWrapper<>("Successfully created the user",responseDTO,true);
      return response;


  }

  @PostMapping("/login")
    public APIResponseWrapper<ResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO)
  {

      ResponseDTO responseDTO=authenticationService.loginUser(loginRequestDTO);




        APIResponseWrapper<ResponseDTO>response=new APIResponseWrapper<>("Sucessfully logged in",responseDTO,true);
      return response;
  }


}
