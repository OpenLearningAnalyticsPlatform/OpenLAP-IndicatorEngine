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

package com.indicator_engine.dao;

import com.indicator_engine.datamodel.SecurityRoleEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 18-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class SecurityRoleEntityDaoImpl implements SecurityRoleEntityDao {
    static Logger log = Logger.getLogger(UserCredentialsDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public List<SecurityRoleEntity> searchRoles(String ROLE ) {
        Session session = factory.getCurrentSession();
        Criteria cr = session.createCriteria(SecurityRoleEntity.class);
        cr.add(Restrictions.eq("role", ROLE));
        return cr.list();
    }

    @Override
    @Transactional
    public List<SecurityRoleEntity> searchRolesByID(long UID ) {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(SecurityRoleEntity.class);
        criteria.setFetchMode("userCredentials", FetchMode.JOIN);
        criteria.createAlias("userCredentials", "uc");
        criteria.add(Restrictions.eq("uc.uid", UID));
        return criteria.list();
    }
}
