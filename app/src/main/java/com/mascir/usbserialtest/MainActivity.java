package com.mascir.usbserialtest;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.felhr.usbserial.SerialPortCallback;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    UsbDevice device;
    UsbDeviceConnection usbConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean supported = UsbSerialDevice.isSupported(device);
        if(supported) {
            UsbSerialDevice serial = UsbSerialDevice.createUsbSerialDevice(device, usbConnection);

            serial.open();
            serial.setBaudRate(9600);
            serial.setDataBits(UsbSerialInterface.DATA_BITS_8);
            serial.setDataBits(UsbSerialInterface.STOP_BITS_1);
            serial.setParity(UsbSerialInterface.PARITY_NONE);

            if(serial.isOpen()){
                Log.i("MAIN", "Port is open, closing...");
                serial.close();
            }
            serial.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);

            /**
             Values:
             UsbSerialInterface.FLOW_CONTROL_OFF
             UsbSerialInterface.FLOW_CONTROL_RTS_CTS
             UsbSerialInterface.FLOW_CONTROL_DSR_DTR
             **/
            serial.setFlowControl(UsbSerialInterface.FLOW_CONTROL_RTS_CTS);

            serial.read(mCallback);
            //serial.write("DATA".getBytes());
            //serial.setRTS(true); // Raised
            //serial.setRTS(false); // Not Raised
            //serial.setDTR(true); // Raised
            //serial.setDTR(false); // Not Raised
            serial.close();
        }

    }

    private final UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {

        @Override
        public void onReceivedData(byte[] arg0)
        {
            // Code here :)
            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_LONG).show();
        }

    };
}