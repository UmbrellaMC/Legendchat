package br.com.devpaulo.legendchat.commands;

import br.com.devpaulo.legendchat.api.Legendchat;
import br.com.devpaulo.legendchat.channels.ChannelManager;
import br.com.devpaulo.legendchat.channels.types.Channel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChannelCommand implements CommandExecutor
{
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (sender==Bukkit.getConsoleSender())
    {
      return false;
    }
    if (args.length==0)
    {
      sender.sendMessage(Legendchat.getMessageManager().getMessage("wrongcmd").replace("@command", "/ch <" + Legendchat.getMessageManager().getMessage("channel") + ">"));
      String mlist = "";
      for (Channel c : Legendchat.getChannelManager().getChannels())
      {
        if (Legendchat.getPlayerManager().canPlayerSeeChannel((Player) sender, c))
        {
          if (mlist.length()==0)
          {
            mlist = c.getName();
          }
          else
          {
            mlist += ", " + c.getName();
          }
        }
      }
      sender.sendMessage(Legendchat.getMessageManager().getMessage("message21").replace("@channels", (mlist.length()==0 ? Legendchat.getMessageManager().getMessage("nothing"):mlist)));
    }
    else
    {
      Channel c = null;
      ChannelManager cm = Legendchat.getChannelManager();
      c = cm.getChannelByName(args[0].toLowerCase());
      if (c==null)
      {
        c = cm.getChannelByNickname(args[0].toLowerCase());
      }
      if (c==null)
      {
        sender.sendMessage(Legendchat.getMessageManager().getMessage("error4"));
        return true;
      }

      Legendchat.getPlayerManager().setPlayerFocusedChannel((Player) sender, c, true);
    }
    return true;
  }
}
