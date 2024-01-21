package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;
//import CSVisionProcessor;

@Config
@Autonomous(name = "SampleVisionOpMode129", group = "Robot")
public class SampleVisionOpMode extends LinearOpMode {
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor liftMotor = null;

    private DcMotor leftLift = null;
    private DcMotor rightLift = null;

    private Servo servoFlip = null;
    private Servo clawOpen = null;
    private ElapsedTime runtime = new ElapsedTime();

    private CSVisionProcessor visionProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
//        clawOpen = hardwareMap.get(Servo.class, "clawOpen");
//        servoFlip = hardwareMap.get(Servo.class, "servoFlip");
//        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
//        rightFront = hardwareMap.get(DcMotor.class, "frontRight");
//        leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
//        leftBack = hardwareMap.get(DcMotor.class, "backLeft");
//        rightBack = hardwareMap.get(DcMotor.class, "backRight");
//
//
//        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
//        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
//        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
//        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
//        liftMotor.setDirection(DcMotor.Direction.REVERSE);
//        MecanumDrive auto = new MecanumDrive(leftFront, rightFront, leftBack, rightBack, runtime);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        visionProcessor = new CSVisionProcessor(telemetry, camera);
//        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), visionProcessor);


        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(visionProcessor)
                .setCameraResolution(new Size(1280, 720))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();



        CSVisionProcessor.StartingPosition startingPos;

        // PSEUDOCODE:
        // lift move up so it is up 90 degrees
        // servoFlip up (parallel)
        // drive to correct position after detect
        // lift down, claw open

//        servoFlip.setPosition(0.2); //fix val!!
//        clawOpen.setPosition(0.1); //fix val!!
        waitForStart();
        while (opModeIsActive()){
            if (visionProcessor.getPosition() == null) {
                telemetry.addLine("starting pos is null");
            } else {
                telemetry.addData("Position: ", visionProcessor.getPosition());
            }
            telemetry.update();
        }
        /*
        liftMotor.setPower(0.35);

        double startTime = runtime.milliseconds();
        while ((runtime.milliseconds() - startTime) < 2000 ) { //change 2000 val

        }
        liftMotor.setPower(0);
        */
//        startingPos = visionProcessor.getStartingPosition();

//        if(startingPos == CSVisionProcessor.StartingPosition.CENTER) {
//            //adjust time
//            auto.moveForward(1000, 0.5);
//            clawOpen.setPosition(0.5); //open
//        } else if (startingPos == CSVisionProcessor.StartingPosition.RIGHT) {
//            auto.moveForward(500, 0.5);
//            auto.strafeRight(500,0.5);
//            clawOpen.setPosition(0.5);
//        } else if (startingPos == CSVisionProcessor.StartingPosition.LEFT) {
//            auto.moveForward(500, 0.5);
//            auto.strafeLeft(500,0.5);
//            clawOpen.setPosition(0.5);
//        } else if (startingPos == CSVisionProcessor.StartingPosition.NONE) {
//            // go park
//        }
        // run until the end of the match (driver presses STOP)

        visionPortal.stopStreaming();

    }

}