package bsmart.technology.rru.pages;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.example.xch.scanzxing.zxing.android.CaptureActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import bsmart.technology.rru.R;
import bsmart.technology.rru.base.App;
import bsmart.technology.rru.base.BaseFragment;
import bsmart.technology.rru.base.api.NetSubscriber;
import bsmart.technology.rru.base.api.NetTransformer;
import bsmart.technology.rru.base.api.RECDTSApi;
import bsmart.technology.rru.base.api.bean.COVID_19_Bean;
import bsmart.technology.rru.base.api.bean.Vaccination_Bean;
import bsmart.technology.rru.base.utils.BSmartUtil;
import bsmart.technology.rru.base.utils.ChannelUtil;
import bsmart.technology.rru.base.utils.HeaderView;
import bsmart.technology.rru.base.utils.ParseUtils;
import bsmart.technology.rru.base.utils.ProfileUtils;
import bsmart.technology.rru.base.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;

public class CheckResultFragment extends BaseFragment {
    private static final int REQUEST_CODE_SCAN = 0x0000;// 扫描二维码

    @BindView(R.id.header)
    HeaderView headerView;

    @BindView(R.id.bookId)
    TextView bookId;
    @BindView(R.id.fullName)
    TextView fullName;

    @BindView(R.id.passport)
    TextView passport;

    @BindView(R.id.temperture)
    TextView temperture;

    @BindView(R.id.spinner1)
    TextView reason;

    @BindView(R.id.test1Result)
    TextView test1Result;

    @BindView(R.id.test2Result)
    TextView test2Result;

    @BindView(R.id.test3Result)
    TextView test3Result;

    @BindView(R.id.test4Result)
    TextView test4Result;

    @BindView(R.id.test5Result)
    TextView test5Result;

    @BindView(R.id.test6Result)
    TextView test6Result;

    @BindView(R.id.test7Result)
    TextView test7Result;

    @BindView(R.id.test8Result)
    TextView test8Result;

    @BindView(R.id.test9Result)
    TextView test9Result;

    @BindView(R.id.test10Result)
    TextView test10Result;

    @BindView(R.id.test11Result)
    TextView test11Result;

    @BindView(R.id.test12Result)
    TextView test12Result;

    @BindView(R.id.test13Result)
    TextView test13Result;

    @BindView(R.id.test14Result)
    TextView test14Result;

    @BindView(R.id.test15Result)
    TextView test15Result;

    @BindView(R.id.remark)
    TextView remarks;

    @BindView(R.id.healthDec)
    CheckBox healthDec;

    @BindView(R.id.tvCertificateBtn)
    TextView tvCertificateBtn;

    @BindView(R.id.tvVaccinationBtn)
    TextView tvVaccinationBtn;

    @BindView(R.id.tvFailed)
    TextView tvFailed;


    @BindView(R.id.resultLayout)
    View resultLayout;

    private String personalInfo;
    private String c_people_rid;

    private boolean isVaccinated = false;

    private String posted_at;

    private View rootView;
    Unbinder unbinder;

    private ConnectivityManager connectivityManager;//用于判断是否有网络

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_health_v2_check, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        View customRightView = LayoutInflater.from(getContext()).inflate(R.layout.action__health_right, null);
        customRightView.findViewById(R.id.flRefresh).setOnClickListener(v -> {
            this.openBarCodeActivity();
        });
        headerView.setRightCustomView(customRightView);

        String countryCode = ProfileUtils.getLoginCountryCode();
        String clinicCode = ProfileUtils.getLoginClinicCode();
        headerView.setTitle("Health Status("+countryCode+","+clinicCode+")");

