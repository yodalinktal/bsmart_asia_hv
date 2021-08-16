package bsmart.technology.rru.base.api.bean;

import java.util.List;

/**
 * COVID-19 Vaccination Information
 */
public class Vaccination_Bean extends Base_Bean {

    private Integer i_covid_vaccine;  //if has vaccination , 0 = No , 1 = Yes
    private Integer i_covid_vaccine_dose_number; // total does number , 0 , 1 , 2.  if = 2 , means finished vaccination
    private String d_update_time; //update record time
    private String d_covid_vaccination; //latest vaccination date

    private List<Vaccination_Item_Bean> vaccines;

    public Integer getI_covid_vaccine() {
        return i_covid_vaccine;
    }

    public void setI_covid_vaccine(Integer i_covid_vaccine) {
        this.i_covid_vaccine = i_covid_vaccine;
    }

    public Integer getI_covid_vaccine_dose_number() {
        return i_covid_vaccine_dose_number;
    }

    public void setI_covid_vaccine_dose_number(Integer i_covid_vaccine_dose_number) {
        this.i_covid_vaccine_dose_number = i_covid_vaccine_dose_number;
    }

    public String getD_update_time() {
        return d_update_time;
    }

    public void setD_update_time(String d_update_time) {
        this.d_update_time = d_update_time;
    }

    public List<Vaccination_Item_Bean> getVaccines() {
        return vaccines;
    }

    public void setVaccines(List<Vaccination_Item_Bean> vaccines) {
        this.vaccines = vaccines;
    }

    public String getD_covid_vaccination() {
        return d_covid_vaccination;
    }

    public void setD_covid_vaccination(String d_covid_vaccination) {
        this.d_covid_vaccination = d_covid_vaccination;
    }
}
