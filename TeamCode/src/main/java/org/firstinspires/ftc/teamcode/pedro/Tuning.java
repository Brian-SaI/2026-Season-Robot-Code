package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner;

public class Tuning {
    @Tuner
    public static Procedure swerveTuner() {
        return new MecanumTuner();
        // Tuners go here
    }
}
