package br.com.makrosystems.testeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int level;
    private TextView textView;
    private Button button;
    private TextView levelTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        levelTextView = findViewById(R.id.levelTextView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                //region Testes
                String appName = "com.sec.android.app.camera";

                String filePath = findInstalledAppFilePath(appName);
                if (filePath != null) {
                    levelTextView.setText("Caminho do arquivo: " + filePath);
                    System.out.println(filePath);
                } else {
                    levelTextView.setText("Aplicativo não encontrado.");
                }
                //endregion
                //lista de apps
                StringBuilder stringBuilder = new StringBuilder();

                PackageManager packageManager = getPackageManager();
                List<ApplicationInfo> applications = packageManager.getInstalledApplications(0);
                for (ApplicationInfo appInfo : applications) {
                    stringBuilder.append(appInfo.packageName).append("\n");
                }

                levelTextView.setText(stringBuilder.toString());

//                textView.setText("Brownser: " + getBrownserDefaultVersion()+"\n"+"OS: " +getAndroidVersion()
//                + "\n" + "Path: " + "");
//                System.out.println("Caminho do arquivo: " + filePath);
//                System.out.println("Real MDF Path: "+pathLevel());
                System.out.println("Lista de Apps: \n"+stringBuilder);
                //Browser
                System.out.println(getBrownserDefaultVersion());
                textView.setText(getBrownserDefaultVersion());
            }
        });
    }

    //Pega o navegador padrão e a versão dele
    public String getBrownserDefaultVersion(){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        PackageManager pm = getPackageManager();

        final ResolveInfo mInfo = pm.resolveActivity(i, 0);

        PackageInfo pInfo = null;

        try {
            pInfo = getPackageManager().getPackageInfo(
                    mInfo.activityInfo.packageName, PackageManager.GET_META_DATA);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//
//        Toast.makeText(getApplicationContext()
//                        ,pm.getApplicationLabel(mInfo.activityInfo.applicationInfo)
//                                + " version " + pInfo.versionName,
//                        Toast.LENGTH_LONG)
//                .show();
        System.out.println("Name: "+ pm.getApplicationLabel(mInfo.activityInfo.applicationInfo));
        System.out.println("Version: "+ pInfo.versionName);
        return pm.getApplicationLabel(mInfo.activityInfo.applicationInfo).toString();
    }

    //pega a versao do android e do OS
    public String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return "Android SDK: " + sdkVersion + " (" + release +")";
    }

    //pega o level do path
    private String findInstalledAppFilePath(String appName) {
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(appName, 0);
            return appInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static String pathLevel(){
//        String pathLevelmy = Build.VERSION.SECURITY_PATCH;
//        return pathLevelmy;
//    }

}