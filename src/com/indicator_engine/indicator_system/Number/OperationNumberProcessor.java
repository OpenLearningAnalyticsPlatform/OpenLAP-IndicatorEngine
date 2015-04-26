package com.indicator_engine.indicator_system.Number;

import com.indicator_engine.dao.GLAEventDao;
import com.indicator_engine.model.indicator_system.Number.SelectNumberParameters;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by Tanmaya Mahapatra on 01-04-2015.
 */
public class OperationNumberProcessor implements OperationNumberProcessorDao {
    @Autowired
    private ApplicationContext appContext;

    @Override
    public long computeResult(SelectNumberParameters selectNumberParameters){
        GLAEventDao glaEventBean = (GLAEventDao) appContext.getBean("glaEvent");
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
        long result = glaEventBean.findNumber(selectNumberParameters.getHql());
        //selectNumberParameters.setHql(selectNumberParameters.getHql() + selectNumberParameters.getEntityValues().get(2).geteValues());
        return result;

    }

}
