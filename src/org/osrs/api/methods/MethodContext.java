package org.osrs.api.methods;

public class MethodContext {
	public Object botInstance=null;

	public Bank bank;
	public Calculations calculations;
	public Camera camera;
	public Chatbox chatbox;
	public Equipment equipment;
	public Game game;
	public GrandExchange grandExchange;
	public GroundItems groundItems;
	public Inventory inventory;
	public Keyboard keyboard;
	public Magic magic;
	public Menu menu;
	public Minimap minimap;
	public Mouse mouse;
	public MouseWheel mouseWheel;
	public Nodes nodes;
	public NPCs npcs;
	public Objects objects;
	public Players players;
	public Region region;
	public Skills skills;
	public Tabs tabs;
	public Varps varps;
	public Walking walking;
	public Widgets widgets;
	
	public MethodContext(Object client){
		botInstance=client;

		bank = new Bank(this);
		calculations = new Calculations(this);
		camera = new Camera(this);
		chatbox = new Chatbox(this);
		equipment = new Equipment(this);
		game = new Game(this);
		grandExchange = new GrandExchange(this);
		groundItems = new GroundItems(this);
		inventory = new Inventory(this);
		keyboard = new Keyboard(this);
		magic = new Magic(this);
		menu = new Menu(this);
		minimap = new Minimap(this);
		mouse = new Mouse(this);
		mouseWheel = new MouseWheel(this);
		nodes = new Nodes(this);
		npcs = new NPCs(this);
		objects = new Objects(this);
		players = new Players(this);
		region = new Region(this);
		skills = new Skills(this);
		tabs = new Tabs(this);
		varps = new Varps(this);
		walking = new Walking(this);
		widgets = new Widgets(this);
	}
	/**
	 * Current instance script will 
	 * have its thread sleep for given millisecond 
	 * timeout.
	 * @param timeout
	 */
	public void sleep(int timeout){
		try{
			Thread.sleep(timeout);
		}
		catch(Exception e){
		}
	}
}