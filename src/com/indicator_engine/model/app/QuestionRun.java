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

package com.indicator_engine.model.app;

import javax.validation.constraints.NotNull;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 11-05-2015.
 */
public class QuestionRun {

    private List<String> availableQuestions = new ArrayList<>();
    private List<String> chartTypes = new ArrayList<>();
    private List<String> chartEngines = new ArrayList<>();
    @NotNull
    private String selectedChartType;
    @NotNull
    private String selectedQuestion;
    @NotNull
    private String selectedChartEngine;

    public QuestionRun(){
        chartTypes.add("Bar");
        chartTypes.add("Pie");
        chartEngines.add("JFreeGraph");
        chartEngines.add("CEWOLF");
    }


    public List<String> getChartEngines() {
        return chartEngines;
    }

    public void setChartEngines(List<String> chartEngines) {
        this.chartEngines = chartEngines;
    }

    public String getSelectedChartEngine() {
        return selectedChartEngine;
    }

    public void setSelectedChartEngine(String selectedChartEngine) {
        this.selectedChartEngine = selectedChartEngine;
    }

    public List<String> getChartTypes() {
        return chartTypes;
    }

    public void setChartTypes(List<String> chartTypes) {
        this.chartTypes = chartTypes;
    }

    public String getSelectedChartType() {
        return selectedChartType;
    }

    public void setSelectedChartType(String selectedChartType) {
        this.selectedChartType = selectedChartType;
    }

    public List<String> getAvailableQuestions() {
        return availableQuestions;
    }

    public void setAvailableQuestions(List<String> availableQuestions) {
        this.availableQuestions = availableQuestions;
    }

    public String getSelectedQuestion() {
        return selectedQuestion;
    }

    public void setSelectedQuestion(String selectedQuestion) {
        this.selectedQuestion = selectedQuestion;
    }
}
