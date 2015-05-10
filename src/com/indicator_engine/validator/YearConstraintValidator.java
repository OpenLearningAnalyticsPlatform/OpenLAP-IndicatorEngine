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

package com.indicator_engine.validator;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@SuppressWarnings({"unused", "unchecked"})
public class YearConstraintValidator implements ConstraintValidator<Year, Date> {

    private int annotationYear;

    @Override
    public void initialize(Year year) {
        this.annotationYear = year.value();
    }

    @Override
    public boolean isValid(Date target, ConstraintValidatorContext cxt) {
        if(target == null) {
            return true;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(target);
        int fieldYear = c.get(Calendar.YEAR);
        return fieldYear == annotationYear;
    }

}