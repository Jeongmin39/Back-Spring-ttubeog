package com.ttubeog.domain.benefit.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttubeog.domain.benefit.domain.Benefit;
import com.ttubeog.domain.benefit.domain.repository.BenefitRepository;
import com.ttubeog.domain.benefit.dto.request.CreateBenefitReq;
import com.ttubeog.domain.benefit.dto.response.CreateBenefitRes;
import com.ttubeog.domain.member.domain.Member;
import com.ttubeog.domain.member.domain.repository.MemberRepository;
import com.ttubeog.domain.store.domain.Store;
import com.ttubeog.domain.store.domain.repository.StoreRepository;
import com.ttubeog.global.DefaultAssert;
import com.ttubeog.global.config.security.token.UserPrincipal;
import com.ttubeog.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BenefitService {

    private final MemberRepository memberRepository;
    private final BenefitRepository benefitRepository;
    private final StoreRepository storeRepository;

    // 혜택 생성
    @Transactional
    public ResponseEntity<?> createBenefit(UserPrincipal userPrincipal, CreateBenefitReq createBenefitReq) throws JsonProcessingException {

        Optional<Member> userOptional = memberRepository.findById(userPrincipal.getId());
        DefaultAssert.isOptionalPresent(userOptional);
        Member member = userOptional.get();

//        Optional<Store> storeOptional = storeRepository.findById(createBenefitReq.getStoreId());
//        Store store;
//        DefaultAssert.isOptionalPresent(storeOptional);
//        store = storeOptional.get();

        Benefit benefit = Benefit.builder()
                .content(createBenefitReq.getContent())
                .type(createBenefitReq.getType())
//                .store(store)
                .build();

        benefitRepository.save(benefit);

        CreateBenefitRes createBenefitRes = CreateBenefitRes.builder()
                .benefitId(benefit.getId())
//                .storeId(benefit.getStore().getId())
                .content(benefit.getContent())
                .type(benefit.getType())
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(createBenefitRes)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
