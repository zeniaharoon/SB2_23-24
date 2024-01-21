package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.List;


public class CSVisionProcessor extends BlocksOpModeCompanion implements VisionProcessor {
    StartingPosition selection = StartingPosition.NONE;
    FtcDashboard ftcDashboard;

    Telemetry telemetry;
    CameraStreamSource camera;




    final int leftTh = 425;
    final int rightTh = 850;
    final int upperTh = 200;
    final int lowerTh = 500;

    final int width = 1280;
    final int height = 720;

    public StartingPosition getPosition() {
        return selection;
    }

    public int getIntPosition() {
        StartingPosition pos = selection;

        if (pos == StartingPosition.LEFT) {
            return 1;
        } else if (pos == StartingPosition.CENTER) {
            return 2;
        } else if (pos == StartingPosition.RIGHT) {
            return 3;
        }

        return 0;
    }

    public CSVisionProcessor(Telemetry telemetry, CameraStreamSource camera) {
        this.telemetry = telemetry;
        this.camera = camera;

    }


    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        ftcDashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, ftcDashboard.getTelemetry());
        ftcDashboard.startCameraStream(camera, 0);
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        List<Mat> mats = new ArrayList<Mat>();
        Core.split(frame, mats);

        Mat hsvMat = new Mat();
        Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV);

        double ilowH = 106;
        double ilowS = 124;
        double ilowV = 0;

        double ihighH = 134;
        double ihighS = 255;
        double ihighV = 191;
        Mat threshMat = new Mat();
        Scalar lower_hsv = new Scalar(ilowH, ilowS, ilowV);
        Scalar higher_hsv = new Scalar(ihighH, ihighS, ihighV);
        Core.inRange(hsvMat, lower_hsv, higher_hsv, threshMat);

        //apply morphology
//        Size kernel1 = new Size(9, 9);
//        Size kernel2 = new Size(15, 15);
//        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, kernel1);
//        Mat cleanMat = new Mat();
//        Imgproc.morphologyEx(threshMat, cleanMat, Imgproc.MORPH_OPEN, kernel);
//        kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, kernel2);
//        Imgproc.morphologyEx(cleanMat, cleanMat, Imgproc.MORPH_CLOSE, kernel);

        telemetry.addData("clean mat size cols", hsvMat.cols());
        telemetry.addData("clean mat size rows", hsvMat.rows());

        int leftCounter = 0;
        int rightCounter = 0;
        int centerCounter = 0;
        for (int i = 0; i < threshMat.cols(); i++) {
            for (int j = 0; j < threshMat.rows(); j++) {
                if (i > 0 && i < leftTh && j > upperTh && j < lowerTh && threshMat.get(j, i) != null) {
                    leftCounter += threshMat.get(j, i)[0];
                }
                else if (i > leftTh && i < rightTh && j > upperTh && j < lowerTh && threshMat.get(j, i) != null) {
                    centerCounter += threshMat.get(j, i)[0];
                }
                else if (j > upperTh && j < lowerTh && threshMat.get(j, i) != null) {
                    rightCounter += threshMat.get(j, i)[0];
                }
            }
        }
        telemetry.addData("Left Counter", leftCounter);
        telemetry.addData("Center Counter", centerCounter);
        telemetry.addData("Right Counter", rightCounter);
        telemetry.update();
        if (leftCounter > centerCounter && leftCounter > rightCounter) {
            selection = StartingPosition.LEFT;
            return selection;
        } else if (rightCounter > centerCounter && rightCounter > leftCounter) {
            selection = StartingPosition.RIGHT;
            return selection;
        } else if (centerCounter > leftCounter && centerCounter > rightCounter) {
            selection = StartingPosition.CENTER;
            return selection;
        } else {
            selection = StartingPosition.NONE;
            return selection;
        }

    }


    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight,
                            float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint selectedPaint = new Paint();
        selectedPaint.setColor(Color.GREEN);
        selectedPaint.setStyle(Paint.Style.STROKE);
        selectedPaint.setStrokeWidth(scaleCanvasDensity * 4);

        Paint nonSelected = new Paint();
        nonSelected.setStrokeWidth(scaleCanvasDensity * 4);
        nonSelected.setStyle(Paint.Style.STROKE);
        nonSelected.setColor(Color.RED);

        android.graphics.Rect drawRectangleLeft = new android.graphics.Rect(0, upperTh, leftTh, lowerTh);
        android.graphics.Rect drawRectangleMiddle = new android.graphics.Rect(leftTh, upperTh, rightTh, lowerTh);
        android.graphics.Rect drawRectangleRight = new android.graphics.Rect(rightTh, upperTh, width, lowerTh);

        selection = (StartingPosition) userContext;

        switch (selection) {
            case LEFT:
                canvas.drawRect(drawRectangleLeft, selectedPaint);
                canvas.drawRect(drawRectangleMiddle, nonSelected);
                canvas.drawRect(drawRectangleRight, nonSelected);
                break;

            case RIGHT:
                canvas.drawRect(drawRectangleLeft, nonSelected);
                canvas.drawRect(drawRectangleMiddle, nonSelected);
                canvas.drawRect(drawRectangleRight, selectedPaint);
                break;
            case CENTER:
                canvas.drawRect(drawRectangleLeft, nonSelected);
                canvas.drawRect(drawRectangleMiddle, selectedPaint);
                canvas.drawRect(drawRectangleRight, nonSelected);
                break;
            case NONE:
                canvas.drawRect(drawRectangleLeft, nonSelected);
                canvas.drawRect(drawRectangleMiddle, nonSelected);
                canvas.drawRect(drawRectangleRight, nonSelected);
                break;

        }

    }

    public StartingPosition getStartingPosition() {
        return selection;
    }

    public enum StartingPosition {
        NONE,
        LEFT,
        RIGHT,
        CENTER
    }


}
