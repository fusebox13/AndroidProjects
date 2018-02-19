package com.example.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;




public class Vote extends Dialog 
    implements OnClickListener
{

   RecordVote recordVote;

   public Vote(Context context,  RecordVote recordVote) {
      super(context);
      this.recordVote = recordVote;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setTitle("Vote your heart");
      setContentView(R.layout.vote);

      Button b = (Button)findViewById(R.id.red);
      b.setOnClickListener(this);
      Button b2 = (Button)findViewById(R.id.blue);
      b2.setOnClickListener(this);
      Button b3 = (Button)findViewById(R.id.none);
      b3.setOnClickListener(this);
     
   }
   public void onClick(View v) {
       int index = v.getId();
       switch(index)
       {
       case R.id.red:     
           recordVote.vote(0);
           break;
           
       case R.id.blue:
           recordVote.vote(1);
           break;
           
       case R.id.none:
           recordVote.vote(-1);
           break;
       }
       dismiss();

   }
}