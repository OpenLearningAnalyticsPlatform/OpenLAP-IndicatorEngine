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

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.google.gson.annotations.Expose;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
@Entity
@Table(name = "gla_Indicator")
public class GLAIndicator implements Serializable {

    @Id
    @Column(name = "indicator_id", unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Expose
    private long id;

    @Column(name = "indicator_name", nullable = false, unique = true)
    @Expose
    private String indicator_name;

    @Column(name = "short_name", nullable = false)
    @Expose
    private String short_name;

    @OneToOne(cascade=CascadeType.ALL, mappedBy="glaIndicator")
    private GLAIndicatorProps  glaIndicatorProps;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "glaIndicator")
    private  Set<GLAQueries> queries = new HashSet<GLAQueries>(0);

    public GLAIndicator() {
        this.short_name = "";
    }

    public GLAIndicator(String indicator_name) {
        this.indicator_name = indicator_name;
        this.short_name = "";
    }

    public GLAIndicator( String indicator_name, Set<GLAQueries> queries) {
        this.indicator_name = indicator_name;
        this.queries = queries;
    }

    public long getId() {
        return id;
    }

    public GLAIndicatorProps getGlaIndicatorProps() {
        return glaIndicatorProps;
    }

    public void setGlaIndicatorProps(GLAIndicatorProps glaIndicatorProps) {
        this.glaIndicatorProps = glaIndicatorProps;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIndicator_name() {
        return indicator_name;
    }

    public void setIndicator_name(String indicator_name) {
        this.indicator_name = indicator_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public Set<GLAQueries> getQueries() {
        return queries;
    }

    public void setQueries(Set<GLAQueries> queries) {
        this.queries = queries;
    }
}
