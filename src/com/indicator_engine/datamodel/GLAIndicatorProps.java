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
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
@Entity
@Table(name = "gla_IndicatorProps")
public class GLAIndicatorProps implements Serializable {

    @Id
    @Column(name = "props_id", unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Expose
    private long id;

    @Column(name = "lex_time", nullable = false)
    @Expose
    private Timestamp last_executionTime;

    @Column(name = "execution_counter", nullable = false)
    @Expose
    private int totalExecutions;

    @Column(name = "user_name", nullable = false)
    @Expose
    private String userName;

    @Column(name = "composite", nullable = false)
    @Expose
    private boolean isComposite;

    @Column(name = "json_data", nullable = false, columnDefinition="TEXT")
    @Expose
    private String json_data;

    @Column(name = "chart_type", nullable = false)
    @Expose
    private String chartType;

    @Column(name = "chart_engine", nullable = false)
    @Expose
    private String chartEngine;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indicator_id", nullable = false)
    private GLAIndicator glaIndicator;

    @PrePersist
    public void prePersist() {
        if(userName == null) //We set default value in case if the value is not set yet.
            userName = "admin";
    }
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

    public GLAIndicator getGlaIndicator() {
        return glaIndicator;
    }

    public void setGlaIndicator(GLAIndicator glaIndicator) {
        this.glaIndicator = glaIndicator;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isComposite() {
        return isComposite;
    }

    public void setComposite(boolean isComposite) {
        this.isComposite = isComposite;
    }

    public String getJson_data() {
        return json_data;
    }

    public void setJson_data(String json_data) {
        this.json_data = json_data;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getChartEngine() {
        return chartEngine;
    }

    public void setChartEngine(String chartEngine) {
        this.chartEngine = chartEngine;
    }
}
