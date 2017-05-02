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

import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAIndicatorProps;
import com.indicator_engine.datamodel.GLAQuestion;
import com.indicator_engine.datamodel.GLAQuestionProps;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tanmaya Mahapatra on 03-06-2015.
 */
public class GLAQuestionDaoImpl implements  GLAQuestionDao{

    static Logger log = Logger.getLogger(GLAIndicatorDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public long add(GLAQuestion glaQuestion,  Set<GLAIndicator> glaIndicatorHashSet){
        if(glaIndicatorHashSet == null || glaQuestion == null)
            return -1;
        log.info("Executing add() : GLAQuestionDaoImpl : MODE : ADD OR UPDATE");
        glaQuestion.setGlaIndicators(glaIndicatorHashSet);
        Set<GLAQuestion> glaQuestionSet =  new HashSet<GLAQuestion>();
        glaQuestionSet.add(glaQuestion);
        for(GLAIndicator glaIndicator : glaIndicatorHashSet)
        {
            glaIndicator.setGlaQuestions(glaQuestionSet);
        }
        factory.getCurrentSession().saveOrUpdate(glaQuestion);
        return glaQuestion.getId();
    }

    /**
     * Lists all the Questions present in the Database.
     * @param colName
     *            Column Name to be used for Sorting the results
     * @param sortDirection
     *            Sort Direction : Ascending/Descending
     * @param sort
     *            True for sorting Required and False to set sorting of results off.
     * @return
     *           Listing of all the Questions.
     *
     */
    @Override
    @Transactional
    public List<GLAQuestion> displayAll(String colName, String sortDirection, boolean sort){
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAQuestion.class);
        criteria.setFetchMode("glaIndicators", FetchMode.JOIN);
        criteria.setFetchMode("glaQuestionProps", FetchMode.JOIN);
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
     * Finds an existing Question using its name. It does an exact lookup.
     * @param questionName Name of Question to be searched in the Database.
     * @return
     *      Returns the Question ID if a match is found.
     */
    @Override
    @Transactional
    public long findQuestionID(String questionName) {
        Session session = factory.getCurrentSession();
        long indicatorID = 0;
        Criteria criteria = session.createCriteria(GLAQuestion.class);
        criteria.setFetchMode("glaIndicators", FetchMode.JOIN);
        criteria.setFetchMode("glaQuestionProps", FetchMode.JOIN);
        criteria.setProjection(Projections.property("id"));
        criteria.add(Restrictions.eq("question_name", questionName));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Object result = criteria.uniqueResult();
        if (result != null) {
            indicatorID = (long) result;
        }
        return indicatorID;
    }

    /**
     * Loads an Question from Database.
     * @param ID Question ID to be searched and loaded.
     * @return The loaded Question with its associated Indicators.
     */

    @Override
    @Transactional
    public GLAQuestion loadByQuestionID(long ID){
        Session session = factory.getCurrentSession();
        GLAQuestion glaQuestion = null;
        Criteria criteria = session.createCriteria(GLAQuestion.class);
        criteria.setFetchMode("glaIndicators", FetchMode.JOIN);
        criteria.setFetchMode("glaQuestionProps", FetchMode.JOIN);
        criteria.add(Restrictions.eq("id", ID));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Object result = criteria.uniqueResult();
        if (result != null) {
            glaQuestion = (GLAQuestion) result;
        }
        return glaQuestion;

    }

    /**
     * Loads an existing Indicator from the Database. Looks up using name in the Database. It does a similarity search in the database.
     * @param questionName Name of the Indicator used for lookup.
     * @return A List of similarly named Indicators present in the Database.
     */
    @Override
    @Transactional
    public List<GLAQuestion> loadByQuestionName(String questionName, boolean exact) {
        Session session = factory.getCurrentSession();
        questionName = "%"+questionName+"%";
        Criteria criteria = session.createCriteria(GLAQuestion.class);
        criteria.setFetchMode("glaIndicators", FetchMode.JOIN);
        criteria.setFetchMode("glaQuestionProps", FetchMode.JOIN);
        if(exact)
            criteria.add(Restrictions.eq("question_name", questionName));
        else
            criteria.add(Restrictions.ilike("question_name", questionName));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return  criteria.list();

    }

    /**
     * Updates the Execution Statistics Counter of a Specific Question.
     * @param ID Question ID to be searched and updated.
     *
     */

    @Override
    @Transactional
    public void updateStatistics(long ID){
        Session session = factory.getCurrentSession();
        GLAQuestion glaQuestion = null;
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        Criteria criteria = session.createCriteria(GLAQuestion.class);
        criteria.setFetchMode("glaIndicators", FetchMode.JOIN);
        criteria.setFetchMode("glaQuestionProps", FetchMode.JOIN);
        criteria.add(Restrictions.eq("id", ID));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Object result = criteria.uniqueResult();
        if (result != null) {
            glaQuestion = (GLAQuestion) result;
        }
        glaQuestion.getGlaQuestionProps().setLast_executionTime(new java.sql.Timestamp(now.getTime()));
        glaQuestion.getGlaQuestionProps().setTotalExecutions(glaQuestion.getGlaQuestionProps().getTotalExecutions()+1);
        factory.getCurrentSession().saveOrUpdate(glaQuestion);

    }

    /**
     * Counts the total number of Questions present in the Database.
     * @return
     *      Total count of Questions
     */
    @Override
    @Transactional
    public int getTotalQuestions() {
        Session session = factory.getCurrentSession();
        return ((Number) session.createCriteria(GLAQuestion.class).setProjection(Projections.rowCount()).uniqueResult()).intValue();

    }

    @Override
    @Transactional
    public List<GLAQuestion> searchQuestionsName(String searchParameter, boolean exactSearch,
                                                   String colName, String sortDirection, boolean sort){
        if(!exactSearch)
            searchParameter = "%"+searchParameter+"%";
        Session session = factory.getCurrentSession();
        Criteria criteria = session.createCriteria(GLAQuestion.class);
        criteria.setFetchMode("glaIndicators", FetchMode.JOIN);
        criteria.setFetchMode("glaQuestionProps", FetchMode.JOIN);
        if(!exactSearch)
            criteria.add(Restrictions.ilike("question_name", searchParameter));
        else
            criteria.add(Restrictions.eq("question_name", searchParameter));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if(sort) {
            if(sortDirection.equals("asc"))
                criteria.addOrder(Order.asc(colName));
            else
                criteria.addOrder(Order.desc(colName));
        }
        return criteria.list();
    }
}
