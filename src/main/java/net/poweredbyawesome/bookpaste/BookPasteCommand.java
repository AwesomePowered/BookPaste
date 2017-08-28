package net.poweredbyawesome.bookpaste;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

/**
 * Created by Lax on 8/23/2017.
 */
public class BookPasteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.hasPermission("book.paste")) {
            Player p = (Player) sender;
            if (p.getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK) {
                BookMeta meta = (BookMeta)p.getInventory().getItemInMainHand().getItemMeta();
                String s = "";
                s += "Title: " + meta.getTitle()+"\n";
                s += "Author: " + meta.getAuthor()+"\n";
                s += "Generation: " + meta.getGeneration()+"\n\n";
                int i = 1;
                for (String page : meta.getPages()) {
                    s += "Page: " + i+"\n"; i++;
                    s += ChatColor.stripColor(page);
                    s += "\n\n";
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aLink: &b" + BookPaste.instance.paste(s)));
            }
        }
        return false;
    }
}
