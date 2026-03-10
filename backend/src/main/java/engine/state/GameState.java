package engine.state;

import java.util.List;

public record GameState(
        String title,
        String statusLine,
        List<String> logLines
) {

}

