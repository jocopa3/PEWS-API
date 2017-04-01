/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

import mcpews.command.TimeSetCommand.TimeOfDay;
import mcpews.mcenum.CommandTarget;
import mcpews.mcenum.EnchantType;
import mcpews.message.MCCommand;

/**
 *
 * @author Jocopa3
 */
public class TitleCommand extends MCCommand {
    
    public static class EnchantByIdInput extends CommandInput {
        
        CommandTarget player;
        int enchantmentId;
        int level = 1; // Default level

        public EnchantByIdInput(CommandTarget player, Integer id) {
            this(player, id, 1);
        }
        
        public EnchantByIdInput(CommandTarget player, Integer id, Integer level) {
            overloadName = "byId";
            this.player = player;
            enchantmentId = id;
            this.level = level;
        }
    }

    public static class EnchantByNameInput extends CommandInput {

        CommandTarget player;
        String enchantmentName;
        int level;

        public EnchantByNameInput(CommandTarget player, String name) {
            this(player, name, 1);
        }
        
        public EnchantByNameInput(CommandTarget player, String name, Integer level) {
            overloadName = "byName";
            this.player = player;
            enchantmentName = name;
            this.level = level;
        }
    }
    
    public TitleCommand(EnchantByNameInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.ENCHANT.getName());
        setVersion(1);
    }
    
    public TitleCommand(EnchantByIdInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.ENCHANT.getName());
        setVersion(1);
    }
}
