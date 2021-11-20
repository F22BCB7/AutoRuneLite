package org.osrs.api.methods;

import java.util.HashMap;

import org.osrs.api.objects.GroundItem;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.objects.Tab;

public class Tabs extends MethodDefinition{
	private final int RESIZE_MODE_TABS_PARENT = 164;
	private final int RESIZE_MODE_TAB_COMBAT_OPTIONS = 59;
	private final int RESIZE_MODE_TAB_SKILLS = 60;
	private final int RESIZE_MODE_TAB_QUEST_LIST = 61;
	private final int RESIZE_MODE_TAB_INVENTORY = 62;	
	private final int RESIZE_MODE_TAB_WORN_EQUIPMENT = 63;
	private final int RESIZE_MODE_TAB_PRAYER = 64;
	private final int RESIZE_MODE_TAB_MAGIC = 65;
	private final int RESIZE_MODE_TAB_CLAN_CHAT = 44;
	private final int RESIZE_MODE_TAB_ACCOUNT_MANAGEMENT = 45;
	private final int RESIZE_MODE_TAB_FRIENDS_LIST = 46;
	private final int RESIZE_MODE_TAB_OPTIONS = 47;
	private final int RESIZE_MODE_TAB_EMOTES = 48;
	private final int RESIZE_MODE_TAB_MUSIC_PLAYER = 49;

	private final int FIXED_MODE_TABS_PARENT = 548;
	private final int FIXED_MODE_TAB_COMBAT_OPTIONS = 49;
	private final int FIXED_MODE_TAB_SKILLS = 50;
	private final int FIXED_MODE_TAB_QUEST_LIST = 51;
	private final int FIXED_MODE_TAB_INVENTORY = 52;	
	private final int FIXED_MODE_TAB_WORN_EQUIPMENT = 53;
	private final int FIXED_MODE_TAB_PRAYER = 54;
	private final int FIXED_MODE_TAB_MAGIC = 55;
	private final int FIXED_MODE_TAB_CLAN_CHAT = 32;
	private final int FIXED_MODE_TAB_ACCOUNT_MANAGEMENT = 33;
	private final int FIXED_MODE_TAB_FRIENDS_LIST = 34;
	private final int FIXED_MODE_TAB_LOGOUT = 35;
	private final int FIXED_MODE_TAB_OPTIONS = 36;
	private final int FIXED_MODE_TAB_EMOTES = 37;
	private final int FIXED_MODE_TAB_MUSIC_PLAYER = 38;
	
	private Tab[] tabs = null;
	public Tab combat = null;
	public Tab skills = null;
	public Tab quests = null;
	public Tab inventory = null;
	public Tab equipment = null;
	public Tab prayer = null;
	public Tab magic = null;
	public Tab friendsList = null;
	public Tab account = null;
	public Tab clan = null;
	public Tab options = null;
	public Tab emotes = null;
	public Tab music = null;
	private int disabledID = -1;
	public Tabs(MethodContext context) {
		super(context);
		combat = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_COMBAT_OPTIONS, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_COMBAT_OPTIONS, "Combat", 0);
		skills = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_SKILLS, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_SKILLS, "Skills", 1);
		quests = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_QUEST_LIST, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_QUEST_LIST, "Quests", 2);
		inventory = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_INVENTORY, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_INVENTORY, "Inventory", 3);
		equipment = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_WORN_EQUIPMENT, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_WORN_EQUIPMENT, "Equipment", 4);
		prayer = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_PRAYER, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_PRAYER, "Prayer", 5);
		magic = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_MAGIC, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_MAGIC, "Magic", 6);
		friendsList = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_FRIENDS_LIST, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_FRIENDS_LIST, "Friends List", 7);
		account = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_ACCOUNT_MANAGEMENT, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_ACCOUNT_MANAGEMENT, "Account Management", 8);
		clan = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_CLAN_CHAT, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_CLAN_CHAT, "Clan Chat", 9);
		options = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_OPTIONS, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_OPTIONS, "Options", 10);
		emotes = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_EMOTES, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_EMOTES, "Emotes", 11);
		music = new Tab(RESIZE_MODE_TABS_PARENT, RESIZE_MODE_TAB_MUSIC_PLAYER, FIXED_MODE_TABS_PARENT, FIXED_MODE_TAB_MUSIC_PLAYER, "Music", 12);
		tabs = new Tab[]{combat, skills, quests, inventory, equipment, prayer, magic, friendsList, account, clan, options, emotes, music};
	}
	public Tab[] getAllTabs(){
		return tabs;
	}
	public Tab getSelectedTab(){
		for(Tab tab : tabs){
			RSWidget widget = tab.getWidget();
			if(widget!=null){
				if(widget.getInternal().spriteID()==1181)//find your selected tab
					return tab;//and return it
			}
		}
		return null;
	}
	public Tab getTab(String name){
		for(Tab tab : tabs){
			if(tab.name.toLowerCase().equals(name.toLowerCase()))
				return tab;
		}
		return null;
	}
	public Tab getTab(int index){
		if(index<0 || index>=tabs.length)
			return null;
		return tabs[index];
	}
}
