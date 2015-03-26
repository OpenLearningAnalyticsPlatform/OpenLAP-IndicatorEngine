/*
 * Copyright (C) 2015  Tanmaya Mahapatra
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

package com.goalla.droolstest;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

import java.math.BigDecimal;

/**
 * Created by Tanmaya Mahapatra on 25-02-2015.
 */
public class Purchase {
    private BigDecimal total;
    private int tacoCount;
    private boolean drinkIncluded;
    private double discount;
    Purchase() {
        this.total = null;
        this.tacoCount = 0;
        this.drinkIncluded = false;
        this.discount = 0;
    }
    public Purchase(BigDecimal total, int tacoCount, boolean
            drinkIncluded) {
        this.total = total;
        this.tacoCount = tacoCount;
        this.drinkIncluded = drinkIncluded;
        this.discount = 0;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public int getTacoCount() {
        return tacoCount;
    }
    public void setTacoCount(int tacoCount) {
        this.tacoCount = tacoCount;
    }
    public boolean isDrinkIncluded() {
        return drinkIncluded;
    }
    public void setDrinkIncluded(boolean drinkIncluded) {
        this.drinkIncluded = drinkIncluded;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public void testRules() {
        StatefulKnowledgeSession session = null;
        try {
            KnowledgeBuilder builder = KnowledgeBuilderFactory.
                    newKnowledgeBuilder();
            builder.add(ResourceFactory.newClassPathResource("com/goalla/droolsrules/discountRules.drl"), ResourceType.DRL);
            if (builder.hasErrors()) {
                throw new RuntimeException(builder.getErrors().toString());
            }
            KnowledgeBase knowledgeBase = KnowledgeBaseFactory.
                    newKnowledgeBase();
            knowledgeBase.addKnowledgePackages(builder.
                    getKnowledgePackages());
            session = knowledgeBase.newStatefulKnowledgeSession();
// purchase > $15 and <= $25
            Purchase firstPurchase = new Purchase(new BigDecimal("27"), 1,
                    false);
            FactHandle purchaseFact = session.insert(firstPurchase);
            session.fireAllRules();
        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            if (session != null) {
                session.dispose();
            }
        }
    }
}