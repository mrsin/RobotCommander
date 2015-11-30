package com.klab.sin.robotcommander;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Timer timer;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;
    private InputStream bluetoothInputStream;
    private OutputStream bluetoothOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.startDiscovery();
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        bluetoothDevice = getBluetoothDevice(devices);
        bluetoothAdapter.cancelDiscovery();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        try {
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            bluetoothSocket.connect();
            bluetoothInputStream = bluetoothSocket.getInputStream();
            bluetoothOutputStream = bluetoothSocket.getOutputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(bluetoothInputStream));
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(bluetoothOutputStream));

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (reader != null) {
                            while (reader.ready()) {
                                final String text = reader.readLine();
                                Log.i("RobotCommander>> ", text);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final TextView view = (TextView) findViewById(R.id.textView);
                                        view.setText(text);
                                    }
                                });
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 100, 100);

            final Button b1 = (Button) findViewById(R.id.button);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        writer.write("C1.");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button b2 = (Button) findViewById(R.id.button2);
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        writer.write("C2.");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button b3 = (Button) findViewById(R.id.button3);
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        writer.write("C3.");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button b4 = (Button) findViewById(R.id.button4);
            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        writer.write("C4.");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button b5 = (Button) findViewById(R.id.button5);
            b5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        writer.write("C5.");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button b6 = (Button) findViewById(R.id.button6);
            b6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        writer.write("C6.");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button b7 = (Button) findViewById(R.id.button7);
            b7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        writer.write("C7.");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button b8 = (Button) findViewById(R.id.button8);
            b8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        writer.write("C8.");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button b9 = (Button) findViewById(R.id.button9);
            b9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final EditText editText = (EditText) findViewById(R.id.editText);
                        writer.write(editText.getText().toString());
                        writer.flush();
                        editText.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            if (bluetoothSocket != null) {
                try {
                    bluetoothSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        timer.cancel();
        try {
            if (bluetoothInputStream != null)
                bluetoothInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bluetoothSocket != null)
                    bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private BluetoothDevice getBluetoothDevice(Set<BluetoothDevice> bluetoothDevices) {
        final TextView view = (TextView) findViewById(R.id.textView);
        //final String mac = "98:D3:31:B1:E7:BF";
        final String mac = "98:D3:31:40:02:D8";
        for (BluetoothDevice device : bluetoothDevices) {
            Log.i("MAC-Device", device.getAddress());
            if (mac.equals(device.getAddress())) {
                view.setText("Ready.");
                return device;
            }
        }
        view.setText("Not connected!");
        return null;
    }
}
