package com.example.database.domain.member.repository;

import com.example.database.domain.member.entity.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberNicknameHistoryRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TABLE = "SNS_MEMBERNICKNAMEHISTORY";

    static final RowMapper<MemberNicknameHistory> rowMapper;

    static {
        rowMapper = (ResultSet resultSet, int rowNum) -> MemberNicknameHistory
                .builder()
                .id(resultSet.getLong("Id"))
                .memberId(resultSet.getLong(MemberRepository.MEMBER_ID))
                .nickname(resultSet.getString("nickname"))
                .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
                .build();
    }

    public List<MemberNicknameHistory> findAllByMemberId(final Long memberId) {
        final var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", MemberNicknameHistoryRepository.TABLE);
        final var params = new MapSqlParameterSource().addValue(MemberRepository.MEMBER_ID, memberId);
        return this.namedParameterJdbcTemplate.query(sql, params, MemberNicknameHistoryRepository.rowMapper);
    }

    public MemberNicknameHistory save(final MemberNicknameHistory memberNicknameHistory) {

        if(memberNicknameHistory.getId() == null) {
            return this.insert(memberNicknameHistory);
        }

        throw new UnsupportedOperationException("MemberNicknameHistory는 갱신을 지원하지 않습니다.");

    }

    private MemberNicknameHistory insert(final MemberNicknameHistory memberNicknameHistory) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        final SqlParameterSource params = new BeanPropertySqlParameterSource(memberNicknameHistory);

        final Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return MemberNicknameHistory
                .builder()
                .id(id)
                .memberId(memberNicknameHistory.getMemberId())
                .nickname(memberNicknameHistory.getNickname())
                .build();
    }

}