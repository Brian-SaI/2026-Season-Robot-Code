package org.firstinspires.ftc.teamcode.Subsystems;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShooterSubsystemTest {

    public static final double POLLEN_GEAR_RATIO = 20.0 / 21.0;
    public static final double NECTAR_GEAR_RATIO = 20.0 / 22.0;

    @Mock
    HardwareMap hardwareMap;

    @Mock
    Telemetry telemetry;

    @Mock
    VisionSubsystem visionSubsystem;

    @Mock
    DcMotorEx pollenShoot;

    @Mock
    DcMotorEx nectarShoot;

    ShooterSubsystem shooter;

    @BeforeEach
    void setUp() {
        when(hardwareMap.get(eq(DcMotorEx.class), eq("PollenShoot"))).thenReturn(pollenShoot);
        when(hardwareMap.get(eq(DcMotorEx.class), eq("NectarShoot"))).thenReturn(nectarShoot);
        when(pollenShoot.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER))
                .thenReturn(new PIDFCoefficients(120.0, 0.0, 15.0, 12.6189));

        shooter = new ShooterSubsystem(hardwareMap, telemetry, visionSubsystem);
    }

    @Test
    void pollenVelocityToRPM() {
        assertEquals(0.0, shooter.pollenVelocityToRPM(0.0), 1e-2);
        assertEquals(3750.0 * POLLEN_GEAR_RATIO, shooter.pollenVelocityToRPM(1750.0), 1e-2);
        assertEquals(2142.86 * POLLEN_GEAR_RATIO, shooter.pollenVelocityToRPM(1000.0), 1e-2);
    }

    @Test
    void pollenRPMToVelocity() {
        assertEquals(0.0, shooter.pollenRPMToVelocity(0.0), 1e-2);
        assertEquals(1666.67 / POLLEN_GEAR_RATIO, shooter.pollenRPMToVelocity(3571.43), 1e-2);
        assertEquals(952.38 / POLLEN_GEAR_RATIO, shooter.pollenRPMToVelocity(2040.82), 1e-2);
    }

    @Test
    void nectarVelocityToRPM() {
        assertEquals(0.0, shooter.nectarVelocityToRPM(0.0), 1e-2);
        assertEquals(3750 * NECTAR_GEAR_RATIO, shooter.nectarVelocityToRPM(1750.0), 1e-2);
        assertEquals(2142.86 * NECTAR_GEAR_RATIO, shooter.nectarVelocityToRPM(1000.0), 1e-2);
    }

    @Test
    void nectarRPMToVelocity() {
        assertEquals(0.0, shooter.nectarRPMToVelocity(0.0), 1e-2);
        assertEquals(1666.67 / NECTAR_GEAR_RATIO, shooter.nectarRPMToVelocity(3571.43), 1e-2);
        assertEquals(952.38 / NECTAR_GEAR_RATIO, shooter.nectarRPMToVelocity(2040.82), 1e-2);
    }

    @Test
    void interpolate() {
        assertEquals(0.0, shooter.interpolate(0.0, 0.0, 0.0, 1.0, 1.0), 1e-2);
        assertEquals(0.5, shooter.interpolate(0.5, 0.0, 0.0, 1.0, 1.0), 1e-2);
        assertEquals(2.2, shooter.interpolate(1.1, 0.0, 0.0, 1.0, 2.0), 1e-2);
    }
}
