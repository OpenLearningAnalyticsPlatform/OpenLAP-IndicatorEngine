/*
 * Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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

    /**
     * Adds a new GLACategory Item to the database.
     * @param glaCategory GLACategory Item to be saved to DB.
     * @return Returns the Category ID of the newly added Category.
     */
    public long add(GLACategory glaCategory){

        log.info("Adding New GLA Category" + glaCategory);
        factory.getCurrentSession().saveOrUpdate(glaCategory);
        log.info("Added New GLA Category" + glaCategory + " with ID : "+ glaCategory.getId());
        return(glaCategory.getId());

    }

    /**
     * Loads all the Category Items from Database..
     * @param colName Column Name to be used for sorting the results before it returns.
     * @param sortDirection Specifies the Sort Direction : asc or desc
     * @param sort Turn Sorting On or Off.
     * @return Returns all the Category Items present in DB.
     */
    @Override
    @Transactional(readOnly = true)
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

    /**
     * Returns the Total count of Category Items from Database.
     * @return Total count of Category Items from Database.
     */
    @Override
    @Transactional(readOnly = true)
    public int getTotalCategories() {
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLACategory.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }
    /**
     * Loads all the Minors Present in the Database.
     * @return List of Minors present in DB.
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> selectAllMinors(){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLACategory.class)
                .setProjection(Projections.property("minor"));
        return criteria.list();
    }

    /**
     * Loads a Category using its Name.
     * @param categoryname Name used to search for a Category.
     * @return Returns the loaded Category item.
     */
    @Override
    @Transactional(readOnly = true)
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

    /**
     * Finds the Category ID of a Particular Minor.
     * @param minor Minor whose Category ID is required.
     * @return Returns the Category ID of a Particular Minor.
     */
    @Override
    @Transactional(readOnly = true)
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

    /**
     * Finds the Categories Using a Category ID and Entity Key.
     * @param category_id Category ID to be used for searching.
     * @param sentity Entity Key Term for Searching.
     * @return Returns List of Matching Categories .
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> findCategoryByID(Long category_id, String sentity){
        Session session = factory.getCurrentSession();
        String hql = "SELECT "+ sentity+ " FROM GLACategory WHERE id ="+ category_id;
        Query query = session.createQuery(hql);
        return query.list();
    }

    /**
     * Searches Category Items based on a Minor Value.
     * @param searchParameter Minor Value used for Searching.
     * @param exactSearch Search Type : Exact or Likewise.
     * @param colName Column Name used for Sorting the Results.
     * @param sortDirection Sorting Direction : Ascending/Descending.
     * @param sort Turn Sorting ON/OFF.
     * @return Returns list of Matching GLACategory Items.
     */
    @Override
    @Transactional(readOnly = true)
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
