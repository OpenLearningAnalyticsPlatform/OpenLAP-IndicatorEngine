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
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Tanmaya Mahapatra on 28-02-2015.
 */
@Entity
@Table(name = "gla_Entity")
public final class GLAEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "entity_id", unique = true, nullable = false)
    private Integer entityId;
    @Column(name = "keys",nullable = false)
    private String key;
    @Column(name = "value",nullable = false, columnDefinition="TEXT")
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private GLAEvent glaEvent;

    public GLAEntity() {}

    public GLAEntity(String key, String value, GLAEvent event) {
        this.key = key;
        this.value = value;
        this.glaEvent = event;
    }
    public GLAEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public GLAEvent getEvent() {
        return glaEvent;
    }

    public void setEvent(GLAEvent event) {
        this.glaEvent = event;
    }

}
