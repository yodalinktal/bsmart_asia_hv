package bsmart.technology.rru.base.api.bean;

/**
 * COVID-19 Test Result Information
 */
public class COVID_19_Bean extends Base_Bean {

    private String v_clinic_name;            // COVID-19 Test Clinic/Hospital Name
    private String d_covid_specimen_collect; // COVID-19 specimen collect datetime
    private String d_update_time; //COVID-19 Latest update datetime
    private String i_body_temp; // temperature
    private String str_covid_test_type; // COVID-19 Test Type
    private Integer i_covid_result;  // 0 = Negative ; 1 = Positive

    public String getV_clinic_name() {
        return v_clinic_name;
    }

    public void setV_clinic_name(String v_clinic_name) {
        this.v_clinic_name = v_clinic_name;
    }

    public String getD_covid_specimen_collect() {
        return d_covid_specimen_collect;
    }

    public void setD_covid_specimen_collect(String d_covid_specimen_collect) {
        this.d_covid_specimen_collect = d_covid_specimen_collect;
    }

    public String getD_update_time() {
        return d_update_time;
    }

    public void setD_update_time(String d_update_time) {
        this.d_update_time = d_update_time;
    }

    public String getI_body_temp() {
        return i_body_temp;
    }

    public void setI_body_temp(String i_body_temp) {
        this.i_body_temp = i_body_temp;
    }

    public String getStr_covid_test_type() {
        return str_covid_test_type;
    }

    public void setStr_covid_test_type(String str_covid_test_type) {
        this.str_covid_test_type = str_covid_test_type;
    }

    public Integer getI_covid_result() {
        return i_covid_result;
    }

    public void setI_covid_result(Integer i_covid_result) {
        this.i_covid_result = i_covid_result;
    }
}
