package com.gearfirst.user_be.user.service;

import com.gearfirst.user_be.region.entity.RegionEntity;
import com.gearfirst.user_be.region.repository.RegionRepository;
import com.gearfirst.user_be.user.dto.RegistResponse;
import com.gearfirst.user_be.user.dto.UserRequest;
import com.gearfirst.user_be.user.dto.UserResponse;
import com.gearfirst.user_be.user.entity.UserEntity;
import com.gearfirst.user_be.user.enums.Rank;
import com.gearfirst.user_be.user.repository.UserRepository;
import com.gearfirst.user_be.user.spec.UserSpecification;
import com.gearfirst.user_be.workType.entity.WorkTypeEntity;
import com.gearfirst.user_be.workType.repository.WorkTypeRepositoy;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WorkTypeRepositoy workTypeRepository;
    private final RegionRepository regionRepository;

    public void updateUser(UserRequest request) {
        UserEntity entity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));

        entity.setName(request.getName());
        entity.setPhoneNum(request.getPhoneNum());
        entity.setEmail(request.getEmail());
        entity.setRank(request.getRank());

        if(request.getRegionId() != null) {
            RegionEntity region = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new EntityNotFoundException("Region not found"));
            entity.setRegion(region);
        }

        if(request.getWorkTypeId() != null) {
            WorkTypeEntity workType = workTypeRepository.findById(request.getWorkTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("WorkType not found"));
            entity.setWorkType(workType);
        }

        userRepository.save(entity);
    }

    public Page<UserResponse> getAllUser(Rank rank, Long workTypeId, Long regionId, String keyword, Pageable pageable) {
        Specification<UserEntity> spec = UserSpecification.build(rank, workTypeId, regionId, keyword);

        Page<UserEntity> userEntityPage = userRepository.findAll(spec, pageable);

        return userEntityPage.map(this::convertToDto);
    }

    private UserResponse convertToDto(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .phoneNum(entity.getPhoneNum())
                .rank(entity.getRank().getStatus())
                .regionId(entity.getRegion().getId())
                .region(entity.getRegion().getRegionName())
                .workType(entity.getWorkType().getWorkTypeName())
                .workTypeId(entity.getWorkType().getId())
                .email(entity.getEmail())
                .build();
    }

    public RegistResponse registerUser(UserRequest userRequest) {
        RegionEntity region = regionRepository.findById(userRequest.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("지역 정보를 찾을 수 없습니다."));

        WorkTypeEntity workType = workTypeRepository.findById(userRequest.getWorkTypeId())
                .orElseThrow(() -> new IllegalArgumentException("근무 지점을 찾을 수 없습니다."));

        UserEntity user = userRepository.findByEmail(userRequest.getEmail());

        if(user != null) throw new EntityExistsException("사용중인 이메일입니다.");


        user = UserEntity.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .phoneNum(userRequest.getPhoneNum())
                .workType(workType)
                .region(region)
                .rank(userRequest.getRank())
                .build();

        userRepository.save(user);

        return RegistResponse.builder()
                .userId(user.getId())
                .build();
    }

    public UserResponse getUser(Long userId){
        UserEntity entity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));

        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .regionId(entity.getRegion().getId())
                .workTypeId(entity.getWorkType().getId())
                .email(entity.getEmail())
                .phoneNum(entity.getPhoneNum())
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
