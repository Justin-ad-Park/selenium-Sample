package com.example.database.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class CursorRequestV2 {
    @Setter
    @Getter
    private Long key;
    @Setter
    @Getter
    private final Long size;
    private Long prevKey = null;
    private boolean updatedNextKey = false;
    public static final Long NONE_KEY = -1L;

    @Builder
    public CursorRequestV2(final Long key, Long size) {
        this.key = key;
        this.size = Objects.requireNonNullElse(size, 1L);
    }

    public Boolean hasKey() {
        return key != null;
    }

    public Long key() {
        return key;
    }

    public Long size() {
        return size;
    }

    public CursorRequestV2 next(Long key)
    {
        return new CursorRequestV2(key, size);
    }

    protected Boolean updatedNextKey() {
        return updatedNextKey;
    }

    protected void updateKeyToNext(Long nextKey) {
        this.prevKey = this.key;
        this.key = nextKey;
        this.updatedNextKey = true;
    }
}
