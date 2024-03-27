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
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.Constants

object ItemUtils {

    val ItemStack?.skyblockId: String?
        get() {
            if (this == null) return null
            return getSubCompound("ExtraAttributes", false)?.getString("id")
        }

    fun ItemStack?.hasSkyblockId(id: String?): Boolean {
        return skyblockId == id
    }

    val ItemStack?.skullTexture: String?
        get() {
            if (this == null) return null
            return getSubCompound("SkullOwner", false)?.getCompoundTag("Properties")
                ?.getTagList("textures", Constants.NBT.TAG_COMPOUND)?.getCompoundTagAt(0)?.getString("Value")
        }

    fun ItemStack?.hasSkullTexture(id: String): Boolean {
        return skullTexture == id
    }
}