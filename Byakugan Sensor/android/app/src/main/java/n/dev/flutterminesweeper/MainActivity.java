package n.dev.flutterminesweeper;

import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import android.util.Log;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
public class MainActivity extends FlutterActivity {
  BluetoothSocket mmSocket;
  BluetoothDevice mmDevice = null;
    final byte delimiter = 33;
    int readBufferPosition = 0;

    public void sendBtMsg(String msg2send){
        //UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"); //Standard SerialPortService ID
        //BluetoothDevice mydevice = mBluetoothAdapter.getRemoteDevice();
        try {

            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);

            if (!mmSocket.isConnected()) {
               try {
                    mmSocket.connect();
                    Log.e("First try", "Connected");
                } catch (IOException e) {
                    Log.e("", e.getMessage());
                    try {
                        Log.e("", "trying fallback...");

                        mmSocket = (BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(mmDevice, 1);
                        mmSocket.connect();

                        Log.e("Second try", "Connected");
                    } catch (Exception e2) {
                        Log.e("", "Couldn't establish Bluetooth connection!");
                    }
                }

                String msg = msg2send;
                //msg += "\n";
                InputStream tmpIn = null;
                OutputStream tmpOut = null;
                try {
                    tmpIn = mmSocket.getInputStream();
                    tmpOut = mmSocket.getOutputStream();
                } catch (IOException e) {
                    Log.e("!!!!!!!!!!!!!", "temp sockets not created", e);
                }
                if (tmpIn!=null)
                    if(tmpOut!=null)
                        Log.e("Houston","We have a problem. Both are not null");
                //OutputStream mmOutputStream = mmSocket.getOutputStream();
                //mmOutputStream.write(msg.getBytes());
                byte[] buffer = new byte[1024];
                int bytes;
                bytes=mmSocket.getInputStream().read(buffer);


                String readMessage = new String(buffer, 0, bytes);
                Log.d("Hope!!!", "Message :: "+readMessage);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("damn","Couldnt create rdcommsockettoservicerecord");
            e.printStackTrace();
        }

    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      //setContentView(R.layout.activity_main);
      Context context = this;
      GeneratedPluginRegistrant.registerWith(this);
      new AlertDialog.Builder(context)
              .setTitle("Delete entry")
              .setMessage("Are you sure you want to delete this entry?")

              // Specifying a listener allows you to take an action before dismissing the dialog.
              // The dialog is automatically dismissed when a dialog button is clicked.
              .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                      // Continue with delete operation
                      Log.i("come on","Pleassse");
                      // Pasted the code here from  oncreate
                      Log.i("yoo","Bro whats the scene");
                      final Handler handler = new Handler();

    /*new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
            new MethodCallHandler() {
              @Override
              public void onMethodCall(MethodCall call, Result result) {
                // TODO
              }
            });*/

                      //Bluetooth code from DVasallo
                      BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                      Log.v("Reached","here");
                      final class workerThread implements Runnable {

                          private String btMsg;

                          public workerThread(String msg) {
                              btMsg = msg;
                              Log.d("MyApp",msg);
                          }

                          public void run()
                          {
                              sendBtMsg(btMsg);
                              while(!Thread.currentThread().isInterrupted())
                              {
                                  int bytesAvailable;
                                  boolean workDone = false;

                                  try {
                                      final InputStream mmInputStream;
                                      mmInputStream = mmSocket.getInputStream();
                                      bytesAvailable = mmInputStream.available();
                                      if(bytesAvailable > 0)
                                      {

                                          byte[] packetBytes = new byte[bytesAvailable];
                                          Log.e("Aquarium recv bt","bytes available");
                                          byte[] readBuffer = new byte[1024];
                                          mmInputStream.read(packetBytes);

                                          for(int i=0;i<bytesAvailable;i++)
                                          {
                                              byte b = packetBytes[i];
                                              if(b == delimiter)
                                              {
                                                  byte[] encodedBytes = new byte[readBufferPosition];
                                                  System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                                  final String data = new String(encodedBytes, "US-ASCII");
                                                  readBufferPosition = 0;

                                                  //The variable data now contains our full command
                                                  handler.post(new Runnable()
                                                  {
                                                      public void run()
                                                      {
                                                          Log.v("function inside handler",data);
                                                          Log.v("function inside handler","!!!!! WOHHOHOOOOO!!!!");
                                                      }
                                                  });

                                                  workDone = true;
                                                  break;


                                              }
                                              else
                                              {
                                                  readBuffer[readBufferPosition++] = b;
                                              }
                                          }

                                          if (workDone == true){
                                              mmSocket.close();
                                              break;
                                          }

                                      }
                                  } catch (IOException e) {
                                      // TODO Auto-generated catch block
                                      e.printStackTrace();
                                  }

                              }
                          }
                      };


                      if(!mBluetoothAdapter.isEnabled())
                      {
                          Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                          startActivityForResult(enableBluetooth, 0);
                      }
                      Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                      if(pairedDevices.size() > 0)
                      {
                          for(BluetoothDevice device : pairedDevices)
                          {
                              Log.v("Bluetooth device name",device.getName());
                              if(device.getName().equals("raspberrypi")) //Note, you will need to change this to match the name of your device
                              {
                                  Log.e("Aquarium",device.getAddress());
                                  mmDevice = device;
                                  break;
                              }
                          }
                      }

                      //end of codepast
                      new Thread(new workerThread("temp")).start();

                  }
              })

              // A null listener allows the button to dismiss the dialog and take no further action.
              .setNegativeButton(android.R.string.no, null)
              .setIcon(android.R.drawable.ic_dialog_alert)
              .show();

  }


}
