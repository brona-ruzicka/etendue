package com.brona.etendue.experiments.event2;

import java.awt.*;

public class EventTesting {

    public static void main(String[] args) {

        EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();


    }


    public static class CustomEvent extends AWTEvent {

        public static final int CUSTOM_EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1000;

        public CustomEvent(Object source) {
            super(source, CUSTOM_EVENT_ID);
        }


    }

    public static class CustomComponent extends Component {

        public CustomComponent() {
            enableEvents(0);
        }

        @Override
        protected void processEvent(AWTEvent e) {
            super.processEvent(e);
        }
    }

}
