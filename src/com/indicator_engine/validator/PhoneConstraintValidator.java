/*
 * Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Tanmaya Mahapatra on 28-04-2015.
 */
public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone String) { }

    @Override
    public boolean isValid(String phoneField, ConstraintValidatorContext cxt) {
        if(phoneField == null) {
            return false;
        }
        return phoneField.matches("[0-9()-]*");
    }
}


