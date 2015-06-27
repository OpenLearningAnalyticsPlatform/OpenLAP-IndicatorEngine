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

import com.indicator_engine.datamodel.GLACategory;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 23-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class GLACategoryDaoImpl implements GLACategoryDao {
    static Logger log = Logger.getLogger(GLAEventDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;
    @Override
    @Transactional
    public long add(GLACategory glaCategory){

        log.info("Adding New GLA Category" + glaCategory);
        factory.getCurrentSession().saveOrUpdate(glaCategory);
        log.info("Added New GLA Category" + glaCategory + " with ID : "+ glaCategory.getId());
        return(glaCategory.getId());

    }

    @Override
    @Transactional
    public List<GLACategory> loadAll(String colName, String sortDirection, boolean sort) {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLACategory.class);
        criteria.setFetchMode("events", FetchMode.JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if(sort) {
            if(sortDirection.equals("asc"))
                criteria.addOrder(Order.asc(colName));
            else
                criteria.addOrder(Order.desc(colName));
        }
        return criteria.list();
    }

    @Override
    @Transactional
    public int getTotalCategories() {
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLACategory.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }
    @Override
    @Transactional
    public List<String> selectAllMinors(){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLACategory.class)
                .setProjection(Projections.property("minor"));
        return criteria.list();
    }

    @Override
    @Transactional
    public GLACategory loadCategoryByName(String categoryname){
        Session session = factory.getCurrentSession();
        GLACategory glaCategory = null;
        Criteria criteria = session.createCriteria(GLACategory.class)
                .add(Restrictions.eq("minor", categoryname));

        // Convenience method to return a single instance that matches the
        // query, or null if the query returns no results.
        //
        Object result = criteria.uniqueResult();
        if (result != null) {
            glaCategory = (GLACategory) result;
        }
        return glaCategory;
    }

    @Override
    @Transactional
    public long findCategoryID(String minor){
        Session session = factory.getCurrentSession();
        GLACategory glaCategory = null;
        Criteria criteria = session.createCriteria(GLACategory.class)
                .add(Restrictions.eq("minor", minor));
        Object result = criteria.uniqueResult();
        if (result != null) {
            glaCategory = (GLACategory) result;
        }
        return glaCategory.getId();
    }

    @Override
    @Transactional
    public List<String> findCategoryByID(Long category_id, String sentity){
        Session session = factory.getCurrentSession();
        String hql = "SELECT "+ sentity+ " FROM GLACategory WHERE id ="+ category_id;
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    @Transactional
    public List<GLACategory> searchCategoryByMinor(String searchParameter, boolean exactSearch,
                                                   String colName, String sortDirection, boolean sort){
        if(!exactSearch)
            searchParameter = "%"+searchParameter+"%";
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLACategory.class);
        criteria.setFetchMode("events", FetchMode.JOIN);
        if(!exactSearch)
            criteria.add(Restrictions.ilike("minor", searchParameter));
        else
            criteria.add(Restrictions.eq("minor", searchParameter));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if(sort) {
            if(sortDirection.equals("asc"))
                criteria.addOrder(Order.asc(colName));
            else
                criteria.addOrder(Order.desc(colName));
        }
        return  criteria.list();
    }

}
