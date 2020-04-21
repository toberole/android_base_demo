package com.zw.ipc;

import android.os.Parcel;
import android.os.Parcelable;

public class Request implements Parcelable {
    public String data;
    public int requestType;

    protected Request(Parcel in) {
        data = in.readString();
        requestType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeInt(requestType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    @Override
    public String toString() {
        return "Request{" +
                "data='" + data + '\'' +
                ", requestType=" + requestType +
                '}';
    }
}
