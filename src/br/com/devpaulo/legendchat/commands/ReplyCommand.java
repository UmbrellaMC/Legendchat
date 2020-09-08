package br.com.devpaulo.legendchat.commands;

import br.com.devpaulo.legendchat.api.Legendchat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReplyCommand implements CommandExecutor
{
  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
  {
    if (sender.hasPermission("legendchat.block.reply") && !sender.hasPermission("legendchat.admin"))
    {
      sender.sendMessage(Legendchat.getMessageManager().getMessage("error6"));
      return true;
    }
    if (args.length==0)
    {
      sender.sendMessage(Legendchat.getMessageManager().getMessage("wrongcmd").replace("@command", "/r <" + Legendchat.getMessageManager().getMessage("message") + ">"));
      return true;
    }
    if (!Legendchat.getPrivateMessageManager().playerHasReply(sender))
    {
      sender.sendMessage(Legendchat.getMessageManager().getMessage("pm_error1"));
      return true;
    }

    CommandSender sendto = Legendchat.getPrivateMessageManager().getPlayerReply(sender);
    String msg = "";

    for (int i = 0; i < args.length; i++)
    {
      if (msg.length()==0)
      {
        msg = args[i];
      }
      else
      {
        msg += " " + args[i];
      }
    }
    Legendchat.getPrivateMessageManager().replyPlayer(sender, msg);
    return true;
  }
}
