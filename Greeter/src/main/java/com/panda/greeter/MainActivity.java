package com.panda.greeter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView greetingView;
    private Greeter greeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greetingView = (TextView) findViewById(R.id.greeting);
        greeter = new Greeter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void greetToWorld(View view) {
        greetingView.setText(greeter.greetToWorld());
    }

    public void greetToPerson(View view) {
        EditText personNameView = (EditText) findViewById(R.id.personName);
        String name = personNameView.getText().toString();
        Person person = new Person(name);

        greetingView.setText(greeter.greetToPerson(person));
    }
}
