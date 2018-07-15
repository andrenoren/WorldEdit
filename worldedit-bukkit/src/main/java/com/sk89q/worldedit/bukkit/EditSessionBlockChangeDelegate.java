/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.bukkit;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.block.data.BlockData;

/**
 * Proxy class to catch calls to set blocks.
 */
public class EditSessionBlockChangeDelegate implements BlockChangeDelegate {

    private EditSession editSession;

    public EditSessionBlockChangeDelegate(EditSession editSession) {
        this.editSession = editSession;
    }

    @Override
    public boolean setBlockData(int x, int y, int z, BlockData blockData) {
        try {
            editSession.setBlock(new Vector(x, y, z), BukkitUtil.toBlock(blockData));
        } catch (MaxChangedBlocksException e) {
            return false;
        }
        return true;
    }

    @Override
    public BlockData getBlockData(int x, int y, int z) {
        return BukkitUtil.toBlock(editSession.getBlock(new Vector(x, y, z)));
    }

    @Override
    public int getHeight() {
        return editSession.getWorld().getMaxY() + 1;
    }

    @Override
    public boolean isEmpty(int x, int y, int z) {
        return editSession.getBlock(new Vector(x, y, z)).getBlockType() == BlockTypes.AIR;
    }

}
