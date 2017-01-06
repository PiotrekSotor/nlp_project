package com.nlpproject.callrecorder.ORMLiteTools;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nlpproject.callrecorder.KeywordDetailsActivity;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.R;
import com.nlpproject.callrecorder.RecordDetailsActivity;

/**
 * Created by Piotrek on 05.01.2017.
 */

public class KeywordListButton extends Button  implements View.OnClickListener{

    private Keyword representedKeyword;

    public KeywordListButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    public KeywordListButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public KeywordListButton(Context context) {
        super(context);
        setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.getContext(), KeywordDetailsActivity.class);
        intent.putExtra(KeywordDetailsActivity.SELECTED_KEYWORD, representedKeyword);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.getContext().startActivity(intent);
    }

    public Keyword getRepresentedKeyword() {
        return representedKeyword;
    }

    public void setRepresentedKeyword(Keyword representedKeyword) {
        this.representedKeyword = representedKeyword;
        this.setText(this.representedKeyword.getOriginalWord());
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
