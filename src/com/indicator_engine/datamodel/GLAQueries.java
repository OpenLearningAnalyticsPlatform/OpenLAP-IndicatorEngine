package com.indicator_engine.datamodel;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
@Entity
@Table(name = "gla_Queries")
public class GLAQueries implements Serializable {

    @Id
    @Column(name = "query_id",unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "question_name", unique = true, nullable = false)
    private String question_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indicator_id", nullable = false)
    private GLAIndicator glaIndicator;

    @Column(name = "hql", nullable = false, columnDefinition="TEXT")
    private String hql;


    public GLAQueries() {
    }

    public GLAQueries(String hql, String question_name) {
        this.hql = hql;
        this.question_name = question_name;
    }

    public GLAQueries(GLAIndicator glaIndicator, String hql, String question_name) {
        this.glaIndicator = glaIndicator;
        this.hql = hql;
        this.question_name = question_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GLAIndicator getGlaIndicator() {
        return glaIndicator;
    }

    public void setGlaIndicator(GLAIndicator glaIndicator) {
        this.glaIndicator = glaIndicator;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }
}
