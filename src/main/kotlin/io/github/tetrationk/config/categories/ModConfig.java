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
package io.github.tetrationk.config.categories;

import io.github.tetrationk.TetRatMod;
import com.google.gson.annotations.Expose;
import io.github.moulberry.moulconfig.Config;
import io.github.moulberry.moulconfig.annotations.Category;

public class ModConfig extends Config {

    @Override
    public String getTitle() {
        return "Tet's Rat Mod " + TetRatMod.getVersion() + " by §2Tetration§r, config by §5Moulberry §rand §5nea89";
    }

    @Override
    public void saveNow() {
        TetRatMod.configManager.save();
    }


    @Expose
    @Category(name = "Rat Pet", desc = "")
    public RatConfig ratConfig = new RatConfig();
}
