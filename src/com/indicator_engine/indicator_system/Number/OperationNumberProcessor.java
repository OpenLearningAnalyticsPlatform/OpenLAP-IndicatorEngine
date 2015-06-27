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

package com.indicator_engine.indicator_system.Number;

import com.indicator_engine.dao.GLAEntityDao;
import com.indicator_engine.dao.GLAEventDao;
import com.indicator_engine.model.indicator_system.Number.EntitySpecification;
import com.indicator_engine.model.indicator_system.Number.SelectNumberParameters;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 01-04-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class OperationNumberProcessor implements OperationNumberProcessorDao {
    static Logger log = Logger.getLogger(OperationNumberProcessor.class.getName());
    @Autowired
    private ApplicationContext appContext;

    @Override
    public void computeResult(EntitySpecification entitySpecification){

        StatefulKnowledgeSession session = null;
        try {
            KnowledgeBuilder builder = KnowledgeBuilderFactory.
                    newKnowledgeBuilder();
            builder.add(ResourceFactory.newClassPathResource("com/indicator_engine/indicator_system/Number/OperationNumberRules.drl"), ResourceType.DRL);
            if (builder.hasErrors()) {
                throw new RuntimeException(builder.getErrors().toString());
            }
            KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
            knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
            session = knowledgeBase.newStatefulKnowledgeSession();
            session.insert(entitySpecification);
            session.insert(new ProcessUserFilters());
            session.fireAllRules();
        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            if (session != null) {
                session.dispose();
            }
        }
        log.info("Dumping HQL" + entitySpecification.getHql());

        log.info("Result Finished ");

    }

}
