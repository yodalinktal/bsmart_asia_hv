package bsmart.technology.rru.pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Nullable;

import bsmart.technology.rru.R;
import bsmart.technology.rru.base.BaseActivity;
import bsmart.technology.rru.base.EncodingUtils;
import bsmart.technology.rru.base.api.bean.LoginRectsBean;
import bsmart.technology.rru.base.utils.BSmartUtil;
import bsmart.technology.rru.base.utils.DateUtil;
import bsmart.technology.rru.base.utils.HeaderView;
import bsmart.technology.rru.base.utils.ProfileUtils;
import bsmart.technology.rru.base.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class CheckVaccinationActivity extends BaseActivity {
    private static final int REQUEST_CODE_SCAN = 0x0000;// 扫描二维码

    @BindView(R.id.header)
    HeaderView headerView;

    @BindView(R.id.content1)
    TextView content1;

    @BindView(R.id.vaccient_content)
    TextView vaccient_content;

    @BindView(R.id.resultLayout)
    View resultLayout;

    @BindView(R.id.certificate)
    TextView certificate;

//    @BindView(R.id.localLogo)
//    ImageView localLogo;

    @BindView(R.id.localTitle)
    TextView localTitle;

    @BindView(R.id.localBand)
    TextView localBand;

    @BindView(R.id.content2)
    TextView content2;

    @BindView(R.id.content3)
    TextView content3;

    @BindView(R.id.stampDate)
    TextView stampDate;

    @BindView(R.id.doctor_name)
    TextView doctor_name;

    @BindView(R.id.localHeadTitle)
    LinearLayout localHeadTitle;

    @BindView(R.id.wholeHome)
    LinearLayout wholeHome;

    @BindView(R.id.certificateTitle)
    TextView certificateTitle;

    @BindView(R.id.certificateQRCode)
    ImageView certificateQRCode;

    @BindView(R.id.covid_flag)
    ImageView covid_flag;

    @BindView(R.id.validDate)
    TextView validDate;

    private LoginRectsBean loginRectsBean;

    boolean isLocal = true;

    @SuppressLint("WrongConstant")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_certificate);
        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        content3.setTypeface(typeface);
        String qrCodeContent= "";
        Intent intent =  getIntent();
        String fullName = intent.getStringExtra("fullName");
        String i_people_nationality = intent.getStringExtra("i_people_nationality");
        String c_people_rid = intent.getStringExtra("c_people_rid");
        qrCodeContent = qrCodeContent+c_people_rid+";";
        String country = "";
        if ("635".equals(i_people_nationality)){
            country = "RWANDA";
        }else if ("639".equals(i_people_nationality)){
            country = "KENYA";
        }else if ("640".equals(i_people_nationality)){
            country = "TANZANIA";
        }else if ("641".equals(i_people_nationality)){
            country = "UGANDA";
        }else if ("642".equals(i_people_nationality)){
            country = "BURUNDI";
        }else if ("659".equals(i_people_nationality)){
            country = "SOUTH SUDAN";
        }else if ("630".equals(i_people_nationality)){
            country = "DRC";
        }
        String v_country_name = intent.getStringExtra("v_country_name");
        if (!TextUtils.isEmpty(v_country_name)){
            country = v_country_name;
        }

        String v_people_nationalid = intent.getStringExtra("v_people_nationalid");
        String people_type = intent.getStringExtra("people_type");
        String passport = intent.getStringExtra("passport");
        String reason = intent.getStringExtra("reason");
        String bookId = intent.getStringExtra("bookId");
        String posted_at = intent.getStringExtra("posted_at");
        String provider_name = intent.getStringExtra("provider_name");

        String flag = intent.getStringExtra("flag");

        String display_posted_at = BSmartUtil.getddMMyyyy(posted_at);
        SimpleDateFormat post_sdf = new SimpleDateFormat("dd/MM/yyyy");
        String titleValidDate = post_sdf.format(BSmartUtil.getCovid19ValidDate(posted_at));
        String HeadTitle = "Electronic MY Covid-19 Testing Certificate Valid till "+titleValidDate;
        //headerView.setTitle_Size(HeadTitle,13);
        //headerView.setSMTitle(getString(R.string.page_certificate_title),getString(R.string.page_certificate_second_title)+" "+titleValidDate, Gravity.LEFT);

        headerView.setLeft(v -> {
            this.finish();
        });
        headerView.setBackgroundResource(R.color.white);
        headerView.setTitle_Main_Color("EACPass");

        validDate.setText("Valid till "+titleValidDate);
        loginRectsBean  = ProfileUtils.getRectLoginBean();
        if (null != reason && reason.toLowerCase().equals("transit")){
            isLocal = false;
        }
        isLocal = false;
        if (isLocal){
            //localLogo.setImageResource(R.drawable.kenya);
            localTitle.setVisibility(View.VISIBLE);
            content2.setVisibility(View.VISIBLE);
            localHeadTitle.setVisibility(View.VISIBLE);
            localBand.setVisibility(View.VISIBLE);
            certificateTitle.setText("ATTESTATION OF COVID-19 TESTING");
        }else{
//            if ("valid".equals(flag)){
//                localLogo.setImageResource(R.drawable.eac);
//            }else{
//                localLogo.setImageResource(R.drawable.eac_nil);
//            }
            localTitle.setVisibility(View.GONE);
            content2.setVisibility(View.GONE);
            localHeadTitle.setVisibility(View.GONE);
            localBand.setVisibility(View.GONE);
            certificateTitle.setText("ELECTRONIC MY COVID-19 VACCINATION CERTIFICATE");
            Typeface certificate_typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
            certificateTitle.setTypeface(certificate_typeface);
        }


        String colorBord = "#000000";
        String colorI = "#000000";
        if ("valid".equals(flag)){
            covid_flag.setVisibility(View.GONE);
        }else{
            covid_flag.setVisibility(View.GONE);
            if ("expired".equals(flag)){
                covid_flag.setImageResource(R.drawable.covid_expired);
            }else{
                covid_flag.setImageResource(R.drawable.covid_invalid);
            }
//            colorBord = "gray";
//            colorI = "gray";
            colorBord = "#000000";
            colorI = "#000000";
        }

        String today2 = BSmartUtil.getyyyMMdd(posted_at);

        qrCodeContent = qrCodeContent+passport+";";

        String name = "<font color='"+colorBord+"'><strong>"+fullName+"</strong></font>";
        String nationality = "<font color='"+colorBord+"'><strong>"+country+"</strong></font>";
        String passportNo = "<font color='"+colorBord+"'><strong>"+passport+"</strong></font>";
        String job = "<font color='"+colorBord+"'><strong>"+people_type+"</strong></font>";
        String nationalid = "<font color='"+colorBord+"'><strong>"+v_people_nationalid+"</strong></font>";
        String work = "<font color='"+colorBord+"'><strong>"+provider_name+"</strong></font>";
        String date = "<font color='"+colorBord+"'><strong>"+display_posted_at+"</strong></font>";
        String hospital = "<font color='"+colorBord+"'><strong>"+""+"</strong></font>";

        if ("635".equals(i_people_nationality)){
            hospital = "<font color='"+colorBord+"'><strong>"+"RBC/National Reference Laboratory, RWANDA"+"</strong></font>";
        }else if ("639".equals(i_people_nationality)){
            hospital = "<font color='"+colorBord+"'><strong>"+"NATIONAL PUBLIC HEALTH LABORATORY, KENYA"+"</strong></font>";
        }else if ("640".equals(i_people_nationality)){
            hospital = "<font color='"+colorBord+"'><strong>"+"National Health Laboratory Quality Assurance and Training Centre (NHLQATC), TANZANIA"+"</strong></font>";
        }else if ("641".equals(i_people_nationality)){
            hospital = "<font color='"+colorBord+"'><strong>"+"Central Public Health Laboratories, UGANDA"+"</strong></font>";
        }

