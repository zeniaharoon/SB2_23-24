package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DriveTrain.Blinkin;

@TeleOp(name = "TeleOp24Test1", group = "Mecanum")
public class TeleOp24Test extends LinearOpMode {
    private Servo clawOpen = null;
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private ColorSensor sensorColor; // Color sensor
    private RevBlinkinLedDriver blinkinLedDriver; // LED driver
    private ElapsedTime runtime = new ElapsedTime();
    private LinearLift2425 lift;

    private FourBarIntake2425 intake2425;
    private FourBarOutake2425 outake2425;
    private DcMotor liftMotor1;
    private DcMotor liftMotor2;

    private DcMotor liftMotor3;

    private DcMotor liftMotor4;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // Initialize drivetrain motors
        rightFront = hardwareMap.get(DcMotor.class, "frontRight");
        leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
        leftBack = hardwareMap.get(DcMotor.class, "backLeft");
        rightBack = hardwareMap.get(DcMotor.class, "backRight");

        // Initialize attachments
        clawOpen = hardwareMap.get(Servo.class, "clawOpen");
        liftMotor1 = hardwareMap.get(DcMotor.class, "liftMotor1");
        liftMotor2 = hardwareMap.get(DcMotor.class, "liftMotor2");
        lift = new LinearLift2425(liftMotor1, liftMotor2, runtime, telemetry);


        liftMotor3 = hardwareMap.get(DcMotor.class, "i4bar");
        intake2425 = new FourBarIntake2425(liftMotor3, runtime, telemetry);
        liftMotor4 = hardwareMap.get(DcMotor.class, "o4bar");
        outake2425 = new FourBarOutake2425(liftMotor4, runtime, telemetry);


        // Initialize the color sensor and LED driver
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");

        // Reset and configure encoders
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set directions for mecanum drive
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        // HSV color scale and RelativeLayout for background color change
        final float hsvValues[] = {0F, 0F, 0F};
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            // Read joystick values
            double y = -gamepad1.left_stick_y;  // Forward/backward (inverted for forward)
            double x = -gamepad1.left_stick_x;   // Side-to-side (strafe)
            double r = -gamepad1.right_stick_x;  // Rotation

            // Calculate motor powers
            double p1 = y + x + r;  // Left Front
            double p2 = y - x - r;  // Right Front
            double p3 = y - x + r;  // Left Back
            double p4 = y + x - r;  // Right Back

            // Normalize motor powers if any exceed 1.0
            double maxval = max(abs(p1), max(abs(p2), max(abs(p3), abs(p4))));
            if (maxval > 1.0) {
                p1 /= maxval;
                p2 /= maxval;
                p3 /= maxval;
                p4 /= maxval;
            }

            // Set motor powers
            leftFront.setPower(p1);
            rightFront.setPower(p2);
            leftBack.setPower(p3);
            rightBack.setPower(p4);

            if (gamepad2.x) {
                lift.lowBasket();
            }
            if (gamepad2.a){
                lift.neutral();
            }

            if (gamepad2.y) {
                intake2425.liftI();
            }

            if(gamepad2.b) {
                outake2425.liftO();
            }


            // Convert the RGB values to HSV values
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            // Update Blinkin LED based on color sensor
            RevBlinkinLedDriver.BlinkinPattern pattern = Blinkin.blinkin(sensorColor);
            blinkinLedDriver.setPattern(pattern);

            // Display telemetry for debugging
            //telemetry.addData("Motor Powers", "p1: %.2f, p2: %.2f, p3: %.2f, p4: %.2f", p1, p2, p3, p4);
            /*telemetry.addData("Detected Pattern", pattern.toString());
            telemetry.addData("Alpha", sensorColor.alpha());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();*/

            telemetry.addData("FourBar Intake position: ", liftMotor3.getCurrentPosition());
            telemetry.addData("FourBar Intake position: ", liftMotor4.getCurrentPosition());
            telemetry.update();

            // Change the background color of the screen to match the hue detected by the RGB sensor
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, hsvValues));
                }
            });


        }

    }

}
