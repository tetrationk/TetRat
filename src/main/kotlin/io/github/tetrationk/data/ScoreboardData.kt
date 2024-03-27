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

import io.github.tetrationk.utils.StringUtils.removeFormatting
import net.minecraft.client.Minecraft
import net.minecraft.scoreboard.Score
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

/**
 * Taken from Skyhanni under GNU Lesser General Public License v2.1
 * https://github.com/hannibal002/SkyHanni/blob/beta/LICENSE
 */

class ScoreboardData {

    companion object {

        // TODO USE SH-REPO
        private val splitIcons = listOf(
            "\uD83C\uDF6B",
            "\uD83D\uDCA3",
            "\uD83D\uDC7D",
            "\uD83D\uDD2E",
            "\uD83D\uDC0D",
            "\uD83D\uDC7E",
            "\uD83C\uDF20",
            "\uD83C\uDF6D",
            "⚽",
            "\uD83C\uDFC0",
            "\uD83D\uDC79",
            "\uD83C\uDF81",
            "\uD83C\uDF89",
            "\uD83C\uDF82",
            "\uD83D\uDD2B",
        )

        fun formatLines(rawList: List<String>): List<String> {
            val list = mutableListOf<String>()
            for (line in rawList) {
                val separator = splitIcons.find { line.contains(it) } ?: continue
                val split = line.split(separator)
                val start = split[0]
                var end = split[1]
                // get last color code in start
                val lastColorIndex = start.lastIndexOf('§')
                val lastColor = when {
                    lastColorIndex != -1 && lastColorIndex + 1 < start.length && (start[lastColorIndex + 1] in '0'..'9' || start[lastColorIndex + 1] in 'a'..'f') -> start.substring(
                        lastColorIndex,
                        lastColorIndex + 2
                    )
                    else -> ""
                }

                // remove first color code from end, when it is the same as the last color code in start
                end = end.removePrefix(lastColor)

                list.add(start + end)
            }

            return list
        }

        var sidebarLinesFormatted: List<String> = emptyList()

        var sidebarLines: List<String> = emptyList() // TODO rename to raw
        var sidebarLinesRaw: List<String> = emptyList() // TODO delete
        var objectiveTitle = ""
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun onTick(event: TickEvent.ClientTickEvent) {

        val list = fetchScoreboardLines().reversed()
        val semiFormatted = list.map { cleanSB(it) }
        if (semiFormatted != sidebarLines) {
//            ScoreboardRawChangeEvent(sidebarLines, semiFormatted).postAndCatch()
            sidebarLines = semiFormatted
        }

        sidebarLinesRaw = list
        val new = formatLines(list)
        if (new != sidebarLinesFormatted) {
//            ScoreboardChangeEvent(sidebarLinesFormatted, new).postAndCatch()
            sidebarLinesFormatted = new
        }
    }

    private fun cleanSB(scoreboard: String): String {
        return scoreboard.toCharArray().filter { it.code in 21..126 || it.code == 167 }.joinToString(separator = "")
    }

    private fun fetchScoreboardLines(): List<String> {
        val scoreboard = Minecraft.getMinecraft().theWorld?.scoreboard ?: return emptyList()
        val objective = scoreboard.getObjectiveInDisplaySlot(1) ?: return emptyList()
        objectiveTitle = objective.displayName
        var scores = scoreboard.getSortedScores(objective)
        val list = scores.filter { input: Score? ->
            input != null && input.playerName != null && !input.playerName.startsWith("#")
        }
        scores = if (list.size > 15) {
            list.drop(15)
        } else {
            list
        }
        return scores.map {
            ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(it.playerName), it.playerName)
        }
    }
}
