package com.example.alex.gimages;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
    private Button scanBtn;
    private EditText urlTxt;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView3);
        //Hide Keyboard from EditText
        urlTxt = (EditText) findViewById(R.id.editText);
        urlTxt.setFocusable(false);
        urlTxt.setFocusableInTouchMode(false);

        scanBtn = (Button) this.findViewById(R.id.button2);
        final Activity activity = this;
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                if(result.getContents().endsWith("jpg")){
                    urlTxt.setText(result.getContents());
                    Picasso.with(getApplicationContext())
                            .load(result.getContents())
                            .into(imageView);
                }else{
                    urlTxt.setText("Not an image");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
