/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.event;

import mcpews.util.Biome;

/**
 *
 * @author Jocopa3
 */
public class EventProperties {

    private int Biome;
    private String Build;
    private int BuildPlat;
    private boolean Cheevos;
    private String ClientId;
    private int Dim;
    private int Dimension;
    private String Flavor;
    private int Mode;
    private String MultiplayerCorrelationId;
    private int NetworkType;
    private String Plat;
    private String PlayerId;
    private String PlayerSessionID;
    private String ServerId;
    private String UserId;
    private int gameMode;
    private boolean vrMode;

    public Biome getBiome() {
        return mcpews.util.Biome.fromId(Biome);
    }

    /**
     * @return the Build
     */
    public String getBuild() {
        return Build;
    }

    /**
     * @return the BuildPlat
     */
    public int getBuildPlat() {
        return BuildPlat;
    }

    /**
     * @return the Cheevos
     */
    public boolean isCheevos() {
        return Cheevos;
    }

    /**
     * @return the ClientId
     */
    public String getClientId() {
        return ClientId;
    }

    /**
     * @return the Dim
     */
    public int getDim() {
        return Dim;
    }

    /**
     * @return the Dimension
     */
    public int getDimension() {
        return Dimension;
    }

    /**
     * @return the Flavor
     */
    public String getFlavor() {
        return Flavor;
    }

    /**
     * @return the Mode
     */
    public int getMode() {
        return Mode;
    }

    /**
     * @return the MultiplayerCorrelationId
     */
    public String getMultiplayerCorrelationId() {
        return MultiplayerCorrelationId;
    }

    /**
     * @return the NetworkType
     */
    public int getNetworkType() {
        return NetworkType;
    }

    /**
     * @return the Plat
     */
    public String getPlat() {
        return Plat;
    }

    /**
     * @return the PlayerId
     */
    public String getPlayerId() {
        return PlayerId;
    }

    /**
     * @return the PlayerSessionID
     */
    public String getPlayerSessionID() {
        return PlayerSessionID;
    }

    /**
     * @return the ServerId
     */
    public String getServerId() {
        return ServerId;
    }

    /**
     * @return the UserId
     */
    public String getUserId() {
        return UserId;
    }

    /**
     * @return the gameMode
     */
    public int getGameMode() {
        return gameMode;
    }

    /**
     * @return the vrMode
     */
    public boolean isVrMode() {
        return vrMode;
    }
}
