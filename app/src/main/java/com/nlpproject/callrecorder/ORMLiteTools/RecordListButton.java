package com.nlpproject.callrecorder.ORMLiteTools;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.RecordDetailsActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Piotrek on 05.01.2017.
 */

public class RecordListButton extends Button  implements View.OnClickListener{

    private DateFormat DATA_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private ProcessingTask representedRecord;
    private Integer numberOfMatches;

    public RecordListButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    public RecordListButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public RecordListButton(Context context) {
        super(context);
        setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.getContext(), RecordDetailsActivity.class);
        intent.putExtra(RecordDetailsActivity.SELECTED_RECORD, representedRecord);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.getContext().startActivity(intent);
    }

    public ProcessingTask getRepresentedRecord() {
        return representedRecord;
    }

    public void setContent(ProcessingTask representedRecord, Integer numberOfMatches){
        this.representedRecord = representedRecord;
        this.numberOfMatches = numberOfMatches;

        StringBuffer sb = new StringBuffer();
        sb.append(representedRecord.getCaller_number());
        if (numberOfMatches != null)
        {
            sb.append(" - ");
            sb.append(numberOfMatches);
        }
        sb.append("\n ");
        sb.append(DATA_FORMAT.format(representedRecord.getRecordDate()));
        String text = String.format("%s - %s", representedRecord.getCaller_number(), representedRecord.getRecordDate().toString());
        this.setText(text);
    }


//
//    private initLayout(){
////
////        <Button
////        android:id="@+id/angry_btn"
////
////        android:text="Button"
////        android:textColor="#FFFFFF"
////        android:textSize="30sp"
////
////        android:layout_width="270dp"
////        android:layout_height="60dp"
////        android:background="@drawable/buttonshape"
////
//
//        setTextColor(0xffffff);
//        setTextSize(10);
//        setBackground(findR.drawable.buttonshape);
//
//    }


}
