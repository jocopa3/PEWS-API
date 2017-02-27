/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

import mcpews.mcenum.EnchantType;
import mcpews.message.MCCommand;

/**
 *
 * @author Jocopa3
 */
public class EnchantCommand extends MCCommand {

    public enum TimeOfDay {

        night,
        day;
    }

    public class EnchantByIdInput extends CommandInput {

        int time;

        public EnchantByIdInput(int time) {
            overloadName = "byId";
            this.time = time;
        }
    }

    public class EnchantByNameInput extends CommandInput {

        String time;

        public EnchantByNameInput(TimeOfDay time) {
            overloadName = "byName";
            this.time = time.name();
        }

        public EnchantByNameInput(String time) {
            overloadName = "byName";
            this.time = time;
        }
    }

    public EnchantCommand(EnchantType enchant) {
        super();

        setInput(new EnchantByNameInput(enchant.getName()));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.LISTD.getName());
        setVersion(1);
    }
}
