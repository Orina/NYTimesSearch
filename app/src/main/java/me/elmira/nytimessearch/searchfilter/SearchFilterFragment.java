package me.elmira.nytimessearch.searchfilter;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.elmira.nytimessearch.R;
import me.elmira.nytimessearch.data.ArticleSearchFilter;

import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by elmira on 3/17/17.
 */

public class SearchFilterFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String PARAM_FILTER = "filter";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");

    private Spinner mOrderSpinner;
    private DatePickerDialog mDatePickerDialog;
    private CheckBox mArtsCheckBox;
    private CheckBox mFashionCheckBox;
    private CheckBox mSportsCheckBox;
    private TextView mBeginDateTextView;
    private Button mSaveButton;


    private Calendar mCalendar = Calendar.getInstance();
    private Date mSelectedDate = null;

    private ArticleSearchFilter mFilter;

    public interface SearchFilterDialogListener {
        void onFinishEditDialog(Date beginDate, String order, List<String> newsDesks);
    }

    public SearchFilterFragment() {

    }

    public static SearchFilterFragment newInstance(ArticleSearchFilter filter) {
        SearchFilterFragment fragment = new SearchFilterFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARAM_FILTER, filter);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_filter, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFilter = getArguments().getParcelable(PARAM_FILTER);

        mOrderSpinner = (Spinner) view.findViewById(R.id.spinnerSortOrder);
        ArrayAdapter orderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.search_order_array, R.layout.spinner_search_order);
        mOrderSpinner.setAdapter(orderAdapter);

        mArtsCheckBox = (CheckBox) view.findViewById(R.id.cbArts);
        mFashionCheckBox = (CheckBox) view.findViewById(R.id.cbFashion);
        mSportsCheckBox = (CheckBox) view.findViewById(R.id.cbSports);
        mBeginDateTextView = (TextView) view.findViewById(R.id.tvBeginDateSetter);
        mSaveButton = (Button) view.findViewById(R.id.btSave);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButton(v);
            }
        });
        mDatePickerDialog = new DatePickerDialog(getContext(), R.style.Article_DialogPicker, this, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE));
        mDatePickerDialog.setButton(BUTTON_NEUTRAL, getText(R.string.button_clear), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == BUTTON_POSITIVE) {
                    mDatePickerDialog.getDatePicker().clearFocus();
                    onDateSet(mDatePickerDialog.getDatePicker(), mDatePickerDialog.getDatePicker().getYear(),
                            mDatePickerDialog.getDatePicker().getMonth(), mDatePickerDialog.getDatePicker().getDayOfMonth());
                }
                else if (which == BUTTON_NEUTRAL) {
                    mDatePickerDialog.getDatePicker().clearFocus();
                    onDateSet(mDatePickerDialog.getDatePicker(), 0, 0, 0);
                }
            }
        });

        mBeginDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerDialog.setTitle("");
                mDatePickerDialog.show();
            }
        });

        if (mFilter != null) {
            if (mFilter.getBeginDate() != null) {
                mSelectedDate = mFilter.getBeginDate();
                mCalendar.setTime(mSelectedDate);
                mBeginDateTextView.setText(dateFormat.format(mFilter.getBeginDate()));
            }
            else {
                mSelectedDate = null;
                mCalendar.setTime(new Date());
                mBeginDateTextView.setText(R.string.not_selected);
            }
            if (mFilter.getNewsDesks() != null) {
                for (String newsDesk : mFilter.getNewsDesks()) {
                    if (ArticleSearchFilter.NEWS_DESK_ARTS.equals(newsDesk))
                        mArtsCheckBox.setChecked(true);
                    else if (ArticleSearchFilter.NEWS_DESK_FASHION.equals(newsDesk))
                        mFashionCheckBox.setChecked(true);
                    else if (ArticleSearchFilter.NEWS_DESK_SPORTS.equals(newsDesk))
                        mSportsCheckBox.setChecked(true);
                }
            }
            if (mFilter.getSortOrder() != null) {
                for (int pos = 0; pos < orderAdapter.getCount(); pos++) {
                    if (mFilter.getSortOrder().equalsIgnoreCase((String) orderAdapter.getItem(pos))) {
                        mOrderSpinner.setSelection(pos, true);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onSaveButton(View view) {
        SearchFilterDialogListener listener = (SearchFilterDialogListener) getActivity();
        List<String> newsDesk = new ArrayList<>();
        if (mArtsCheckBox.isChecked()) newsDesk.add(ArticleSearchFilter.NEWS_DESK_ARTS);
        if (mFashionCheckBox.isChecked()) newsDesk.add(ArticleSearchFilter.NEWS_DESK_FASHION);
        if (mSportsCheckBox.isChecked()) newsDesk.add(ArticleSearchFilter.NEWS_DESK_SPORTS);

        listener.onFinishEditDialog(mSelectedDate, mOrderSpinner.getSelectedItem().toString(), newsDesk);
        dismiss();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (year > 1800) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            mSelectedDate = mCalendar.getTime();
            mBeginDateTextView.setText(dateFormat.format(mSelectedDate));
        }
        else {
            mCalendar.setTime(new Date());
            mBeginDateTextView.setText(R.string.not_selected);
            mSelectedDate = null;
        }
    }
}
