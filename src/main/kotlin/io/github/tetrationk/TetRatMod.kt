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
package io.github.tetrationk

import io.github.tetrationk.commands.CommandManager
import io.github.tetrationk.config.ConfigManager
import io.github.tetrationk.config.categories.ModConfig
import io.github.tetrationk.data.HypixelData
import io.github.tetrationk.data.ScoreboardData
import io.github.tetrationk.features.Chat
import io.github.tetrationk.features.Rat
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = TetRatMod.MOD_ID, useMetadata = true, guiFactory = "io.github.tetrationk.config.ConfigGuiForgeInterop")
class TetRatMod {

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        configManager = ConfigManager()
        MinecraftForge.EVENT_BUS.register(configManager)
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        CommandManager()

        MinecraftForge.EVENT_BUS.register(HypixelData())
        MinecraftForge.EVENT_BUS.register(ScoreboardData())
        MinecraftForge.EVENT_BUS.register(Chat())
        MinecraftForge.EVENT_BUS.register(Rat())
    }

    companion object {
        lateinit var configManager: ConfigManager
        const val MOD_ID = "tetrat"

        @JvmStatic
        val version: String
            get() = Loader.instance().indexedModList[MOD_ID]!!.version

        val config: ModConfig
            get() = configManager.config ?: error("config is null")
    }
}
