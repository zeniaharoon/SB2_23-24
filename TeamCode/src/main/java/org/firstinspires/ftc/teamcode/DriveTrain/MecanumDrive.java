package org.firstinspires.ftc.teamcode.DriveTrain;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MecanumDrive extends DriveTrain {
    MecanumDrive(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, ElapsedTime runtime) {
        super(leftFront, rightFront, leftBack, rightBack, runtime);
    }

    @Override
    void moveForward(int milliseconds, int power) {
        double p1, p2, p3, p4;
        double maxval;
        double y;
        double x;
        double r;
        y = power;
        x = 0;
        r = 0;
        p1 = x + y + r;
        p2 = x - y - r;
        p3 = x + y - r;
        p4 = x - y + r;

        maxval = max(abs(p1), abs(p2));
        maxval = max(maxval, abs(p3));
        maxval = max(maxval, abs(p4));

        if (maxval > 1) {
            p1 *= (1 / maxval);
            p2 *= (1 / maxval);
            p3 *= (1 / maxval);
            p4 *= (1 / maxval);
        }


        //Joystick Controls (Does everything)
        leftFront.setPower(p1);
        rightFront.setPower(p2);
        leftBack.setPower(p3);
        rightBack.setPower(p4);
        double startTime = runtime.milliseconds();
        while ((runtime.milliseconds() - startTime) < milliseconds) {

        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    @Override
    void moveBackward(int milliseconds, int power) {
        double p1, p2, p3, p4;
        double maxval;
        double y;
        double x;
        double r;
        y = -power;
        x = 0;
        r = 0;
        p1 = x + y + r;
        p2 = x - y - r;
        p3 = x + y - r;
        p4 = x - y + r;

        maxval = max(abs(p1), abs(p2));
        maxval = max(maxval, abs(p3));
        maxval = max(maxval, abs(p4));

        if (maxval > 1) {
            p1 *= (1 / maxval);
            p2 *= (1 / maxval);
            p3 *= (1 / maxval);
            p4 *= (1 / maxval);
        }


        //Joystick Controls (Does everything)
        leftFront.setPower(p1);
        rightFront.setPower(p2);
        leftBack.setPower(p3);
        rightBack.setPower(p4);
        double startTime = runtime.milliseconds();
        while ((runtime.milliseconds() - startTime) < milliseconds) {

        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

    }

    @Override
    void turnClockwise(int milliseconds, int power) {
        double p1, p2, p3, p4;
        double maxval;
        double y;
        double x;
        double r;
        y = 0;
        x = 0;
        r = -power;
        p1 = x + y + r;
        p2 = x - y - r;
        p3 = x + y - r;
        p4 = x - y + r;

        maxval = max(abs(p1), abs(p2));
        maxval = max(maxval, abs(p3));
        maxval = max(maxval, abs(p4));

        if (maxval > 1) {
            p1 *= (1 / maxval);
            p2 *= (1 / maxval);
            p3 *= (1 / maxval);
            p4 *= (1 / maxval);
        }


        //Joystick Controls (Does everything)
        leftFront.setPower(p1);
        rightFront.setPower(p2);
        leftBack.setPower(p3);
        rightBack.setPower(p4);
        double startTime = runtime.milliseconds();
        while ((runtime.milliseconds() - startTime) < milliseconds) {

        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

    }

    @Override
    void turnCounterClockwise(int milliseconds, int power) {
        double p1, p2, p3, p4;
        double maxval;
        double y;
        double x;
        double r;
        y = 0;
        x = 0;
        r = power;
        p1 = x + y + r;
        p2 = x - y - r;
        p3 = x + y - r;
        p4 = x - y + r;

        maxval = max(abs(p1), abs(p2));
        maxval = max(maxval, abs(p3));
        maxval = max(maxval, abs(p4));

        if (maxval > 1) {
            p1 *= (1 / maxval);
            p2 *= (1 / maxval);
            p3 *= (1 / maxval);
            p4 *= (1 / maxval);
        }


        //Joystick Controls (Does everything)
        leftFront.setPower(p1);
        rightFront.setPower(p2);
        leftBack.setPower(p3);
        rightBack.setPower(p4);
        double startTime = runtime.milliseconds();
        while ((runtime.milliseconds() - startTime) < milliseconds) {

        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    @Override
    void strafeLeft(int milliseconds, int power) {
        double p1, p2, p3, p4;
        double maxval;
        double y;
        double x;
        double r;
        y = 0;
        x = -power;
        r = 0;
        p1 = x + y + r;
        p2 = x - y - r;
        p3 = x + y - r;
        p4 = x - y + r;

        maxval = max(abs(p1), abs(p2));
        maxval = max(maxval, abs(p3));
        maxval = max(maxval, abs(p4));

        if (maxval > 1) {
            p1 *= (1 / maxval);
            p2 *= (1 / maxval);
            p3 *= (1 / maxval);
            p4 *= (1 / maxval);
        }


        //Joystick Controls (Does everything)
        leftFront.setPower(p1);
        rightFront.setPower(p2);
        leftBack.setPower(p3);
        rightBack.setPower(p4);
        double startTime = runtime.milliseconds();
        while ((runtime.milliseconds() - startTime) < milliseconds) {

        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    @Override
    void strafeRight(int milliseconds, int power) {
        double p1, p2, p3, p4;
        double maxval;
        double y;
        double x;
        double r;
        y = 0;
        x = power;
        r = 0;
        p1 = x + y + r;
        p2 = x - y - r;
        p3 = x + y - r;
        p4 = x - y + r;

        maxval = max(abs(p1), abs(p2));
        maxval = max(maxval, abs(p3));
        maxval = max(maxval, abs(p4));

        if (maxval > 1) {
            p1 *= (1 / maxval);
            p2 *= (1 / maxval);
            p3 *= (1 / maxval);
            p4 *= (1 / maxval);
        }


        //Joystick Controls (Does everything)
        leftFront.setPower(p1);
        rightFront.setPower(p2);
        leftBack.setPower(p3);
        rightBack.setPower(p4);
        double startTime = runtime.milliseconds();
        while ((runtime.milliseconds() - startTime) < milliseconds) {

        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

    }
}
