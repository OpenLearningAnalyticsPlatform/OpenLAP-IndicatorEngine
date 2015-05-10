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

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
public class GLAQueriesDaoImpl implements GLAQueriesDao {

    static Logger log = Logger.getLogger(GLAQueriesDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public long add(GLAQueries glaQueries){
        log.info("Adding New GLA Query" + glaQueries);
        factory.getCurrentSession().saveOrUpdate(glaQueries);
        log.info("Added New GLA QUERY" + glaQueries + " with ID : "+ glaQueries.getId());
        return(glaQueries.getId());

    }
    @Override
    @Transactional
    public void addWithExistingIndicator(GLAQueries glaQueries, long id){
        Session session = factory.getCurrentSession();
        GLAIndicator glaIndicator = null;
        Criteria criteria = session.createCriteria(GLAIndicator.class)
               .add(Restrictions.eq("id", id));
       Object result = criteria.uniqueResult();
       if (result != null) {
           glaIndicator = (GLAIndicator) result;
       }
        factory.getCurrentSession().saveOrUpdate(glaIndicator);
        glaQueries.setGlaIndicator(glaIndicator);
        glaIndicator.getQueries().add(glaQueries);
        factory.getCurrentSession().save(glaQueries);
    }

    @Override
    @Transactional
    public List<GLAQueries> displayall(){
        return factory.openSession().createQuery("from GLAQueries gQ").list();
    }

    @Override
    @Transactional
    public List<GLAQueries> searchQuestionsName(String searchParameter,boolean exactSearch) {
        if(!exactSearch)
            searchParameter = "%"+searchParameter+"%";
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAQueries.class);
        criteria.setFetchMode("glaIndicator", FetchMode.JOIN);
        if(!exactSearch)
            criteria.add(Restrictions.ilike("question_name", searchParameter));
        else {
            criteria.add(Restrictions.eq("question_name", searchParameter));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        }
        return  criteria.list();
    }

    @Override
    @Transactional
    public long findQuestionID(String questionName) {
        Session session = factory.getCurrentSession();
        long indicatorID = 0;
        Criteria criteria = session.createCriteria(GLAQueries.class);
        criteria.setFetchMode("glaIndicator", FetchMode.JOIN);
        criteria.setProjection(Projections.property("id"));
        criteria.add(Restrictions.eq("question_name", questionName));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Object result = criteria.uniqueResult();
        if (result != null) {
            indicatorID = (long) result;
        }
        return indicatorID;
    }

    @Override
    @Transactional
    public void deleteQuestion(long question_id){
        long indicatorId = 0 ;
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAQueries.class);
        criteria.createAlias("glaIndicator", "indicator");
        criteria.setFetchMode("indicator", FetchMode.JOIN);
        criteria.setProjection(Projections.property("indicator.id"));
        criteria.add(Restrictions.eq("id", question_id));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Object result = criteria.uniqueResult();
        if (result != null) {
            indicatorId = (Long) result;
        }
        if(indicatorId !=0){
            GLAIndicator glaIndicator = null;
            Set<GLAQueries> glaQuery;
            Criteria indCriteria = session.createCriteria(GLAIndicator.class);
            indCriteria.setFetchMode("queries", FetchMode.JOIN);
            indCriteria.setFetchMode("glaIndicatorProps", FetchMode.JOIN);
            indCriteria.add(Restrictions.eq("id", indicatorId));
            indCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            result = indCriteria.uniqueResult();
            if (result != null) {
                glaIndicator = (GLAIndicator) result;
            }
            if(glaIndicator != null) {
                glaQuery = glaIndicator.getQueries();
                for (GLAQueries gQ : glaQuery) {
                    if (gQ.getId() == question_id){
                        glaIndicator.getQueries().remove(gQ);
                        gQ.setGlaIndicator(null);
                        session.delete(gQ);
                    }
                }
                if(glaIndicator.getQueries().size() == 0)
                    session.delete(glaIndicator);
            }
        }
    }

}
