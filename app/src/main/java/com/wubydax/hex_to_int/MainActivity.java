package com.wubydax.hex_to_int;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnLongClickListener{
    EditText hex, hex2;
    TextView integer, smaliHex;
    ImageView colorImg, colorImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hex = (EditText) findViewById(R.id.hex);
        hex2 = (EditText) findViewById(R.id.hex2);
        integer = (TextView) findViewById(R.id.integer);
        smaliHex = (TextView) findViewById(R.id.reverseHex);
        colorImg = (ImageView) findViewById(R.id.chosenColor);
        colorImg2 = (ImageView) findViewById(R.id.chosenColor2);
        final ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int size = colorImg.getHeight();
                outline.setOval(0, 0, size, size);

            }
        };
        colorImg.setOutlineProvider(viewOutlineProvider);
        colorImg2.setOutlineProvider(viewOutlineProvider);
        colorImg.setClipToOutline(true);
        colorImg2.setClipToOutline(true);
        integer.setOnLongClickListener(this);
        smaliHex.setOnLongClickListener(this);
    }


    public void convertColor(View view) {
        String input = hex.getText().toString();
        StringBuilder sb = new StringBuilder();
        if(input.length()==3 || input.length()==4){
            for(int i=0; i<input.length(); i++){
                sb.append(input.charAt(i));
                sb.append(input.charAt(i));
            }
            input = sb.toString();


        }
        if(input.length()==6){
            input="ff"+input;
        }
        if(input.length()<3||input.length()>8||input.length()==5||input.length()==7){
            Toast.makeText(this, getString(R.string.illegal_hex_value_toast), Toast.LENGTH_SHORT).show();
        }else {
            hex.setText(input);
            int color = Color.parseColor("#" + input);
            integer.setText(String.valueOf(color));
            colorImg.setBackgroundColor(color);
        }

    }

    public void reverseColor(View view){
        String input = hex2.getText().toString();
        StringBuilder sb = new StringBuilder();
        if(input.length()==3 || input.length()==4){
            for(int i=0; i<input.length(); i++){
                sb.append(input.charAt(i));
                sb.append(input.charAt(i));
            }
            input = sb.toString();


        }
        if(input.length()==6){
            input="ff"+input;
        }
        if(input.length()<3||input.length()>8||input.length()==5||input.length()==7){
            Toast.makeText(this, getString(R.string.illegal_hex_value_toast), Toast.LENGTH_SHORT).show();
        }else {
            hex2.setText(input);
            int color = Color.parseColor("#" + input);
            int decimalAlpha = Integer.parseInt(input.substring(0, 2), 16);
            String s;
            if (decimalAlpha>=128){
                s = "-0x" + Integer.toHexString((color*-1));
            }else{
                s = "0x"+Integer.toHexString(color);
            }



            colorImg2.setBackgroundColor(color);

            smaliHex.setText(s);
        }

    }


    @Override
    public boolean onLongClick(View v) {
        ClipboardManager cbm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        int id = v.getId();
        TextView tv = (TextView) findViewById(id);
        ClipData cd = ClipData.newPlainText("copied", tv.getText().toString());
        cbm.setPrimaryClip(cd);
        Toast.makeText(this, tv.getText().toString() + getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show();

        return true;
    }
}