        tvCertificateBtn.setOnClickListener(view->{

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Checking... Please wait.");
            progressDialog.show();
            String[] t = personalInfo.split("\\|");
            Map<String, String> requestData = new HashMap<>();
            requestData.put("c_people_rid", t[2]);

            //Get Json Data from API
            /**
             * {
             *     "account": {
             *         "id": 40314,
             *         "v_smartdriver_id": "47NKTNTE",
             *         "v_smartdriver_password": "******",
             *         "i_people_rid": null,
             *         "n_account_balance": "0.00",
             *         "v_taxi_colour": "White",
             *         "v_taxi_permit": "KCP 905N",
             *         "v_remarks": null,
             *         "c_user_group_rid": "6A2BDEFE-1FCD-F76D-1710B2D8F761E0B6",
             *         "v_created_user": "paito.ronald",
             *         "d_create_date": "2020-08-30 10:00:44",
             *         "v_last_update_user": "robert.onyango",
             *         "d_last_update_date": "2020-09-12 12:15:06",
             *         "v_smartdriver_name": "PAUL ODHIAMBO OTIENO",
             *         "v_mobile_no": "+254723784573",
             *         "i_gender": "0",
             *         "v_email_address": "info@health.go.ug",
             *         "v_company_name_others": null,
             *         "v_taxi_type": null,
             *         "v_company_name": "HUAYE",
             *         "v_account_type": null,
             *         "v_status": "Active",
             *         "v_bdt_login_id": null,
             *         "v_taxi_model": "AXOR",
             *         "c_taxi_group_rid": null,
             *         "i_officer_region": 313,
             *         "c_asp_rid": "6A2BDEFE-1FCD-F76D-1710B2D8F761E0B6",
             *         "c_operator_rid": null,
             *         "api_token": null,
             *         "i_people_type": 1,
             *         "v_people_first_name": "PAUL",
             *         "v_people_last_name": "ODHIAMBO OTIENO",
             *         "v_people_email": "info@health.go.ug",
             *         "v_people_passport": "21853278",
             *         "i_people_nationality": 639,
             *         "v_people_nationalid": "21853278",
             *         "v_mobile1_no": null,
             *         "v_mobile2_no": null,
             *         "v_address": null,
             *         "v_city": "MOMBASA",
             *         "v_postal": "NA",
             *         "v_county": "Mombasa",
             *         "v_district": "Migadini",
             *         "v_email": null,
             *         "d_dob": "1979-06-23",
             *         "i_age": 41,
             *         "v_nok_first_name": "NORA",
             *         "v_nok_last_name": "ANYANGO",
             *         "v_nok_mobile": "+254729287323",
             *         "v_nok_mobile1": null,
             *         "v_nok_email": null,
             *         "v_companypic_first_name": "ZANG",
             *         "v_companypic_last_name": "ZANG ZANG",
             *         "v_company_mobile": "+25677888999",
             *         "v_company_mobile1": null,
             *         "v_company_email": "NA",
             *         "c_people_rid": "F2D6F591-7E13-7251-40F7983237269F40",
             *         "v_country": null,
             *         "c_parent_rid": null,
             *         "i_eacpass": null,
             *         "i_mco_id": 11760,
             *         "i_covid_result": 0,
             *         "i_covid_test_type": null,
             *         "d_covid_test": null,
             *         "i_body_temp": "36",
             *         "d_covid_specimen_collect": "2020-09-12 00:00:00",
             *         "d_update_time": "2021-08-13 15:06:25",
             *         "d_create_time": "2020-09-12 18:41:14",
             *         "v_hospital": null,
             *         "i_clinic_country": 641,          //Lab Name from Country (MCC Code)
             *         "v_clinic_name": "Malaba POE",   //COVID-19 Test Lab Name
             *         "i_covid_vaccine": null,
             *         "i_covid_vaccine_dose_number": 0,
             *         "v_covid_vaccine_lot_number": null,
             *         "i_covid_vaccine_type": 0,
             *         "d_covid_vaccination": null,
             *         "v_provider_name": "Ministry of Health - Uganda",
             *         "v_country_name": "Kenya",
             *         "str_covid_test_type": "PCR",
             *         "flag": "expired",
             *         "vaccines": [
             *             {
             *                 "i_covid_vaccine_dose_number": 1,
             *                 "v_covid_vaccine_type": "",
             *                 "d_covid_vaccination": "",
             *                 "v_covid_vaccine_facility": "",
             *                 "v_covid_vaccine_batch_no": ""
             *             },
             *             {
             *                 "i_covid_vaccine_dose_number": 2,
             *                 "v_covid_vaccine_type": "",
             *                 "d_covid_vaccination": "",
             *                 "v_covid_vaccine_facility": "",
             *                 "v_covid_vaccine_batch_no": ""
             *             }
             *         ]
             *     }
             * }
             */


            RECDTSApi.getAppHAHVDVService().accountHealthDetail(requestData)
                    .compose(new NetTransformer<>(JsonObject.class))
                    .subscribe(new NetSubscriber<>(bean -> {

                        progressDialog.dismiss();
                        if (bean.get("account").isJsonNull()){
                            ToastUtils.showShort("No test results found");
                            return;
                        }
                        JsonObject account =  bean.get("account").getAsJsonObject();
                        if(null != account){
                            Intent intent = new Intent(getActivity(),CheckCertificateActivity.class);
                            intent.putExtra("fullName",account.get("v_smartdriver_name").getAsString());
                            intent.putExtra("i_people_nationality",account.get("i_people_nationality").getAsString());
                            intent.putExtra("c_people_rid",t[2]);
                            //valid invalid expired
                            if (null != account.get("flag")){
                                intent.putExtra("flag",account.get("flag").getAsString());
                            }else{
                                intent.putExtra("flag","valid");
                            }

                            String v_country_name = "";

                            if(account.has("v_country_name") && !account.get("v_country_name").isJsonNull()){
                                v_country_name = account.get("v_country_name").getAsString();
                            }
                            intent.putExtra("v_country_name",v_country_name);

                            String v_people_nationalid = "";
                            if (!account.get("v_people_nationalid").isJsonNull()){
                                v_people_nationalid = account.get("v_people_nationalid").getAsString();
                            }

                            intent.putExtra("v_people_nationalid",v_people_nationalid);

                            int i_people_type = account.get("i_people_type").getAsInt();
                            String people_type = "Driver";
                            if(i_people_type==1){
                                people_type = "Driver";
                            }else if(i_people_type==2){
                                people_type = "Co-Driver";
                            }else {
                                people_type = "Turn-Boy";
                            }intent.putExtra("people_type",people_type);

                            intent.putExtra("passport",v_people_nationalid);
                            intent.putExtra("reason","transit");
                            intent.putExtra("bookId",account.get("i_mco_id").getAsString());


                            intent.putExtra("provider_name",account.get("v_provider_name").getAsString());

                            if (!account.get("v_hospital").isJsonNull()){
                                intent.putExtra("v_hospital",account.get("v_hospital").getAsString());
                            }else{
                                intent.putExtra("v_hospital","");
                            }
                            String d_covid_specimen_collect = "";
                            if (!account.get("d_covid_specimen_collect").isJsonNull()){
                                System.out.println(">>>"+account.get("d_covid_specimen_collect").getAsString());
                                intent.putExtra("posted_at",account.get("d_covid_specimen_collect").getAsString());

                                if (!account.get("i_gender").isJsonNull()){
                                    if (account.get("i_gender").getAsInt() == 0){
                                        intent.putExtra("gender","MALE");
                                    }else{
                                        intent.putExtra("gender","FEMALE");
                                    }
                                }else{
                                    intent.putExtra("gender","");
                                }
                                System.out.println(">>>rid:"+t[2]);
                                intent.putExtra("testType",account.get("str_covid_test_type").getAsString());
                                System.out.println(">>>type:"+account.get("str_covid_test_type").getAsString());
                                intent.putExtra("testCollectedDate",account.get("d_update_time").getAsString());
                                if (!account.get("d_update_time").isJsonNull()){
                                    String testResult = "";
                                    if (account.get("i_covid_result").getAsInt()==0){
                                        testResult = "NEGATIVE";
                                    }else if(account.get("i_covid_result").getAsInt()==1){
                                        testResult = "POSITIVE";
                                    }
                                    intent.putExtra("testResult",testResult);
                                }else{
                                    intent.putExtra("testResult","");
                                }

                                intent.putExtra("clinicName",account.get("v_clinic_name").getAsString());

                                getActivity().startActivity(intent);
                            }else{
                                intent.putExtra("posted_at","");
                                ToastUtils.showShort("No COVID-19 test results were detected, and the certificate cannot be viewed.");
                            }

                        }else{
                            ToastUtils.showShort("No test results found");
                        }


                    }, e -> {
                        progressDialog.dismiss();
                        ToastUtils.showShort("Failed,please check network");
                    }));

        });

