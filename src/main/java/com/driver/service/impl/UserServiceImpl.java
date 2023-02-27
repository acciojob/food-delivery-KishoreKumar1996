package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.model.response.UserResponse;
import com.driver.service.FoodService;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) throws Exception {
        //creating User by userDto
        //rule set the attributes of userDto in user for convention
       UserEntity userEntity=new UserEntity();
       String str=user_RandomUUID();
       userEntity.setEmail(user.getEmail());
       userEntity.setFirstName(user.getFirstName());
       userEntity.setLastName(user.getLastName());
       userEntity.setUserId((str));
       userRepository.save(userEntity);

       user.setId(userRepository.findByEmail(user.getEmail()).getId());
       user.setUserId(userRepository.findByEmail(user.getEmail()).getUserId());

        return user;
    }
    static String user_RandomUUID() {

        UUID randomUUID = UUID.randomUUID();

        return randomUUID.toString().replaceAll("_", "");

    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity= userRepository.findByEmail(email);

//        UserDto userDto=new UserDto();
//        userDto.setUserId(userEntity.getUserId());
//        userDto.setEmail(email);
//        userDto.setFirstName(userEntity.getFirstName());
//        userDto.setLastName(userEntity.getLastName());
//        userDto.setId(userEntity.getId());
//        return userDto;
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setUserId(userEntity.getUserId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());

        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        UserDto userDto=new UserDto();
        userDto.setUserId(userEntity.getUserId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setId(userEntity.getId());
        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userRepository.save(userEntity);

        user.setUserId(userEntity.getUserId());
        user.setId(userEntity.getId());
        return user;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        long id=userRepository.findByUserId(userId).getId();
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> list= (List<UserEntity>) userRepository.findAll(); //implicity to list
        List<UserDto> listDto=new ArrayList<>();

        for(UserEntity user : list){
            UserDto userDto=new UserDto();
            userDto.setId(user.getId());
            userDto.setUserId(user.getUserId());
            userDto.setEmail(user.getEmail());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            listDto.add(userDto);
        }
        return listDto;
    }

    public UserResponse createUser(UserDetailsRequestModel userDetails) throws Exception {


        UserDto userDto = new UserDto();
        userDto.setFirstName(userDetails.getFirstName());
        userDto.setLastName(userDetails.getLastName());
        userDto.setEmail(userDetails.getEmail());

        UserDto finalUserDto = createUser(userDto);


        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(finalUserDto.getUserId());
        userResponse.setEmail(finalUserDto.getEmail());
        userResponse.setFirstName(finalUserDto.getFirstName());
        userResponse.setLastName(finalUserDto.getLastName());

        return userResponse;
    }

    public UserResponse getUser_id(String id) throws Exception {
        UserDto userDto;
        if(id.contains(".com")){
            userDto = getUser(id);
        }
        else {
            userDto = getUserByUserId(id);
        }
        //Now we will convert this above userDto into userResponse
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userDto.getUserId());
        userResponse.setEmail(userDto.getEmail());
        userResponse.setFirstName(userDto.getFirstName());
        userResponse.setLastName(userDto.getLastName());

        return userResponse;
    }

    public UserResponse updateUser(String id, UserDetailsRequestModel userDetails) throws Exception {

        //Now we will convert 'userDetails' into 'UserDto'

        UserDto userDto = new UserDto();
        userDto.setFirstName(userDetails.getFirstName());
        userDto.setLastName(userDetails.getLastName());
        userDto.setEmail(userDetails.getEmail());

        String userId = userRepository.findByUserId(id).getUserId();

        UserDto finalUserDto = updateUser(userId,userDto);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(finalUserDto.getUserId());
        userResponse.setEmail(finalUserDto.getEmail());
        userResponse.setFirstName(finalUserDto.getFirstName());
        userResponse.setLastName(finalUserDto.getLastName());

        return userResponse;
    }

    public OperationStatusModel delete_User(String id) throws Exception {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());
        try {
            deleteUser(id);
        } catch (Exception e){
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
            return operationStatusModel;
        }
        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());
        return operationStatusModel;
    }

    public List<UserResponse> get_Users(){
        List<UserDto> userDtoList = getUsers();
        List<UserResponse> userResponseList = new ArrayList<>();
        for(UserDto h : userDtoList){
            userResponseList.add(new UserResponse(h.getUserId(),h.getEmail(), h.getFirstName(), h.getLastName()));
        }
        return userResponseList;
    }

}


