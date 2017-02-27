package mcpews.listeners;

import mcpews.MCClient;
import mcpews.MCListener;
import mcpews.command.SayCommand;
import mcpews.command.SummonCommand;
import mcpews.command.TimeSetCommand;
import mcpews.command.TimeSetCommand.TimeOfDay;
import mcpews.event.EventType;
import mcpews.event.PlayerMessageEvent;
import mcpews.mcenum.EntityType;
import mcpews.message.MCCommand;
import mcpews.message.MCEvent;
import mcpews.message.MCMessage;

public class CommandsListener implements MCListener {

    @Override
    public void onConnected(MCClient client) {
        client.subscribeToEvent(EventType.PLAYER_MESSAGE);
    }

    @Override
    public void onDisconnected(MCClient client) {
    }

    @Override
    public void onEvent(MCClient client, MCMessage eventMessage) {
        MCEvent event = (MCEvent) eventMessage.getBody();

        switch (event.getEventType()) {
            case PLAYER_MESSAGE:
                if (event instanceof PlayerMessageEvent) {
                    PlayerMessageEvent pme = (PlayerMessageEvent) event;

                    if (pme.getMessage().toLowerCase().equals("blow me up")
                            || pme.getMessage().toLowerCase().equals("send me to space")) {
                        SummonCommand tnt;
                        client.send(this, new SayCommand("Boom goes the dynamite"));
                        for (int i = 0; i < 100; i++) {
                            tnt = new SummonCommand(EntityType.TNT);
                            client.send(this, tnt);
                        }
                        return;
                    }

                    if (pme.getMessage().toLowerCase().startsWith("spawn")) {
                        SummonCommand tnt;
                        String entity = pme.getMessage().toLowerCase().replaceFirst("spawn ", "");
                        EntityType entityT = EntityType.fromString(entity);
                        if (entityT == null) {
                            client.send(this, new SayCommand("\"" + entity + "\" is not a valid entity name"));
                            return;
                        }
                        client.send(this, new SayCommand("Spawning " + entity));
                        tnt = new SummonCommand(EntityType.fromString(entity));
                        client.send(this, tnt);
                        return;
                    }

                    if (pme.getMessage().toLowerCase().equals("day pls")) {
                        client.send(this, new SayCommand("Makin it day time"));
                        client.send(this, new TimeSetCommand(TimeOfDay.day));
                        return;
                    }

                    if (pme.getMessage().toLowerCase().equals("night pls")) {
                        client.send(this, new SayCommand("Makin it night time"));
                        client.send(this, new TimeSetCommand(TimeOfDay.night));
                        return;
                    }

                    if (pme.getMessage().toLowerCase().equals("all")) {
                        SummonCommand tnt;
                        client.send(this, new SayCommand("Spawning all entities"));
                        for (EntityType type : EntityType.values()) {
                            tnt = new SummonCommand(type);
                            client.send(this, tnt);
                        }
                        return;
                    }
                }
                break;
        }
    }

    @Override
    public void onResponse(MCClient client, MCMessage responseMessage, MCMessage requestMessage) {
    }

    @Override
    public void onError(MCClient client, MCMessage errorMessage, MCMessage requestMessage) {
    }
}
