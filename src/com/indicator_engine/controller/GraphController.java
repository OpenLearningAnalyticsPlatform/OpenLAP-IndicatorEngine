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
import com.indicator_engine.datamodel.GLAIndicator;
import com.indicator_engine.datamodel.GLAQueries;
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
import java.util.List;
import java.util.Set;

/**
 * Created by Tanmaya Mahapatra on 11-05-2015.
 */
@Controller
@RequestMapping("/graphs")

public class GraphController {

    @Autowired
    private ApplicationContext appContext;

    @RequestMapping(value = "/jgraph", method = RequestMethod.GET)
    public void processGraphRequests(@RequestParam(value="type", defaultValue="bar") String gType,
                                     @RequestParam(value="indicator", required = true)String indicatorName,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {


        response.setContentType("image/png");
        GLAIndicatorDao glaIndicatorBean = (GLAIndicatorDao) appContext.getBean("glaIndicator");

        long indicator_id = glaIndicatorBean.findIndicatorID(indicatorName);
        GLAIndicator glaIndicator = glaIndicatorBean.loadByIndicatorID(indicator_id);

        if(gType.equals("Pie")) {
            PieDataset pdSet = createDataSet(glaIndicator);
            JFreeChart chart = createPieChart(pdSet, glaIndicator.getIndicator_name());
            try {
                ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
                        750, 400);
                response.getOutputStream().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if(gType.equals("Bar")) {
            CategoryDataset dataset = createCategoryDataset(glaIndicator);
            JFreeChart chart = createBarChart(dataset, glaIndicator.getIndicator_name());
            try {
                ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
                        750, 400);
                response.getOutputStream().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }


    }

    private PieDataset createDataSet(GLAIndicator glaIndicator) {
        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        DefaultPieDataset dpd = new DefaultPieDataset();
        Set<GLAQueries> queries = glaIndicator.getQueries();
        for(GLAQueries glaQuery : queries){
            dpd.setValue(glaQuery.getQuestion_name(), glaEntityBean.findNumber(glaQuery.getHql()));
        }
        return dpd;
    }

    private CategoryDataset createCategoryDataset(GLAIndicator glaIndicator) {

        GLAEntityDao glaEntityBean = (GLAEntityDao) appContext.getBean("glaEntity");
        long total = 0;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Set<GLAQueries> queries = glaIndicator.getQueries();
        for(GLAQueries glaQuery : queries){
            total += glaEntityBean.findNumber(glaQuery.getHql());
        }
        for(GLAQueries glaQuery : queries) {
            dataset.setValue((glaEntityBean.findNumber(glaQuery.getHql())*100)/total, glaIndicator.getIndicator_name(),
                    glaQuery.getQuestion_name());
        }
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
                "Questions",               // domain axis label
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
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }
}
