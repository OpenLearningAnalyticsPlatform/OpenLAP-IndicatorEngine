

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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 28-02-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class GLAEventDaoImpl implements GLAEventDao {
    static Logger log = Logger.getLogger(GLAEventDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;
    /**
     * Adds a new GLA Event Object to the Database & associates with a Particular GLA Entity Object.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public void add(GLAEvent gEvent, GLAEntity entity) {
        log.info("Executing add()");
        factory.getCurrentSession().saveOrUpdate(gEvent);
        log.info("Contact Saved..." + gEvent.getId());
        entity.setEvent(gEvent);
        gEvent.getEntities().add(entity);
        factory.getCurrentSession().save(entity);
    }

    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<GLAEvent> loadAllEvents(String colName, String sortDirection, boolean sort){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEvent.class);
        criteria.setFetchMode("entities", FetchMode.JOIN);
        criteria.setFetchMode("glaUser", FetchMode.JOIN);
        criteria.setFetchMode("glaCategory", FetchMode.JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if(sort) {
            if(sortDirection.equals("asc"))
                criteria.addOrder(Order.asc(colName));
            else
                criteria.addOrder(Order.desc(colName));
        }
        return  criteria.list();

    }
    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public int getTotalEvents(){
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLAEvent.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }

    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<String> selectAllEvents() {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEvent.class)
                .setProjection(Projections.property("id"));
        return criteria.list();

    }

    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public GLAEvent loadEventByID(Long id)
    {
        Session session = factory.getCurrentSession();
        GLAEvent glaEvent = null;
        Criteria criteria = session.createCriteria(GLAEvent.class)
                .add(Restrictions.eq("id", id));

        // Convenience method to return a single instance that matches the
        // query, or null if the query returns no results.
        //
        Object result = criteria.uniqueResult();
        if (result != null) {
            glaEvent = (GLAEvent) result;
        }
        return glaEvent;
    }
    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<String> loadEventByCategoryID(Long categoryID){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEvent.class);
        criteria.createAlias("glaCategory", "category");
        criteria.setFetchMode("entities", FetchMode.JOIN);
        criteria.setFetchMode("glaUser", FetchMode.JOIN);
        criteria.setFetchMode("category", FetchMode.JOIN);
        criteria.add(Restrictions.eq("category.id", categoryID));
        log.info("Dumping criteria" + criteria.list());
        List<GLAEvent> list  = criteria.list();
        log.info("Dumping List<GLAEvent> list" + list);
        List<String> selectedEvents = new ArrayList<>();
        for(GLAEvent data : list) {
            selectedEvents.add(data.getAction());
            log.info("Dumping List<GLAEvent>.Actions list" + data.getAction());
        }
        log.info("Dumping Events By Category ID" + selectedEvents);
        return selectedEvents;
    }
    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<String> selectAll(String EventComponent){

        Session session = factory.getCurrentSession();
        String hql = "SELECT DISTINCT " + EventComponent +" FROM GLAEvent";
        Query query = null;
        try{
            query = session.createQuery(hql);
        } catch(Exception ex){String message = getStackTrace(ex);}
        return query.list();
    }

    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<Long> findCategoryId(String action, String source, String platform){
        Session session = factory.getCurrentSession();
        String hql = "SELECT DISTINCT glaCategory.id FROM GLAEvent WHERE action = '"+ action+"'"+ " AND source = '"+ source+"'"+ " AND platform = '"+ platform+"'";
        Query query = session.createQuery(hql);
        return query.list();

    }

    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<String> searchSimilarSessionDetails(String searchType, String searchCriteria)
    {
        Session session = factory.getCurrentSession();
        String hql = null;
        if(searchType.equals("SEARCH LIKE"))
            hql = "SELECT DISTINCT session FROM GLAEvent WHERE session LIKE '%"+ searchCriteria +"%'";
        else if (searchType.equals("ALL"))
            hql = "SELECT DISTINCT session FROM GLAEvent";
        log.info("Search Session Hibernate Query " + hql);
        Query query = session.createQuery(hql);
        log.info("Result of Search Session Hibernate Query " + query.list());
        return query.list();
    }

    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<Timestamp> searchSimilarTimeDetails(String searchType, String searchCriteria)
    {
        Session session = factory.getCurrentSession();
        String hql = null;
        if(searchType.equals("LIKE"))
            hql = "SELECT DISTINCT timestamp FROM GLAEvent WHERE timestamp LIKE "+ searchCriteria +"";
        else if (searchType.equals("ALL"))
            hql = "SELECT DISTINCT timestamp FROM GLAEvent";
        else if (searchType.equals("EXACT"))
            hql = "SELECT DISTINCT timestamp FROM GLAEvent WHERE timestamp = '"+ searchCriteria +"'";
        log.info("Search Session Hibernate Query " + hql);
        Query query = session.createQuery(hql);
        log.info("Result of Search Session Hibernate Query " + query.list());
        return query.list();
    }

    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    /**
     * Adds a new GLA Entity Object to the Database.
     * @param glaEntity New GLA Entity Object to be saved.
     * @return ID of the Newly Created GLA Entity object in DB.
     **/
    @Override
    @Transactional(readOnly = true)
    public List<GLAEvent> searchEventsByAction(String searchParameter, boolean exactSearch,
                                               String colName, String sortDirection, boolean sort){
        if(!exactSearch)
            searchParameter = "%"+searchParameter+"%";
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEvent.class);
        criteria.setFetchMode("entities", FetchMode.JOIN);
        criteria.setFetchMode("glaUser", FetchMode.JOIN);
        criteria.setFetchMode("glaCategory", FetchMode.JOIN);
        if(!exactSearch)
            criteria.add(Restrictions.ilike("action", searchParameter));
        else
            criteria.add(Restrictions.eq("action", searchParameter));
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

