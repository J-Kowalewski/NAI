#include <opencv2/opencv.hpp>
#include <opencv2/highgui.hpp>
#include <iostream>
int main( int argc, char** argv ) {
	bool capturing = true;
	cv::VideoCapture cap(0);
	if( !cap.isOpened() ){
		std::cerr << "error opening frames source" << std::endl;
		return -1;
	}
	do{
		cv::Mat frame;
		if (cap.read( frame )) {
			cv::flip(frame, frame, 1);
			cv::imshow( "smart window", frame);
	}else {
		capturing = false;
	}
	if((cv::waitKey(1000.0/60.0)&0x0ff) ==27) capturing =false;
	}while( capturing);
	return 0;
	}
