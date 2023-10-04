package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "TeleOp2223", group = "Mecanum")
public class TeleOp2223 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    //wheels
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;

    private DcMotor lift1 = null;
    private DcMotor lift2 = null;

    private Servo clawServo1 = null;



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
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);



        //attatchments
/*
       lift1 = hardwareMap.get(DcMotor.class, "lift1");
       lift2 = hardwareMap.get(DcMotor.class, "lift2");


       clawServo1 = hardwareMap.get(Servo.class, "servo1");
 */

        double x;
        double y;
        double r;

        // clawServo1.setPosition(0);
        printMessage("test 1");

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            //clawServo.setPosition(0);

            printMessage("test 2");

            x = -gamepad1.right_stick_y; //forward backward
            y = gamepad1.right_stick_x; //strafe left and right
            r = gamepad1.left_stick_x; // turning left and right
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

            //moving forward
            if (gamepad1.y){
                leftFront.setPower(1);
                rightFront.setPower(1);
                leftBack.setPower(-1);
                rightBack.setPower(1);
            }else{
                leftFront.setPower(0);
                rightFront.setPower(0);
                leftBack.setPower(0);
                rightBack.setPower(0);
            }

        //moving backward
        if (gamepad1.a){
            leftFront.setPower(-1);
            rightFront.setPower(-1);
            leftBack.setPower(1);
            rightBack.setPower(-1);
        }else{
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
        }

        //spin left
        if (gamepad1.dpad_left){
            leftFront.setPower(-1);
            rightFront.setPower(1);
            leftBack.setPower(1);
            rightBack.setPower(1);
        }else{
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
        }

        //spin right
        if (gamepad1.dpad_right){
            leftFront.setPower(1);
            rightFront.setPower(-1);
            leftBack.setPower(-1);
            rightBack.setPower(-1);
        }else{
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
        }

        //strafe left
        if (gamepad1.x){
            leftFront.setPower(-1);
            rightFront.setPower(1);
            leftBack.setPower(-1);
            rightBack.setPower(-1);
        }else{
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
        }

        //strafe right
        if (gamepad1.b){
            leftFront.setPower(1);
            rightFront.setPower(-1);
            leftBack.setPower(1);
            rightBack.setPower(1);
        }else{
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
        }

            //Joystick Everything
            leftFront.setPower(p1);
            rightFront.setPower(p2);
            leftBack.setPower(p3);
            rightBack.setPower(p4);
            //lift

        if (gamepad2.dpad_up){
            //lift1.setPower(.5);
            lift2.setPower(-.9);

        }else if (gamepad2.dpad_down) {
            //lift1.setPower(-.5);
            lift2.setPower(.9);
        }else{
             lift1.setPower(0);
            lift2.setPower(0);
        }


            //servo MOTIONS
        /*
       // double position = 0.1;
        if(gamepad2.a){
            //claw.setPower(1.5);
            //clawServo1.setPosition(0.1 + position);
            clawServo1.setPosition(1);
            clawServo1.setPosition(1);
             //telemetry.addData("-1", -1);
              //  telemetry.update();
            //clawPower = .20;
        }else if(gamepad2.b){
            clawServo1.setPosition(0.1);
            //clawPower = -.20;
        }

        */
            //clawServo1.setPower(clawpower);

        }
    }
    public void printMessage(String message) {
        telemetry.addData(message,"");
        telemetry.update();
    }

}