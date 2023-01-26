//
// Decompiled by Jadx - 835ms
//
package ropl.momo.item;

import android.content.Context;
import android.icu.util.GregorianCalendar;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import ropl.momo.item.IPCCond.IPCService;
import ropl.momo.item.Service.FloatService;

class MainActivity$100000004 implements Runnable {
    private final MainActivity this$0;
    private final Context val$context;
    private final String val$卡密;

    MainActivity$100000004(MainActivity mainActivity, String str, Context context) {
        this.this$0 = mainActivity;
        this.val$卡密 = str;
        this.val$context = context;
    }

    @Override
    public void run() {
        Surface surface = new Surface();
        IMutual iMutual = new IMutualImpl();
        iMutual.SurfaceCreate(surface, width, height);
        String stringBuffer = new StringBuffer().append(MainActivity.wy_url).append("/api/?id=kmlogon").toString();
        String string = Settings.Secure.getString(this.this$0.getContentResolver(), "android_id");
        Long l = new Long(System.currentTimeMillis() / 1000);
        String stringBuffer2 = new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append("&app=").append(MainActivity.WY_APPID).toString()).append("&kami=").toString()).append(this.val$卡密).toString()).append("&markcode=").toString()).append(string).toString()).append("&t=").toString()).append(l).toString()).append("&sign=").toString()).append(MainActivity.encodeMD5(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append("kami=").append(this.val$卡密).toString()).append("&markcode=").toString()).append(string).toString()).append("&t=").toString()).append(l).toString()).append("&").toString()).append(MainActivity.WY_APPKEY).toString())).toString();
        String stringBuffer3 = new StringBuffer().append(stringBuffer).append(stringBuffer2).toString();
        if (MainActivity.UrlPost(MainActivity.wy_url, stringBuffer3) != null) {
            String stringBuffer4 = new StringBuffer().append(new StringBuffer().append(UUID.randomUUID().toString().replace("-", "")).append(MainActivity.WY_APPKEY).toString()).append(string).toString();
            try {
                JSONObject jSONObject = new JSONObject(RC4Util.decryRC4(MainActivity.UrlPost(new StringBuffer().append(new StringBuffer().append(stringBuffer3).append("&app=").toString()).append(MainActivity.WY_APPID).toString(), new StringBuffer().append(new StringBuffer().append(new StringBuffer().append("data=").append(RC4Util.encryRC4String(stringBuffer2, MainActivity.WY_RC4KEY, "UTF-8")).toString()).append("&value=").toString()).append(stringBuffer4).toString()), MainActivity.WY_RC4KEY, "UTF-8"));
                String string2 = jSONObject.getString("code");
                String string3 = jSONObject.getString("msg");
                if (jSONObject.optString("check").equals(MainActivity.encodeMD5(new StringBuffer().append(new StringBuffer().append(new Long(jSONObject.optLong("time")).toString()).append(MainActivity.WY_APPKEY).toString()).append(string).toString()))) {
                    Looper.prepare();
                    Toast.makeText(this.val$context, "非法操作", 1).show();
                    Looper.loop();
                } else if (string2.equals("149")) {
                    Long l2 = new Long(new JSONObject(string3).optLong("vip"));
                    GregorianCalendar gregorianCalendar = new GregorianCalendar();
                    gregorianCalendar.setTimeInMillis(l2.longValue() * 1000);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Looper.prepare();
                    Toast.makeText(this.val$context, new StringBuffer().append("登录成功\n到期时间:").append(simpleDateFormat.format(gregorianCalendar.getTime())).toString(), 1).show();
                    if (!FloatService.mSurfaceViewIo && IPCService.isConnect()) {
                        try {
                            FloatService.ShowFloat(this.this$0);
                            IPCService.GetIPC().SetSavePath(this.this$0.getFilesDir().toString());
                        } catch (RemoteException e) {
                            Log.v("连接输出", Log.getStackTraceString(e));
                        }
                    }
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(this.val$context, string3, 1).show();
                    Looper.loop();
                }
            } catch (JSONException unused) {
                Long l3 = new Long(4092599349000L);
                GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
                gregorianCalendar2.setTimeInMillis(l3.longValue() * 1000);
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Looper.prepare();
                Toast.makeText(this.val$context, new StringBuffer().append("登录成功\n到期时间:").append(simpleDateFormat2.format(gregorianCalendar2.getTime())).toString(), 1).show();
                FloatService.ShowFloat(this.this$0);
                IPCService.GetIPC().SetSavePath(this.this$0.getFilesDir().toString());
                Looper.loop();
            } catch (Exception e2) {
            }
        }
    }
}
