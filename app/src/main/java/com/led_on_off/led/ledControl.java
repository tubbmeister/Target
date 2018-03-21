package com.led_on_off.led;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

import static java.lang.Thread.sleep;


public class ledControl extends ActionBarActivity {

   // Button btnOn, btnOff, btnDis;
    ImageButton On, Off, Discnt, Abt;

    String address = null;
    EditText editText,editText2;
    String timeUp, timeDown;
    int timer;
    private ProgressDialog progress;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
    RadioButton radioButton5,radioButton6;
    double units = 1;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the ledControl
        setContentView(R.layout.activity_led_control);

        //START OF MY CODE!!

        //addListenerOnChkIos();


        //call the widgets
        On = (ImageButton)findViewById(R.id.on);
        radioButton5 = (RadioButton)findViewById(R.id.radioButton5);
        radioButton6 = (RadioButton)findViewById(R.id.radioButton6);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox)findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox)findViewById(R.id.checkBox4);
        Off = (ImageButton)findViewById(R.id.off);
        Discnt = (ImageButton)findViewById(R.id.discnt);
        Abt = (ImageButton)findViewById(R.id.abt);

        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.setText("", TextView.BufferType.EDITABLE); //clears text on press
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);




                    //================ Hide Virtual Key Board When  Clicking==================//


//======== Hide Virtual Keyboard =====================//

                }
            }
        });
        final EditText editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText2.setText("", TextView.BufferType.EDITABLE); //clears text on press
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);




                    //================ Hide Virtual Key Board When  Clicking==================//


//======== Hide Virtual Keyboard =====================//

                }
            }

        });
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);




        //================ Hide Virtual Key Board When  Clicking==================//


//======== Hide Virtual Keyboard =====================//


        new ConnectBT().execute(); //Call the class to connect

        //commands to be sent to bluetooth
        On.setOnClickListener(new View.OnClickListener() //run auto programme
        {
            @Override
            public void onClick(View v)
            {if (radioButton5.isChecked()){
                //On.setVisibility(View.VISIBLE);
                timeUp=editText.getText().toString();
                timer= Integer.parseInt(timeUp); // convert string to int
                timer=(timer * 1000);
                turnOnLed();

                try {

                    Thread.sleep(timer);

                } catch (InterruptedException ex) {

                    System.out.println("Error! :(");

                }
            }
                turnOffLed();      //method to turn off

            }
        });
       // Target.setOnClickListener(new View.OnClickListener()
       // {
      //      @Override
      //      public void onClick(View v)
      //      {
      //          turnOnLed();      //method to turn on
      //      }
     //   });
        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                turnOffLed();   //method to turn off
            }
        });

        Discnt.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Disconnect(); //close connection
        }
    });
        checkBox1.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            checkBox1(); //close connection
        }
    });

        checkBox2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkBox2(); //close connection
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkBox3(); //close connection
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkBox4(); //close connection
            }
        });


    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    private void turnOffLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("0".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("1".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void checkBox1() {

        //checkBox1 = (CheckBox) findViewById(R.id.checkBox);

        //  checkBox1.setOnClickListener(new View.OnClickListener() {
        if (btSocket!=null)
        {

            try {
                if (checkBox1.isChecked()) {
                    btSocket.getOutputStream().write("2".toString().getBytes()); // select target3
                } else {
                    btSocket.getOutputStream().write("3".toString().getBytes()); // select target3
                }
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }


    }


    private void checkBox2() {

        //checkBox1 = (CheckBox) findViewById(R.id.checkBox);

        //  checkBox1.setOnClickListener(new View.OnClickListener() {
        if (btSocket!=null)
        {
            try {
                if (checkBox2.isChecked()) {
                    btSocket.getOutputStream().write("4".toString().getBytes()); // select target2
                } else {
                    btSocket.getOutputStream().write("5".toString().getBytes()); // select target2
                }
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }


    }

    private void checkBox3() {

        //checkBox1 = (CheckBox) findViewById(R.id.checkBox);

        //  checkBox1.setOnClickListener(new View.OnClickListener() {
        if (btSocket!=null)
        {
            try {
                if (checkBox3.isChecked()) {
                    btSocket.getOutputStream().write("6".toString().getBytes()); // select target3
                } else {
                    btSocket.getOutputStream().write("7".toString().getBytes()); // select target3
                }
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }


    }

    private void checkBox4() {

        //checkBox1 = (CheckBox) findViewById(R.id.checkBox);

        //  checkBox1.setOnClickListener(new View.OnClickListener() {
        if (btSocket!=null)
        {
            try {
                if (checkBox4.isChecked()) {
                    btSocket.getOutputStream().write("1".toString().getBytes()); // select target2
                } else {
                    btSocket.getOutputStream().write("0".toString().getBytes()); // select target2
                }
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }


    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    public  void about(View v)
    {
        if(v.getId() == R.id.abt)
        {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                 myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                 BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                 btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                 btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }







        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }}