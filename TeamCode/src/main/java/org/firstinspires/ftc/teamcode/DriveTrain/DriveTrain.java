package org.firstinspires.ftc.teamcode.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class DriveTrain{
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    ElapsedTime runtime;
DriveTrain(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, ElapsedTime runtime){
    this.leftFront = leftFront;
    this.rightFront = rightFront;
    this.leftBack = leftBack;
    this.rightBack = rightBack;
    this.runtime = runtime;
}
    abstract void moveForward(int milliseconds, int power);
    abstract void moveBackward(int milliseconds, int power);
    abstract void turnClockwise(int milliseconds, int power);
    abstract void turnCounterClockwise(int milliseconds, int power);
    abstract void strafeLeft(int milliseconds, int power);
    abstract void strafeRight(int milliseconds, int power);
}
