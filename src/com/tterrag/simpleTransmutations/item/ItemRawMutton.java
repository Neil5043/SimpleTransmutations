package com.tterrag.simpleTransmutations.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRawMutton extends ItemFood
{
	public ItemRawMutton(int id)
	{
		super(id, 2, 0.0F, true);
		setMaxStackSize(64);
		setUnlocalizedName(ItemInfo.RAW_MUTTON_UNLOC_NAME);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		itemIcon = register.registerIcon(ItemInfo.TEXTURE_LOC + ":"
				+ ItemInfo.RAW_MUTTON_ICON);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{

		int rand;

		if (!world.isRemote)
			if (!player.inventory.addItemStackToInventory(new ItemStack(
					Item.bone)))
				player.dropItem(Item.bone.itemID, 1);

		rand = (int) (Math.random() * 2);
		if (rand == 0 && !world.isRemote)
			player.addPotionEffect(new PotionEffect(17, 300, 6));

		rand = (int) (Math.random() * 2.3);
		if (rand == 0 && !world.isRemote)
			player.addPotionEffect(new PotionEffect(19, 200, 2));

		rand = (int) (Math.random() * 4);
		if (rand == 0 && !world.isRemote)
			player.addPotionEffect(new PotionEffect(9, 400, 0));

		if (player.getHealth() < 6.0F && !world.isRemote)
		{
			player.setHealth(0F);
			player.inventory.dropAllItems();
			player.setDead();
			
			for (EntityPlayer playerIter : (List<EntityPlayer>) player.worldObj.playerEntities)
			{
				playerIter.addChatMessage(player.username
						+ " wanted those bones a little too much");
			}
		}

		return super.onEaten(stack, world, player);
	}
}
