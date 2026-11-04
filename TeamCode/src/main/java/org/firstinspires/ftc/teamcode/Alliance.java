package org.firstinspires.ftc.teamcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Alliance {
    BLUE("BLUE SCORING","BLUE AUDIENCE"),
    RED("RED SCORING","RED AUDIENCE");

    private Set<String> shortNames = new HashSet<>();
    Alliance(String... shortNames) {
        this.shortNames.addAll(Arrays.asList(shortNames));
    }

    public boolean isTarget(String shortName) {
        return shortNames.contains(shortName);
    }
}
