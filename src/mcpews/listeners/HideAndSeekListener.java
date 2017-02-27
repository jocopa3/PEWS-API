package mcpews.listeners;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mcpews.MCClient;
import mcpews.MCListener;
import mcpews.MCSocketServer;
import mcpews.command.SayCommand;
import mcpews.command.SetBlockCommand;
import mcpews.command.TestForBlockCommand;
import mcpews.event.EventType;
import mcpews.event.PlayerMessageEvent;
import mcpews.event.PlayerTransformEvent;
import mcpews.mcenum.BlockPos;
import mcpews.mcenum.BlockType;
import mcpews.message.MCEvent;
import mcpews.message.MCMessage;
import mcpews.message.MCResponse;
import mcpews.response.TestForBlockResponse;
import mcpews.util.ChatFormatCode;

/**
 *
 * @author Jocopa3
 */
public class HideAndSeekListener implements MCListener {

    private MCSocketServer server;

    private HashMap<MCClient, HideAndSeekRunner> runningGames;

    public HideAndSeekListener(MCSocketServer server) {
        this.server = server;
        runningGames = new HashMap<>();
    }

    @Override
    public void onResponse(MCClient client, MCMessage responseMessage, MCMessage requestMessage) {
        MCResponse response = (MCResponse) responseMessage.getBody();

        switch (response.getResponseType()) {
            case TESTFORBLOCK:
                if (response instanceof TestForBlockResponse) {
                    TestForBlockResponse tfbr = (TestForBlockResponse) response;
                    HideAndSeekRunner game = runningGames.get(client);

                    if (game != null) {
                        game.setPlaying(tfbr.matches());
                    }
                }
                break;
        }
    }

    @Override
    public void onEvent(MCClient client, MCMessage eventMessage) {
        MCEvent event = (MCEvent) eventMessage.getBody();

        switch (event.getEventType()) {
            case PLAYER_MESSAGE:
                if (event instanceof PlayerMessageEvent) {
                    PlayerMessageEvent pme = (PlayerMessageEvent) event;

                    if (pme.getMessage().toLowerCase().equals("play hide and seek")) {
                        HideAndSeekRunner game = new HideAndSeekRunner(this, client);
                        runningGames.put(client, game);
                        new Thread(game).start();
                        return;
                    }
                }
                break;
            case PLAYER_TRANSFORM:
                if (event instanceof PlayerTransformEvent) {
                    PlayerTransformEvent pte = (PlayerTransformEvent) event;
                    HideAndSeekRunner game = runningGames.get(client);

                    if (game != null) {
                        game.setPlayerPos(pte.getPosition());
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
        client.subscribeToEvent(EventType.PLAYER_MESSAGE);
        client.subscribeToEvent(EventType.PLAYER_TRANSFORM);
    }

    @Override
    public void onDisconnected(MCClient client) {
    }

    private void finishGame(MCClient client) {
        runningGames.remove(client);
    }

    private class HideAndSeekRunner implements Runnable {

        private HideAndSeekListener listener;
        private MCClient client;
        private boolean playing;
        private BlockPos randomPos;
        private BlockPos playerPos;
        private double distanceDelta = 0;

        private BlockPos randomPos() {
            return new BlockPos(Math.random() * 200 - 100, Math.random() * 64 + 4, Math.random() * 200 - 100);
        }

        HideAndSeekRunner(HideAndSeekListener listener, MCClient client) {
            this.listener = listener;
            this.client = client;
            playing = true;
            randomPos = randomPos();
            playerPos = new BlockPos(10000, 10000, 10000);
            client.send(listener, new SetBlockCommand(randomPos, BlockType.DIAMOND_BLOCK));
        }

        @Override
        public void run() {
            int a = 0;
            while (playing) {
                client.send(listener, new TestForBlockCommand(randomPos, BlockType.DIAMOND_BLOCK));

                String message;

                if (distanceDelta < -1.0) {
                    message = ChatFormatCode.BLUE_STRING + ChatFormatCode.BOLD_STRING + "Getting Super Cold";
                } else if (distanceDelta < -0.7) {
                    message = ChatFormatCode.BLUE_STRING + "Getting Colder";
                } else if (distanceDelta < -0.15) {
                    message = ChatFormatCode.AQUA_STRING + "Getting Cooler";
                } else if (distanceDelta > 1.0) {
                    message = ChatFormatCode.RED_STRING + ChatFormatCode.BOLD_STRING + "Getting Extremly Hot";
                } else if (distanceDelta > 0.7) {
                    message = ChatFormatCode.RED_STRING + "Getting Hotter";
                } else if (distanceDelta > 0.15) {
                    message = ChatFormatCode.YELLOW_STRING + "Getting Warmer";
                } else {
                    double dist = playerPos.distance(randomPos);
                    
                    if (dist < 4.0) {
                        message = ChatFormatCode.RED_STRING + ChatFormatCode.BOLD_STRING + "On Fire!";
                    } else if (dist < 8.5) {
                        message = ChatFormatCode.RED_STRING + ChatFormatCode.BOLD_STRING + "Super Hot";
                    } else if (dist < 15.0) {
                        message = ChatFormatCode.RED_STRING + "Hot";
                    } else if (dist < 25.0) {
                        message = ChatFormatCode.YELLOW_STRING + "Warm";
                    } else if (dist < 50.0) {
                        message = ChatFormatCode.AQUA_STRING + "Cold";
                    } else {
                        message = ChatFormatCode.BLUE_STRING + "Super Cold";
                    }
                }

                distanceDelta = 0.0;

                a++;
                if (a % 2 == 0) {
                    client.send(listener, new SayCommand(message));
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
            }

            client.send(listener, new SayCommand(ChatFormatCode.GREEN_STRING + "You won!"));
            listener.finishGame(client);
        }

        public void setPlaying(boolean playing) {
            this.playing = playing;
        }

        public void setPlayerPos(BlockPos pos) {
            double distance = playerPos.distance(randomPos);
            this.playerPos = pos;
            distanceDelta = distance - playerPos.distance(randomPos);
        }
    }
}
