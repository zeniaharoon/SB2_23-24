package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DriveTrain.MecanumDrive;

@Autonomous(name="BlueLeftAuto", group= "Robot")
public class BlueLeftAuto extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;

    private DcMotor leftLift = null;
    private DcMotor rightLift = null;

    private Servo servoFlip = null;
    private Servo clawOpen = null;
    private ElapsedTime runtime = new ElapsedTime();
    private enum states {
        CHECK_SPIKES,
        PLACE_PIXEL_MIDDLE,
        PLACE_PIXEL_RIGHT,
        PLACE_PIXEL_LEFT,
        BACK_INTO_WALL,
        GO_TO_BACKBOARD,
        CHECK_DISTANCE,
        STRAFE_SCAN,
        CENTER_APRIL_TAGS,
        PLACE_PIXEL_ON_BACKDROP,
        PARK_TO_THE_RIGHT,
        STOP
    }
    private states state;

    @Override
    public void runOpMode() {
        state = states.CHECK_SPIKES;
        rightFront = hardwareMap.get(DcMotor.class, "frontRight");
        leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
        leftBack = hardwareMap.get(DcMotor.class, "backLeft");
        rightBack = hardwareMap.get(DcMotor.class, "backRight");


        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);


        //attachments
        //Add lifts + servo motors to Configuration

        leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");

        clawOpen = hardwareMap.get(Servo.class, "clawOpen");
        servoFlip = hardwareMap.get(Servo.class, "servoFlip");
        clawOpen.setPosition(0.1);
        servoFlip.setPosition(0.5);

        waitForStart();

        MecanumDrive auto = new MecanumDrive(leftFront, rightFront, leftBack, rightBack, runtime);
        while (state != states.STOP) {
            switch (state) {
                case CHECK_SPIKES:
                    boolean onMiddle = true;
                    if (onMiddle) {

                        state = states.PLACE_PIXEL;
                    } else {
                        state = states.BACK_INTO_WALL;
                    }
                    break;

                case PLACE_PIXEL:
                        state = states.BACK_INTO_WALL;
                    break;

                case BACK_INTO_WALL:
                    auto.moveBackward(1000,0.5);
                    state = states.GO_TO_BACKBOARD;
                    break;
                case GO_TO_BACKBOARD:
                    auto.moveForward(500, 0.5);
                    auto.turnCounterClockwise(500,0.5);
                    auto.moveForward(1000,0.5);
                    state = states.CHECK_DISTANCE;
                    break;
                case CHECK_DISTANCE:
                    //check distance from backdrop
                    //if too close move backwards
                    //if too far move forwards
                    state = states.STRAFE_SCAN;
                    break;
                case STRAFE_SCAN:
                    auto.strafeRight(500,0.5);
                    //scan april tag
                    state = states.CENTER_APRIL_TAGS;
                    break;
                case CENTER_APRIL_TAGS:
                    //strafe left or right to center the april tag on camera
                    state = states.PLACE_PIXEL_ON_BACKDROP;
                    break;
                case PLACE_PIXEL_ON_BACKDROP:
                    auto.moveForward(500,0.5);
                    //raise lift
                    //place claw against backboard
                    //open claw to place pixel
                    state = states.PARK_TO_THE_RIGHT;
                    break;
                case PARK_TO_THE_RIGHT:
                    auto.strafeRight(1000,0.5);
                    auto.moveForward(500,0.5);
                    state = states.STOP;
                    break;
                case STOP:
                    break;
            }
        }
    }
}