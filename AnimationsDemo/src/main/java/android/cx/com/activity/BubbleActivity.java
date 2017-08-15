package android.cx.com.activity;

import android.cx.com.list_card.R;
import android.cx.com.paopao.RelativeLayoutWithBubble;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BubbleActivity extends AppCompatActivity {

    RelativeLayoutWithBubble relativeLayoutWithBubble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        relativeLayoutWithBubble = (RelativeLayoutWithBubble)findViewById(R.id.bubbleView);
        relativeLayoutWithBubble.attachBubbleToParentView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