        tvVaccinationBtn.setOnClickListener(view -> {


            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Checking... Please wait.");
            progressDialog.show();

            String[] t = personalInfo.split("\\|");
            Map<String, String> requestData = new HashMap<>();
            String c_people_rid = t[2];
            requestData.put("c_people_rid", c_people_rid);

            RECDTSApi.getAppHAHVDVService().accountHealthDetail(requestData)
                    .compose(new NetTransformer<>(JsonObject.class))
                    .subscribe(new NetSubscriber<>(bean -> {

                        progressDialog.dismiss();
                        if (bean.get("account").isJsonNull()){
                            ToastUtils.showShort("No test results found");
                            return;
                        }
                        JsonObject account =  bean.get("account").getAsJsonObject();
                        if(null != account){
                            Intent intent = new Intent(getActivity(),CheckVaccinationActivity.class);
                            intent.putExtra("fullName",account.get("v_smartdriver_name").getAsString());
                            intent.putExtra("i_people_nationality",account.get("i_people_nationality").getAsString());
                            intent.putExtra("c_people_rid",c_people_rid);
                            //valid invalid expired

                            String v_country_name = "";

                            if(account.has("v_country_name") && !account.get("v_country_name").isJsonNull()){
                                v_country_name = account.get("v_country_name").getAsString();
                            }
                            intent.putExtra("v_country_name",v_country_name);

                            if (null != account.get("flag")){
                                intent.putExtra("flag",account.get("flag").getAsString());
                            }else{
                                intent.putExtra("flag","valid");
                            }

                            String v_people_nationalid = "";
                            if (!account.get("v_people_nationalid").isJsonNull()){
                                v_people_nationalid = account.get("v_people_nationalid").getAsString();
                            }

                            intent.putExtra("v_people_nationalid",v_people_nationalid);

                            int i_people_type = account.get("i_people_type").getAsInt();
                            String people_type = "Driver";
                            if(i_people_type==1){
                                people_type = "Driver";
                            }else if(i_people_type==2){
                                people_type = "Co-Driver";
                            }else {
                                people_type = "Turn-Boy";
                            }intent.putExtra("people_type",people_type);

                            intent.putExtra("passport",v_people_nationalid);
                            intent.putExtra("reason","transit");
                            intent.putExtra("bookId",account.get("i_mco_id").getAsString());

                            intent.putExtra("provider_name",account.get("v_provider_name").getAsString());

                            if (!account.get("v_hospital").isJsonNull()){
                                intent.putExtra("v_hospital",account.get("v_hospital").getAsString());
                            }else{
                                intent.putExtra("v_hospital","");
                            }
                            String d_covid_specimen_collect = "";
                            if (!account.get("d_covid_specimen_collect").isJsonNull()){
                                System.out.println(">>>"+account.get("d_covid_specimen_collect").getAsString());
                                intent.putExtra("posted_at",account.get("d_covid_specimen_collect").getAsString());
                            }else{
                                intent.putExtra("posted_at","");
                            }

                            //[Start]Add vaccine parameter
                            JsonArray jsonarray = account.get("vaccines").getAsJsonArray();
                            JsonObject j = new JsonObject();
                            j.add("vaccines",account.get("vaccines").getAsJsonArray());
                            Log.i("json",j.toString());
                            intent.putExtra("vaccines",j.toString());

                            //[End]Add vaccine parameter


                            getActivity().startActivity(intent);

                        }else{
                            ToastUtils.showShort("No test results found");
                        }


                    }, e -> {
                        progressDialog.dismiss();
                        ToastUtils.showShort("Failed,please check network");
                    }));


        });

