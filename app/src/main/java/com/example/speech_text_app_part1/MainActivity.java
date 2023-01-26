package com.example.speech_text_app_part1;

import android.content.ActivityNotFoundException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final int SPEECH_TO_TEXT_REQUEST_CODE = 1;
    private EditText speechToTextEditText;
    private EditText textToSpeechEditText;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speechToTextEditText = findViewById(R.id.etSpeechToText);
        textToSpeechEditText = findViewById(R.id.etTextToSpeech);

        /* start of the onlcik method starts here function part 1 */
        Button speechToTextButton = findViewById(R.id.btnSpeechToText);
        speechToTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechToText();
            }
        });
        /* ends of the onclick method ends here function part 1 */

        /* start of the function happens here part 6*/
        Button textToSpeechButton = findViewById(R.id.btnTextToSpeech);
        textToSpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = textToSpeechEditText.getText().toString();
                if (!text.isEmpty()) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                } else {

                    Toast.makeText(MainActivity.this, "Please enter text to convert to speech", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /* end of this function here part 6*/

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

    }/* its is the bracket for oncreate() function end */

    /* start of start speech to text starts from this part function part 2*/
    private void startSpeechToText() {
        // Create an intent for speech recognition
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");

        // Start the intent
        try {
            startActivityForResult(intent, SPEECH_TO_TEXT_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Speech recognition not supported", Toast.LENGTH_SHORT).show();
        }
    }
    /* end of the start speech to text ends here function part2*/

    /* start of on activity result function starts form here function part 3 */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_TO_TEXT_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            speechToTextEditText.setText(spokenText);
        }
    }
    /*ends of the function of on activity result came to end here function part 3*/

    /* start of the function start text to speech from here function part 4*/


    /*start of on destory came here part5*/
    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
    /* end of the ondestroy function have to be done here function part 5*/


}/* public class end bracket is this */






