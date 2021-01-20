package xyz.haoteng.mcribbl.helpers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {
    private String[] errors = {
            "Invalid command. ",
            "Invalid usage. ",
            "Invalid arguments. ",
            "Why don't you read the manuals? "
    };

    private String commandTemplate;

    public void setCommandTemplate(String commandTemplate){
        this.commandTemplate = commandTemplate;
    }

    public void displayError(CommandSender sender, int type){
        sender.sendMessage(ChatColor.RED + errors[type] + ChatColor.GOLD + commandTemplate);
    }

}
