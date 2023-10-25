package org.firstinspires.ftc.teamcode.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class driveTrain {
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    ElapsedTime runtime;
driveTrain(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, ElapsedTime runtime){
    this.leftFront = leftFront;
    this.rightFront = rightFront;
    this.leftBack = leftBack;
    this.rightBack = rightBack;
    this.runtime = runtime;
}
    public abstract void moveForward(int milliseconds, double power);
    public abstract void moveBackward(int milliseconds, double power);
    public abstract void turnClockwise(int milliseconds, double power);
    public abstract void turnCounterClockwise(int milliseconds, double power);
    public abstract void strafeLeft(int milliseconds, double power);
    public abstract void strafeRight(int milliseconds, double power);
}
