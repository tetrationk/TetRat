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
package io.github.tetrationk.config

import io.github.moulberry.moulconfig.gui.GuiScreenElementWrapper
import io.github.tetrationk.TetRatMod
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.client.IModGuiFactory
import net.minecraftforge.fml.client.IModGuiFactory.RuntimeOptionCategoryElement
import net.minecraftforge.fml.client.IModGuiFactory.RuntimeOptionGuiHandler
import org.lwjgl.input.Keyboard
import java.io.IOException

/**
 * Taken from Skyhanni under GNU Lesser General Public License v2.1
 * https://github.com/hannibal002/SkyHanni/blob/beta/LICENSE
 */
@Suppress("unused")
class ConfigGuiForgeInterop : IModGuiFactory {

    override fun initialize(minecraft: Minecraft) {}
    override fun mainConfigGuiClass() = WrappedConfig::class.java

    override fun runtimeGuiCategories(): Set<RuntimeOptionCategoryElement>? = null

    override fun getHandlerFor(element: RuntimeOptionCategoryElement): RuntimeOptionGuiHandler? = null

    class WrappedConfig(private val parent: GuiScreen) :
        GuiScreenElementWrapper(TetRatMod.configManager.editor) {

        @Throws(IOException::class)
        override fun handleKeyboardInput() {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                Minecraft.getMinecraft().displayGuiScreen(parent)
                return
            }
            super.handleKeyboardInput()
        }
    }
}
