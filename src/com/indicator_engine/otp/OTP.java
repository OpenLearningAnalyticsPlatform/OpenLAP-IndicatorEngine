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

package com.indicator_engine.otp;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Tanmaya Mahapatra on 25-02-2015.
 */
@SuppressWarnings({"unused", "unchecked"})
public class OTP {

    public static String generatePassword() {
        String chars = "abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

        final int PW_LENGTH = 20;
        Random rnd = new SecureRandom();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < PW_LENGTH; i++)
            pass.append(chars.charAt(rnd.nextInt(chars.length())));
        return pass.toString();
    }
}
