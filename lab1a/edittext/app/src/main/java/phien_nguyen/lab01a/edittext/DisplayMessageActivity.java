package phien_nguyen.lab01a.edittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;




import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
}