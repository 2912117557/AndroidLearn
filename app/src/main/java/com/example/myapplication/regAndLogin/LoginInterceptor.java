package com.example.myapplication.regAndLogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LoginInterceptor {

    public static final String mINVOKER = "INTERCEPTOR_INVOKER";

    public static void interceptor(Context ctx, String target, Bundle bundle, Intent intent) {
            LoginCarrier invoker = new LoginCarrier(target, bundle);
            if (getLogin()) {
                invoker.invoke(ctx);
            } else {
                if (intent == null) {
                    intent = new Intent(ctx, LoginActivity.class);
                }
                login(ctx, invoker, intent);
            }
    }

    public static void interceptor(Context ctx, String target, Bundle bundle) {
        interceptor(ctx, target, bundle, null);
    }

    private static boolean getLogin() {
        return MyConfig.is_login;
}

    private static void login(Context context, LoginCarrier invoker, Intent intent) {
        try{
            intent.putExtra(mINVOKER, invoker);
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
