package net.zygotelabs.locker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }

    public void onCheckboxClicked(View view) {
        getMainFragment().onCheckBoxClicked(((CheckBox) view).isChecked());
    }

    public void onSafeModeCheckboxChecked(View view) {
        getMainFragment().onSafeModeCheckBoxClicked(((CheckBox) view).isChecked());
    }

    public void enableProtection(View view) {
        getMainFragment().toggleLockProtection();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private MainFragment getMainFragment() {
        return (MainFragment) getSupportFragmentManager().findFragmentById(R.id.container);
    }
}
