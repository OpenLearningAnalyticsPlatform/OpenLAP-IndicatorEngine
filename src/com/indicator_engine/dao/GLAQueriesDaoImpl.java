package com.indicator_engine.dao;

import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQueries;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

}
