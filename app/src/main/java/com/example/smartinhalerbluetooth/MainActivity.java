package com.example.smartinhalerbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {

    TextView textViewData;
    Button openBtn,closeBtn,reminderBtn;
    ListView listview;
    ArrayAdapter listadapter;
    BluetoothDevice[] btArray;
    BluetoothDevice clickedbyuser;

    BluetoothSocket mmSocket;

    BluetoothAdapter mBluetoothAdapter;
    Intent btEnablingIntent;
    int requestCodeForBluetooth=1;

    static final  int STATE_LISTENING = 1;
    static final  int STATE_CONNECTING = 2;
    static final  int STATE_CONNECTED = 3;
    static final  int STATE_CONNECTION_FAILED = 4;
    static final  int STATE_MESSAGE_RECEIVED = 5;

    String APP_NAME = "SmartInhaler";
    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hello rahul

        intilizeView();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch(msg.what){

                case STATE_LISTENING:
                    textViewData.setText("Searching for devices");
                    break;

                case STATE_CONNECTING:
                    textViewData.setText("connecting to the device");
                    break;

                case STATE_CONNECTED:
                    textViewData.setText("connection successful!");
                    break;

                case STATE_CONNECTION_FAILED:
                    textViewData.setText("connection failed!");
                    break;

                case STATE_MESSAGE_RECEIVED:

                    byte[] readbuffer  = (byte[]) msg.obj;
                    String tempmsg = new String(readbuffer,0,msg.arg1);
                    textViewData.setText(tempmsg);
                    break;

            }


            return true;
        }
    });

    private void intilizeView() {
        textViewData = (TextView)findViewById(R.id.data);
        openBtn = (Button) findViewById(R.id.search_button);
        closeBtn =(Button)findViewById(R.id.close_button);
        reminderBtn =(Button)findViewById(R.id.reminderBtn);
        listview =(ListView)findViewById(R.id.listview);




        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);


        //Open Button
        openBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
               bluetoothTurnOn();
            }
        });

        //Close button
        closeBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(mBluetoothAdapter!=null)
                mBluetoothAdapter.disable();
            }
        });



        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Reminder.class);
                startActivity(i);
            }
        });



        //listview bonded device click listener

      listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              Toast.makeText(MainActivity.this, "Connecting to "+btArray[position].getName(), Toast.LENGTH_SHORT).show();
              clickedbyuser = btArray[position];

                  Thread t = new Thread(new Runnable() {
                      @Override
                      public void run() {
                          try {
                              openBT();
                          } catch (IOException e) {
                              e.printStackTrace();
                          }

                      }
                  });
              t.start();
          }
      });

    }

    private void bluetoothTurnOn() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter==null){

            textViewData.setText("Bluetooth not supported!");
        }
        else if(!mBluetoothAdapter.isEnabled()){

            startActivityForResult(btEnablingIntent,requestCodeForBluetooth);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==requestCodeForBluetooth){
           {
                if (resultCode==RESULT_OK){

                    textViewData.setText("Bluetooth Enabled! Showing Available Devices!");
                    showAvailableDevices();

//                    ServerClass serverclass = new ServerClass();
//                    serverclass.start();



                }else if(resultCode==RESULT_CANCELED){

                    textViewData.setText("Bluetooth permission denied!");
                }
            }
        }
    }

    private void showAvailableDevices() {

        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        btArray = new BluetoothDevice[devices.size()];
        String[] devicelist = new String[devices.size()];
        int index =0;

        if(devices.size()>0) {
            for (BluetoothDevice bd : devices) {

                btArray[index]= bd;
                devicelist[index]=bd.getName();
                index++;

            }
        }
        listadapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,devicelist);
        listview.setAdapter(listadapter);

        }


     private class ServerClass extends Thread{

         private BluetoothServerSocket serverSocket;

         public ServerClass(){

             try {
                 serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME,MY_UUID);
             } catch (IOException e) {
                 e.printStackTrace();
             }

         }

         public void run(){

             BluetoothSocket socket =null;

             while(socket==null){

                 try {

                     Message message = Message.obtain();
                     message.what= STATE_CONNECTING;
                     handler.sendMessage(message);
                     socket = serverSocket.accept();
                 } catch (IOException e) {
                     e.printStackTrace();
                     Message message = Message.obtain();
                     message.what= STATE_CONNECTION_FAILED;
                     handler.sendMessage(message);
                     break;
                 }

                 if(socket!=null){

                     Message message = Message.obtain();
                     message.what= STATE_CONNECTED;
                     handler.sendMessage(message);
                //code for sending and receiving from smart inhaler HC-05 will be written here
                    break;
                 }

             }

         }


     }


     private class ClientClass extends Thread{

         private BluetoothSocket socket;
         private BluetoothDevice dvc;

         public ClientClass(BluetoothDevice dvc){
             this.dvc = dvc;

             try {
                 socket = dvc.createRfcommSocketToServiceRecord(MY_UUID);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }

         public void run(){

             try {
                 socket.connect();

                 Message message = Message.obtain();
                 message.what= STATE_CONNECTED;
                 handler.sendMessage(message);

             } catch (IOException e) {
                 e.printStackTrace();
                 Message message = Message.obtain();
                 message.what= STATE_CONNECTION_FAILED;
                 handler.sendMessage(message);
             }

         }
     }

     private class SendReceive extends Thread {

         private final BluetoothSocket bluetoothsocket;
         private final InputStream inputstream;
         private final OutputStream outputstream;

         public  SendReceive(BluetoothSocket bs){

             this.bluetoothsocket=bs;

             InputStream tempInput = null;
             OutputStream tempOutput = null;

             try {
                 tempInput = bluetoothsocket.getInputStream();
                 tempOutput = bluetoothsocket.getOutputStream();

             } catch (IOException e) {
                 e.printStackTrace();
             }

             inputstream = tempInput;
             outputstream =tempOutput;

         }

         public void run(){

             byte[] buffer = new byte[1024];
             int bytesize;

             while(true){

                 try {
                     bytesize=inputstream.read(buffer);

                     handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytesize,-1,buffer).sendToTarget();

                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
     }

    public void openBT() throws IOException
    {
        mmSocket = clickedbyuser.createRfcommSocketToServiceRecord(MY_UUID);
        Log.d("socketopenBt","before socket");
        mmSocket.connect();
        Log.d("socketopenBt","after socket");

        InputStream mmInputStream = mmSocket.getInputStream();
        OutputStream mmOutputStream = mmSocket.getOutputStream();



        byte[] buffer = new byte[1024];
        byte[] senderbuffer ;
        int bytesize;

        while(true){

            try {
                bytesize=mmInputStream.read(buffer);

                //senderbuffer ="1000".getBytes();

                handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytesize,-1,buffer).sendToTarget();

               // mmOutputStream.write(senderbuffer);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}



