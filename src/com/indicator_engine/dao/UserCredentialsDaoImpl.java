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

package com.indicator_engine.dao;

import com.indicator_engine.datamodel.SecurityRoleEntity;
import com.indicator_engine.datamodel.UserCredentials;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 26-02-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class UserCredentialsDaoImpl implements UserCredentialsDao {
    static Logger log = Logger.getLogger(UserCredentialsDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public List<UserCredentials> displayall(){
        return factory.openSession().createQuery("from UserCredentials uc").list();
    }

    public UserCredentials displayByID(int id){
        return (UserCredentials) factory.openSession().load(UserCredentials.class, id);

    }
    @Transactional
    public UserCredentials add(UserCredentials uc){
        log.info("Executing add()");
        factory.getCurrentSession().saveOrUpdate(uc);
        SecurityRoleEntity entity = new SecurityRoleEntity("ROLE_USER");
        entity.setUc(uc);
        uc.getRoleEntitySet().add(entity);
        factory.getCurrentSession().save(entity);
        System.out.println("Contact Saved..." + uc.getUid());
        return uc;
    }
    @Transactional
    public void delete(UserCredentials uc){

    }
    @Transactional
    public List<UserCredentials> searchByUserName(String uname) {
        Session session = factory.getCurrentSession();
        Criteria cr = session.createCriteria(UserCredentials.class);
        cr.add(Restrictions.eq("uname", uname));
        return cr.list();

    }

    public UserCredentials findUserByName(String userName) {
        return (UserCredentials) factory.openSession().get(UserCredentials.class, userName);

    }
    @Transactional
    public void update(UserCredentials uc){
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(uc);
    }

    @Transactional
    public void addAdminROLE(UserCredentials uc){
        log.info("Executing add()");
        factory.getCurrentSession().saveOrUpdate(uc);
        SecurityRoleEntity entity = new SecurityRoleEntity("ROLE_ADMIN");
        entity.setUc(uc);
        uc.getRoleEntitySet().add(entity);
        factory.getCurrentSession().save(entity);
        ;
    }
}
