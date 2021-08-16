package bsmart.technology.rru.base.api.bean;

/**
 * Vaccination does item detail information
 */
public class Vaccination_Item_Bean {

    private Integer i_covid_vaccine_dose_number; // vaccination does number no
    private String v_covid_vaccine_type; // vaccination type , such as : Pfizer
    private String d_covid_vaccination; // vaccination does date, such as : 2021-08-10
    private String v_covid_vaccine_facility; // vaccination clinic/lab, such as : ABC Clinic
    private String v_covid_vaccine_batch_no; // vaccination batch no , such as : EF949320

    public Integer getI_covid_vaccine_dose_number() {
        return i_covid_vaccine_dose_number;
    }

    public void setI_covid_vaccine_dose_number(Integer i_covid_vaccine_dose_number) {
        this.i_covid_vaccine_dose_number = i_covid_vaccine_dose_number;
    }

    public String getV_covid_vaccine_type() {
        return v_covid_vaccine_type;
    }

    public void setV_covid_vaccine_type(String v_covid_vaccine_type) {
        this.v_covid_vaccine_type = v_covid_vaccine_type;
    }

    public String getD_covid_vaccination() {
        return d_covid_vaccination;
    }

    public void setD_covid_vaccination(String d_covid_vaccination) {
        this.d_covid_vaccination = d_covid_vaccination;
    }

    public String getV_covid_vaccine_facility() {
        return v_covid_vaccine_facility;
    }

    public void setV_covid_vaccine_facility(String v_covid_vaccine_facility) {
        this.v_covid_vaccine_facility = v_covid_vaccine_facility;
    }

    public String getV_covid_vaccine_batch_no() {
        return v_covid_vaccine_batch_no;
    }

    public void setV_covid_vaccine_batch_no(String v_covid_vaccine_batch_no) {
        this.v_covid_vaccine_batch_no = v_covid_vaccine_batch_no;
    }
}
