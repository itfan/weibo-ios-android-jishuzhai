package com.example.anjoyosinaweibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MessageAct extends Activity {

	Intent in;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_layout);
	    in=	new Intent(MessageAct.this,MessageDetail.class);
		findViewById(R.id.atme_layout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				in.putExtra("what", MessageDetail.AT_ME);
				startActivity(in);
			}
		});
		findViewById(R.id.comment_layout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				in.putExtra("what", MessageDetail.COMMENTS_ME);
				startActivity(in);
			}
		});
	}
}
