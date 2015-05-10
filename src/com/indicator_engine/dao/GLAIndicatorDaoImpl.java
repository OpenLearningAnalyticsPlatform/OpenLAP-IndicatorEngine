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

import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAIndicatorProps;
import com.indicator_engine.datamodel.GLAQueries;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
public class GLAIndicatorDaoImpl implements GLAIndicatorDao {

    static Logger log = Logger.getLogger(GLAIndicatorDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public long add(GLAIndicator glaIndicator, GLAQueries glaQueries) {
        log.info("Executing add() : GLAIndicatorDaoImpl : MODE : ADD OR UPDATE");
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        factory.getCurrentSession().saveOrUpdate(glaIndicator);
        log.info("Indicator Saved..." + glaIndicator.getId());
        log.info("Saving associated Qeries \n");
        glaQueries.setGlaIndicator(glaIndicator);
        glaIndicator.getQueries().add(glaQueries);
        log.info("Finished Saving associated Qeries \n");
        factory.getCurrentSession().save(glaQueries);
        GLAIndicatorProps glaIndicatorProps  = new GLAIndicatorProps();
        glaIndicatorProps.setTotalExecutions(1);
        glaIndicatorProps.setLast_executionTime(new java.sql.Timestamp(now.getTime()));
        glaIndicatorProps.setGlaIndicator(glaIndicator);
        glaIndicator.setGlaIndicatorProps(glaIndicatorProps);
        factory.getCurrentSession().save(glaIndicatorProps);
        return glaIndicator.getId();
    }
    @Override
    @Transactional
    public GLAIndicator loadByIndicatorID(long ID){
        Session session = factory.getCurrentSession();
        GLAIndicator glaIndicator = null;
        Criteria criteria = session.createCriteria(GLAIndicator.class);
        criteria.setFetchMode("queries", FetchMode.JOIN);
        criteria.setFetchMode("glaIndicatorProps", FetchMode.JOIN);
        criteria.add(Restrictions.eq("id", ID));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Object result = criteria.uniqueResult();
        if (result != null) {
            glaIndicator = (GLAIndicator) result;
        }
        return glaIndicator;

    }
    @Override
    @Transactional
    public List<GLAIndicator> loadByIndicatorByName(String indicatorName) {
        Session session = factory.getCurrentSession();
        indicatorName = "%"+indicatorName+"%";
        GLAIndicator glaIndicator = null;
        Criteria criteria = session.createCriteria(GLAIndicator.class);
        criteria.setFetchMode("queries", FetchMode.JOIN);
        criteria.setFetchMode("glaIndicatorProps", FetchMode.JOIN);
        criteria.add(Restrictions.ilike("indicator_name", indicatorName));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return  criteria.list();
    }

    @Override
    @Transactional
    public List<GLAIndicator> displayall(){
        return factory.openSession().createQuery("from GLAIndicator gI").list();
    }
    @Override
    @Transactional
    public List<GLAIndicator> loadIndicatorsRange(long startRange, long endRange){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAIndicator.class);
        criteria.setFetchMode("queries", FetchMode.JOIN);
        criteria.setFetchMode("glaIndicatorProps", FetchMode.JOIN);
        criteria.add(Restrictions.ge("id", startRange));
        criteria.add(Restrictions.le("id", endRange));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return  criteria.list();

    }

    @Override
    @Transactional
    public List<GLAIndicator> searchIndicatorsName(String searchParameter){
        searchParameter = "%"+searchParameter+"%";
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAIndicator.class);
        criteria.setFetchMode("queries", FetchMode.JOIN);
        criteria.setFetchMode("glaIndicatorProps", FetchMode.JOIN);
        criteria.add(Restrictions.ilike("indicator_name", searchParameter));
        return  criteria.list();
    }

    @Override
    @Transactional
    public int getTotalIndicators() {
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLAIndicator.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }

    @Override
    @Transactional
    public long findIndicatorID(String indicatorName) {
        Session session = factory.getCurrentSession();
        long indicatorID = 0;
        Criteria criteria = session.createCriteria(GLAIndicator.class);
        criteria.setFetchMode("queries", FetchMode.JOIN);
        criteria.setFetchMode("glaIndicatorProps", FetchMode.JOIN);
        criteria.setProjection(Projections.property("id"));
        criteria.add(Restrictions.eq("indicator_name", indicatorName));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Object result = criteria.uniqueResult();
        if (result != null) {
            indicatorID = (long) result;
        }
        return indicatorID;
    }
}
