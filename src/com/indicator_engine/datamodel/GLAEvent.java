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
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Tanmaya Mahapatra on 28-02-2015.
 */
@Entity
@Table(name = "gla_Event")
public final class GLAEvent implements Serializable {

    @Id
    @Column(name = "event_id",unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Expose
    private long id;
    @Column(name = "action", nullable = false)
    @Expose
    private String action;
    @Column(name = "session", nullable = false)
    @Expose
    private String session;
    @Column(name = "platform",nullable = false)
    @Expose
    private String platform;
    @Column(name = "timestamp", nullable = false)
    @Expose
    private Timestamp timestamp;
    @Column(name = "source", nullable = false)
    @Expose
    private String source;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "glaEvent")
    private Set<GLAEntity> entities = new HashSet<GLAEntity>(0);
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Expose
    private GLAUser glaUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @Expose
    private GLACategory glaCategory;

    public GLAEvent(){}
    public GLAEvent(String action, String session, String platform, Timestamp timestamp) {
        this.action = action;
        this.session = session;
        this.platform = platform;
        this.timestamp = timestamp;
    }
    public GLAEvent(String action, String session, String platform, Timestamp timestamp, Set<GLAEntity> entities) {
        this.action = action;
        this.session = session;
        this.platform = platform;
        this.timestamp = timestamp;
        this.entities = entities;
    }

    public GLAEvent(String action, String session, String platform, Timestamp timestamp, GLAUser glaUser) {
        this.action = action;
        this.session = session;
        this.platform = platform;
        this.timestamp = timestamp;
        this.glaUser = glaUser;
    }

    public GLAEvent(String action, String session, String platform, Timestamp timestamp, Set<GLAEntity> entities, GLAUser glaUser) {
        this.action = action;
        this.session = session;
        this.platform = platform;
        this.timestamp = timestamp;
        this.entities = entities;
        this.glaUser = glaUser;
    }

    public GLAEvent(String action, String session, String platform, Timestamp timestamp, Set<GLAEntity> entities, GLAUser glaUser, GLACategory glaCategory) {
        this.action = action;
        this.session = session;
        this.platform = platform;
        this.timestamp = timestamp;
        this.entities = entities;
        this.glaUser = glaUser;
        this.glaCategory = glaCategory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Set<GLAEntity> getEntities() {
        return entities;
    }

    public void setEntities(Set<GLAEntity> entities) {
        this.entities = entities;
    }

    public String getSource() {
        return source;
    }

    public GLAUser getGlaUser() {
        return glaUser;
    }

    public void setGlaUser(GLAUser glaUser) {
        this.glaUser = glaUser;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public GLACategory getGlaCategory() {
        return glaCategory;
    }

    public void setGlaCategory(GLACategory glaCategory) {
        this.glaCategory = glaCategory;
    }
}
