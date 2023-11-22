package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Lift_Claw_Test_11_18_23", group = "Mecanum")
public class Lift_Claw_Test_11_18_23 extends LinearOpMode {
    private Servo clawOpen = null;
    private Servo servoFlip = null;
    private DcMotor liftMotor;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        printMessage("Initialized");

        clawOpen = hardwareMap.get(Servo.class, "clawOpen");
        servoFlip = hardwareMap.get(Servo.class, "servoFlip");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        clawOpen.setPosition(0);

        while (opModeIsActive()) {
            double liftPower = 0.5;

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Lift Power", liftPower);
            telemetry.update();

            if (gamepad2.b) {
                clawOpen.setPosition(0.1);
            }
            if (gamepad2.a) {
                clawOpen.setPosition(0.35);
            }

            if(gamepad2.x){
                clawOpen.setPosition(0.1);
                sleep(250);
                servoFlip.setPosition(0.5);
            }

            if(gamepad2.y){
                servoFlip.setPosition(0.2);
            }
            if ((gamepad2.left_stick_y>0.1)) {
                liftMotor.setDirection(DcMotor.Direction.FORWARD);
                liftMotor.setPower(liftPower);
                sleep(1);
                liftMotor.setPower(0);

            }
            if ((gamepad2.left_stick_y<-0.1)) {
                liftMotor.setDirection(DcMotor.Direction.REVERSE);
                liftMotor.setPower(liftPower);
                sleep(1);
                liftMotor.setPower(0);
            }

        }
    }

    public void printMessage(String message){
        telemetry.addData(message, "");
        telemetry.update();
    }

}
