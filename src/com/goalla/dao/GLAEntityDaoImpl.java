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

package com.goalla.dao;

import com.goalla.datamodel.GLAEntity;
import com.goalla.datamodel.GLAEvent;
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

/**
 * Created by Tanmaya Mahapatra on 23-03-2015.
 */
public class GLAEntityDaoImpl implements GLAEntityDao{
    static Logger log = Logger.getLogger(GLAEventDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;
    @Override
    @Transactional
    public long add(GLAEntity glaEntity){
        log.info("Adding New GLA Entity" + glaEntity);
        factory.getCurrentSession().saveOrUpdate(glaEntity);
        log.info("Added New GLA Entity" + glaEntity + " with ID : "+ glaEntity.getEntityId());
        return(glaEntity.getEntityId());

    }
    @Override
    @Transactional
    public void addWithExistingEvent(GLAEntity glaEntity, String id){
        Session session = factory.getCurrentSession();
        GLAEvent glaEvent = null;
        Criteria criteria = session.createCriteria(GLAEvent.class)
                .add(Restrictions.eq("id", Long.parseLong(id)));

        // Convenience method to return a single instance that matches the
        // query, or null if the query returns no results.
        //
        Object result = criteria.uniqueResult();
        if (result != null) {
            glaEvent = (GLAEvent) result;
        }
        factory.getCurrentSession().saveOrUpdate(glaEvent);
        glaEntity.setEvent(glaEvent);
        glaEvent.getEntities().add(glaEntity);
        factory.getCurrentSession().save(glaEntity);
    }

    @Override
    @Transactional
    public List<GLAEntity> loadEntitesRange(Integer maxId) {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAEntity.class);
        criteria.setFetchMode("glaEvent", FetchMode.JOIN);
        criteria.add(Restrictions.le("id", maxId));
        return  criteria.list();

    }

    @Override
    @Transactional
    public int getTotalEntities() {
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLAEntity.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }

}
