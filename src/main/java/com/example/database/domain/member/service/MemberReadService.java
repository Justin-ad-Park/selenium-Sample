package com.example.database.domain.member.service;

import com.example.database.domain.member.dto.MemberDto;
import com.example.database.domain.member.dto.MemberNicknameHistoryDto;
import com.example.database.domain.member.entity.Member;
import com.example.database.domain.member.entity.MemberNicknameHistory;
import com.example.database.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.database.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(final Long id) {
        final var member = this.memberRepository.findById(id).orElseThrow();

        return this.toDto(member);
    }

    public List<MemberDto> getMembers(List<Long> ids) {
        var members = memberRepository.findAllByIdIn(ids);
        return members.stream().map(this::toDto).toList();
    }

    public List<MemberNicknameHistoryDto> getMemberNicknameHistories(final Long memberId) {
        return this.memberNicknameHistoryRepository.findAllByMemberId(memberId)
                .stream()
                .map(history -> this.toDto(history))
                .toList()
                ;
    }

    public MemberDto toDto(final Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

    private MemberNicknameHistoryDto toDto(final MemberNicknameHistory history) {
        return new MemberNicknameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );
    }
}
