package org.osrs.api.constants;

public class ChatMessageType {
	/**
	 * A normal game message.
	 */
	public static final int GAMEMESSAGE = 0;
	/**
 	* A message in the public chat from a moderator
 	*/
	public static final int MODCHAT = 1;
	/**
	 * A message in the public chat.
	 */
	public static final int PUBLICCHAT = 2;
	/**
	 * A private message from another player.
	 */
	public static final int PRIVATECHAT = 3;
	/**
	 * A message that the game engine sends.
	 */
	public static final int ENGINE = 4;
	/**
	 * A message received when a friend logs in or out.
	 */
	public static final int LOGINLOGOUTNOTIFICATION = 5;
	/**
	 * A private message sent to another player.
	 */
	public static final int PRIVATECHATOUT = 6;
	/**
	 * A private message received from a moderator.
	 */
	public static final int MODPRIVATECHAT = 7;
	/**
	 * A message received in friends chat.
	 */
	public static final int FRIENDSCHAT = 9;
	/**
	 * A message received with information about the current friends chat.
	 */
	public static final int FRIENDSCHATNOTIFICATION = 11;
	/**
	 * A trade request being sent.
	 */
	public static final int TRADE_SENT = 12;
	/**
	 * A game broadcast.
	 */
	public static final int BROADCAST = 14;
	/**
	 * An abuse report submitted.
	 */
	public static final int SNAPSHOTFEEDBACK = 26;
	/**
	 * Examine item description.
	 */
	public static final int ITEM_EXAMINE = 27;
	/**
	 * Examine NPC description.
	 */
	public static final int NPC_EXAMINE = 28;
	/**
	 * Examine object description.
	 */
	public static final int OBJECT_EXAMINE = 29;
	/**
	 * Adding player to friend list.
	 */
	public static final int FRIENDNOTIFICATION = 30;
	/**
	 * Adding player to ignore list.
	 */
	public static final int IGNORENOTIFICATION = 31;
	/**
	 * A chat message in a clan chat.
	 */
	public static final int CLAN_CHAT = 41;
	/**
	 * A system message in a clan chat.
	 */
	public static final int CLAN_MESSAGE = 43;
	/**
	 * A chat message in the guest clan chat.
	 */
	public static final int CLAN_GUEST_CHAT = 44;
	/**
	 * A system message in the guest clan chat.
	 */
	public static final int CLAN_GUEST_MESSAGE = 46;
	/**
	 * An autotyper message from a player.
	 */
	public static final int AUTOTYPER = 90;
	/**
	 * An autotyper message from a mod.
	 */
	public static final int MODAUTOTYPER = 91;
	/**
	 * A game message.  = ie. when a setting is changed
	 */
	public static final int CONSOLE = 99;
	/**
	 * A message received when somebody sends a trade offer.
	 */
	public static final int TRADEREQ = 101;
	/**
	 * A message received when completing a trade or a duel
	 */
	public static final int TRADE = 102;
	/**
	 * A message received when somebody sends a duel offer.
	 */
	public static final int CHALREQ_TRADE = 103;
	/**
	 * A message received when someone sends a friends chat challenge offer.
	 */
	public static final int CHALREQ_FRIENDSCHAT = 104;
	/**
	 * A message that was filtered.
	 */
	public static final int SPAM = 105;
	/**
	 * A message that is relating to the player.
	 */
	public static final int PLAYERRELATED = 106;
	/**
	 * A message that times out after 10 seconds.
	 */
	public static final int TENSECTIMEOUT = 107;
	/**
	 * The "Welcome to RuneScape" message
	 */
	public static final int WELCOME = 108;
	/**
	 * Clan creation invitation.
	 */
	public static final int CLAN_CREATION_INVITATION = 109;
	/**
	 * Clan wars challenge for clans rather than FCs
	 */
	public static final int CLAN_CLAN_WARS_CHALLENGE = 110;

	public static final int CLAN_GIM_FORM_GROUP = 111;
	public static final int CLAN_GIM_GROUP_WITH = 112;
	public static final int CLAN_GIM_CHAT = -1;
	public static final int CLAN_GIM_MESSAGE = -1;
}
