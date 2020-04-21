package com.zw.ipc;

import android.os.Parcel;
import android.os.Parcelable;

public class Response implements Parcelable {
    public String data;

    protected Response(Parcel in) {
        data = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    @Override
    public String toString() {
        return "Response{" +
                "data='" + data + '\'' +
                '}';
    }
}
