package org.osrs.api.methods;

import org.osrs.api.objects.ChatboxChannel;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;

public class Chatbox extends MethodDefinition{
	private RSInterface chatboxParent;
	private RSWidget chatboxWindow;
	public ChatboxChannel allChannel;
	public ChatboxChannel gameChannel;
	public ChatboxChannel publicChannel;
	public ChatboxChannel privateChannel;
	public ChatboxChannel channelChannel;
	public ChatboxChannel clanChannel;
	public ChatboxChannel tradeChannel;
	public ChatboxChannel[] allChatChannels;
	public Chatbox(MethodContext context){
		super(context);
		allChannel = new ChatboxChannel(context);
		gameChannel = new ChatboxChannel(context);
		publicChannel = new ChatboxChannel(context);
		privateChannel = new ChatboxChannel(context);
		channelChannel = new ChatboxChannel(context);
		clanChannel = new ChatboxChannel(context);
		tradeChannel = new ChatboxChannel(context);
		allChatChannels = new ChatboxChannel[]{allChannel, gameChannel, publicChannel, privateChannel, channelChannel, clanChannel, tradeChannel};
	}
	public ChatboxChannel getSelectedChannel(){
		if(allChatChannels!=null){
			for(ChatboxChannel cc : allChatChannels){
				if(cc.isSelected())
					return cc;
			}
		}
		return null;
	}
	public void updateChatboxWidgets(RSInterface parent, RSWidget window){
		chatboxParent = parent;
		chatboxWindow = window;
		if(chatboxParent!=null){
			RSWidget[] chatboxWidgets = chatboxParent.getChildren();
			for(int i=0;i<chatboxWidgets.length;++i){
				RSWidget w = chatboxWidgets[i];
				RSWidget w2 = (i+1<chatboxWidgets.length)?chatboxWidgets[i+1]:null;
				if(w!=null){
					if(w2!=null && w.containsAction("Switch tab")){
						if(w.actionContains("Game:"))
							gameChannel.updateWidget(w,  w2);
						else if(w.actionContains("Public:"))
							publicChannel.updateWidget(w, w2);
						else if(w.actionContains("Private:"))
							privateChannel.updateWidget(w, w2);
						else if(w.actionContains("Channel:"))
							channelChannel.updateWidget(w, w2);
						else if(w.actionContains("Clan:"))
							clanChannel.updateWidget(w, w2);
						else if(w.actionContains("Trade:"))
							tradeChannel.updateWidget(w, w2);
						else if(w.actionContains("Set chat mode:"))
							allChannel.updateWidget(w, w2);
					}
				}
			}
		}
	}
}
