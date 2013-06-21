package com.example.anjoyosinaweibo;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainTab extends TabActivity {

	RadioGroup group;
	TabHost host;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		group = (RadioGroup) findViewById(R.id.radiogroupbutton);
		host = getTabHost();

		TabSpec spec = host.newTabSpec("home").setIndicator("home").setContent(new Intent(this, HomeTimeLine.class));
		host.addTab(spec);

		TabSpec spec2 = host.newTabSpec("friend").setIndicator("home").setContent(new Intent(this, FirendAct.class));
		host.addTab(spec2);
		
		
		TabSpec spec3=host.newTabSpec("msg").setIndicator("msg").setContent(new Intent(this,MessageAct.class));
		host.addTab(spec3);
		
		((RadioButton) findViewById(R.id.home)).setChecked(true);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.home:
					host.setCurrentTabByTag("home");
					break;
				case R.id.friend:
					host.setCurrentTabByTag("friend");
					break;
				case R.id.msg:
					host.setCurrentTabByTag("msg");
					break;
				}

			}
		});

	}
}
