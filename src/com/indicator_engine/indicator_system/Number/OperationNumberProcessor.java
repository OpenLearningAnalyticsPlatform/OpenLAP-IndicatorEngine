package com.indicator_engine.indicator_system.Number;

import com.indicator_engine.dao.GLACategoryDao;
import com.indicator_engine.dao.GLAEntityDao;
import com.indicator_engine.model.SelectNumberParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 01-04-2015.
 */
public class OperationNumberProcessor implements OperationNumberProcessorDao {
    @Autowired
    private ApplicationContext appContext;

    @Override
    public int computeResult(SelectNumberParameters selectNumberParameters){
        GLACategoryDao glacategoryBean = (GLACategoryDao) appContext.getBean("glaCategory");
        long category_id = glacategoryBean.findCategoryID(selectNumberParameters.getSelectedMinor());
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        List<String> selectedEntities = glaEntityBean.loadEntitiesByCategoryIDName(category_id, selectNumberParameters.getSelectedKeys());
        /*StatefulKnowledgeSession session = null;
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
            session.insert(glacategoryBean);
            session.insert(glaEntityBean);
            session.fireAllRules();
        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            if (session != null) {
                session.dispose();
            }
        }*/
        return selectedEntities.size();

    }

}
