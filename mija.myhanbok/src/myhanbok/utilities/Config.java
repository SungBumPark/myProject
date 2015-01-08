package myhanbok.utilities;

import java.util.ArrayList;


import mija.myhanbok.R;
import myhanbok.model.TabMenu;





public class Config {
	
	static ArrayList<TabMenu> tabMenu = null;

	public static ArrayList<TabMenu> createTab() {
		tabMenu = new ArrayList<TabMenu>();
		
		
		tabMenu.add(new TabMenu(R.drawable.home_button));
		tabMenu.add(new TabMenu(R.drawable.intro_tab));
		tabMenu.add(new TabMenu(R.drawable.coordination_tab));
		tabMenu.add(new TabMenu(R.drawable.reservation_tab));
		tabMenu.add(new TabMenu(R.drawable.ask_tab));
		return tabMenu;
	}

}