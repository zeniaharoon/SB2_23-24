# Simple python script to capture images from a webcam
#       QR Codes
#       Simple color thresholding

import os
import cv2
import numpy as np

import image_processing as ip


# def nothing(x):
#     pass


cv2.namedWindow('image')

# create trackbars for color change
# cv2.createTrackbar('lowH', 'image', 0, 179, nothing)
# cv2.createTrackbar('highH', 'image', 179, 179, nothing)
#
# cv2.createTrackbar('lowS', 'image', 0, 255, nothing)
# cv2.createTrackbar('highS', 'image', 255, 255, nothing)
#
# cv2.createTrackbar('lowV', 'image', 0, 255, nothing)
# cv2.createTrackbar('highV', 'image', 255, 255, nothing)


# get current positions of the trackbars


CAM_NUM = ip.findCamera()

if len(CAM_NUM) < 1:
    print("Error: Unable to find capture device")
    exit()

# Assume first device
CAM_NUM = CAM_NUM[0]

cam = cv2.VideoCapture(CAM_NUM)

if not cam.read()[0]:
    print('Capture Failed')
else:
    print('Capture Not failed')

while True:
    # ilowH = cv2.getTrackbarPos('lowH', 'image')
    low = [115, 125, 13]

    # ihighH = cv2.getTrackbarPos('highH', 'image')
    high = [150, 235, 211]
    # ilowS = cv2.getTrackbarPos('lowS', 'image')
    # ihighS = cv2.getTrackbarPos('highS', 'image')
    # ilowV = cv2.getTrackbarPos('lowV', 'image')
    # ihighV = cv2.getTrackbarPos('highV', 'image')
    ret, raw = cam.read()
    output = raw
    if not ret:
        print("failed to grab frame")
        break

    # Create a window

    # convert color to hsv because it is easy to track colors in this color model
    hsv = cv2.cvtColor(raw, cv2.COLOR_BGR2HSV)
    lower_hsv = np.array(low)
    higher_hsv = np.array(high)
    # Apply the cv2.inrange method to create a mask
    thresh = cv2.inRange(hsv, lower_hsv, higher_hsv)
    # print(lower_hsv)
    # print(higher_hsv)

    # hsv = cv2.cvtColor(raw, cv2.COLOR_BGR2HSV)

    # create a binary thresholded image on hue between red and yellow
    # lower = (90,20,10)
    # upper = (255,115,100)
    # thresh = cv2.inRange(hsv, lower, upper)

    # apply morphology
    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (9, 9))
    clean = cv2.morphologyEx(thresh, cv2.MORPH_OPEN, kernel)
    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (15, 15))
    clean = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, kernel)

    # get external contours
    contours = cv2.findContours(clean, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    contours = contours[0] if len(contours) == 2 else contours[1]

    for c in contours:
        # get rotated rectangle from contour
        rot_rect = cv2.minAreaRect(c)
        box = cv2.boxPoints(rot_rect)
        box = np.int0(box)
        # draw rotated rectangle on copy of img
        cv2.drawContours(output, [box], 0, (0, 0, 0), 2)

        # cv2.imshow("test", output)

    # if k % 256 == 27:
    #     # ESC pressed
    #     print("Escape hit, closing...")
    #     break
    #
    # elif k % 256 == 32 and DO_ENABLE_SAVE_FRAMES:
    #     # SPACE pressed
    #     img_name = "opencv_frame_{}.png".format(img_counter)
    #     cv2.imwrite(img_name, raw)
    #     print("{} written!".format(img_name))
    #     img_counter += 1

cv2.waitKey(0)
cv2.destroyAllWindows()

cam.release()
