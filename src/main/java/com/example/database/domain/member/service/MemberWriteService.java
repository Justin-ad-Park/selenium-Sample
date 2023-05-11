package com.example.database.domain.member.service;

import com.example.database.domain.member.dto.RegisterMemberCommand;
import com.example.database.domain.member.entity.Member;
import com.example.database.domain.member.entity.MemberNicknameHistory;
import com.example.database.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.database.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member create(final RegisterMemberCommand command) {
        /*
            목표 - 회원정보(이메일, 닉네임, 생년월일)를 등록한다.
                - 닉네임은 10자를 넘길 수 없다.
            파라미터 : MemberRegisterCommand

            val member = Member.of(memberRegisterCommand)
            memberRepository.save(member)
         */

        final Member member = Member.builder()
                .nickname(command.nick())
                .email(command.email())
                .birthday(command.birthday())
                .build();

        return this.memberRepository.save(member);
    }

    public Member changeNickname(final Long memberId, final String nickname) {
        /*
            1. 회원의 닉네임을 변경한다.
            2. 변경 내역을 히스토리에 저장한다.
         */
        final var member = this.memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        this.memberRepository.save(member);

        final var history = MemberNicknameHistory
                .builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        this.memberNicknameHistoryRepository.save(history);
        return member;
    }
}
