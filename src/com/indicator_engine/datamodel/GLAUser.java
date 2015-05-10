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
 * Created by Tanmaya Mahapatra on 17-03-2015.
 */
@Entity
@Table(name = "gla_User")
public final class GLAUser implements Serializable {

    @Id
    @Column(name = "user_id",unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Expose
    private long id;
    @Column(name = "username", nullable = false)
    @Expose
    private String username;
    @Column(name = "password", nullable = false)
    @Expose
    private String password;
    @Column(name = "email", nullable = false)
    @Expose
    private String email;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "glaUser")
    private Set<GLAEvent> events = new HashSet<GLAEvent>(0);

    public GLAUser(){}

    public GLAUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public GLAUser(String username, String password, String email, Set<GLAEvent> events) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.events = events;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<GLAEvent> getEvents() {
        return events;
    }

    public void setEvents(Set<GLAEvent> events) {
        this.events = events;
    }
}
