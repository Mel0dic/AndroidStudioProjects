package com.bgrummitt.notes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private List<Note> notes = new ArrayList<>();
    private ListAdapter mListAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView =  findViewById(R.id.list);

        // Set FAB and then set on click listener to create new note
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote();
            }
        });

        // Create the list adapter and set the recycler views adapter to the created list adapter
        mListAdapter = new ListAdapter(this, notes);
        mRecyclerView.setAdapter(mListAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // When at end of list give half oval show still pulling
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(mListAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        // Make it so fab disappears when scrolling down then reappears when scrolling back up
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                // If the rate of change of y > 0 hide else show
                if(dy > 0){
                    fab.hide();
                } else{
                    fab.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newNote(){
        // Build the pop up for the new note
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        // Create a new view with the note layout
        View mView = getLayoutInflater().inflate(R.layout.dialog_new_note, null);

        // Get the edit elements from the view
        final EditText mSubject = mView.findViewById(R.id.subjectEditText);
        final EditText mNotes = mView.findViewById(R.id.mainNotesEditText);
        Button mSaveButton = mView.findViewById(R.id.saveButton);

        // Build the dialog with the view
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        // Add the on click listener so when the sections have been filled the note is added and
        // the dialog gets dismissed
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mSubject.getText().toString().isEmpty() && !mNotes.getText().toString().isEmpty()){
                    makeNewNote(mSubject.getText().toString(), mNotes.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    public void makeNewNote(String subject, String note){
        // Add the note in the adapter and refresh
        mListAdapter.addNote(new Note(subject, note));
        mListAdapter.notifyDataSetChanged();
    }

}
