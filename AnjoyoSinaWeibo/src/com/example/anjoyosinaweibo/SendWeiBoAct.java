package com.example.anjoyosinaweibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anjoyosinaweibo.adapter.EmotaionAdapter;
import com.example.anjoyosinaweibo.api.PlaceAPI;
import com.example.anjoyosinaweibo.api.StatusesAPI;
import com.example.anjoyosinaweibo.util.Myspf;
import com.example.anjoyosinaweibo.util.WeiboAutolinkUtil;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

public class SendWeiBoAct extends WeiboAct {

	EditText etcontent;
	Button btsend, btcancle;
	StatusesAPI api;
	ImageView send_shoseimgview;
	TextView tvaddress, tvclear;
	RelativeLayout center_relat;
    GridView gridView;
	public static final int GET_PIC = 101;
	String filepath = "";

	Location location;
	String lat = "";
	String lon = "";
	LocationManager locationManager;

	/*软键盘管理服务**/
	InputMethodManager inputmanger;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_layout);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		inputmanger=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		initview();
		
		api = new StatusesAPI(accessToken);
		loadlistener();
		etcontent.setText(Myspf.getMsgBox(con));
	}

	private void initview() {
		etcontent = (EditText) findViewById(R.id.etweibocontent);
		btsend = (Button) findViewById(R.id.btsendweibo);
		btcancle = (Button) findViewById(R.id.btcancle);
		send_shoseimgview = (ImageView) findViewById(R.id.send_shoseimgview);
		tvaddress = (TextView) findViewById(R.id.send_tvlocation);
		tvclear = (TextView) findViewById(R.id.send_tvclear);
		center_relat=(RelativeLayout) findViewById(R.id.bottom_relative);
		gridView=(GridView) findViewById(R.id.emotions_gridview);
		EmotaionAdapter adapter=new EmotaionAdapter(this, MyApplication.getInstence().emotionlist);
		gridView.setAdapter(adapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etcontent.getText().insert(etcontent.getSelectionStart(), gridView.getAdapter().getItem(position).toString());
                etcontent.setText(WeiboAutolinkUtil.Autolink(etcontent.getText().toString(), con));
            }
        });
	}

	private void sendweibo() {
		if(etcontent.getText().toString().trim().length()<1){
			ToastInfo("请输入微博内容");return;
		}
		if(filepath.length()<1){
		    api.update(etcontent.getText().toString(), lat, lon, new RequestListener() {
                
                @Override
                public void onIOException(IOException arg0) {
                    
                }
                
                @Override
                public void onError(WeiboException arg0) {
                    System.out.println(arg0.getMessage());
                }
                
                @Override
                public void onComplete4binary(ByteArrayOutputStream arg0) {
                }
                
                @Override
                public void onComplete(String arg0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                        ToastInfo("发送成功") ;     finish();  
                        }
                    });
                }
            });
		}
		
		api.upload(etcontent.getText().toString(), filepath, lat, lon, new RequestListener() {
			@Override
			public void onIOException(IOException arg0) {
			}
			@Override
			public void onError(WeiboException arg0) {
				System.err.println(arg0);
			}
			@Override
			public void onComplete4binary(ByteArrayOutputStream arg0) {
			}
			@Override
			public void onComplete(String arg0) {
			       handler.post(new Runnable() {
                       
                       @Override
                       public void run() {
                       ToastInfo("发送成功") ;    
                       finish();
                       }
                   });
               }
			
		});
	}

	private void loadlistener() {
		tvclear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				etcontent.setText("");
			}
		});
		etcontent.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				tvclear.setText(140 - s.toString().length() + "");
			}
		});
		btsend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendweibo();
			}
		});

		btcancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                    //如果没有写要发送的数据 直接退出
				if (etcontent.getText().toString() == null || etcontent.getText().toString().trim().length() < 1) {
					finish();
					return;
				}
				new AlertDialog.Builder(con).setTitle("退出").setMessage("是否保存草稿?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Myspf.saveMsgBox(con, etcontent.getText().toString());
					    dialog.dismiss();
						finish();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
                             dialog.dismiss();
                             finish();
					}
				}).create().show();
			}
		});
		etcontent.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                center_relat.setVisibility(View.GONE);
            }
        });

	}

	public void doclick(View v) {
		switch (v.getId()) {
		case R.id.send_cose_camera:
			Intent in = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(in, GET_PIC);
			break;
		case R.id.send_cose_photo:
			/** 获取当前手机相册里面的图片 */
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			/** 设置请求类型 */
			intent.setType("image/*");
			startActivityForResult(intent, GET_PIC);
			break;
		case R.id.send_cose_mention:
			break;
		case R.id.send_cose_emotion:
		    ToastInfo("emtions");
		    center_relat.setVisibility(View.VISIBLE);
		    //隐藏软键盘
		    inputmanger.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			break;
		case R.id.send_cose_more:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == GET_PIC) {
				Uri uri = data.getData();
				if (uri == null)
					return;
				Cursor c = SendWeiBoAct.this.getContentResolver().query(uri, new String[] { Media.DATA }, null, null, null);
				if (c != null) {
					c.moveToFirst();
					filepath = c.getString(c.getColumnIndex(Media.DATA));
					c.close();
					// 设置一个缩放
					Options options = new Options();
					options.inSampleSize = 4;
					send_shoseimgview.setImageBitmap(BitmapFactory.decodeFile(filepath, options));
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		getlocation();
		super.onResume();
	}

	/***
	 * 获取当前用户的位置信息
	 */
	public void getlocation() {
		location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location == null) {
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		if (location != null) {
			showaddress();
		} else {
			ToastInfo("获取位置信息失败");
			showaddress();
		}
	}

	public void showaddress() {
		String coordinate = "";
		if (location == null) {
			lat = "39.97948";
			lon = "116.31635";
		} else {
			lat = String.valueOf(location.getLatitude());
			lon = String.valueOf(location.getLongitude());
		}
		coordinate = lon + "," + lat;
		PlaceAPI api = new PlaceAPI(accessToken);
		api.parselocationtoGeo(coordinate, new RequestListener() {
			@Override
			public void onIOException(IOException arg0) {
			}

			@Override
			public void onError(WeiboException arg0) {
			}

			@Override
			public void onComplete4binary(ByteArrayOutputStream arg0) {
			}

			@Override
			public void onComplete(String arg0) {
				System.out.println(arg0 + "位置信息");
				try {
					JSONObject jsonObject = new JSONObject(arg0).getJSONArray("geos").getJSONObject(0);
					final String address = jsonObject.getString("address");
					final String name;
					if (jsonObject.has("name")) {
						name = jsonObject.getString("name");
					} else {
						name = "";
					}
					System.out.println(address);
					// 解析完成 显示
					handler.post(new Runnable() {
						@Override
						public void run() {
							tvaddress.setText(address + "," + name);
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

	}

}
