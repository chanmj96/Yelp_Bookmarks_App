package com.example.mattchan.bookmarksapplication;

/**
 * Created by mattchan on 1/14/17.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class Bluetooth extends AppCompatActivity{
    InputStream inputs;
    OutputStream outputs;
    public TextView out;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (bluetooth == null) {
            Toast.makeText(this, "Bluetooth NOT supported. Aborting.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!bluetooth.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
    }

    public void make_discoverable() // upon press of make discoverable button
    {
        if (bluetooth.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
        {
            Intent discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverable);
        }
    }

    public void start_search()      //upon searching button pressed
    {
        bluetooth.startDiscovery();
        out.append("\nDone with discovery...\n");
        Set<BluetoothDevice> devices = bluetooth.getBondedDevices();

        for (BluetoothDevice device : devices) {
            out.append("\nFound device: " + device.getName() + " Address: " + device.getAddress());
        }

        if(devices.size() == 0)
            return;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of device to connect with: ");
        String input = scanner.nextLine();

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //default uuid
        BluetoothSocket socket = null;

        for (BluetoothDevice device : devices) {
            if(device.getName().equals(input)) {
                try {
                    uuid = device.getUuids()[0].getUuid();
                    socket = device.createRfcommSocketToServiceRecord(uuid);
                }
                catch(NullPointerException|IOException e)
                {
                    System.out.println("\nDevice UUID failed. now trying default UUID\n");
                    try{
                        socket = device.createRfcommSocketToServiceRecord(uuid);
                    }
                    catch(IOException e2)
                    {
                        System.out.println("The default UUID failed. now aborting program\n");
                        return;
                    }
                }
            }
            else {
                System.out.println("Device was not found. Please restart and try again\n");
            }
        }

        try {
            socket.connect();
            inputs = socket.getInputStream();
            outputs = socket.getOutputStream();
            if(socket.isConnected())//while(socket.isConnected())
            {
                outputs.write("eeeee".getBytes());
                //pressReceiveButton();
                //pressSendButton();
            }
            socket.close();
        }
        catch(IOException e){System.out.println("Error: terminating connection"); return;}


    }

//    public void pressSendButton(String message){
//        //if button is pressed, then
//
//        try {
//            outputs.write(message.getBytes());
//        }
//        catch(IOException e)
//        {
//            return;
//        }
//    }
//    public void pressReceiveButton(String message){
//        //if button is pressed, then
//
//        try {
//            inputs.read(message.getBytes());
//            //export to file
//        }
//        catch(IOException e)
//        {
//            return;
//        }
//    }

}
