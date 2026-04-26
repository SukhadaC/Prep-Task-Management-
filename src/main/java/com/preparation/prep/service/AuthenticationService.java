package com.preparation.prep.service;


import com.preparation.prep.dto.LoginRequestDTO;
import com.preparation.prep.dto.ResponseDTO;
import com.preparation.prep.dto.SignUpRequestDTO;
import com.preparation.prep.entity.TaskUser;
import com.preparation.prep.exception.DuplicateResourceException;
import com.preparation.prep.exception.ResourceNotFoundException;
import com.preparation.prep.repository.UserRepository;
import com.preparation.prep.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class AuthenticationService {

    public  TaskUser mapSignUpDTOtoUser(SignUpRequestDTO requestDTOt)
    {
        TaskUser user=new TaskUser();

        user.setName(requestDTOt.getName());
        user.setEmail(requestDTOt.getEmail());
        user.setUsername(requestDTOt.getUsername());

        return user;

    }


    public ResponseDTO mapUserToResponseDTO(TaskUser user)
    {
        ResponseDTO responseDTO=new ResponseDTO();

        responseDTO.setName(user.getName());
        responseDTO.setUsername(user.getUsername());

        return responseDTO;
    }




    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;





    public ResponseDTO createUser(SignUpRequestDTO requestDTO)
    {

        String  hashedPassword=passwordEncoder.encode(requestDTO.getPassword());

        //Checking for duplicates
        if(userRepository.findByUsername(requestDTO.getUsername())!=null)
            throw new DuplicateResourceException("This user already exists");

        TaskUser taskUser=mapSignUpDTOtoUser(requestDTO);

        taskUser.setPassword(hashedPassword);

        userRepository.save(taskUser);

        return mapUserToResponseDTO(taskUser);
    }

    public String  loginUser(LoginRequestDTO loginRequestDTO)
    {
        TaskUser taskUser =userRepository.findByUsername(loginRequestDTO.getUsername());


        if(taskUser==null|| !passwordEncoder.matches(loginRequestDTO.getPassword(), taskUser.getPassword()))
        {
            throw new RuntimeException("Invalid Credentials");
        }



        return jwtUtil.generateToken(loginRequestDTO.getUsername());
    }



}
