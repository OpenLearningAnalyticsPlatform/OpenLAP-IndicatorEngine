package com.indicator_engine.misc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext ctx = null;
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }
}
