package com.indicator_engine.model.indicator_system.Number;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Tanmaya Mahapatra on 07-05-2015.
 */
public class GenQuery implements Serializable {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int queryID;
    private final String query;
    private final String questionName;

    public GenQuery(String query, String questionName) {
        this.query = query;
        this.questionName = questionName;
        queryID = count.incrementAndGet();
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public int getQueryID() {
        return queryID;
    }

    public String getQuery() {
        return query;
    }

    public String getQuestionName() {
        return questionName;
    }
}
