package network.starplum.special;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Build implements CommandExecutor, Listener {
	
	private ArrayList<UUID> buildMode = new ArrayList<UUID>();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		final CommandSender sender = arg0;
		final Player player = (Player) sender;
		
		final boolean isPlayer = sender instanceof Player;
		final boolean hasPermission = player.hasPermission("starplum.build");
		
		final String enabled = "§5§lStarplum §8➥ §fYour build mode has been §a§lenabled";
		final String disabled = "§5§lStarplum §8➥ §fYour build mode has been §d§ldisabled";
		final String noConsole = "§5§lStarplum §8➥ §fOnly players can use this command.";
		final String noPermission = "§5§lStarplum §8➥ §fYou don't have permission to use this comamnd";
		
		if(!isPlayer) {
			sender.sendMessage(noConsole);
			return true;
		} else {
			if(hasPermission) {
				if(buildMode.contains(player.getUniqueId())) {
					buildMode.remove(player.getUniqueId());
					player.sendMessage(disabled);
				} else {
					buildMode.add(player.getUniqueId());
					player.sendMessage(enabled);
				}
			} else player.sendMessage(noPermission);
		} return true;
	}
	
	@EventHandler
	public void placeblock(BlockPlaceEvent a) {
		final Player player = a.getPlayer();
		final String itsDisabled = "§5§lStarplum §8➥ §fYou currently have build mode disabled.";
		final boolean hasPermission = player.hasPermission("starplum.build");
		if(!buildMode.contains(player.getUniqueId())) {
			a.setCancelled(true);
			if(hasPermission) {
				player.sendMessage(itsDisabled);
			} else return;
		}
	}
	
	@EventHandler
	public void breakblock(BlockBreakEvent a) {
		final Player player = a.getPlayer();
		final String itsDisabled = "§5§lStarplum §8➥ §fYou currently have build mode disabled.";
		final boolean hasPermission = player.hasPermission("starplum.build");
		if(!buildMode.contains(player.getUniqueId())) {
			a.setCancelled(true);
			if(hasPermission) {
				player.sendMessage(itsDisabled);
			} else return;
		}
	}

}
