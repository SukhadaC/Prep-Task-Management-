package com.preparation.prep.service;


import com.preparation.prep.dto.LoginRequestDTO;
import com.preparation.prep.dto.ResponseDTO;
import com.preparation.prep.dto.SignUpRequestDTO;
import com.preparation.prep.entity.TaskUser;
import com.preparation.prep.repository.UserRepository;
import org.apache.catalina.User;
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
        user.setPassword(requestDTOt.getPassword());
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

        TaskUser taskUser=mapSignUpDTOtoUser(requestDTO);
        taskUser.setPassword(hashedPassword);

        userRepository.save(taskUser);




        return mapUserToResponseDTO(taskUser);
    }

    public ResponseDTO loginUser(LoginRequestDTO loginRequestDTO)
    {
        TaskUser taskUser =userRepository.findByUsername(loginRequestDTO.getUsername());
        ResponseDTO responseDTO=mapUserToResponseDTO(taskUser);
        if(passwordEncoder.matches(loginRequestDTO.getPassword(), taskUser.getPassword()))
        {
            return responseDTO;
        }

        return null;
    }



}
