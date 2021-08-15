package bsmart.technology.rru.pages;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import bsmart.technology.rru.base.utils.TextViewUtil;
import bsmart.technology.rru.base.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckCertificateActivity extends BaseActivity {
    private static final int REQUEST_CODE_SCAN = 0x0000;// 扫描二维码

    @BindView(R.id.header)
    HeaderView headerView;

    @BindView(R.id.content1)
    TextView content1;

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

    @BindView(R.id.certificateTitle)
    TextView certificateTitle;

    @BindView(R.id.certificateQRCode)
    ImageView certificateQRCode;

    @BindView(R.id.covid_flag)
    ImageView covid_flag;

    @BindView(R.id.validDate)
    TextView validDate;

    @BindView(R.id._patientName)
    TextView _patientName;
    @BindView(R.id._clinicName)
    TextView _clinicName;
    @BindView(R.id._gender)
    TextView _gender;
    @BindView(R.id._passport)
    TextView _passport;
    @BindView(R.id._nationality)
    TextView _nationality;
    @BindView(R.id._collectionDate)
    TextView _collectionDate;
    @BindView(R.id._testResult)
    TextView _testResult;
    @BindView(R.id._testRecordedDate)
    TextView _testRecordedDate;
    @BindView(R.id._testType)
    TextView _testType;

    private LoginRectsBean loginRectsBean;

    boolean isLocal = true;




    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_certificate);
        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        content3.setTypeface(typeface);
        TextViewUtil.justify(content3);
        Intent intent =  getIntent();
        String fullName = intent.getStringExtra("fullName");
        String i_people_nationality = intent.getStringExtra("i_people_nationality");
        String c_people_rid = intent.getStringExtra("c_people_rid");
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
        String HeadTitle = "Electronic EAC Covid-19 Testing Certificate Valid till "+titleValidDate;
        //headerView.setTitle_Size(HeadTitle,13);
        //headerView.setSMTitle(getString(R.string.page_certificate_title),getString(R.string.page_certificate_second_title)+" "+titleValidDate, Gravity.LEFT);

        headerView.setLeft(v -> {
            this.finish();
        });
        headerView.setBackgroundResource(R.color.white);
        headerView.setTitle_Main_Color("EACPass");
        validDate.setVisibility(View.GONE);
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
            certificateTitle.setText("MY COVID-19 TESTING CERTIFICATE");
            Typeface certificate_typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
            certificateTitle.setTypeface(certificate_typeface);
        }

        String colorBord = "#000000";
        String colorI = "";
        if ("valid".equals(flag)){
            covid_flag.setVisibility(View.GONE);
        }else{
            covid_flag.setVisibility(View.VISIBLE);
            if ("expired".equals(flag)){
                covid_flag.setImageResource(R.drawable.covid_expired);
            }else{
                covid_flag.setImageResource(R.drawable.covid_invalid);
            }
            colorBord = "gray";
            colorI = "gray";
        }

        String today2 = BSmartUtil.getyyyMMdd(posted_at);

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

        String contentStr = "<i style='color:"+colorI+"'>This is to certify that</i> " + name+" "+
                "<i style='color:"+colorI+"'>ID Number: </i>   " + nationalid +" "+
                "<i style='color:"+colorI+"'>working at (specify exact place of work)</i>  " +work+" "+
                "<i style='color:"+colorI+"'>has been tested and found negative for COVID-19 following laboratory tests conducted on</i> " +
                date+" " +
                "<i style='color:"+colorI+"'>at the</i> " + hospital + " "+
                "<i></i>";

        if (!isLocal){
            contentStr = "<i style='color:"+colorI+"'>This is to certify that Mr./Ms</i> " + name+" "+
                    "<i style='color:"+colorI+"'>Nationality </i>   " + nationality +" "+
                    "<i style='color:"+colorI+"'>ID Number: </i>   " + nationalid +" "+
//                    "<i style='color:"+colorI+"'>working as </i>  " +job+" "+
                    "<i style='color:"+colorI+"'>has been tested and found negative for COVID-19 following laboratory tests conducted on</i> " +
                    date+" " +
                    "<i style='color:"+colorI+"'>at the</i> " + hospital + " "+
                    "<i></i>";
        }
        content1.setVisibility(View.GONE);
        content1.setText(Html.fromHtml(contentStr));

        String certificateStr = "<strong>"+"CERTIFICATE NO"+":"+i_people_nationality+today2+certificate(bookId)+"</strong>";
        certificate.setVisibility(View.GONE);
        localBand.setVisibility(View.VISIBLE);
        localBand.setText(Html.fromHtml(certificateStr));
        stampDate.setText(display_posted_at);
        doctor_name.setText("");

        //Add new format
        String _txt_patientName = "：<strong><font color='red'>"+fullName+"</font></strong>";
//        String _txt_patientName = "：<strong><font color='red'>ALEX MWENDWA MUSYIMI MWAVU</font></strong>";
        String _txt_clinicName = "：<strong><font color='red'>"+intent.getStringExtra("clinicName")+"</font></strong>";
//        String _txt_clinicName = "：<strong><font color='red'>Mahi Mahiu Mobile Laboratory</font></strong>";
        String _txt_gender = "：<strong><font color='red'>"+intent.getStringExtra("gender")+"</font></strong>";
        String _txt_passport = "：<strong><font color='red'>"+nationalid+"</font></strong>";
        String _txt_nationality = "：<strong><font color='red'>"+nationality+"</font></strong>";
        String _txt_collectDate = "：<strong><font color='red'>"+DateUtil.formatVaccineDate(intent.getStringExtra("posted_at"))+"</font></strong>";
        String _txt_testResult = "：<strong><font color='red'>"+intent.getStringExtra("testResult")+"</font></strong>";
        String _txt_testRecordedDate = "：<strong><font color='red'>"+ DateUtil.formatVaccineDate(intent.getStringExtra("testCollectedDate")) +"</font></strong>";
        String _txt_testType = "：<strong><font color='red'>"+intent.getStringExtra("testType")+"</font></strong>";

        _patientName.setText(Html.fromHtml(_txt_patientName));
        _clinicName.setText(Html.fromHtml(_txt_clinicName));
        _gender.setText(Html.fromHtml(_txt_gender));
        _passport.setText(Html.fromHtml(_txt_passport));
        _nationality.setText(Html.fromHtml(_txt_nationality));
        _collectionDate.setText(Html.fromHtml(_txt_collectDate));
        _testResult.setText(Html.fromHtml(_txt_testResult));
        _testRecordedDate.setText(Html.fromHtml(_txt_testRecordedDate));
        _testType.setText(Html.fromHtml(_txt_testType));

        updateQRCode(certificateQRCode,c_people_rid,flag);

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
//        barCode.setImageBitmap(bp);
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
