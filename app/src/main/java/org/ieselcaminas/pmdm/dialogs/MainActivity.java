package org.ieselcaminas.pmdm.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CharSequence[] items = { "Google", "Apple", "Microsoft" };
    boolean[] itemsChecked = new boolean [items.length];

    CharSequence[] itemsList = { "Wifi", "3G/4G" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(0).show();
            }
        });
        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(1).show();
            }
        });
        Button b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = createDialog(2);
                new Thread(new Runnable(){
                    public void run(){
                        try {
                            //---simulate doing something lengthy---
                            Thread.sleep(5000);
                            //---dismiss the dialog---
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "THE END", Toast.LENGTH_SHORT).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        Button b4 = (Button) findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog =  (ProgressDialog) createDialog(3);
                progressDialog.show();
                progressDialog.setProgress(0);
                new Thread(new Runnable(){
                    public void run(){
                        for (int i=1; i<=15; i++) {
                            try {
                                //---simulate doing something lengthy---
                                Thread.sleep(1000);
                                //---update the dialog---
                                progressDialog.incrementProgressBy((int)(100/15));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();
                    }
                }).start();
            }
        });
    }

    private Dialog createDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_launcher)
                        .setTitle("This is a dialog with some simple text...")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(getBaseContext(),
                                                "OK clicked!", Toast.LENGTH_SHORT).show();
                                        emptyCheckedItems();
                                    }
                                }
                        )
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(getBaseContext(),
                                                "Cancel clicked!", Toast.LENGTH_SHORT).show();
                                        emptyCheckedItems();
                                    }
                                }
                        )
                        .setMultiChoiceItems(items, itemsChecked,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which, boolean isChecked) {
                                        Toast.makeText(getBaseContext(),
                                                items[which] + (isChecked ? " checked!":" unchecked!"),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ).create();
            case 1:
                return new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_launcher)
                        .setTitle("This is a dialog with some simple text...")
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(getBaseContext(),
                                                "Cancel clicked!", Toast.LENGTH_SHORT).show();
                                        emptyCheckedItems();
                                    }
                                }
                        ).setItems(itemsList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(),
                                        itemsList[which],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
            case 2:
                ProgressDialog progressDialog = ProgressDialog.show(
                        MainActivity.this, "Doing something", "Please wait...", true);
                progressDialog.setCancelable(true);
                return progressDialog;
            case 3:
                progressDialog = new ProgressDialog(this);
                progressDialog.setIcon(R.drawable.ic_launcher);
                progressDialog.setTitle("Downloading files...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getBaseContext(), "OK clicked!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getBaseContext(), "Cancel clicked!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                return progressDialog;
        }
        return null;
    }

    private void emptyCheckedItems() {
        for (int i=0; i<itemsChecked.length; i++) {
            itemsChecked[i] = false;
        }
    }
}
