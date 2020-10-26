#include <opencv2/opencv.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/tracking.hpp>
#include <iostream>

using namespace cv;
using namespace std;

int sigma = 3;
const int max_value_H = 360/2;
const int max_value = 255;
int low_H = 0, low_S = 0, low_V = 0;
int high_H = max_value_H, high_S = max_value, high_V = max_value;
const String window_capture_name = "Video Capture";
const String window_detection_name = "Object Detection";
	
static void on_low_H_thresh_trackbar(int, void *)
{
    low_H = min(high_H-1, low_H);
    setTrackbarPos("Low H", window_detection_name, low_H);
}
static void on_high_H_thresh_trackbar(int, void *)
{
    high_H = max(high_H, low_H+1);
    setTrackbarPos("High H", window_detection_name, high_H);
}
static void on_low_S_thresh_trackbar(int, void *)
{
    low_S = min(high_S-1, low_S);
    setTrackbarPos("Low S", window_detection_name, low_S);
}
static void on_high_S_thresh_trackbar(int, void *)
{
    high_S = max(high_S, low_S+1);
    setTrackbarPos("High S", window_detection_name, high_S);
}
static void on_low_V_thresh_trackbar(int, void *)
{
    low_V = min(high_V-1, low_V);
    setTrackbarPos("Low V", window_detection_name, low_V);
}
static void on_high_V_thresh_trackbar(int, void *)
{
    high_V = max(high_V, low_V+1);
    setTrackbarPos("High V", window_detection_name, high_V);
}

int main( int argc, char** argv ) {
	
	
	int width;
	int height;
	char *p;
	

	if(argc>1){
		width=strtol(argv[1], &p, 10);
		height=strtol(argv[2], &p, 10);
	}
	else{
		width=320;
		height=320;	
	}

	bool capturing = true;
	VideoCapture cap(0);
	if( !cap.isOpened() ){
		cerr << "error opening frames source" << endl;
		return -1;
	}
	namedWindow(window_capture_name);
    	namedWindow(window_detection_name);
	resizeWindow(window_capture_name, width, height);

    	createTrackbar("Low H", window_detection_name, &low_H, max_value_H, on_low_H_thresh_trackbar);
        createTrackbar("High H", window_detection_name, &high_H, max_value_H, on_high_H_thresh_trackbar);
        createTrackbar("Low S", window_detection_name, &low_S, max_value, on_low_S_thresh_trackbar);
        createTrackbar("High S", window_detection_name, &high_S, max_value, on_high_S_thresh_trackbar);
        createTrackbar("Low V", window_detection_name, &low_V, max_value, on_low_V_thresh_trackbar);
        createTrackbar("High V", window_detection_name, &high_V, max_value, on_high_V_thresh_trackbar);

   	Mat frame, frame_HSV, frame_threshold,smoothed;
	do{
		Mat frame;
		if (cap.read( frame )) {
			int ksize = (sigma*5)|1;

        		GaussianBlur(frame, smoothed, Size(ksize, ksize), sigma, sigma);
			cvtColor(smoothed, frame_HSV, COLOR_BGR2HSV);
			inRange(frame_HSV, Scalar(low_H, low_S, low_V), Scalar(high_H, high_S, high_V), frame_threshold);
			
	putText(frame_HSV, "Low H:"+to_string(low_H)+" High H:"+to_string(high_H), Point(100,100), FONT_HERSHEY_DUPLEX, 0.5, CV_RGB(255,255,255), 2);
	putText(frame_HSV, "Low S:"+to_string(low_S)+" High S:"+to_string(high_S), Point(100,150), FONT_HERSHEY_DUPLEX, 0.5, CV_RGB(255,255,255), 2);
	putText(frame_HSV, "Low V:"+to_string(low_V)+" High V:"+to_string(high_V), Point(100,200), FONT_HERSHEY_DUPLEX, 0.5, CV_RGB(255,255,255), 2);



       			imshow(window_capture_name, frame_HSV);
        		imshow(window_detection_name, frame_threshold);
	}else {
		capturing = false;
	}
	if((waitKey(1000.0/60.0)&0x0ff) ==27) capturing =false;
	if(key == 'x' ){
	       
        Rect roi = selectROI(frame);
        Mat imCrop = frame(roi);
        imwrite("screen_shot.png", imCrop);
      }
	}while( capturing);
	return 0;
	}
