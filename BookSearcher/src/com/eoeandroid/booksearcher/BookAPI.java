package com.eoeandroid.booksearcher;

public class BookAPI {

	public static final int RESPONSE_CODE_SUCCEED = 200;
	public static final int RESPONSE_CODE_ERROR_NET_EXCEPTION = -1;
	public static final int RESPONSE_CODE_ERROR_BOOK_NOT_FOUND = 6000;
	
	public static final String TAG_ERROR_CODE = "code";
	
	
	public static final String TAG_TITLE = "title";
	public static final String TAG_COVER = "image";
	public static final String TAG_AUTHOR = "author";
	public static final String TAG_PUBLISHER = "publisher";
	public static final String TAG_PUBLISHDATE = "pubdate";
	public static final String TAG_ISBN = "isbn13";
	public static final String TAG_SUMMARY = "summary";
	
	private static final String URL_BASE = "https://api.douban.com/v2/book/";
    // for example: https://api.douban.com/v2/book/isbn/7505715666
    public static final String URL_ISBN_BASE = URL_BASE + "isbn/";

}
