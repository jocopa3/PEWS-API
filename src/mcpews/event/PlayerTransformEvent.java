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
public class PlayerTransformEvent extends MCEvent {

    public PlayerTransformEvent(EventType type, EventProperties properties, EventMeasurements measurements) {
        super(type, properties, measurements);
    }

    protected class PlayerTransformProperties extends EventProperties {
        protected double PlayerYRot;
        protected double PosX;
        protected double PosY;
        protected double PosZ;
    }
    
    public BlockPos getPosition() {
        PlayerTransformProperties props = (PlayerTransformProperties)properties;
        return new BlockPos(props.PosX, props.PosY, props.PosZ);
    }

    @Override
    public String toString() {
        return getPosition().toString();
    }
}
