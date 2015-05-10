/*
 * Open Platform Learning Analytics : Indicator Engine
 * Copyright (C) 2015  Learning Technologies Group, RWTH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.indicator_engine.datamodel;

import com.google.gson.annotations.Expose;

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
    @Expose
    private long id;

    @Column(name = "question_name", unique = true, nullable = false)
    @Expose
    private String question_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indicator_id", nullable = false)
    @Expose
    private GLAIndicator glaIndicator;

    @Column(name = "hql", nullable = false, columnDefinition="TEXT")
    @Expose
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
