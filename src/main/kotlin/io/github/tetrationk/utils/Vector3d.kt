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

import kotlin.math.abs
import kotlin.math.sqrt
import net.minecraft.entity.Entity

class Vector3d(
    val x: Double,
    val y: Double,
    val z: Double
) {
    fun l1(): Double {
        return abs(x) + abs(y) + abs(z)
    }
    fun l2sq(): Double {
        return x*x + y*y + z*z
    }

    fun l2(): Double {
        return sqrt(l2sq())
    }

    operator fun component1(): Double {
        return x
    }
    operator fun component2(): Double {
        return y
    }
    operator fun component3(): Double {
        return z
    }

    operator fun plus(other: Vector3d): Vector3d {
        return Vector3d(x + other.x, y + other.y, z + other.z)
    }

    operator fun minus(other: Vector3d): Vector3d {
        return Vector3d(x - other.x, y - other.y, z - other.z)
    }

    override fun toString(): String{
        return "$x $y $z"
    }
}

fun Entity.getVector3d(): Vector3d = Vector3d(posX, posY, posZ)