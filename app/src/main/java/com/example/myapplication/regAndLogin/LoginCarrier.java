package com.example.myapplication.regAndLogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class LoginCarrier implements Parcelable {
    public String mTargetAction;
    public Bundle mbundle;

    public LoginCarrier(String target, Bundle bundle) {
        mTargetAction = target;
        mbundle = bundle;
    }

    public void invoke(Context ctx) {
        Intent intent = new Intent(mTargetAction);
        if (mbundle != null) {
            intent.putExtras(mbundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        ctx.startActivity(intent);
    }

    public LoginCarrier(Parcel parcel) {
        // 按变量定义的顺序读取
        mTargetAction = parcel.readString();
        mbundle = parcel.readParcelable(Bundle.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // 按变量定义的顺序写入
        parcel.writeString(mTargetAction);
        parcel.writeParcelable(mbundle, flags);
    }

    public static final Parcelable.Creator<LoginCarrier> CREATOR = new Parcelable.Creator<LoginCarrier>() {

        @Override
        public LoginCarrier createFromParcel(Parcel source) {
            return new LoginCarrier(source);
        }

        @Override
        public LoginCarrier[] newArray(int arg0) {
            return new LoginCarrier[arg0];
        }
    };
}
