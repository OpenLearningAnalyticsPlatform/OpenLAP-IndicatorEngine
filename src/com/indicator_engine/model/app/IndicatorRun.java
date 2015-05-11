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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanmaya Mahapatra on 11-05-2015.
 */
public class IndicatorRun {

    private List<String> availableIndicators = new ArrayList<>();
    @NotNull
    private String selectedIndicator;

    public List<String> getAvailableIndicators() {
        return availableIndicators;
    }

    public void setAvailableIndicators(List<String> availableIndicators) {
        this.availableIndicators = availableIndicators;
    }

    public String getSelectedIndicator() {
        return selectedIndicator;
    }

    public void setSelectedIndicator(String selectedIndicator) {
        this.selectedIndicator = selectedIndicator;
    }
}
