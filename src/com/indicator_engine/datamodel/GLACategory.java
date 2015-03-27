/*
 *
 *  * Copyright (C) 2015  Tanmaya Mahapatra
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package com.indicator_engine.datamodel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Tanmaya Mahapatra on 17-03-2015.
 */
@Entity
@Table(name = "gla_Category")
public final class GLACategory implements Serializable {

    @Id
    @Column(name = "category_id",unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @Column(name = "major", nullable = false)
    private String major;
    @Column(name = "minor", nullable = false)
    private String minor;
    @Column(name = "type", nullable = false)
    private String type;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "glaCategory")
    private transient Set<GLAEvent> events = new HashSet<GLAEvent>(0);

    public GLACategory(){}

    public GLACategory(String major, String minor, String type, Set<GLAEvent> events) {
        this.major = major;
        this.minor = minor;
        this.type = type;
        this.events = events;
    }

    public GLACategory(String major, String minor, String type) {
        this.major = major;
        this.minor = minor;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<GLAEvent> getEvents() {
        return events;
    }

    public void setEvents(Set<GLAEvent> events) {
        this.events = events;
    }
}
