/* Copyright 2024 (C) Tet's Rat Mod Contributors
 *
 * This file is part of Tet's Rat Mod.
 *
 * Tet's Rat Mod is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * Tet's Rat Mod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tet's Rat Mod. If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.tetrationk.utils;

import java.awt.Color;

/**
 * Taken from NotEnoughUpdates
 * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
 * @author Moulberry
 */
public class SpecialColour {

    private static final int RADIX = 10;

    private static int[] decompose(String csv) {
        String[] split = csv.split(":");

        int[] arr = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            arr[i] = Integer.parseInt(split[split.length - 1 - i], RADIX);
        }
        return arr;
    }

    public static int getSpeed(String special) {
        return decompose(special)[4];
    }

    public static float getSecondsForSpeed(int speed) {
        return (255 - speed) / 254f * (MAX_CHROMA_SECS - MIN_CHROMA_SECS) + MIN_CHROMA_SECS;
    }

    private static final int MIN_CHROMA_SECS = 1;
    private static final int MAX_CHROMA_SECS = 60;

    public static long startTime = -1;

    public static int specialToChromaRGB(String special) {
        if (startTime < 0) startTime = System.currentTimeMillis();

        int[] d = decompose(special);
        int chr = d[4];
        int a = d[3];
        int r = d[2];
        int g = d[1];
        int b = d[0];

        float[] hsv = Color.RGBtoHSB(r, g, b, null);

        if (chr > 0) {
            float seconds = getSecondsForSpeed(chr);
            hsv[0] += (System.currentTimeMillis() - startTime) / 1000f / seconds;
            hsv[0] %= 1;
            if (hsv[0] < 0) hsv[0] += 1;
        }

        return (a & 0xFF) << 24 | (Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]) & 0x00FFFFFF);
    }

    public static int rotateHue(int argb, int degrees) {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = (argb) & 0xFF;

        float[] hsv = Color.RGBtoHSB(r, g, b, null);

        hsv[0] += degrees / 360f;
        hsv[0] %= 1;

        return (a & 0xFF) << 24 | (Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]) & 0x00FFFFFF);
    }
}
