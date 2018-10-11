/*
 * FirstAid
 * Copyright (C) 2017-2018
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ichttt.mods.firstaid.common.apiimpl;

import ichttt.mods.firstaid.FirstAid;
import ichttt.mods.firstaid.api.item.HealingItemApiHelper;
import ichttt.mods.firstaid.api.item.ItemHealing;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class HealingItemApiHelperImpl extends HealingItemApiHelper {
    private static final HealingItemApiHelperImpl INSTANCE = new HealingItemApiHelperImpl();

    static void init() {
        HealingItemApiHelper.setImpl(INSTANCE);
    }

    static void verify() {
        HealingItemApiHelper registryImpl = HealingItemApiHelper.getImpl();
        if (registryImpl == null)
            throw new IllegalStateException("The apiimpl has not been set! Something went seriously wrong!");
        if (registryImpl != INSTANCE)
            throw new IllegalStateException("A mod has registered a custom apiimpl for the registry. THIS IS NOT ALLOWED!" +
                    "It should be " + INSTANCE.getClass().getName() + " but it actually is " + registryImpl.getClass().getName());
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemHealing itemHealing, World world, EntityPlayer player, EnumHand hand) {
        if (world.isRemote)
            FirstAid.proxy.showGuiApplyHealth(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Nonnull
    @Override
    public CreativeTabs getFirstAidTab() {
        return FirstAid.CREATIVE_TAB;
    }
}
