package org.firstinspires.ftc.teamcode.DriveTrain;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
 * This class extends ColorSensorTest and changes LED colors based on
 * the detected color from the color sensor.
 */
@TeleOp(name = "BlinkinCode", group = "Sensor")
public class Blinkin extends ColorSensorTest {

    final double SCALE_FACTOR = 255;
    // Declare the LED driver
    RevBlinkinLedDriver blinkinLedDriver;
    RevBlinkinLedDriver.BlinkinPattern pattern;

    @Override
    public void runOpMode() {
        // Initialize the color sensor from the parent class
        super.runOpMode();

        // Initialize the LED driver
        blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");

        // Wait for the start button to be pressed
        waitForStart();

        // Loop while the op mode is active
        while (opModeIsActive()) {
            // Convert the RGB values to HSV values (this is done in the parent class)
            float hsvValues[] = {0F, 0F, 0F};
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            // Check the detected color and set the LED color accordingly
            if (sensorColor.red() > sensorColor.green() && sensorColor.red() > sensorColor.blue()) {
                // If red is the dominant color, set LED to red
                pattern = RevBlinkinLedDriver.BlinkinPattern.RED;
            } else if (sensorColor.blue() > sensorColor.red() && sensorColor.blue() > sensorColor.green()) {
                // If blue is the dominant color, set LED to blue
                pattern = RevBlinkinLedDriver.BlinkinPattern.BLUE;
            } else if (sensorColor.red() > 200 && sensorColor.green() > 200 && sensorColor.blue() < 100) {
                // If the detected color is yellow (high red and green values, low blue)
                pattern = RevBlinkinLedDriver.BlinkinPattern.YELLOW;
            } else {
                // If no specific color is detected, set LED to green (default)
                pattern = RevBlinkinLedDriver.BlinkinPattern.GREEN;
            }

            // Set the LED pattern based on the detected color
            blinkinLedDriver.setPattern(pattern);

            // Update the telemetry with the color sensor data
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Pattern", pattern.toString());
            telemetry.update();
        }
    }
}
