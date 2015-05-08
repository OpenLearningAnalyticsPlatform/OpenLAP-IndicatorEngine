package com.indicator_engine.dao;

import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQueries;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Tanmaya Mahapatra on 08-05-2015.
 */
public class GLAIndicatorDaoImpl implements GLAIndicatorDao {

    static Logger log = Logger.getLogger(GLAIndicatorDaoImpl.class.getName());
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public void add(GLAIndicator glaIndicator, GLAQueries glaQueries) {
        log.info("Executing add() : GLAIndicatorDaoImpl : MODE : ADD OR UPDATE");
        factory.getCurrentSession().saveOrUpdate(glaIndicator);
        log.info("Indicator Saved..." + glaIndicator.getId());
        log.info("Saving associated Qeries \n");
        glaQueries.setGlaIndicator(glaIndicator);
        glaIndicator.getQueries().add(glaQueries);
        log.info("Finished Saving associated Qeries \n" );
        factory.getCurrentSession().save(glaQueries);
    }
}
