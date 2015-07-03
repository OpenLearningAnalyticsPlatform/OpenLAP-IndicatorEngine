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

package com.indicator_engine.controller;

import com.indicator_engine.dao.GLAEntityDao;
import com.indicator_engine.dao.GLAIndicatorDao;
import com.indicator_engine.dao.GLAQuestionDao;
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQuestion;
import com.indicator_engine.model.indicator_system.Number.EntitySpecification;
import com.indicator_engine.model.indicator_system.Number.GenQuery;
import com.indicator_engine.model.indicator_system.Number.Questions;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Tanmaya Mahapatra on 11-05-2015.
 */
@Controller
@RequestMapping("/graphs")

public class GraphController {

    @Autowired
    private ApplicationContext appContext;
    static Logger log = Logger.getLogger(GraphController.class.getName());

    @RequestMapping(value = "/jgraph", method = RequestMethod.GET)
    public void processGraphRequests(@RequestParam(value="indicator" ,required = false)String indicatorName,
                                     @RequestParam(value="bean", defaultValue="false", required = false)String runFromContextBean,
                                     @RequestParam(value="default", defaultValue="false", required = false)String defaultRun,
                                     HttpServletResponse response) {


        response.setContentType("image/png");
        if(defaultRun.equals("true")) {
            DefaultPieDataset dataSet = new DefaultPieDataset();
            dataSet.setValue("One", new Double(43.2));
            dataSet.setValue("Two", new Double(10.0));
            dataSet.setValue("Three", new Double(27.5));
            dataSet.setValue("Four", new Double(17.5));
            dataSet.setValue("Five", new Double(11.0));
            dataSet.setValue("Six", new Double(19.4));
            JFreeChart chart = createPieChart(dataSet, "Sample Graph");
            try {
                ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
                        750, 400);
                response.getOutputStream().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if(runFromContextBean.equals("true")) {
            EntitySpecification entitySpecificationBean = (EntitySpecification) appContext.getBean("entitySpecifications");
            if(entitySpecificationBean.getSelectedChartType().equals("Pie")) {
                PieDataset pdSet = createDataSet(entitySpecificationBean);
                JFreeChart chart = createPieChart(pdSet, entitySpecificationBean.getQuestionName());
                try {
                    ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
                            750, 400);
                    response.getOutputStream().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else if(entitySpecificationBean.getSelectedChartType().equals("Bar")) {
                CategoryDataset dataset = createCategoryDataSet(entitySpecificationBean);
                JFreeChart chart = createBarChart(dataset, entitySpecificationBean.getQuestionName());
                try {
                    ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
                            750, 400);
                    response.getOutputStream().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        else {

            GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
            long indicatorID = glaIndicatorBean.findIndicatorID(indicatorName);
            GLAIndicator glaIndicator = glaIndicatorBean.loadByIndicatorID(indicatorID);
            if(glaIndicator.getGlaIndicatorProps().getChartType().equals("Pie")) {
                PieDataset pdSet = createDataSet(glaIndicator);
                JFreeChart chart = createPieChart(pdSet, glaIndicator.getIndicator_name());
                try {
                    ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
                            750, 400);
                    glaIndicatorBean.updateStatistics(indicatorID);
                    //also update question stats
                    response.getOutputStream().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else if(glaIndicator.getGlaIndicatorProps().getChartType().equals("Bar")) {
                CategoryDataset dataset = createCategoryDataSet(glaIndicator);
                JFreeChart chart = createBarChart(dataset, glaIndicator.getIndicator_name());
                try {
                    ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
                            750, 400);
                    glaIndicatorBean.updateStatistics(indicatorID);
                    //also update question stats
                    response.getOutputStream().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    private PieDataset createDataSet(GLAIndicator glaIndicator) {
        DefaultPieDataset dpd = new DefaultPieDataset();
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");
        log.info("PIE CHART DATA : STARTED \n" + glaIndicator.getIndicator_name());
        dpd.setValue(glaIndicator.getIndicator_name(), glaEntityBean.findNumber(glaIndicator.getHql()));
        glaIndicatorBean.updateStatistics(glaIndicator.getId());
        return dpd;
    }

    private PieDataset createDataSet(EntitySpecification entitySpecification) {
        DefaultPieDataset dpd = new DefaultPieDataset();
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        log.info("PIE CHART DATA : STARTED \n" + entitySpecification.getHql());
        dpd.setValue(entitySpecification.getIndicatorName(), glaEntityBean.findNumber(entitySpecification.getHql()));
        /*if(entitySpecification.getQuestionsContainer().getGenQueries().size() !=0){
            for(GenQuery genQuery : entitySpecification.getQuestionsContainer().getGenQueries()) {
                dpd.setValue(genQuery.getIndicatorName(), glaEntityBean.findNumber(genQuery.getQuery()));
            }
        }*/
        return dpd;
    }

    private CategoryDataset createCategoryDataSet(GLAIndicator glaIndicator) {

        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        long total = 0;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        total += glaEntityBean.findNumber(glaIndicator.getHql());
        dataset.setValue((glaEntityBean.findNumber(glaIndicator.getHql())*100)/total, glaIndicator.getIndicator_name(),
                glaIndicator.getIndicator_name());
        // glaIndicatorBean.updateStatistics(glaIndicator.getId());
        return dataset;
    }

    private CategoryDataset createCategoryDataSet(EntitySpecification entitySpecification) {

        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        long total = 0;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        total += glaEntityBean.findNumber(entitySpecification.getHql());
        /*if(entitySpecification.getQuestionsContainer().getGenQueries().size() !=0){
            for(GenQuery genQuery : entitySpecification.getQuestionsContainer().getGenQueries()) {
                total += glaEntityBean.findNumber(genQuery.getQuery());
            }
        }*/
        if(total != 0)
            dataset.setValue((glaEntityBean.findNumber(entitySpecification.getHql()) *100)/total, entitySpecification.getIndicatorName(),
                    entitySpecification.getIndicatorName());
        else
            dataset.setValue((glaEntityBean.findNumber(entitySpecification.getHql()) *100), entitySpecification.getIndicatorName(),
                    entitySpecification.getIndicatorName());
        /*if(entitySpecification.getQuestionsContainer().getGenQueries().size() !=0){
            for(GenQuery genQuery : entitySpecification.getQuestionsContainer().getGenQueries()) {
                dataset.setValue((glaEntityBean.findNumber(genQuery.getQuery()) *100)/total, genQuery.getIndicatorName(),
                        genQuery.getIndicatorName());
            }
        }*/

        return dataset;
    }

    private JFreeChart createPieChart(final PieDataset pdSet, final String chartTitle) {

        JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, pdSet,
                true, true, false);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }

    private JFreeChart createBarChart(final CategoryDataset dataset,  final String chartTitle) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                chartTitle,         // chart title
                "Indicators",               // domain axis label
                "Percentage",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
        );

        // SOME OPTIONAL CUSTOMISATION OF THE CHART

        // set the background color for the chart
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, Color.blue,
                0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp1 = new GradientPaint(
                0.0f, 0.0f, Color.green,
                0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, Color.red,
                0.0f, 0.0f, Color.lightGray
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        return chart;

    }
}
