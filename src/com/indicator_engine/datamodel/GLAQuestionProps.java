/*
 * Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Tanmaya Mahapatra on 04-06-2015.
 */
@Entity
@Table(name = "gla_QuestionProps")
public class GLAQuestionProps implements Serializable {

    @Id
    @Column(name = "props_id", unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Expose
    private long id;

    @Column(name = "lex_time", nullable = false)
    @Expose
    private Timestamp last_executionTime;

    @Column(name = "user_name", nullable = false)
    @Expose
    private String userName;

    @Column(name = "execution_counter", nullable = false)
    @Expose
    private int totalExecutions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private GLAQuestion glaQuestion;

    public GLAQuestionProps() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getLast_executionTime() {
        return last_executionTime;
    }

    public void setLast_executionTime(Timestamp last_executionTime) {
        this.last_executionTime = last_executionTime;
    }

    public int getTotalExecutions() {
        return totalExecutions;
    }

    public void setTotalExecutions(int totalExecutions) {
        this.totalExecutions = totalExecutions;
    }

    public GLAQuestion getGlaQuestion() {
        return glaQuestion;
    }

    public void setGlaQuestion(GLAQuestion glaQuestion) {
        this.glaQuestion = glaQuestion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

