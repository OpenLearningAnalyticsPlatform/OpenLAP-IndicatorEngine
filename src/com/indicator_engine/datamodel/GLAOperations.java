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
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Tanmaya Mahapatra on 17-03-2015.
 */
@Entity
@Table(name = "gla_Operations")
public final class GLAOperations implements Serializable {

    @Id
    @Column(name = "operation_id",unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Expose
    private long id;
    @Column(name = "operations", nullable = false, unique = true)
    @Expose
    private String operations;

    public GLAOperations() {}
    public GLAOperations(String operations) {
        this.operations = operations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }
}
