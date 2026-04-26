package com.preparation.prep.controller;


import com.preparation.prep.dto.LoginRequestDTO;
import com.preparation.prep.dto.ResponseDTO;
import com.preparation.prep.dto.SignUpRequestDTO;
import com.preparation.prep.entity.TaskUser;
import com.preparation.prep.exception.ExpiredTokenException;
import com.preparation.prep.response.APIResponseWrapper;
import com.preparation.prep.security.JWTUtil;
import com.preparation.prep.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

@Autowired
    private AuthenticationService authenticationService;

@Autowired
  private JWTUtil jwtUtil;

@GetMapping("/validateToken")
public String test(@RequestHeader("Authorization") String header)
{
    if (header==null||!header.startsWith("Bearer"))
        throw new RuntimeException("Invalid or missing Authorizaton header");


    String token=header.substring(7);

    String username =jwtUtil.extractUsername(token);


    if (!jwtUtil.isTokenValid(token,username))
        throw  new ExpiredTokenException("Token is invalid");
    return "UserName:"+username;
}


  @PostMapping("/signUp")
    public APIResponseWrapper<ResponseDTO> createUser(@RequestBody @Valid SignUpRequestDTO requestDTO)
  {

      ResponseDTO responseDTO=authenticationService.createUser(requestDTO);
      APIResponseWrapper<ResponseDTO>response=new APIResponseWrapper<>("Successfully created the user",responseDTO,true);
      return response;


  }

  @PostMapping("/login")
    public APIResponseWrapper<String> loginUser(@RequestBody LoginRequestDTO loginRequestDTO)
  {

      String jwtToken=authenticationService.loginUser(loginRequestDTO);




        APIResponseWrapper<String>response=new APIResponseWrapper<>("Sucessfully logged in",jwtToken,true);
      return response;
  }


}
