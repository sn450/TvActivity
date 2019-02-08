package sample.tatastrive.snehal.tvactivity;

import android.content.Intent;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView voiceInput, speakButton;
    public final static int RQS_SPEECH = 100;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private int STORAGE_PERMISSION_CODE = 27;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RQS_SPEECH:
                if (requestCode == RESULT_OK && null != data) {
                    ArrayList<String> strings = data.getStringArrayListExtra(RecognizerIntent.EXTRA_MAX_RESULTS);
                    voiceInput.setText(strings.get(0));
                    zindagikorakagaj(voiceInput.getText().toString());
                }
                break;
        }
    }

    private void zindagikorakagaj(String s) {
        calendar = Calendar.getInstance();// using for timestamp
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        String systemtimestamp = simpleDateFormat.format(calendar.getTime());
        systemtimestamp = "snehal" + systemtimestamp + ".txt";
        Toast.makeText(this, "filename" + systemtimestamp, Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        File zindagikitab = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File teriyaad = new File(zindagikitab, systemtimestamp);
        dopalkijindgani(teriyaad, s);
    }

    private void dopalkijindgani(File teriyaad, String s) {
        FileOutputStream fileOutputStream=null;
        try  {
            fileOutputStream = new FileOutputStream(teriyaad);
            fileOutputStream.write(s.getBytes());
            Toast.makeText(this, "Done"+teriyaad.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voiceInput = findViewById(R.id.btnSpeak);
        speakButton = findViewById(R.id.voiceInput);
        voiceInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hie", Toast.LENGTH_SHORT).show();
                najarkesamanejigarkepass();
            }

            private void najarkesamanejigarkepass() {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Sunn rahha hai na tu..");
                startActivityForResult(intent, RQS_SPEECH);

            }


        });

    }
}
