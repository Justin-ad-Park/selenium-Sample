package com.example.database.domain.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.database.domain.member.entity.Member;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TABLE = "SNS_MEMBER";
    public static final String MEMBER_ID = "memberId";

    static final RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member
                .builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .nickname(resultSet.getString("nickname"))
                .birthday(resultSet.getObject("birthday", LocalDate.class))
                .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
                .build();


    public Optional<Member> findById(final Long id) {
        final var sql = String.format("SELECT * FROM %s WHERE id = :id", MemberRepository.TABLE);
        final var param = new MapSqlParameterSource()
                .addValue("id", id);

        final var member = this.namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);
        return Optional.ofNullable(member);

    }

    public List<Member> findAllByIdIn(List<Long> ids) {
        if(ids.isEmpty())
            return List.of();

        var sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
        var params = new MapSqlParameterSource().addValue("ids", ids);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }


    public Member save(final Member member) {
        /*
            member id가 없으면 신규 있으면 갱신
            반환값은 id를 담아서 반환한다.
         */

        if(member.getId() == null)
            return this.insert(member);
        else
            return this.update(member);
    }

    private Member insert(final Member member) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        final SqlParameterSource params = new BeanPropertySqlParameterSource(member);

        final Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(final Member member) {
        final var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", MemberRepository.TABLE);
        final SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        this.namedParameterJdbcTemplate.update(sql, params);
        return member;
    }

}
