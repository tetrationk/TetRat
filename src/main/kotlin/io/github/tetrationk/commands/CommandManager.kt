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
package io.github.tetrationk.commands

import io.github.tetrationk.TetRatMod
import io.github.tetrationk.commands.SimpleCommand.ProcessCommandRunnable
import io.github.tetrationk.data.HypixelData
import io.github.tetrationk.utils.ChatUtils
import io.github.tetrationk.utils.ItemUtils.skyblockId
import io.github.tetrationk.utils.PlayerUtils
import io.github.tetrationk.utils.PlayerUtils.onSkyblock
import net.minecraft.client.Minecraft
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraftforge.client.ClientCommandHandler

class CommandManager {

    init {
        /*
        registerCommand("testcommand") {
            ChatUtils.messageToChat("Test swap successful. Swapped!")
        }
        */
        registerCommand("rat") {
            TetRatMod.configManager.openConfigGui()
        }
        registerCommand("tetrat") {
            TetRatMod.configManager.openConfigGui()
        }
        /*
        registerCommand("onsb") {
            ChatUtils.messageToChat("live: ${HypixelData.hypixelLive}, alpha: ${HypixelData.hypixelAlpha}, onHypixel: ${PlayerUtils.onHypixel}, skyblock ${HypixelData.skyblock}, onSkyblock: $onSkyblock")
        }
        registerCommand("getscoreboard") {
            ChatUtils.messageToChat(HypixelData.testScoreboard())
        }
        registerCommand("getid") {
            val inventoryPlayer: InventoryPlayer = Minecraft.getMinecraft().thePlayer.inventory
            val itemStack: ItemStack? = inventoryPlayer.mainInventory[inventoryPlayer.currentItem]
            if (itemStack == null) {
                ChatUtils.messageToChat("No item in hand!")
                return@registerCommand
            }
            val itemId: String? = itemStack.skyblockId
            if (itemId != null) {
                ChatUtils.messageToChat(itemId)
            }
            else {
                ChatUtils.messageToChat("Error: No ID found")
            }
            ChatUtils.messageToChat("Hewwo")
        }
        registerCommand("formatmessage") { args ->
            val colorName = args.firstOrNull()
            if (colorName == null) {
                ChatUtils.messageToChat("Error: Invalid usage: /formatmessage <color> <message>")
            }
            val colorCode = when (colorName) {
                "red" -> "§c"
                "blue" -> "§9"
                "green" -> "§a"
                "yellow" -> "§e"
                "pink" -> "§d"
                else -> {
                    ChatUtils.messageToChat("Error: Invalid color '$colorName'!")
                    return@registerCommand
                }
            }

            val rest = args.drop(1)
            if (rest.isEmpty()) {
                ChatUtils.messageToChat("Error: Message can not be empty!")
                return@registerCommand
            }

            ChatUtils.messageToChat(colorCode + rest.joinToString(" "))
        }
        */
    }

    private fun registerCommand(name: String, function: (Array<String>) -> Unit) {
        ClientCommandHandler.instance.registerCommand(SimpleCommand(name, createCommand(function)))
    }

    private fun registerCommand0(
        name: String,
        function: (Array<String>) -> Unit,
        autoComplete: ((Array<String>) -> List<String>) = { listOf() }
    ) {
        val command = SimpleCommand(
            name,
            createCommand(function),
            object : SimpleCommand.TabCompleteRunnable {
                override fun tabComplete(sender: ICommandSender?, args: Array<String>?, pos: BlockPos?): List<String> {
                    return autoComplete(args ?: emptyArray())
                }
            }
        )
        ClientCommandHandler.instance.registerCommand(command)
    }

    private fun createCommand(function: (Array<String>) -> Unit) = object : ProcessCommandRunnable() {
        override fun processCommand(sender: ICommandSender?, args: Array<String>?) {
            if (args != null) function(args.asList().toTypedArray())
        }
    }
}