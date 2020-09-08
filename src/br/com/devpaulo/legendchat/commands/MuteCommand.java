package br.com.devpaulo.legendchat.commands;

import br.com.devpaulo.legendchat.api.Legendchat;
import br.com.devpaulo.legendchat.channels.types.Channel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor
{
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args)
  {
    if (sender==Bukkit.getConsoleSender())
    {
      return false;
    }
    if (args.length==0)
    {
      sender.sendMessage(Legendchat.getMessageManager().getMessage("wrongcmd").replace("@command", "/mute <" + Legendchat.getMessageManager().getMessage("channel") + ">"));
      if (Legendchat.getIgnoreManager().playerHasIgnoredChannelsList((Player) sender))
      {
        String mlist = "";
        for (Channel c : Legendchat.getIgnoreManager().getPlayerIgnoredChannelsList((Player) sender))
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
        sender.sendMessage(Legendchat.getMessageManager().getMessage("message20").replace("@channels", (mlist.length()==0 ? "...":mlist)));
      }
      return true;
    }
    Channel c = Legendchat.getChannelManager().getChannelByNameOrNickname(args[0]);
    if (c==null)
    {
      sender.sendMessage(Legendchat.getMessageManager().getMessage("error4"));
      return true;
    }
    if (Legendchat.getIgnoreManager().hasPlayerIgnoredChannel((Player) sender, c))
    {
      Legendchat.getIgnoreManager().playerUnignoreChannel((Player) sender, c);
      sender.sendMessage(Legendchat.getMessageManager().getMessage("message19").replace("@channel", c.getName()));
    }
    else
    {
      if (sender.hasPermission("legendchat.channel." + c.getName().toLowerCase() + ".blockmute") && !sender.hasPermission("legendchat.admin"))
      {
        sender.sendMessage(Legendchat.getMessageManager().getMessage("error13"));
        return true;
      }
      if (!c.getPlayersWhoCanSeeChannel().contains((Player) sender))
      {
        sender.sendMessage(Legendchat.getMessageManager().getMessage("error4"));
        return true;
      }
      Legendchat.getIgnoreManager().playerIgnoreChannel((Player) sender, c);
      sender.sendMessage(Legendchat.getMessageManager().getMessage("message18").replace("@channel", c.getName()));
    }

    return true;
  }
}
