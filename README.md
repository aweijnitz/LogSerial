# Overview
This is a simple program to read from a serial device and log to a log file (or stdout). Useful for hobby projects, like reading measurements and messages from an Arduino connected to a USB port.

## Installing
- Install [RXTX](http://rxtx.qbang.org/wiki/index.php/Main_Page) 
- Clone this repository
- Compile the source (javac SerialLog.java)

## Usage
- General: - **java serial.LogSerial serial_device baudrate [-t for added timestamps] [logfile]**
- Basic example: **java serial.LogSerial /dev/cu.usbserial-A600aSkM 115200**
- To file, with time stamps **java serial.LogSerial /dev/cu.usbserial-A600aSkM 115200 -t ./logfile.txt**


## Notes and trouble-shooting
### Mac OSX
If you are on a "modern" version of OSX (64bit) you need to install a 64bit version of the rxtx binaries. 

Get a pre-compiled version here: <http://blog.iharder.net/2009/08/18/rxtx-java-6-and-librxtxserial-jnilib-on-intel-mac-os-x/>. You still need the original RXTXcomm.jar of course. Stick them both in the folder */Library/Java/Extensions*.


