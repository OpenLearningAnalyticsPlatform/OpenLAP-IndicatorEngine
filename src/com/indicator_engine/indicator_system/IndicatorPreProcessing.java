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

package com.indicator_engine.indicator_system;

import com.indicator_engine.dao.*;
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQuestion;
import com.indicator_engine.datamodel.GLAQuestionProps;
import com.indicator_engine.model.indicator_system.Number.*;
import com.sun.deploy.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 31-03-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class IndicatorPreProcessing implements IndicatorPreProcessingDao {
    static Logger log = Logger.getLogger(IndicatorPreProcessingDao.class.getName());
    @Autowired
    private ApplicationContext appContext;

    @Override
    public SelectNumberParameters initSelectNumberParametersObject() {
        SelectNumberParameters obj = new SelectNumberParameters();
        //obj.getEntityValues().add(new EntityValues());
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLAOperationsDao glaOperationsBean = (GLAOperationsDao) appContext.getBean("glaOperations");
        obj.setSource(glaEventBean.selectAll("source"));
        obj.setPlatform(glaEventBean.selectAll("platform"));
        obj.setAction(glaEventBean.selectAll("action"));
        obj.setOperations(glaOperationsBean.selectAllOperations());
        return obj;
    }

    @Override
    public List<String> initPopulateMinors(List<String> sources, List<String> actions, List<String> platforms) {
        log.info("initPopulateMinors : STARTED");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<String> minor = new ArrayList<>();
        //for(String source : sources) {
            //log.info("initPopulateMinors : Selected Source : \t" + source + "\n");
            List<Long> category_id= glaEventBean.findCategoryId(actions, sources, platforms);
            log.info("initPopulateMinors : Category ID : \t" + category_id + "\n");
            for( long catId : category_id) {
                for(String values : glacategoryBean.findCategoryByID(catId, "minor")) {
                    if(values != null)
                        minor.add(values);
                }
            }

        //}
        log.info("initPopulateMinors : Computed Minor : \t" + minor + "\n");
        log.info("initPopulateMinors : ENDED");
        return minor;
    }

    @Override
    public List<String> initPopulateMajors(List<String> sources, List<String> actions, List<String> platforms) {
        log.info("initPopulateMajors : STARTED");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<String> major = new ArrayList<>();
        //for(String source : sources) {
            //log.info("initPopulateMajors : Selected Source : \t" + source + "\n");
            List<Long> category_id  = glaEventBean.findCategoryId(actions, sources, platforms);
            log.info("initPopulateMajors : Category ID : \t" + category_id + "\n");
            for( long catId : category_id) {
                for(String values : glacategoryBean.findCategoryByID(catId, "major")){
                    if(values != null)
                        major.add(values);
                }
            }
        //}
        log.info("initPopulateMajors : Computed Major : \t" + major + "\n");
        log.info("initPopulateMajors : ENDED");
        return major;
    }

    @Override
    public List<String> initPopulateTypes(List<String> sources, List<String> actions, List<String> platforms) {
        log.info("initPopulateTypes : STARTED");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        List<String> types = new ArrayList<>();
        //for(String source : sources) {

            //log.info("initPopulateTypes : Selected Source : \t" + source + "\n");
            List<Long> category_id = glaEventBean.findCategoryId(actions, sources, platforms);
            log.info("initPopulateTypes : Category ID : \t" + category_id + "\n");
            for( long catId : category_id) {
                for(String values : glacategoryBean.findCategoryByID(catId, "type")){
                    log.info("initPopulateTypes : Values for Category ID : \t" + values + "\n");
                    if(values != null)
                        types.add(values);
                }
            }
        //}
        log.info("initPopulateTypes : Computed Types : \t" + types + "\n");
        log.info("initPopulateTypes : ENDED");
        return types;
    }

    @Override
    public List<String> initAvailableEntities_DB(List<Integer> minor) {
        return null;
    }

    @Override
    public List<String> initAvailableEvents_DB(List<Integer> minor) {
        return null;
    }

    //Tanamaya Code
    /*@Override
    public List<String> initAvailableEntities_DB(String minor) {
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        long category_id = glacategoryBean.findCategoryID(minor);
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        return glaEntityBean.loadEntitiesByCategoryID(category_id);
    }

    @Override
    public List<String> initAvailableEvents_DB(String minor) {
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
        long category_id = glacategoryBean.findCategoryID(minor);
        return glaEventBean.loadEventByCategoryID(category_id);
    }*/
}