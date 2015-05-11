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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Tanmaya Mahapatra on 11-05-2015.
 */
@Controller
@RequestMapping("/graphs")
public class GraphController {
    @RequestMapping(value = "/piechart", method = RequestMethod.GET)
    public void drawPieChart(HttpServletRequest request,
                             HttpServletResponse response) {
        response.setContentType("image/png");
        PieDataset pdSet = createDataSet();

        JFreeChart chart = createChart(pdSet, "My Pie Chart");

        try {
            ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
                    750, 400);
            response.getOutputStream().close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private PieDataset createDataSet() {
        DefaultPieDataset dpd = new DefaultPieDataset();
        dpd.setValue("Mac", 21);
        dpd.setValue("Linux", 30);
        dpd.setValue("Window", 40);
        dpd.setValue("Others", 9);
        return dpd;
    }

    private JFreeChart createChart(PieDataset pdSet, String chartTitle) {

        JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, pdSet,
                true, true, false);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
}
