package phiennguyen.lab1b.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements HeadlineFragment.OnHeadlineSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onArticleSelected(int position) {

        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.news_fragment);
        newsFragment.updateArticleView(position);
    }
}