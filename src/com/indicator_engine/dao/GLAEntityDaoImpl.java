

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

import com.indicator_engine.datamodel.GLAEntity;
import com.indicator_engine.datamodel.GLAEvent;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 23-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class GLAEntityDaoImpl implements GLAEntityDao{
    static Logger log = Logger.getLogger(GLAEventDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;

    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional
    public long add(GLAEntity glaEntity){
        log.info("Adding New GLA Entity" + glaEntity);
        factory.getCurrentSession().saveOrUpdate(glaEntity);
        log.info("Added New GLA Entity" + glaEntity + " with ID : "+ glaEntity.getEntityId());
        return(glaEntity.getEntityId());

    }

    /**
     * Add a new GLA Entity Object in DB and associate it with an Existing GLA Event Object in Database.
     * @param glaEntity New GLA Entity object to be saved.
     * @param id Event ID to which the GLA Entity object would be associated..
     */
    @Override
    @Transactional
    public void addWithExistingEvent(GLAEntity glaEntity, String id){
        Session session = factory.getCurrentSession();
        GLAEvent glaEvent = null;
        Criteria criteria = session.createCriteria(GLAEvent.class)
                .add(Restrictions.eq("id", Long.parseLong(id)));
        Object result = criteria.uniqueResult();
        if (result != null) {
            glaEvent = (GLAEvent) result;
        }
        factory.getCurrentSession().saveOrUpdate(glaEvent);
        glaEntity.setEvent(glaEvent);
        glaEvent.getEntities().add(glaEntity);
        factory.getCurrentSession().save(glaEntity);
    }

    /**
     * Loads all GLA Entity Objects present in the Database.
     * @param colName Sort the Results using this Column Name.
     * @param sortDirection Specify Sort Direction : asc/desc.
     * @param sort Turn Sorting ON/OFF
     * @return Returns all GLA Entity Objects present in Database and sorted if specified.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<GLAEntity> loadAll(String colName, String sortDirection, boolean sort) {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEntity.class);
        criteria.createAlias("glaEvent", "events");
        criteria.setFetchMode("events", FetchMode.JOIN);
        criteria.createAlias("events.glaCategory", "category");
        criteria.setFetchMode("category", FetchMode.JOIN);
        criteria.createAlias("events.glaUser", "users");
        criteria.setFetchMode("users", FetchMode.JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if(sort) {
            if(sortDirection.equals("asc"))
                criteria.addOrder(Order.asc(colName));
            else
                criteria.addOrder(Order.desc(colName));
        }
        return criteria.list();

    }

    /**
     * Returns the Total Number of GLA Entity Objects present in the Database.
     * @return Total Number of GLA Entity Objects present in the Database.
     **/
    @Override
    @Transactional(readOnly = true)
    public int getTotalEntities() {
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLAEntity.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }

    /**
     * Loads all GLA Entity Objects present in the Database based on a specific Category ID.
     * @param categoryID Category ID used for selecting relevant GLA Entity Objects.
     * @return Returns all GLA Entity Objects present in Database and matching the specified Category ID.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<String> loadEntitiesByCategoryID(Long categoryID){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEntity.class);
        criteria.setProjection(Projections.distinct(Projections.property("key")));
        criteria.createAlias("glaEvent", "events");
        criteria.setFetchMode("events", FetchMode.JOIN);
        criteria.createAlias("events.glaCategory", "category");
        criteria.setFetchMode("category", FetchMode.JOIN);
        criteria.add(Restrictions.eq("category.id", categoryID));
        return criteria.list();

    }

    /**
     * Loads all GLA Entity Keys present in the Database based on a specific Category ID.
     * @param categoryID Category ID used for selecting relevant GLA Entity Objects.
     * @return Returns all GLA Entity Keys present in Database and matching the specified Category ID.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<String> loadEntityKeyValuesByCategoryID(Long categoryID, String key){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEntity.class);
        criteria.createAlias("glaEvent", "events");
        criteria.setFetchMode("events", FetchMode.JOIN);
        criteria.createAlias("events.glaCategory", "category");
        criteria.setFetchMode("category", FetchMode.JOIN);
        criteria.add(Restrictions.eq("category.id", categoryID));
        criteria.add(Restrictions.eq("key", key));
        criteria.setProjection(Projections.property("value"));
        return criteria.list();

    }

    /**
     * Loads all GLA Entity Objects present in the Database based on a specific Category ID.
     * @param categoryID Category ID used for selecting relevant GLA Entity Objects.
     * @return Returns all GLA Entity Objects present in Database and matching the specified Category ID.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<String> loadEntitiesByCategoryIDName(Long categoryID, String name){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEntity.class);
        criteria.createAlias("glaEvent", "events");
        criteria.setFetchMode("events", FetchMode.JOIN);
        criteria.createAlias("events.glaCategory", "category");
        criteria.setFetchMode("category", FetchMode.JOIN);
        criteria.add(Restrictions.eq("category.id", categoryID));
        criteria.add(Restrictions.eq("key", name));
        return criteria.list();

    }

    /**
     * Executes a Hibernate Query and Returns the Number of Records fetched
     * @param hql Hibernate Query to Execute.
     * @return Returns Number of Records fecthed by a specific HQL
     **/
    @Override
    @Transactional(readOnly = true)
    public long findNumber(String hql) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery(hql);
        return ((Long) query.uniqueResult()).longValue();
    }

    /**
     * Search GLA Entity Objects based on Entity Key
     * @param searchParameter Entity Key used for searching
     * @param exactSearch Search Pattern : Exact or Likewise
     * @param colName Sort Results based on this Column Name
     * @param sortDirection Specify Sort Direction ASC/DESC
     * @param sort Turn Sorting ON/OFF.
     * @return Returns all GLA Entity Objects present in Database and matching the Search Criteria.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<GLAEntity> searchEntitiesByKey(String searchParameter, boolean exactSearch,
                                               String colName, String sortDirection, boolean sort){
        if(!exactSearch)
            searchParameter = "%"+searchParameter+"%";
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEntity.class);
        criteria.createAlias("glaEvent", "events");
        criteria.setFetchMode("events", FetchMode.JOIN);
        criteria.createAlias("events.glaCategory", "category");
        criteria.setFetchMode("category", FetchMode.JOIN);
        criteria.createAlias("events.glaUser", "users");
        criteria.setFetchMode("users", FetchMode.JOIN);
        if(!exactSearch)
            criteria.add(Restrictions.ilike("key", searchParameter));
        else
            criteria.add(Restrictions.eq("key", searchParameter));
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
