/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.event;

import mcpews.mcenum.BlockPos;
import mcpews.message.MCEvent;

/**
 *
 * @author Jocopa3
 */
public class PlayerTravelledEvent extends MCEvent {

    public PlayerTravelledEvent(EventType type, EventProperties properties, EventMeasurements measurements) {
        super(type, properties, measurements);
    }

    protected class PlayerTravelledMeasurements extends EventMeasurements {

        protected double MetersTravelled;
        protected int NewBiome;
        protected double PosXAvg, PosYAvg, PosZAvg;
    }

    protected class PlayerTravelledProperties extends EventProperties {

        boolean HasRelevantBuff;
        int MobType;
        int TravelMethodType; // 2 = Elytra, 5 = creative mode flying
        int WorldFeature;
    }

    public int getTravelMethod() {
        return ((PlayerTravelledProperties) properties).TravelMethodType;
    }

    public BlockPos getAvgPos() {
        PlayerTravelledMeasurements meas = (PlayerTravelledMeasurements) measurements;
        return new BlockPos(meas.PosXAvg, meas.PosYAvg, meas.PosZAvg);
    }

    public double getMetersTravelled() {
        return ((PlayerTravelledMeasurements) measurements).MetersTravelled;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
