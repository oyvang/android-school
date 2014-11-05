package org.oyvang.gallery;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        fragment_files ff = (fragment_files) fm.findFragmentById(R.id.fragment_list_file);
        switch (item.getItemId()) {
            case R.id.next:
                ff.changeImage(ff.nextImage((ff.selectedImage)));
                break;
            case R.id.prev:
                ff.changeImage(ff.prevImage((ff.selectedImage)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
