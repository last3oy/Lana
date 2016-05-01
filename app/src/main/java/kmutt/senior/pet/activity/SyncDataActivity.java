package kmutt.senior.pet.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import kmutt.senior.pet.R;
import kmutt.senior.pet.adapter.DogProfileAdapter;
import kmutt.senior.pet.model.DogProfileInput;
import kmutt.senior.pet.service.BluetoothLeService;
import kmutt.senior.pet.util.DatabaseHelper;

public class SyncDataActivity extends AppCompatActivity {


    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private TextView mConnectionState;
    private TextView mDataField;
    private String mDeviceName;
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private DatabaseHelper db;
    private ListView lvProfile;
    private ArrayList<DogProfileInput> allProfile;
    private DogProfileAdapter mAdapter;
    private boolean mConnected = false;
    private int dogId;
    private int flag;
    private ProgressDialog progress;
    private MenuItem itemMenu;
    private boolean bConnect;

    private ArrayList<Integer> q = new ArrayList<Integer>();
    private int i = 0, m;
    private String a;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e("TAG", "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
            itemMenu.setTitle("Connect");
            bConnect = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            itemMenu.setTitle("Disconnect");
            bConnect = false;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                mDataField.setText("Connected");

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                mDataField.setText("Disconnect");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {

                m = intent.getIntExtra(BluetoothLeService.EXTRA_DATA, 0);
                if (m != 0) {
                    q.add(m);
                    i++;
                }
                if (i == 10) {
                    calulateBPM();
                }


            }
        }
    };

    private void calulateBPM() {
        unregisterReceiver(mGattUpdateReceiver);
        int sum = 0;
        for (int value : q) {
            sum += value;
        }
        sum /= q.size();
        db.createBpmvalue(dogId, sum);
        progress.dismiss();
        if (flag == 0) {
            finish();
        } else if (flag == 1) {
            Intent intent = new Intent(SyncDataActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);

        initInstance();
    }

    private void initInstance() {

        getSupportActionBar().setTitle("Sync");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        db = new DatabaseHelper(this);

        lvProfile = (ListView) findViewById(R.id.lvProfile);


        mAdapter = new DogProfileAdapter(this, allProfile);
        lvProfile.setAdapter(mAdapter);

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        flag = intent.getIntExtra("flag", -1);


        setProgressDialog();

        mDataField = (TextView) findViewById(R.id.tvDeviceName);


        lvProfile.setOnItemClickListener(profileClicked);

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        allProfile = db.getListSelectProfile();
        if (allProfile == null) {
            showAlertDialog();
        }

    }

    private void showAlertDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("You haven't dog profile pls add.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void setProgressDialog() {
        progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Syncing...");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGattUpdateReceiver != null) {
            registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
            if (mBluetoothLeService != null) {
                final boolean result = mBluetoothLeService.connect(mDeviceAddress);
                Log.d("TAG", "Connect request result=" + result);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGattUpdateReceiver.isOrderedBroadcast()) {
            unregisterReceiver(mGattUpdateReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connect_device, menu);
        itemMenu = menu.findItem(R.id.action_connect);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_connect) {
            if (bConnect) {
                itemMenu.setTitle("Connect");
                mBluetoothLeService.disconnect();
                bConnect = false;
                return true;
            } else {
                itemMenu.setTitle("Disconnect");
                mBluetoothLeService.connect(mDeviceAddress);
                bConnect = true;
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    // init filter
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    AdapterView.OnItemClickListener profileClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dogId = mAdapter.getIdProfile(position);
            mBluetoothLeService.readCustomCharacteristic();
            progress.show();
        }
    };

}