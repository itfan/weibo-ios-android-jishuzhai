package com.example.anjoyosinaweibo.api;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.net.RequestListener;
/**
 * 该类封装了用户接口，详情请参考<a href="http://open.weibo.com/wiki/API%E6%96%87%E6%A1%A3_V2#.E7.94.A8.E6.88.B7">用户接口</a>
 * @author xiaowei6@staff.sina.com.cn
 */
public class UsersAPI extends WeiboAPI {
	public UsersAPI(Oauth2AccessToken accessToken) {
        super(accessToken);
    }

    private static final String SERVER_URL_PRIX = API_SERVER + "/users";

	/**
	 * 根据用户ID获取用户信息
	 * @param uid 需要查询的用户ID。
	 * @param listener
	 */
	public void show( long uid, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		request( SERVER_URL_PRIX + "/show.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 根据用户ID获取用户信息
	 * @param screen_name 需要查询的用户昵称。
	 * @param listener
	 */
	public void show( String screen_name, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("screen_name", screen_name);
		request( SERVER_URL_PRIX + "/show.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 通过个性化域名获取用户资料以及用户最新的一条微博
	 * @param domain 需要查询的个性化域名。
	 * @param listener
	 */
	public void domainShow( String domain, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("domain", domain);
		request( SERVER_URL_PRIX + "/domain_show.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 批量获取用户的粉丝数、关注数、微博数
	 * @param uids 需要获取数据的用户UID，多个之间用逗号分隔，最多不超过100个。
	 * @param listener
	 */
	public void counts( long[] uids, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		StringBuilder strb = new StringBuilder();
		for (long cid : uids) {
			strb.append(String.valueOf(cid)).append(",");
		}
		strb.deleteCharAt(strb.length() - 1);
		params.add("uids", strb.toString());
		request( SERVER_URL_PRIX + "/counts.json", params, HTTPMETHOD_GET, listener);
	}

	
	/**
	 * 
     * @param since_id 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
	 * @param max_id 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
	 * @param count 单页返回的记录条数，默认为50。
	 * @param page 返回结果的页码，默认为1。
	 * @param FILTER 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
	 * @param filter_by_source 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0。
	 */
	public void getatme(long since_id, long max_id, int count, int page,
			AUTHOR_FILTER FILTER,int filter_by_source,RequestListener listener){
		WeiboParameters params=new WeiboParameters();
		params.add("since_id", since_id);
		params.add("max_id", max_id);
		params.add("count", count);
		params.add("page", page);
		params.add("filter_by_author", 0);
		params.add("filter_by_source", 0);
		String url="https://api.weibo.com/2/comments/mentions.json";
		request(url, params, "GET", listener);
	}
	
	public void getComment(long since_id, long max_id, int count, int page,
			AUTHOR_FILTER FILTER,int filter_by_source,RequestListener listener){
		WeiboParameters params=new WeiboParameters();
		params.add("since_id", since_id);
		params.add("max_id", max_id);
		params.add("count", count);
		params.add("page", page);
		params.add("filter_by_author", 0);
		params.add("filter_by_source", 0);
		String url="https://api.weibo.com/2/comments/to_me.json";
		request(url, params, "GET", listener);
	}
	
}
