package com.ayutaki.chinjufumod.init.wallpane;

import com.ayutaki.chinjufumod.init.ChinjufuModTabs;

public class BlockRock_DIO_SlabHalf extends BlockRock_DIO_Slab {

	public BlockRock_DIO_SlabHalf(String unlocalizedName) {
		super(unlocalizedName);
		this.setCreativeTab(ChinjufuModTabs.tab_wallpanel);
	}

	@Override
	public boolean isDouble() {
		return false;
	}

}