        return rootView;
    }

    private void openBarCodeActivity() {
        //动态权限申请
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            goScan();
        }
    }

    private long lastClickBack = 0;


    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        tvCertificateBtn.setVisibility(View.GONE);
        tvVaccinationBtn.setVisibility(View.GONE);
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        getActivity().startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScan();
                } else {
                    Toast.makeText(getContext(), "make sure you allow the camera permission.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SCAN:// 二维码
                // 扫描二维码回传
                if (resultCode == getActivity().RESULT_OK) {
                    if (data != null) {
                        //获取扫描结果
                        Bundle bundle = data.getExtras();
                        String result = bundle.getString("content");
                        Log.i("Andrewlu", "扫描结果:" + result);
                        this.judgeQRCode(result);
                    }
                }
                break;
            default:
                break;
        }
    }


    private void judgeQRCode(String text){
        if (TextUtils.isEmpty(text)){
            ToastUtils.showLong("Scan result is empty.");
        }

        new Thread(scanLoggerWorker).start();

        String[] result = text.split(":");
        String channel = result[0];
        Log.i("BarCodeFragment", "scan channel:" + channel);

        if (isAESHeader(channel)){
            if(!hasNetwork()){
                ToastUtils.showLong("Please check if the mobile device network is available!");
                return;
            }

            //Request Remote Server Decode AES QRCode
            JSONObject bodyJson = new JSONObject();
            try {
                bodyJson.put("qrCode",text);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Parsing... Please wait.");
            progressDialog.show();
            String strEntity = bodyJson.toString();
            System.out.println("strEntity:"+strEntity);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
            RECDTSApi.getAppHAHVDVService().parseCode(body)
                    .compose(new NetTransformer<>(JsonObject.class))
                    .subscribe(new NetSubscriber<>(bean -> {
                        progressDialog.dismiss();
                        System.out.println("bean:"+bean.toString());
                        if (bean.has("qrCode")){
                            String realCode = bean.get("qrCode").getAsString();
                            showResult(realCode);

                        }else{
                            ToastUtils.showLong("Parse QRCode Failed.");

                        }

                    }, e -> {
                        ToastUtils.showLong(e.getMessage());
                        progressDialog.dismiss();
                    }));

        }else{
            showResult(text);
        }
    }

    private boolean hasNetwork(){
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (null != info) {
            return true;
        }else{
            return false;
        }
    }

    private boolean isAESHeader(String channel){
        Pattern p1 = Pattern.compile("^"+ChannelUtil.channel_eacpass_value+"\\d+");
        Pattern p2 = Pattern.compile("^"+ChannelUtil.channel_eacpass_value.toLowerCase()+"\\d+");
        boolean b1 = p1.matcher(channel).matches();
        boolean b2 = p2.matcher(channel).matches();
        return b1 || b2;
    }

    private final Runnable scanLoggerWorker = new Runnable() {

        @Override
        public void run() {
            Map<String, Object> requestData = new HashMap<>();
            String countryCode = ProfileUtils.getLoginCountryCode();
            String clinicCode = ProfileUtils.getLoginClinicCode();
            requestData.put("countryCode", countryCode);
            requestData.put("clinicCode", clinicCode);
            RECDTSApi.getAppHAHVDVService().scanLogger(requestData)
                    .compose(new NetTransformer<>(JsonObject.class))
                    .subscribe(new NetSubscriber<>(bean -> {
                        System.out.println("scanLogger:"+bean.toString());

                    }, e -> {
                        System.out.println("scanLogger:"+e.getMessage());
                    }));

        }
    };


    private void showResult(String text) {
        String[] result = text.split(";");
        if (result.length < 2 || !(result[0].equals(ChannelUtil.channel_recdts_value)
                || result[0].equals(ChannelUtil.channel_eacpass_value)
                || result[0].equals(ChannelUtil.channel_eacpass_value.toLowerCase())
        )) {
            ToastUtils.showShort("Invalid QRCode!");
            return;
        } else {
            String[] txts = text.trim().split(";");
            switch (txts.length) {
                case 24:{

                }
                case 23: {

                }
                case 22: {

                }
                case 21: {

                }
                case 20: {

                }
                case 19: {

                }
                case 18: {

                }
                case 17: {

                }
                case 16: {

                }
                case 15: {

                }
                case 14: {

                }
                case 13: {

                }
                case 12: {

                }
                case 11: {

                }
                case 10: {

                }
                case 9: {

                }
                case 8: {

                }
                case 7: {

                }
                case 6: {

                }
                case 5: {

                }
                case 4: {

                }
                case 3: {

                }
                case 2: {
                    personalInfo = txts[1];
                    String[] t = txts[1].split("\\|");
                    c_people_rid = t[2];

                }
            }

            parseHealthDetail(c_people_rid);

        }
    }

    private void parseHealthDetail(String c_people_rid){
        Map<String, String> requestData = new HashMap<>();
        requestData.put("c_people_rid",c_people_rid);
        RECDTSApi.getAppHAHVDVService().accountHealthDetail(requestData)
                .compose(new NetTransformer<>(JsonObject.class))
                .subscribe(new NetSubscriber<>(bean -> {

                    COVID_19_Bean covid_19_bean = ParseUtils.getCOVID_19_Data(bean);
                    Vaccination_Bean vaccination_bean = ParseUtils.getVaccination_Data(bean);

                    //todo: you can get COVID-19 Data , Vaccination Data, you can get data like this
                    String d_covid_specimen_collect = covid_19_bean.getD_covid_specimen_collect();
                    Log.d("parseHealthDetail",d_covid_specimen_collect);



                }, e -> {
                    System.out.println("Failed,please check network");
                }));
    }

    private void showResult_delete(String text) {
        String[] result = text.split(";");
        if (result.length < 2 || !(result[0].equals(ChannelUtil.channel_recdts_value)
                || result[0].equals(ChannelUtil.channel_eacpass_value)
                || result[0].equals(ChannelUtil.channel_eacpass_value.toLowerCase())
        )) {
            ToastUtils.showShort("Invalid QRCode!");
            return;
        } else {
            resultLayout.setVisibility(View.VISIBLE);
            tvCertificateBtn.setVisibility(View.VISIBLE);
            tvFailed.setVisibility(View.GONE);
            String[] txts = text.trim().split(";");
            switch (txts.length) {
                case 24:{
                    posted_at = txts[23];
                }
                case 23: {
                    remarks.setText(txts[22]);
                }
                case 22: {
                    healthDec.setChecked("1".equals(txts[21]));
                }
                case 21: {
                    test15Result.setText("0".equals(txts[20]) ? "No" : "Yes");
                }
                case 20: {
                    test14Result.setText("0".equals(txts[19]) ? "No" : "Yes");
                }
                case 19: {
                    test13Result.setText("0".equals(txts[18]) ? "No" : "Yes");
                }
                case 18: {
                    test12Result.setText("0".equals(txts[17]) ? "No" : "Yes");
                }
                case 17: {
                    test11Result.setText("0".equals(txts[16]) ? "No" : "Yes");
                }
                case 16: {
                    test10Result.setText("0".equals(txts[15]) ? "No" : "Yes");
                }
                case 15: {
                    test9Result.setText("0".equals(txts[14]) ? "No" : "Yes");
                }
                case 14: {
                    test8Result.setText("0".equals(txts[13]) ? "No" : "Yes");
                }
                case 13: {
                    test7Result.setText("0".equals(txts[12]) ? "No" : "Yes");
                }
                case 12: {
                    test6Result.setText("0".equals(txts[11]) ? "No" : "Yes");
                }
                case 11: {
                    test5Result.setText("0".equals(txts[10]) ? "No" : "Yes");
                }
                case 10: {
                    test4Result.setText("0".equals(txts[9]) ? "No" : "Yes");
                }
                case 9: {
                    test3Result.setText("0".equals(txts[8]) ? "No" : "Yes");
                }
                case 8: {
                    test2Result.setText("0".equals(txts[7]) ? "No" : "Yes");
                }
                case 7: {
                    test1Result.setText("0".equals(txts[6]) ? "No" : "Yes");
                }
                case 6: {
                    temperture.setText(txts[5]);
                }
                case 5: {
                    passport.setText(txts[4]);
                }
                case 4: {
                    fullName.setText(txts[3]);
                }
                case 3: {
                    reason.setText(txts[2]);
                    isVaccinated = false;
                    String vaccination[] = txts[2].split("\\|");
                    if (vaccination.length>=3){
                        if (vaccination[2].equals("2")){
                            isVaccinated = true;
                        }
                    }
                }
                case 2: {
                    personalInfo = txts[1];
                    String[] t = txts[1].split("\\|");
                    String digit = t[2];
                    //BSmartUtil.getQRCodeDigit(t[1], ""+t[3],""+t[0]);
//                    bookId.setText(t[0]);
                    bookId.setText(digit);
                    //更新数据
                    //updateJobDetail(t[0]);
                }
            }



            //说明检查结果没有过期
            if (!TextUtils.isEmpty(posted_at) && posted_at.compareTo(BSmartUtil.getDateByDayBefore(-14))>0){
                //37.8+ 是发烧了

                if(     test1Result.getText().toString().equals("No")
                        && test2Result.getText().toString().equals("No")
                        && test3Result.getText().toString().equals("No")
                        && test4Result.getText().toString().equals("No")
                        && test5Result.getText().toString().equals("No")
                        && test6Result.getText().toString().equals("No")
                        && test7Result.getText().toString().equals("No")
                        && test8Result.getText().toString().equals("No")
                        && test9Result.getText().toString().equals("No")
                        && test10Result.getText().toString().equals("No")
                        && test11Result.getText().toString().equals("No")
                        && test12Result.getText().toString().equals("No")
                        && test13Result.getText().toString().equals("No")
                        && test14Result.getText().toString().equals("No")
                ){
                    /**
                     如果个人对1到4的所有问题回答“否”并且没有发烧，则表示他们通过了。他们可以继续/被允许进入一个国家，需要戴上口罩，并应被告知自我监测者是否出现症状，并在旅行结束（商定的时期）提醒他们进行必要的筛查或再次筛查（如果之前进行过检查）证书）。
                     */
                    tvCertificateBtn.setVisibility(View.VISIBLE);
                    tvFailed.setVisibility(View.GONE);

                }else if(test1Result.getText().toString().equals("Yes")
                        || test2Result.getText().toString().equals("Yes")
                        || test3Result.getText().toString().equals("Yes")
                        || test4Result.getText().toString().equals("Yes")
                        || test5Result.getText().toString().equals("Yes")
                        || test6Result.getText().toString().equals("Yes")
                        || test7Result.getText().toString().equals("Yes")
                        || test8Result.getText().toString().equals("Yes")
                        || test9Result.getText().toString().equals("Yes")
                        || test10Result.getText().toString().equals("Yes")
                        || test11Result.getText().toString().equals("Yes")
                        || test12Result.getText().toString().equals("Yes")
                        || test13Result.getText().toString().equals("Yes")){
                    /**
                     * 如果个人对1到3中的任何一个问题回答“是”，则表示他们未通过且无法输入/进入该国家。他们应立即回家进行自我隔离。员工应联系其经理/直属上司。应转介给初级保健提供者，当地公共卫生部门（包括港口卫生部门）或远程医疗部门，以讨论其症状和/或暴露情况，并寻求有关测试的建议。
                     */
                    //不通过
                    tvCertificateBtn.setVisibility(View.GONE);
                    tvFailed.setVisibility(View.VISIBLE);
                    tvFailed.setText("NOT PASSED! Please contact Health supervisor for further action!");
                }else if(test14Result.getText().toString().equals("Yes")
                        && test15Result.getText().toString().equals("Yes")
                        && new Float(temperture.getText().toString().trim()).compareTo(new Float(37.8))>0){
                    //通过
                    tvCertificateBtn.setVisibility(View.VISIBLE);
                    tvFailed.setVisibility(View.GONE);
                }else if(test14Result.getText().toString().equals("Yes")
                        && test15Result.getText().toString().equals("No")){
                    //不通过
                    tvCertificateBtn.setVisibility(View.GONE);
                    tvFailed.setVisibility(View.VISIBLE);
                    tvFailed.setText("NOT PASSED! Please contact Health Supervisor for further action!");
                }else {
                    tvCertificateBtn.setVisibility(View.VISIBLE);
                    tvFailed.setVisibility(View.GONE);
                }
            }else{

                tvCertificateBtn.setVisibility(View.VISIBLE);
                tvFailed.setVisibility(View.VISIBLE);
                tvFailed.setText("Covid-19 Attestation Certificate has expired! Please contact Health Supervisor for further action!");
            }

            if (isVaccinated){
                tvVaccinationBtn.setVisibility(View.VISIBLE);
                tvVaccinationBtn.setBackgroundResource(R.color.colorGreen);
                tvVaccinationBtn.setClickable(true);
            }else{
                tvVaccinationBtn.setVisibility(View.VISIBLE);
                tvVaccinationBtn.setBackgroundResource(R.color.colorBtnGreen);
                tvVaccinationBtn.setClickable(false);
            }




        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
