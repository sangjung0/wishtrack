package com.mobile.wishtrack.domain.model;

public class QueryStatement {
    private final String query;
    private final QueryOptions options;

    public static QueryStatement newInstance(String query, QueryOptions options){
        return new QueryStatement(query, options);
    }

    private QueryStatement(String query, QueryOptions options){
        this.query = query;
        this.options = options;
    }

    public String getQuery() {
        return query;
    }

    public QueryOptions getOptions() {
        return options;
    }
}
