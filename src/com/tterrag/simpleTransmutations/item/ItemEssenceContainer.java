package com.tterrag.simpleTransmutations.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEssenceContainer extends Item
{
	Iterator<?> iter;

	public ItemEssenceContainer(int id)
	{
		super(id);
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName(ItemInfo.ESSENCE_CONTAINER_UNLOC_NAME);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		itemIcon = register.registerIcon(ItemInfo.TEXTURE_LOC + ":"
				+ ItemInfo.ESSENCE_CONTAINER_ICON);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return ItemInfo.ESSENCE_CONTAINER_UNLOC_NAME + stack.getItemDamage();
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
			Entity entity)
	{
		if (!(ModItem.hasEssenceNames))
		{
			ModItem.createEssenceNameList();
			for (String s : ModItem.essenceNames)
				System.out.println(s);
		}
		
			iter = EntityList.classToStringMapping.entrySet().iterator();
		int i = 0;
		EntityLivingBase entityLiving;

		if (!player.worldObj.isRemote && entity instanceof EntityLivingBase
				&& ((EntityLivingBase) entity).getHealth() < 3.0F)
		{
			entityLiving = (EntityLivingBase) entity;
			System.out.println(entityLiving.getEntityName());
			System.out.println(entityLiving.getHealth() - 1.0F);
			while (iter.hasNext())
			{
				Entry e = (Entry)iter.next();
                Class c = (Class)e.getKey();
                if(entity.getClass().isAssignableFrom(c))
                {
                	stack.setItemDamage(i);
					break;
				}
				i++;
			}
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack,
			EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		list.clear();
		list.add("Essence Container");
		int damage = 0;
		String name = "";
		for (String s : ModItem.essenceNames)
		{
			if (s.equals(ModItem.essenceNames.get(stack.getItemDamage())))
			{
				name = s;
			}
		}
		list.add("Contains: " + name);
	}
	/*{
		Iterator<?> iter = EntityList.classToStringMapping.entrySet().iterator();
		String s = "Error";
		Entry e;
		
		for (int i = 0; i < damage; i++)
		{
			if (iter.hasNext()) 
			{
				e = (Entry)iter.next();
				s = (String) e.getValue();
			}
			
		}
		LanguageRegistry.addName(stack.getItem(), s);
	}*/

}
