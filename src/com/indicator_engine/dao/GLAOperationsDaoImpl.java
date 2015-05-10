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

import com.indicator_engine.datamodel.GLAOperations;
import com.indicator_engine.datamodel.GLAUser;
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
 * Created by Tanmaya Mahapatra on 01-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class GLAOperationsDaoImpl implements GLAOperationsDao {
    static Logger log = Logger.getLogger(GLAOperationsDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;
    @Override
    @Transactional
    public long addNewOperation(GLAOperations glaOperation){
        log.info("Adding New GLA Operation" + glaOperation);
        factory.getCurrentSession().saveOrUpdate(glaOperation);
        log.info("Added New GLA Operation" + glaOperation + " with ID : "+ glaOperation.getId());
        return(glaOperation.getId());
    }

    @Override
    @Transactional
    public List<String> selectAllOperations(){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAOperations.class)
                .setProjection(Projections.property("operations"));
        return criteria.list();
    }

    @Override
    @Transactional
    public List<GLAOperations> loadOperationsRange(long maxId) {
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAOperations.class);
        criteria.add(Restrictions.le("id", maxId));
        return  criteria.list();

    }

    @Override
    @Transactional
    public int getTotalOperations() {
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLAOperations.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }

}
