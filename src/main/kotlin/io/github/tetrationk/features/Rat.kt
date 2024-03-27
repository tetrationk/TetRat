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
package io.github.tetrationk.features

import io.github.tetrationk.TetRatMod
import io.github.tetrationk.utils.*
import io.github.tetrationk.utils.EntityUtils.entityItemSkullTexture
import io.github.tetrationk.utils.EntityUtils.entityItemSkyblockId
import io.github.tetrationk.utils.EntityUtils.hasEntityItemSkullTexture
import io.github.tetrationk.utils.EntityUtils.hasEntityItemSkyblockId
import io.github.tetrationk.utils.PlayerUtils.onSkyblock
import io.github.tetrationk.utils.PlayerUtils.playerLocation
import io.github.tetrationk.utils.RenderUtils.drawWaypointFilled
import net.minecraft.entity.item.EntityItem
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import java.awt.Color

class Rat {
    private val config get() = TetRatMod.config.ratConfig
    private val cheeseDrops = mutableSetOf<EntityItem>()
    private val id = "CHEESE_FUEL"
    private val CHEESE_SKULL = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE1MzlkYmNkMzZmODc3MjYzMmU1NzM5ZTJlNTE0ODRlZGYzNzNjNTU4ZDZmYjJjNmI2MWI3MmI3Y2FhIn19fQ"

    private var tickCount = 0;
    private var lastCheeseLocation: Vector3d = Vector3d(0.0, 0.0, 0.0)
    private var lastBeaconLocation: Vector3d = Vector3d(0.0, 0.0, 0.0)
    private val cheeseHighlightEnabled get() = onSkyblock && config.cheeseHighlight

    @SubscribeEvent
    fun onTick(event: ClientTickEvent) {
        if (!cheeseHighlightEnabled) return

        EntityUtils.getEntities<EntityItem>().filter {
            it !in cheeseDrops && it.hasEntityItemSkullTexture(CHEESE_SKULL) && it.entityItemSkyblockId == null
        }.forEach {
            cheeseDrops.add(it)
        }

        /*tickCount ++
        if (tickCount % 100 == 0) {
//            ChatUtils.messageToChat(cheeseDrops.size.toString())
            ChatUtils.messageToChat("$lastCheeseLocation $lastBeaconLocation")
        }*/
    }

    @SubscribeEvent
    fun onRenderWorld(event: RenderWorldLastEvent) {
        if (!cheeseHighlightEnabled) return

        val color = Color(SpecialColour.specialToChromaRGB(config.cheeseHighlightColor), true)

        val playerLocation = playerLocation()
        val offset = Vector3d(-0.5, 0.0, -0.5)
        val cheeseIterator = cheeseDrops.iterator()
        for (cheeseDrop in cheeseIterator) {
            if (cheeseDrop.isDead) {
                cheeseIterator.remove()
                continue
            }
            val cheeseLocation = cheeseDrop.getVector3d()
            event.drawWaypointFilled(cheeseLocation + offset, color, extraSize = -0.25, beacon = true)
            lastCheeseLocation = cheeseLocation
            lastBeaconLocation = cheeseLocation + offset
        }
    }

    @SubscribeEvent
    fun onWorldChange(event: WorldEvent.Load) {
        cheeseDrops.clear()
    }

}