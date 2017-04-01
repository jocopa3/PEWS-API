/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.listeners;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import mcpews.MCClient;
import mcpews.MCListener;
import mcpews.MCSocketServer;
import mcpews.command.SayCommand;
import mcpews.command.SetBlockCommand;
import mcpews.event.EventType;
import mcpews.event.PlayerMessageEvent;
import mcpews.logger.LogLevel;
import mcpews.mcenum.BlockPos;
import mcpews.mcenum.BlockType;
import mcpews.message.MCCommand;
import mcpews.message.MCEvent;
import mcpews.message.MCMessage;
import mcpews.message.MCResponse;
import mcpews.response.ListDetailResponse;
import mcpews.response.ListResponse;
import mcpews.response.SayResponse;

/**
 *
 * @author Jocopa3
 */
public class TextToBlockListener implements MCListener {

    private MCSocketServer server;

    /*
     * This listener requires a reference to the server in-order to get all
     * conncted clients.
     */
    public TextToBlockListener(MCSocketServer server) {
        this.server = server;
    }

    /*
     * Sends a message using /say to a specific client only.
     */
    private void sendMessageToClient(MCClient client, String message) {
        if (server == null) {
            return;
        }

        // Create a new /say command and send it to the client
        MCCommand say = new SayCommand(new SayCommand.SayCommandInput(message));
        client.send(this, say);
    }

    private BlockType[] blocks = new BlockType[]{BlockType.DIAMOND_BLOCK, BlockType.REDSTONE_BLOCK, BlockType.EMERALD_BLOCK, BlockType.IRON_BLOCK, BlockType.GOLD_BLOCK, BlockType.LAPIS_BLOCK};

    /*
     * Sends a message using /say to a specific client only.
     */
    private void setBlock(MCClient client, BlockType block, int x, int y, int z) {
        y += 4;

        // Create a new /say command and send it to the client
        MCCommand say = new SetBlockCommand(new SetBlockCommand.SetBlockInput(new BlockPos(x, y, z), block));
        client.send(this, say);
    }

    /*
     * This method is called anytime a new client connects to the server
     */
    @Override
    public void onConnected(MCClient client) {
        // Subscribe to player message events to recieve messages from the client
        client.subscribeToEvent(EventType.PLAYER_MESSAGE);
    }

    /*
     * This method is called >after< a client disconnects from the server.
     */
    @Override
    public void onDisconnected(MCClient client) {
    }

    private String fontName = "Sans Serif";
    private Integer fontSize = 12;
    private int fontType = Font.PLAIN;
    private BlockType block = BlockType.STONE;

    private void drawText(MCClient client, String message) {
        Font font = new Font(fontName, fontType, fontSize);
        BufferedImage f = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = f.getGraphics();
        FontMetrics metrics = g.getFontMetrics(font);

        BufferedImage img = new BufferedImage((int) (metrics.stringWidth(message) * 1.2), metrics.getHeight() + 8, BufferedImage.TYPE_INT_ARGB);
        g = img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(message, fontSize / 16, fontSize / 2 + fontSize / 4);

        // Reverted to old setblock algorithm. New algorithm was slower.
        // Todo: implement a better algorithm
        
        int cycle = 1;
        int y = 0;
        for (int x = 0; y < img.getHeight();) {
            int rgb = img.getRGB(x, y);
            if ((rgb & 1) == 0) {
                setBlock(client, block, x, img.getHeight() - y, 0);
                cycle++;
            }
            x++;
            System.out.print((rgb & 1) == 0 ? "\u2593" : "\u2591");

            if (x == img.getWidth() - 1) {
                x = 0;
                y++;
                System.out.println();
            }
        }
    }

