package com.example.snake_coursework;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class RecordActivity extends AppCompatActivity {
    private TextView highScoreTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        highScoreTextView = findViewById(R.id.highScoreTextView);
        // Отримати рекорд з файлу
        int highScore = readHighScoreFromFile();
        // Вивести рекорд на екран
        highScoreTextView.setText(String.valueOf(highScore));
    }
    private int readHighScoreFromFile() {
        FileInputStream fis = null;
        BufferedReader reader = null;
        try {
            fis = openFileInput("record.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}