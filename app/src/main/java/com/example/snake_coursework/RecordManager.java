package com.example.snake_coursework;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
public class RecordManager {
    private static final String FILE_NAME = "record.txt";
    public static void saveRecord(Context context, int record) {
        int currentRecord = loadRecord(context);
        if (record > currentRecord) {
            File file = new File(context.getFilesDir(), FILE_NAME);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(String.valueOf(record).getBytes());
                Intent intent = new Intent(context, RecordActivity.class);
                intent.putExtra("highScore", record);
            } catch (IOException e) {
                Log.e("RecordManager", "Error saving record: " +
                        e.getMessage());
            }
        }
    }
    public static int loadRecord(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new
                    FileReader(file))) {
                String content = reader.readLine();
                return Integer.parseInt(content.trim());
            } catch (IOException e) {
                Log.e("RecordManager", "Error loading record: " +
                        e.getMessage());
            }
        }
        return 0;
    }

}
