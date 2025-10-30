package com.gearfirst.user_be.workType.service;

import com.gearfirst.user_be.workType.repository.WorkTypeRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkTypeService {
    private final WorkTypeRepositoy workTypeRepositoy;
}
