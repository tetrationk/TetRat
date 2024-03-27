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
package io.github.tetrationk.data

import io.github.tetrationk.utils.PlayerUtils
import io.github.tetrationk.utils.StringUtils.removeFormatting
import net.minecraft.client.Minecraft
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent

/**
 * Taken from Skyhanni under GNU Lesser General Public License v2.1
 * https://github.com/hannibal002/SkyHanni/blob/beta/LICENSE
 */
class HypixelData {
    companion object {
        var hypixelLive = false
        var hypixelAlpha = false
        var skyblock = false

        fun testScoreboard(): String {
            val minecraft = Minecraft.getMinecraft()
            val world = minecraft.theWorld ?: return "No world found"

            val objective = world.scoreboard.getObjectiveInDisplaySlot(1) ?: return "Objective not found"
            val displayName = objective.displayName
            val scoreboardTitle = displayName.removeFormatting()
            return scoreboardTitle
        }
    }

    private fun checkHypixel() {
        val list = ScoreboardData.sidebarLinesFormatted
        if (list.isEmpty()) return

        val last = list.last()
        hypixelLive = last == "§ewww.hypixel.net"
        hypixelAlpha = last == "§ealpha.hypixel.net"
    }

    @SubscribeEvent
    fun onDisconnect(event: FMLNetworkEvent.ClientDisconnectionFromServerEvent) {
        hypixelLive = false
        hypixelAlpha = false
        skyblock = false
    }

    @SubscribeEvent
    fun onWorldChange(event: WorldEvent.Load) {
        skyblock = false
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (!PlayerUtils.onHypixel){
            checkHypixel()
        }
        if (!PlayerUtils.onSkyblock) {
            skyblock = checkScoreboard()
        }
    }

    private fun checkScoreboard(): Boolean {
        val minecraft = Minecraft.getMinecraft()
        val world = minecraft.theWorld ?: return false

        val objective = world.scoreboard.getObjectiveInDisplaySlot(1) ?: return false
        val displayName = objective.displayName
        val scoreboardTitle = displayName.removeFormatting()
        return scoreboardTitle.contains("SKYBLOCK") ||
                scoreboardTitle.contains("SKIBLOCK") // April 1st jokes are so funny
    }


}