package com.gearfirst.user_be.user.service;

import com.gearfirst.user_be.mail.service.MailService;
import com.gearfirst.user_be.region.entity.RegionEntity;
import com.gearfirst.user_be.region.repository.RegionRepository;
import com.gearfirst.user_be.user.client.AuthClient;
import com.gearfirst.user_be.user.dto.*;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WorkTypeRepositoy workTypeRepository;
    private final RegionRepository regionRepository;
    private final AuthClient authClient;
    private final MailService mailService;

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
    @Transactional
    public RegistResponse registerUser(CreateUserRequest userRequest) {
        RegionEntity region = regionRepository.findById(userRequest.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("지역 정보를 찾을 수 없습니다."));

        WorkTypeEntity workType = workTypeRepository.findById(userRequest.getWorkTypeId())
                .orElseThrow(() -> new IllegalArgumentException("근무 지점을 찾을 수 없습니다."));

        UserEntity user = userRepository.findByEmail(userRequest.getEmail());

        if(user != null) throw new EntityExistsException("사용중인 이메일입니다.");

        String tempPassword = RandomStringUtils.random(10, true, true);

        //user 먼저 저장
        user = UserEntity.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .phoneNum(userRequest.getPhoneNum())
                .workType(workType)
                .region(region)
                .rank(userRequest.getRank())
                .build();

        userRepository.save(user);

        //Auth 서버 호출 서버 실패시 예외 발생 -> 자동 롤백
        try {
            authClient.createAccount(new CreateAccountRequest(user.getEmail(), tempPassword));
        } catch (Exception e) {
            // Auth 서버 통신 실패 시 롤백
            throw new IllegalStateException("Auth 서버 계정 생성 중 오류가 발생했습니다: " + e.getMessage());
        }

        // 5️ 이메일 발송
        try {
            mailService.sendUserRegistrationMail(user.getEmail(), user.getName(), tempPassword);
        } catch (Exception e) {
            throw new IllegalStateException("메일 발송 중 오류가 발생했습니다: " + e.getMessage());
        }

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
