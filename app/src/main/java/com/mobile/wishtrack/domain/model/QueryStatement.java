package com.mobile.wishtrack.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryStatement {
    private final String query;
    private final QueryOptions options;

    public static QueryStatement newInstance(String query, QueryOptions options){
        return new QueryStatement(query, options);
    }
}