//        if ("8A7E9780-9823-ED4B-A9657B1A79DF042F".equals(c_people_rid)){
//            hospital = "<font color='"+colorBord+"'><strong>"+intent.getStringExtra("v_hospital")+"</strong></font>";
//
//        }

        String contentStr = "<span style='color:"+colorI+"'>This is to certify that </span><span style='color:#228B22'>" + name+"</span> "+
                "<span style='color:"+colorI+"'>With Nationality</span> <span style='color:#228B22'>"+ nationality +"</span> <span style='color:"+colorI+"'>ID/Passport Number </span><span style='color:#228B22'>" + nationalid +"</span> "+
                "<span style='color:"+colorI+"'>has been vaccinated COVID-19 as per below records</span> ";

        String vaccinated_1 = "";

        String str_vaccines = intent.getStringExtra("vaccines");
        Log.i("str_vaccines_json",str_vaccines);
        try {
            JSONObject json_vaccines = new JSONObject(str_vaccines);
            JSONArray list = json_vaccines.getJSONArray("vaccines");

            for (int i = 0; i<list.length()&& i<2;i++){



                Integer i_covid_vaccine_dose_number = list.getJSONObject(i).getInt("i_covid_vaccine_dose_number");
                String s_covid_vaccine_dose_number = "";
                if (i_covid_vaccine_dose_number==1){
                    s_covid_vaccine_dose_number = "1st";
                }else if(i_covid_vaccine_dose_number==2){
                    s_covid_vaccine_dose_number = "2nd";
                }else if(i_covid_vaccine_dose_number==3){
                    s_covid_vaccine_dose_number = "3rd";
                }

                String color = "#228B22";
                if (TextUtils.isEmpty(list.getJSONObject(i).getString("d_covid_vaccination"))){
                    color = "black";
                    if (i_covid_vaccine_dose_number==1){
                        //wholeHome.setBackgroundResource(R.color.colorRed);
                    }
                }

                String v_covid_vaccine_type = "";
                if(!TextUtils.isEmpty(list.getJSONObject(i).getString("v_covid_vaccine_type"))){
                    v_covid_vaccine_type = list.getJSONObject(i).getString("v_covid_vaccine_type");
                }

                String v_covid_vaccine_batch_no = "";
                if(!TextUtils.isEmpty(list.getJSONObject(i).getString("v_covid_vaccine_batch_no"))){
                    v_covid_vaccine_batch_no = list.getJSONObject(i).getString("v_covid_vaccine_batch_no");
                }

                String d_covid_vaccination = "";
                if(!TextUtils.isEmpty(list.getJSONObject(i).getString("d_covid_vaccination"))){
                    d_covid_vaccination = list.getJSONObject(i).getString("d_covid_vaccination");
                }

                String v_covid_vaccine_facility = "";
                if(!TextUtils.isEmpty(list.getJSONObject(i).getString("v_covid_vaccine_facility"))){
                    v_covid_vaccine_facility = list.getJSONObject(i).getString("v_covid_vaccine_facility");
                }

                vaccinated_1 = vaccinated_1 + "<font color='"+color+"'><strong>"+s_covid_vaccine_dose_number+" Dose COVID-19</strong></font>";
                vaccinated_1 = vaccinated_1 + "<br><strong>VACCINATION TYPE:</strong> <font color='red'>"+v_covid_vaccine_type+"</font>";
                vaccinated_1 = vaccinated_1 + "<br><strong>VACCINATION LOT&nbsp;&nbsp;&nbsp;" +
                        ":</strong> <font color='red'>"+v_covid_vaccine_batch_no+"</font>";
                vaccinated_1 = vaccinated_1 + "<br><strong>VACCINATION DATE:</strong> <font color='red'>"+ DateUtil.formatVaccineDate(d_covid_vaccination)+"</font>";
                vaccinated_1 = vaccinated_1 + "<br><strong>HEALTHCARE SITE&nbsp;&nbsp;&nbsp;:</strong>  <font color='red'>"+v_covid_vaccine_facility+"</font>";
                vaccinated_1 = vaccinated_1 + "<br><br>";

                qrCodeContent = qrCodeContent+i_covid_vaccine_dose_number+","+v_covid_vaccine_type+","+d_covid_vaccination+","+v_covid_vaccine_facility+";";

            }


        }catch (Exception e){
            e.printStackTrace();
        }

        content1.setText(Html.fromHtml(contentStr));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            content1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        vaccient_content.setText(Html.fromHtml(vaccinated_1));

        String certificateStr = "<strong>"+"CERTIFICATE NO "+":"+i_people_nationality+today2+certificate(bookId)+"</strong>";
        certificate.setText(Html.fromHtml(certificateStr));
        stampDate.setText(display_posted_at);
        doctor_name.setText("");
        certificate.setVisibility(View.GONE);
        localBand.setVisibility(View.VISIBLE);
        localBand.setText(Html.fromHtml(certificateStr));

        updateQRCode(certificateQRCode,qrCodeContent,flag);

    }

    private int getColorFlag(String flag) {
        if("valid".equals(flag)){ // light blue
            return 0xff294FE9;
        }
        return 0xff000000;
    }

    private int getColor(boolean success) {
        return success ? 0xff000000 : 0xff000000;
    }

    private void updateQRCode(ImageView barCode,String text, String flag) {
        int px = Utils.dp2px(this, 120);
        Bitmap bp = EncodingUtils.createQRCode(text, px, px, getColorFlag(flag));
        Resources res = getResources();
        Bitmap logo = BitmapFactory.decodeResource(res, R.drawable.commonpass);
        Bitmap newQRCode = EncodingUtils.addLogo(bp,logo);
        barCode.setImageBitmap(newQRCode);
    }

    private  String certificate(String bookId){
        if (null == bookId || "".equals(bookId)){
            return "0000000001";
        }else{
            int length = bookId.length();
            String result = "";
            if (length<10){

                for (int i = 0; i < 10-length; i++) {
                    result = result + "0";
                }
                result = result + bookId;
            }else {
                result  = bookId;
            }
            return result;
        }
    }

    private String getDateByDay(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);
        String dayDate = sdf.format(c.getTime());
        return dayDate;
    }



}
