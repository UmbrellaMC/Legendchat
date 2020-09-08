package br.com.devpaulo.legendchat.commands;

import br.com.devpaulo.legendchat.api.Legendchat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TellCommand implements CommandExecutor
{
  private CommandSender console = Bukkit.getConsoleSender();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
  {
    if (sender.hasPermission("legendchat.block.tell") && !sender.hasPermission("legendchat.admin"))
    {
      sender.sendMessage(Legendchat.getMessageManager().getMessage("error6"));
      return true;
    }
    if (args.length==0)
    {
      if (Legendchat.getPrivateMessageManager().isPlayerTellLocked(sender))
      {
        Legendchat.getPrivateMessageManager().unlockPlayerTell(sender);
        sender.sendMessage(Legendchat.getMessageManager().getMessage("message11"));
      }
      else
      {
        sender.sendMessage(Legendchat.getMessageManager().getMessage("wrongcmd").replace("@command", "/tell <player> [" + Legendchat.getMessageManager().getMessage("message") + "]"));
      }
      return true;
    }
    CommandSender to = Bukkit.getPlayer(args[0]);
    if (to==null)
    {
      if (args[0].equalsIgnoreCase("console"))
      {
        to = console;
      }
      else
      {
        sender.sendMessage(Legendchat.getMessageManager().getMessage("error8"));
        return true;
      }
    }
    if (to==sender)
    {
      sender.sendMessage(Legendchat.getMessageManager().getMessage("error9"));
      return true;
    }
    if (args.length==1)
    {
      if (sender==console)
      {
        sender.sendMessage(Legendchat.getMessageManager().getMessage("wrongcmd").replace("@command", "/tell <player> [" + Legendchat.getMessageManager().getMessage("message") + "]"));
        return true;
      }
      if (Legendchat.getPrivateMessageManager().isPlayerTellLocked(sender) && Legendchat.getPrivateMessageManager().getPlayerLockedTellWith(sender)==to)
      {
        Legendchat.getPrivateMessageManager().unlockPlayerTell(sender);
        sender.sendMessage(Legendchat.getMessageManager().getMessage("message11"));
      }
      else
      {
        if (sender.hasPermission("legendchat.block.locktell") && !sender.hasPermission("legendchat.admin"))
        {
          sender.sendMessage(Legendchat.getMessageManager().getMessage("error6"));
          return true;
        }

        Legendchat.getPrivateMessageManager().lockPlayerTell(sender, to);
        sender.sendMessage(Legendchat.getMessageManager().getMessage("message10").replace("@player", to.getName()));
      }
    }
    else
    {
      StringBuilder msg = new StringBuilder();

      for (int i = 1; i < args.length; i++)
      {
        if (msg.length()==0)
        {
          msg = new StringBuilder(args[i]);
        }
        else
        {
          msg.append(" ").append(args[i]);
        }
      }

      Legendchat.getPrivateMessageManager().tellPlayer(sender, to, msg.toString().trim());
    }
    return true;
  }
}
