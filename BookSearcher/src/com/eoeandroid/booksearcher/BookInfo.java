package com.eoeandroid.booksearcher;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class BookInfo implements Parcelable {

	private String mTitle = ""; // book name
	private Bitmap mCover; // book cover
	private String mAuthor = ""; // book author
	private String mPublisher = ""; // book publisher
	private String mPublishDate = ""; // book publish date
	private String mISBN = ""; // book isbn
	private String mSummary = ""; // book summary

	public static final Parcelable.Creator<BookInfo> CREATOR = new Creator<BookInfo>() {

		@Override
		public BookInfo createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			BookInfo bookInfo = new BookInfo();
			bookInfo.mTitle = source.readString();
			bookInfo.mCover = source.readParcelable(Bitmap.class
					.getClassLoader());
			bookInfo.mAuthor = source.readString();
			bookInfo.mPublisher = source.readString();
			bookInfo.mPublishDate = source.readString();
			bookInfo.mISBN = source.readString();
			bookInfo.mSummary = source.readString();
			return bookInfo;
		}

		@Override
		public BookInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new BookInfo[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(mTitle);
		dest.writeParcelable(mCover, flags);
		dest.writeString(mAuthor);
		dest.writeString(mPublisher);
		dest.writeString(mPublishDate);
		dest.writeString(mISBN);
		dest.writeString(mSummary);
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public Bitmap getCover() {
		return mCover;
	}

	public void setCover(Bitmap mCover) {
		this.mCover = mCover;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public void setAuthor(String mAuthor) {
		this.mAuthor = mAuthor;
	}

	public String getPublisher() {
		return mPublisher;
	}

	public void setPublisher(String mPublisher) {
		this.mPublisher = mPublisher;
	}

	public String getPublishDate() {
		return mPublishDate;
	}

	public void setPublishDate(String mPublishDate) {
		this.mPublishDate = mPublishDate;
	}

	public String getISBN() {
		return mISBN;
	}

	public void setISBN(String mISBN) {
		this.mISBN = "ISBN: " + mISBN;
	}

	public String getSummary() {
		return mSummary;
	}

	public void setSummary(String mSummary) {
		this.mSummary = mSummary;
	}

}
