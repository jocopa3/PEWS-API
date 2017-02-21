/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.event;

import mcpews.message.MCEvent;

/**
 *
 * @author Jocopa3
 */
public class PerformanceMetricsEvent extends MCEvent {

    public PerformanceMetricsEvent(EventType type, EventProperties properties, EventMeasurements measurements) {
        super(type, properties, measurements);
    }

    protected class PerformanceMetricsMeasurements extends EventMeasurements {
        protected String Message;
        protected String Sender;
    }

    @Override
    public String toString() {
        return "";
    }
}
