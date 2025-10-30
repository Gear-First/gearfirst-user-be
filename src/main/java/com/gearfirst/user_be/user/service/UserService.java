package com.gearfirst.user_be.user.service;

import com.gearfirst.user_be.region.entity.RegionEntity;
import com.gearfirst.user_be.region.repository.RegionRepository;
import com.gearfirst.user_be.user.dto.UserRequest;
import com.gearfirst.user_be.user.dto.UserResponse;
import com.gearfirst.user_be.user.entity.UserEntity;
import com.gearfirst.user_be.user.repository.UserRepository;
import com.gearfirst.user_be.workType.entity.WorkTypeEntity;
import com.gearfirst.user_be.workType.repository.WorkTypeRepositoy;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WorkTypeRepositoy workTypeRepositoy;
    private final RegionRepository regionRepository;

    public void registUser(UserRequest userRequest) {
        RegionEntity region = regionRepository.findById(userRequest.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("지역 정보를 찾을 수 없습니다."));

        WorkTypeEntity workType = workTypeRepositoy.findById(userRequest.getWorkTypeId())
                .orElseThrow(() -> new IllegalArgumentException("근무 지점을 찾을 수 없습니다."));

        UserEntity user = userRepository.findByEmail(userRequest.getEmail());

        if(user != null) throw new EntityExistsException("사용중인 이메일입니다.");

        user = UserEntity.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .phoneNum(userRequest.getPhoneNum())
                .workType(workType)
                .region(region)
                .build();

        userRepository.save(user);
    }

    public UserResponse getUser(String email){
        UserEntity entity = userRepository.findByEmail(email);

        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .rank(entity.getRank().getStatus())
                .region(entity.getRegion().getRegionName())
                .workType(entity.getWorkType().getWorkTypeName())
                .build();
    }

    public void deleteUser(String email) {
        UserEntity entity = userRepository.findByEmail(email);

        if(entity == null) throw new EntityNotFoundException("해당 사용자가 없습니다.");

        userRepository.delete(entity);
    }
}
