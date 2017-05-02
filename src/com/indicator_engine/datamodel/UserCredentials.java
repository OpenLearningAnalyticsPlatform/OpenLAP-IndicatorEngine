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
import com.indicator_engine.otp.OTP;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Tanmaya Mahapatra on 27-02-2015.
 */

@Entity
@Table(name = "user_credentials")
public final class UserCredentials implements Serializable {

    @Id
    @Column(name = "uid")
    @GeneratedValue
    private long uid;
    @Column(name = "password", nullable=false)
    private String password;
    private String otp;
    private boolean  activation_status;
    @Column(name = "uname", nullable=false)
    private String uname;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private UserProfile up;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCredentials")
    private Set<SecurityRoleEntity> roleEntitySet = new HashSet<SecurityRoleEntity>(0);

    public UserCredentials() {
    }

    public UserCredentials(String uname,String password, UserProfile up){
        this.password = password;
        this.uname = uname;
        this.up = up;
        this.otp = OTP.generatePassword();
        this.activation_status = false;
    }
    public UserCredentials(String uname,String password, UserProfile up, Set<SecurityRoleEntity> roleEntitySet) {
        this.password = password;
        this.uname = uname;
        this.up = up;
        this.otp = OTP.generatePassword();
        this.activation_status = false;
        this.roleEntitySet = roleEntitySet;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean getActivation_status() {
        return activation_status;
    }

    public void setActivation_status(boolean activation_status) {
        this.activation_status = activation_status;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public UserProfile getUp() {
        return up;
    }

    public void setUp(UserProfile up) {
        this.up = up;
    }

    public boolean isActivation_status() {
        return activation_status;
    }

    public Set<SecurityRoleEntity> getRoleEntitySet() {
        return roleEntitySet;
    }

    public void setRoleEntitySet(Set<SecurityRoleEntity> roleEntitySet) {
        this.roleEntitySet = roleEntitySet;
    }
}
