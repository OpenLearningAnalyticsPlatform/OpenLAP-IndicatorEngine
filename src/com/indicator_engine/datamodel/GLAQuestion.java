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
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Tanmaya Mahapatra on 29-05-2015.
 */
@Entity
@Table(name = "gla_Question")
public class GLAQuestion implements Serializable {
    @Id
    @Column(name = "question_id", unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Expose
    private long id;

    @Column(name = "question_name", nullable = false, unique = true)
    @Expose
    private String question_name;

    @Column(name = "indicators_num", nullable = false)
    @Expose
    private int indicators_num;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "gla_Question_Indicator", joinColumns = { @JoinColumn(name = "question_id") }, inverseJoinColumns = { @JoinColumn(name = "indicator_id") })
    private Set<GLAIndicator> glaIndicators = new HashSet<GLAIndicator>();

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL,
            mappedBy="glaQuestion", orphanRemoval=true)
    private GLAQuestionProps  glaQuestionProps;

    public GLAQuestion() {
    }

    public GLAQuestion(String question_name, int indicators_num, Set<GLAIndicator> glaIndicators) {
        this.question_name = question_name;
        this.indicators_num = indicators_num;
        this.glaIndicators = glaIndicators;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }

    public int getIndicators_num() {
        return indicators_num;
    }

    public void setIndicators_num(int indicators_num) {
        this.indicators_num = indicators_num;
    }

    public Set<GLAIndicator> getGlaIndicators() {
        return glaIndicators;
    }

    public void setGlaIndicators(Set<GLAIndicator> glaIndicators) {
        this.glaIndicators = glaIndicators;
    }

    public GLAQuestionProps getGlaQuestionProps() {
        return glaQuestionProps;
    }

    public void setGlaQuestionProps(GLAQuestionProps glaQuestionProps) {
        this.glaQuestionProps = glaQuestionProps;
    }
}
