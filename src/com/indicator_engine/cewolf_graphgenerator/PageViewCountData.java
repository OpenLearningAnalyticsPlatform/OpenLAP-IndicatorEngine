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

package com.indicator_engine.cewolf_graphgenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;

/**
 * Created by Tanmaya Mahapatra on 11-05-2015.
 */
public class PageViewCountData implements DatasetProducer, CategoryToolTipGenerator, CategoryItemLinkGenerator, Serializable {

    private static final Log log = LogFactory.getLog(PageViewCountData.class);

    // These values would normally not be hard coded but produced by
    // some kind of data source like a database or a file
    private final String[] categories =    {"mon", "tue", "wen", "thu", "fri", "sat", "sun"};
    private final String[] seriesNames =    {"cewolfset.jsp", "tutorial.jsp", "testpage.jsp", "performancetest.jsp"};

    /**
     *  Produces some random data.
     */
    public Object produceDataset(Map params) throws DatasetProduceException {
        log.debug("producing data.");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(){
            /**
             * @see java.lang.Object#finalize()
             */
            protected void finalize() throws Throwable {
                super.finalize();
                log.debug(this +" finalized.");
            }
        };
        for (int series = 0; series < seriesNames.length; series ++) {
            int lastY = (int)(Math.random() * 1000 + 1000);
            for (int i = 0; i < categories.length; i++) {
                final int y = lastY + (int)(Math.random() * 200 - 100);
                lastY = y;
                dataset.addValue(y, seriesNames[series], categories[i]);
            }
        }
        return dataset;
    }

    /**
     * This producer's data is invalidated after 5 seconds. By this method the
     * producer can influence Cewolf's caching behaviour the way it wants to.
     */
    public boolean hasExpired(Map params, Date since) {
        log.debug(getClass().getName() + "hasExpired()");
        return (System.currentTimeMillis() - since.getTime())  > 5000;
    }

    /**
     * Returns a unique ID for this DatasetProducer
     */
    public String getProducerId() {
        return "PageViewCountData DatasetProducer";
    }

    /**
     * Returns a link target for a special data item.
     */
    public String generateLink(Object data, int series, Object category) {
        return seriesNames[series];
    }

    /**
     * @see java.lang.Object#finalize()
     */
    protected void finalize() throws Throwable {
        super.finalize();
        log.debug(this + " finalized.");
    }

    /*
     * @see org.jfree.chart.tooltips.CategoryToolTipGenerator#generateToolTip(CategoryDataset, int, int)
     */
    public String generateToolTip(CategoryDataset arg0, int series, int arg2) {
        return seriesNames[series];
    }

}