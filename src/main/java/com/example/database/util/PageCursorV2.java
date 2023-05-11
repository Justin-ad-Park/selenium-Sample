package com.example.database.util;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.function.ToLongFunction;

public class PageCursorV2<T>{
    private CursorRequestV2 cursorRequestV2;
    private final List<T> body;

    public List<T> getBody() {
        return this.body;
    }
    public List<T> body() {
        return this.body;
    }
    public CursorRequestV2 getCursorRequestV2() { return this.cursorRequestV2;}

    public PageCursorV2(final CursorRequestV2 cursorRequestV2, final List<T> body) {
        Assert.isTrue(cursorRequestV2.updatedNextKey(), "Key를 업데이트 하지 못했습니다. 기존에 mapper가 포함된 생성자를 이용하세요.");

        this.cursorRequestV2 = cursorRequestV2;
        this.body = body;
    }

    public PageCursorV2(final CursorRequestV2 cursorRequestV2, final List<T> body
            , ToLongFunction<? super T> getMinIdMapper ) {
        Objects.requireNonNull(getMinIdMapper, "페이징용 PK를 조회할 수 있는 getMethod를 제공해야 합니다.");

        this.cursorRequestV2 = cursorRequestV2;
        this.body = body;

        updateKeyToNext(getMinIdMapper);
    }

    private void updateKeyToNext(ToLongFunction<? super T> mapper) {
        if(body.size() < cursorRequestV2.size()) {
            cursorRequestV2.updateKeyToNext(CursorRequestV2.NONE_KEY);
            return;
        }

        Long nextKey = body.stream()
                .mapToLong(mapper)
                .min()
                .orElse(CursorRequestV2.NONE_KEY);

        cursorRequestV2.updateKeyToNext(nextKey);
    }
}
