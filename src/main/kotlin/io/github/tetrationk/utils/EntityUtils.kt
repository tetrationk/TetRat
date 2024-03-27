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
package io.github.tetrationk.utils

import io.github.tetrationk.utils.ItemUtils.skullTexture
import io.github.tetrationk.utils.ItemUtils.skyblockId
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.nbt.NBTTagCompound

object EntityUtils {
    fun getAllEntities(): Sequence<Entity> = Minecraft.getMinecraft()?.theWorld?.loadedEntityList?.let {
        if (Minecraft.getMinecraft().isCallingFromMinecraftThread) it else it.toMutableList()
    }?.asSequence()?.filterNotNull() ?: emptySequence()

    inline fun <reified R : Entity> getEntities(): Sequence<R> = getAllEntities().filterIsInstance<R>()

    val EntityItem.entityItemSkyblockId: String?
        get() {
            return entityItem.skyblockId
        }

    fun EntityItem.hasEntityItemSkyblockId(id: String): Boolean {
        return entityItemSkyblockId == id
    }

    val EntityItem.entityItemSkullTexture: String?
        get() {
            return entityItem.skullTexture
        }

    fun EntityItem.hasEntityItemSkullTexture(id: String?): Boolean {
        return entityItemSkullTexture == id
    }
}