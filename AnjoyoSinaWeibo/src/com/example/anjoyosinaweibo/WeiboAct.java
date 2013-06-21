package com.example.anjoyosinaweibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.anjoyosinaweibo.util.AccessTokenKeeper;
import com.example.anjoyosinaweibo.util.ConstantS;
import com.example.anjoyosinaweibo.util.HttpUtil;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

@SuppressLint("HandlerLeak")
public class WeiboAct extends Activity implements WeiboAuthListener,RequestListener{

	public static final int GET_TOKEN_ERROR = 404;
	public static final int GET_TOKEN_OK = 200;
	public static final int OAUTH_ERROE = 800;
	public static final int OAUTH_VALID= 500;
	public static final int LOADDATA_OVER = 600;
	public static final int LOADDATA_ERROR = 604;
	public static final int LOADUSER_ERROR = 602;
	
	Context con;
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			handmessage(msg);
			super.handleMessage(msg);
		}
	};

	Oauth2AccessToken accessToken;
	Weibo weibo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		con=this;
		weibo = Weibo.getInstance(ConstantS.APP_KEY, ConstantS.REDIRECT_URL, ConstantS.SCOPE);
		accessToken=AccessTokenKeeper.readAccessToken(this);
		if (!HttpUtil.NetISok(this)) {
			ToastInfo("网络连接异常");
		}
		if(!accessToken.isSessionValid()){
			weibo.anthorize(con, this);
		}else{
			handler.sendEmptyMessage(OAUTH_VALID);
		}
	}
	
	/**
	 * 当我们向handle发送消息的时候 会调用这个方法
	 * @param msg
	 */
	protected void handmessage(Message msg){
		
	}
	
	
	
	@Override
	public void onCancel() {
		
	}
	@Override
	public void onComplete(Bundle arg0) {
		// 当授权成功的时候 返回code码
		String code = arg0.getString("code");
		// 用code码 访问sina服务器 换取token 并且保存到本地
		AccessTokenKeeper.SavecodeToken(code, con, handler);
	}
	@Override
	public void onError(WeiboDialogError arg0) {
		handler.sendEmptyMessage(GET_TOKEN_ERROR);
	}
	@Override
	public void onWeiboException(WeiboException arg0) {
		handler.sendEmptyMessage(GET_TOKEN_ERROR);
	}
	
	
	
	public void ToastInfo(String text){
		Toast.makeText(con, text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onComplete(String arg0) {
		
	}

	@Override
	public void onComplete4binary(ByteArrayOutputStream arg0) {
		
	}
	@Override
	public void onError(WeiboException arg0) {
	    System.out.println(arg0.getMessage());
		ToastInfo(arg0.getMessage());
	}

	@Override
	public void onIOException(IOException arg0) {
	}
}
