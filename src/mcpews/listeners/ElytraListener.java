package mcpews.listeners;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mcpews.MCClient;
import mcpews.MCListener;
import mcpews.MCSocketServer;
import mcpews.command.ExecuteCommand;
import mcpews.command.FillCommand;
import mcpews.command.SayCommand;
import mcpews.command.SetBlockCommand;
import mcpews.command.TestForBlockCommand;
import mcpews.event.EventType;
import mcpews.event.PlayerMessageEvent;
import mcpews.event.PlayerTransformEvent;
import mcpews.event.PlayerTravelledEvent;
import mcpews.logger.LogLevel;
import mcpews.mcenum.BlockPos;
import mcpews.mcenum.BlockType;
import mcpews.mcenum.CommandTarget;
import mcpews.message.MCEvent;
import mcpews.message.MCMessage;
import mcpews.message.MCResponse;
import mcpews.response.TestForBlockResponse;
import mcpews.util.ChatFormatCode;

/**
 *
 * @author Jocopa3
 */
public class ElytraListener implements MCListener {

    private MCSocketServer server;

    private HashMap<MCClient, BlockPos> playerPositions;
    private HashMap<MCClient, ElytraFlyer> flyers;

    public ElytraListener(MCSocketServer server) {
        this.server = server;
        playerPositions = new HashMap<>();
        flyers = new HashMap<>();
    }

    private BlockPos getClientPosition(MCClient client) {
        return playerPositions.get(client);
    }

    private void setClientPosition(MCClient client, BlockPos pos) {
        playerPositions.put(client, pos);
    }

    BlockPos radius = new BlockPos(10, 7, 10);

    private void doElytraFlying(MCClient client, BlockPos pos, double metersTravelled) {
        BlockPos clientPosition = getClientPosition(client);

        if (clientPosition == null) {
            setClientPosition(client, pos);
            return;
        }

        BlockPos lowerBound = pos.sub(radius);
        BlockPos upperBound = pos.add(radius);

        //FillCommand fill = new FillCommand(new FillCommand.FillCommandInput(lowerBound, upperBound, BlockType.AIR));
        //client.send(fill);
        //SetBlockCommand setBlock
        setClientPosition(client, pos);
    }

    @Override
    public void onResponse(MCClient client, MCMessage responseMessage, MCMessage requestMessage) {
    }

    @Override
    public void onEvent(MCClient client, MCMessage eventMessage) {
        MCEvent event = (MCEvent) eventMessage.getBody();

        switch (event.getEventType()) {
            case PLAYER_TRAVELLED:
                if (event instanceof PlayerTravelledEvent) {
                    PlayerTravelledEvent pte = (PlayerTravelledEvent) event;

                    int travelMethod = pte.getTravelMethod();
                    server.getLog().log(LogLevel.DEBUG, "TravelMethodType: {0}", travelMethod);
                    switch (travelMethod) {
                        case 2:
                            flyers.get(client).setFlying(true);
                            //doElytraFlying(client, pte.getAvgPos(), pte.getMetersTravelled());
                            break;
                        default:
                            flyers.get(client).setFlying(false);
                            break;
                    }
                }
                break;
        }
    }

    @Override
    public void onError(MCClient client, MCMessage errorMessage, MCMessage requestMessage) {
    }

    @Override
    public void onConnected(MCClient client) {
        client.subscribeToEvent(EventType.PLAYER_TRAVELLED);
        ElytraFlyer flyer = new ElytraFlyer(client);
        flyers.put(client, flyer);
        Thread t = new Thread(flyer);
        t.start();
    }

    @Override
    public void onDisconnected(MCClient client) {
    }

    private class ElytraFlyer implements Runnable {

        private MCClient client;

        public ElytraFlyer(MCClient client) {
            this.client = client;
        }

        boolean flying = false;
        private BlockPos relative = new BlockPos("~", "~", "~");
        private CommandTarget localPlayer = new CommandTarget("@p");

        @Override
        public void run() {
            while (true) {
                if (flying) {
                    ExecuteCommand exec = new ExecuteCommand(new ExecuteCommand.ExecuteAsOtherInput(localPlayer, relative, "fill ~-4 ~-4 ~-4 ~4 ~4 ~4 air"));
                    client.send(exec);
                }
                try {
                    Thread.sleep(40);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ElytraListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public void setFlying(boolean flying) {
            this.flying = flying;
        }
    }
}
