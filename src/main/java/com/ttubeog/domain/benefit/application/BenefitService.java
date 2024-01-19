package com.ttubeog.domain.benefit.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttubeog.domain.benefit.domain.Benefit;
import com.ttubeog.domain.benefit.domain.MemberBenefit;
import com.ttubeog.domain.benefit.domain.repository.BenefitRepository;
import com.ttubeog.domain.benefit.domain.repository.MemberBenefitRepository;
import com.ttubeog.domain.benefit.dto.request.CreateBenefitReq;
import com.ttubeog.domain.benefit.dto.request.UpdateBenefitReq;
import com.ttubeog.domain.benefit.dto.response.CreateBenefitRes;
import com.ttubeog.domain.benefit.dto.response.SaveBenefitRes;
import com.ttubeog.domain.benefit.dto.response.UpdateBenefitRes;
import com.ttubeog.domain.benefit.exception.NonExistentBenefitException;
import com.ttubeog.domain.benefit.exception.OverlappingBenefitException;
import com.ttubeog.domain.member.domain.Member;
import com.ttubeog.domain.member.domain.repository.MemberRepository;
import com.ttubeog.domain.member.exception.InvalidMemberException;
import com.ttubeog.domain.store.domain.Store;
import com.ttubeog.domain.store.domain.repository.StoreRepository;
import com.ttubeog.global.DefaultAssert;
import com.ttubeog.global.config.security.token.UserPrincipal;
import com.ttubeog.global.payload.ApiResponse;
import com.ttubeog.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BenefitService {

    private final MemberRepository memberRepository;
    private final BenefitRepository benefitRepository;
    private final StoreRepository storeRepository;
    private final MemberBenefitRepository memberBenefitRepository;

    // 혜택 생성
    @Transactional
    public ResponseEntity<?> createBenefit(UserPrincipal userPrincipal, CreateBenefitReq createBenefitReq) throws JsonProcessingException {

        memberRepository.findById(userPrincipal.getId()).orElseThrow(InvalidMemberException::new);

//        Store store = storeRepository.findById(createBenefitReq.getStoreId()).orElseThrow(에러::new);

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

    // 혜택 삭제
    @Transactional
    public ResponseEntity<?> deleteBenefit(UserPrincipal userPrincipal, Long benefitId) throws JsonProcessingException {

        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(InvalidMemberException::new);
        Benefit benefit = benefitRepository.findById(benefitId).orElseThrow(NonExistentBenefitException::new);

        benefitRepository.delete(benefit);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(Message.builder().message("혜택을 삭제했습니다.").build())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    //혜택 수정
    @Transactional
    public ResponseEntity<?> updateBenefit(UserPrincipal userPrincipal, UpdateBenefitReq updateBenefitReq) throws JsonProcessingException {

        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(InvalidMemberException::new);
        Benefit benefit = benefitRepository.findById(updateBenefitReq.getBenefitId()).orElseThrow(NonExistentBenefitException::new);

        //TODO userOptional과 benefit의 userId가 일치하는지 확인

        benefit.updateContent(updateBenefitReq.getContent());

        UpdateBenefitRes updateBenefitRes = UpdateBenefitRes.builder()
                .benefitId(benefit.getId())
                //.storeId(benefit.getStore().getId())
                .content(benefit.getContent())
                .type(benefit.getType())
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(updateBenefitRes)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    //게임 성공 후 혜택 저장
    @Transactional
    public ResponseEntity<?> saveBenefit(UserPrincipal userPrincipal, Long benefitId) throws JsonProcessingException {

        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(InvalidMemberException::new);
        Benefit benefit = benefitRepository.findById(benefitId).orElseThrow(NonExistentBenefitException::new);

        //유저에게 이미 있는 benefit인지 확인
        List<MemberBenefit> memberBenefitList = memberBenefitRepository.findAllByBenefitAndCreatedAtIsAfter(benefit, LocalDateTime.now().minusMonths(1));
        //같은 benefit이고, 저장한지 한달이 지나지 않았으면 에러 호출
        if (memberBenefitList.size() > 0) {
            throw new OverlappingBenefitException();
        }

        MemberBenefit memberBenefit = MemberBenefit.builder()
                .member(member)
                .benefit(benefit)
                .is_used(false)
                .has_expired(false)
                .build();

        memberBenefitRepository.save(memberBenefit);

        SaveBenefitRes saveBenefitRes = SaveBenefitRes.builder()
                .id(memberBenefit.getId())
                .benefitId(benefit.getId())
//                .storeId(memberBenefit.getBenefit().getStore().getId())
                .content(benefit.getContent())
                .type(benefit.getType())
                .isUsed(memberBenefit.getIs_used())
                .hasExpried(memberBenefit.getHas_expired())
                .createdAt(memberBenefit.getCreatedAt())
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(saveBenefitRes)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
