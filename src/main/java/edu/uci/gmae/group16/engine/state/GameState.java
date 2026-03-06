package edu.uci.gmae.group16.engine.state;

import java.util.List;

public record GameState(
        String title,
        String statusLine,
        List<String> logLines
) {

}