    /*
     * This method is called whenever the server recieves an Event message.
     * The eventMessage parameter will contain an MCEvent as the body.
     */
    @Override
    public void onEvent(MCClient client, MCMessage eventMessage) {
        MCEvent event = (MCEvent) eventMessage.getBody();

        switch (event.getEventType()) {
            case PLAYER_MESSAGE:
                if (event instanceof PlayerMessageEvent) {
                    PlayerMessageEvent pme = (PlayerMessageEvent) event;

                    // Ignore the event if the sender is named External
                    if (!pme.getSender().equals("External")) {
                        String m = pme.getMessage();
                        if (m.toLowerCase().startsWith("setfont:")) {
                            fontName = m.replace("setfont:", "").trim();

                            if (Font.decode(fontName) != null) {
                                sendMessageToClient(client, "Set font to: " + fontName);
                            } else {
                                sendMessageToClient(client, "Invalid font name");
                            }
                        }
                        if (m.toLowerCase().startsWith("setsize:")) {
                            try {
                                fontSize = Integer.parseInt(m.replace("setsize:", "").trim());
                                sendMessageToClient(client, "Set font size to: " + fontSize);
                            } catch (Exception e) {
                                sendMessageToClient(client, "Invalid font size");
                            }
                        }
                        if (m.toLowerCase().startsWith("setblock:")) {
                            try {
                                String[] b = m.replace("setblock:", "").trim().split(" ");
                                System.out.println(Arrays.toString(b));
                                if (b.length == 1) {
                                    block = BlockType.fromString(b[0].trim());
                                } else if (b.length == 2) {
                                    block = BlockType.fromString(b[0].trim(), Integer.parseInt(b[1].trim()));
                                }
                                sendMessageToClient(client, "Set block to: " + block);
                            } catch (Exception e) {
                                sendMessageToClient(client, "Invalid block");
                            }
                        }
                        if (m.toLowerCase().startsWith("drawtext:")) {
                            String text = m.replace("drawtext:", "").trim();
                            try {
                                drawText(client, text);
                            } catch (Exception e) {
                                sendMessageToClient(client, "Couldn't draw text");
                            }
                        }
                    }
                }
                break;
        }
    }

    /*
     * This method is called whenever the server recieves a command response.
     * 
     * The responseMessage parameter will contain an MCResponse as the body.
     * The requestMessage parameter is the original MCCommand request that 
     * triggered the response.
     */
    @Override
    public void onResponse(MCClient client, MCMessage responseMessage, MCMessage requestMessage) {
        MCResponse response = (MCResponse) responseMessage.getBody();

        switch (response.getResponseType()) {
        }
    }

    /*
     * This method is called whenever the server recieves an error from a client.
     * 
     * The errorMessage parameter will contain an MCError as the body.
     * The requestMessage parameter is the original MCCommand request that 
     * triggered the error.
     */
    @Override
    public void onError(MCClient client, MCMessage errorMessage, MCMessage requestMessage) {
        //server.getLog().log(LogLevel.WARNING, errorMessage.toString());
        System.out.println(errorMessage);
    }
}

class CoordinatePair {

    int x, y;

    public CoordinatePair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int A = (int) (x >= 0 ? 2 * x : -2 * x - 1);
        int B = (int) (y >= 0 ? 2 * y : -2 * y - 1);
        int C = (int) ((A >= B ? A * A + A + B : A + B * B) / 2);
        return x < 0 && y < 0 || x >= 0 && y >= 0 ? C : -C - 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CoordinatePair other = (CoordinatePair) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public long getXY() {
        return x << 31 | y;
    }
}

class AABB {
    CoordinatePair A;
    CoordinatePair B;
    
    public AABB(CoordinatePair A, CoordinatePair B) {
        this.A = A;
        this.B = B;
    }
    
    public CoordinatePair getLower() {
        return A;
    }
    
    public CoordinatePair getUpper() {
        return B;
    }
    
    public boolean contains(CoordinatePair C) {
        return C.x >= A.x && C.x <= B.x && C.y >= A.y && C.y <= B.y;
    }
}