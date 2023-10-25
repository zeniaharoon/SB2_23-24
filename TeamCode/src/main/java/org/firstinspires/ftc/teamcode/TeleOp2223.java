package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "TeleOp2223", group = "Mecanum")
public class TeleOp2223 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    //wheels
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;

    private DcMotor leftLift = null;
    private DcMotor rightLift = null;

    private Servo clawOpen = null;
    private Servo servoFlip = null;


    @Override
    public void runOpMode() {
        printMessage("Initialized");

        //wheels
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

        double x;
        double y;
        double r;


        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            clawOpen.setPosition(0);
            y = gamepad1.x?-0.75:gamepad1.b?0.75:0; //forward backward
            r = gamepad1.dpad_up?-0.75:gamepad1.dpad_down?0.75:0; //strafe left and right
            x = gamepad1.dpad_left?-0.75:gamepad1.dpad_right?0.75:0; // turning left and right
            String message = "";
            message += x;
            message += ",";
            message += y;
            message += ",";
            message += r;

            printMessage(message);

            double p1, p2, p3, p4;
            double maxval;

            p1 = x + y + r;
            p2 = x - y - r;
            p3 = x + y - r;
            p4 = x - y + r;

            maxval = max(abs(p1),abs(p2));
            maxval = max(maxval,abs(p3));
            maxval = max(maxval,abs(p4));

            if (maxval > 1) {
                p1 *= (1/maxval);
                p2 *= (1/maxval);
                p3 *= (1/maxval);
                p4 *= (1/maxval);
            }


            //Joystick Controls (Does everything)
            leftFront.setPower(p1);
            rightFront.setPower(p2);
            leftBack.setPower(p3);
            rightBack.setPower(p4);

            if (gamepad2.dpad_up){
                //lift1.setPower(.5);
                leftLift.setPower(-.25);

            }else if (gamepad2.dpad_down) {
                //lift1.setPower(-.5);
                leftLift.setPower(.25);
            }else{
                leftLift.setPower(0);
                rightLift.setPower(0);
            }
            if(gamepad2.x){
                clawOpen.setPosition(0);
            }
            if(gamepad2.b){
                clawOpen.setPosition(0.05);
            }
            //}
            if(gamepad2.y){
                servoFlip.setPosition(0.65);
            }
            if(gamepad2.a){
                servoFlip.setPosition(0.45);
            }
            if (gamepad2.dpad_up){
                //lift1.setPower(.5);
                rightLift.setPower(-.1);

            }else if (gamepad2.dpad_down) {
                //lift1.setPower(-.5);
                rightLift.setPower(.1);
            }else{
                leftLift.setPower(0);
                rightLift.setPower(0);
            }
        }
    }
    public void printMessage(String message) {
        telemetry.addData(message,"");
        telemetry.update();
    }

}