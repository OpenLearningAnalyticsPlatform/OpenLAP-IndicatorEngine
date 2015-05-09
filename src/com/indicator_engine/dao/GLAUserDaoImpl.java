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

import com.indicator_engine.datamodel.GLAUser;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 19-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class GLAUserDaoImpl implements  GLAUserDao{

    static Logger log = Logger.getLogger(GLAUserDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;
    @Override
    @Transactional
    public long add(GLAUser glaUser){
        log.info("Adding New GLA USer" + glaUser);
        factory.getCurrentSession().saveOrUpdate(glaUser);
        log.info("Added New GLA USer" + glaUser + " with ID : "+ glaUser.getId());
        return(glaUser.getId());

    }
    @Override
    @Transactional
    public List<GLAUser> loadUsersRange(long startRange, long endRange) {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAUser.class);
        criteria.setFetchMode("events", FetchMode.JOIN);
        criteria.add(Restrictions.ge("id", startRange));
        criteria.add(Restrictions.le("id", endRange));
        return  criteria.list();

    }

    @Override
    @Transactional
    public List<GLAUser> loadAll() {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAUser.class);
        criteria.setFetchMode("events", FetchMode.JOIN);
        return criteria.list();

    }

    @Override
    @Transactional
    public int getTotalUsers() {
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLAUser.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }

    @Override
    @Transactional
    public List<String> selectAllUsers() {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAUser.class)
                .setProjection(Projections.property("username"));
        return criteria.list();

    }

    @Override
    @Transactional
    public List<GLAUser> searchLikeUsers(String searchParameter){
        searchParameter = "%"+searchParameter+"%";
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAUser.class);
        criteria.setFetchMode("events", FetchMode.JOIN);
        criteria.add(Restrictions.ilike("username", searchParameter));
        return  criteria.list();
    }


    @Override
    @Transactional
    public GLAUser loaduserByName(String username)
    {
        Session session = factory.getCurrentSession();
        GLAUser glaUser = null;
        Criteria criteria = session.createCriteria(GLAUser.class)
                .add(Restrictions.eq("username", username));

        // Convenience method to return a single instance that matches the
        // query, or null if the query returns no results.
        //
        Object result = criteria.uniqueResult();
        if (result != null) {
             glaUser = (GLAUser) result;
        }
        return glaUser;
    }

    @Override
    @Transactional
    public List<String> searchSimilarUserDetails(String userDetail, String searchCriteria)
    {
        Session session = factory.getCurrentSession();
        String hql = null;
        if(userDetail.equals("UserName"))
            hql = "SELECT DISTINCT username FROM GLAUser WHERE username LIKE '%"+ searchCriteria +"%'";
        else if (userDetail.equals("UserEmail"))
            hql = "SELECT DISTINCT email FROM GLAUser WHERE email LIKE '%"+ searchCriteria +"%'";
        log.info("Search Hibernate Query " + hql);
        Query query = session.createQuery(hql);
        log.info("Search Hibernate Query " + query.list());
        return query.list();
    }

}
