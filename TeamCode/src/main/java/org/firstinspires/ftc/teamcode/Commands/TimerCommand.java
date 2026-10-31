package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.util.Timing;

import java.util.concurrent.TimeUnit;

public class TimerCommand extends CommandBase {

    Timing.Timer timer;
    public TimerCommand(long timeInMs) {
        timer = new Timing.Timer(timeInMs, TimeUnit.MILLISECONDS);
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.done();
    }

}
