package com.example.thefriendzone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/**
 * A Spinner view that does not dismiss the dialog displayed when the control is "dropped down"
 * and the user presses it. This allows for the selection of more than one option.
 */
public class MultiSelectInterests extends androidx.appcompat.widget.AppCompatSpinner implements OnMultiChoiceClickListener {
    String[] strings = { "Collecting: Antiques", "Collecting: Cars", "Collecting: Action Figures", "Collecting: Books", "Collecting: Art",
            "Arts & Crafts: Drawing", "Arts & Crafts: Ceramics", "Arts & Crafts: Photography", "Arts & Crafts: Sewing", "Arts & Crafts: Wood Working",
            "Games: Arcade", "Games: Card", "Games: Board", "Games: Video", "Games: GeoCaching",
            "Electronics: Web Design", "Electronics: Remote Control", "Electronics: Communication", "Electronics: Robotics", "Coding",
            "Sports & Outdoors: Body Building", "Sports & Outdoors: Shooting", "Sports & Outdoors: Water", "Sports & Outdoors: Cardio", "Sports & Outdoors: Snow",
            "Performing Arts: Dancing", "Performing Arts: Singing", "Performing Arts: Acting", "Performing Arts: Magic", "Performing Arts: Puppetry",
            "Music: Composer", "Music: Guitarist", "Music: Singer", "Music: Musicals", "Music: Beat Boxing",
            "Food & Drink: Taste Tester", "Food & Drink: Baker", "Food & Drink: Cook", "Food & Drink: Sweet", "Food & Drink: Savory",
            "Pets & Animals: Cats", "Pets & Animals: Dogs", "Pets & Animals: Birds", "Pets & Animals: Reptiles", "Pets & Animals: Insect-Spiders"};
    boolean[] _selection = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    ArrayAdapter<String> _proxyAdapter;

    /**
     * Constructor for use when instantiating directly.
     * @param context
     */
    public MultiSelectInterests(Context context) {
        super(context);

        _proxyAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(_proxyAdapter);
    }

    /**
     * Constructor used by the layout inflater.
     * @param context
     * @param attrs
     */
    public MultiSelectInterests(Context context, AttributeSet attrs) {
        super(context, attrs);

        _proxyAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(_proxyAdapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (_selection != null && which < _selection.length) {
            _selection[which] = isChecked;

            _proxyAdapter.clear();
            _proxyAdapter.add(buildSelectedItemString());
            setSelection(0);
        }
        else {
            throw new IllegalArgumentException("Argument 'which' is out of bounds.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(strings, _selection, this);
        builder.show();
        return true;
    }

    /**
     * MultiSelectSpinner does not support setting an adapter. This will throw an exception.
     * @param adapter
     */
    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException("setAdapter is not supported by MultiSelectSpinner.");
    }

    /**
     * Sets the options for this spinner.
     * @param items
     */
    public void setItems(String[] items) {
        strings = items;
        _selection = new boolean[strings.length];

        Arrays.fill(_selection, false);
    }

    /**
     * Sets the options for this spinner.
     * @param items
     */
    public void setItems(List<String> items) {
        strings = items.toArray(new String[items.size()]);
        _selection = new boolean[strings.length];

        Arrays.fill(_selection, false);
    }


    /**
     * Sets the selected options based on a list of string.
     * @param selection
     */
    public void setSelection(List<String> selection) {
        for (String sel : selection) {
            for (int j = 0; j < strings.length; ++j) {
                if (strings[j].equals(sel)) {
                    _selection[j] = true;
                }
            }
        }
    }

    /**
     * Sets the selected options based on an array of positions.
     * @param selectedIndicies
     */
    public void setSelection(int[] selectedIndicies) {
        for (int index : selectedIndicies) {
            if (index >= 0 && index < _selection.length) {
                _selection[index] = true;
            }
            else {
                throw new IllegalArgumentException("Index " + index + " is out of bounds.");
            }
        }
    }

    /**
     * Returns a list of strings, one for each selected item.
     * @return
     */
    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<String>();
        for (int i = 0; i < strings.length; ++i) {
            if (_selection[i]) {
                selection.add(strings[i]);
            }
        }
        return selection;
    }

    /**
     * Returns a list of positions, one for each selected item.
     * @return
     */
    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<Integer>();
        for (int i = 0; i < strings.length; ++i) {
            if (_selection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    /**
     * Builds the string for display in the spinner.
     * @return comma-separated list of selected items
     */
    public String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < strings.length; ++i) {
            if (_selection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(strings[i]);
            }
        }

        return sb.toString();
    }

}