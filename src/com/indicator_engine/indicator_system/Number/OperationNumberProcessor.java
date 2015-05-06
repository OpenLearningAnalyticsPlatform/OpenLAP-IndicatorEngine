package com.indicator_engine.indicator_system.Number;

import com.indicator_engine.dao.GLAEntityDao;
import com.indicator_engine.dao.GLAEventDao;
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
    public long computeResult(SelectNumberParameters selectNumberParameters){
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
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
            session.insert(selectNumberParameters);
            session.insert(new ProcessUserFilters());
            session.fireAllRules();
        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            if (session != null) {
                session.dispose();
            }
        }
        log.info("Dumping HQL" + selectNumberParameters.getHql());
        long result = glaEntityBean.findNumber(selectNumberParameters.getHql());
        log.info("Dumping Result \n" + result);
        log.info("Result Finished ");
        return result;

    }

}
