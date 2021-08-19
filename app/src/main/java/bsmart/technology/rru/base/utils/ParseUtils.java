package bsmart.technology.rru.base.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

import bsmart.technology.rru.base.api.bean.COVID_19_Bean;
import bsmart.technology.rru.base.api.bean.Vaccination_Bean;
import bsmart.technology.rru.base.api.bean.Vaccination_Item_Bean;

/**
 * Tools for Parsing COVID-19 and Vaccination data from API
 */
public class ParseUtils {

    /**
     * Parse COVID-19
     * @param bean
     * @return
     */
    public static COVID_19_Bean getCOVID_19_Data(JsonObject bean){
        if (null == bean){
            return null;
        }
        if (bean.get("account").isJsonNull()){
            return null;
        }
        JsonObject account =  bean.get("account").getAsJsonObject();
        if(null != account){

            COVID_19_Bean covid_19_bean = new COVID_19_Bean();

            covid_19_bean.setC_people_rid(account.get("c_people_rid").getAsString());
            covid_19_bean.setI_people_nationality(account.get("i_people_nationality").getAsString());
            covid_19_bean.setV_people_first_name(account.get("v_people_first_name").getAsString());
            covid_19_bean.setV_people_last_name(account.get("v_people_last_name").getAsString());
            covid_19_bean.setV_people_passport(account.get("v_people_passport").getAsString());
            covid_19_bean.setV_people_nationalid(account.get("v_people_nationalid").getAsString());
            covid_19_bean.setV_country_name(account.get("v_country_name").getAsString());

            if (account.has("v_clinic_name") && !account.get("v_clinic_name").isJsonNull()){
                covid_19_bean.setV_clinic_name(account.get("v_clinic_name").getAsString());
            }

            if (account.has("d_covid_specimen_collect") && !account.get("d_covid_specimen_collect").isJsonNull()){
                covid_19_bean.setD_covid_specimen_collect(account.get("d_covid_specimen_collect").getAsString());
            }

            if (account.has("d_update_time") && !account.get("d_update_time").isJsonNull()){
                covid_19_bean.setD_update_time(account.get("d_update_time").getAsString());
            }

            if (account.has("i_body_temp") && !account.get("i_body_temp").isJsonNull()){
                covid_19_bean.setI_body_temp(account.get("i_body_temp").getAsString());
            }

            if (account.has("str_covid_test_type") && !account.get("str_covid_test_type").isJsonNull()){
                covid_19_bean.setStr_covid_test_type(account.get("str_covid_test_type").getAsString());
            }

            if (account.has("i_covid_result") && !account.get("i_covid_result").isJsonNull()){
                covid_19_bean.setI_covid_result(account.get("i_covid_result").getAsInt());
            }


            return covid_19_bean;
        }else {
            return null;
        }

    }

    /**
     * Parse Vaccination
     * @param bean
     * @return
     */
    public static Vaccination_Bean getVaccination_Data(JsonObject bean){
        if (null == bean){
            return null;
        }
        if (bean.get("account").isJsonNull()){
            return null;
        }
        JsonObject account =  bean.get("account").getAsJsonObject();
        if(null != account){

            Vaccination_Bean vaccination_bean = new Vaccination_Bean();

            vaccination_bean.setC_people_rid(account.get("c_people_rid").getAsString());
            vaccination_bean.setI_people_nationality(account.get("i_people_nationality").getAsString());
            vaccination_bean.setV_people_first_name(account.get("v_people_first_name").getAsString());
            vaccination_bean.setV_people_last_name(account.get("v_people_last_name").getAsString());
            vaccination_bean.setV_people_passport(account.get("v_people_passport").getAsString());
            vaccination_bean.setV_people_nationalid(account.get("v_people_nationalid").getAsString());
            vaccination_bean.setV_country_name(account.get("v_country_name").getAsString());


            if (account.has("d_update_time") && !account.get("d_update_time").isJsonNull()){
                vaccination_bean.setD_update_time(account.get("d_update_time").getAsString());
            }

            if (account.has("i_covid_vaccine") && !account.get("i_covid_vaccine").isJsonNull()){
                vaccination_bean.setI_covid_vaccine(account.get("i_covid_vaccine").getAsInt());
            }
            if (account.has("i_covid_vaccine_dose_number") && !account.get("i_covid_vaccine_dose_number").isJsonNull()){
                vaccination_bean.setI_covid_vaccine_dose_number(account.get("i_covid_vaccine_dose_number").getAsInt());
            }
            if (account.has("d_covid_vaccination") && !account.get("d_covid_vaccination").isJsonNull()){
                vaccination_bean.setD_covid_vaccination(account.get("d_covid_vaccination").getAsString());
            }

            if (account.has("vaccines") && !account.get("vaccines").isJsonNull()){
                JsonArray jsonarray = account.get("vaccines").getAsJsonArray();
                List<Vaccination_Item_Bean> list = new ArrayList<>();
                for (int i=0;i<jsonarray.size();i++){
                    JsonElement jsonElement = jsonarray.get(i);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    Vaccination_Item_Bean vaccination_item_bean = new Vaccination_Item_Bean();
                    vaccination_item_bean.setD_covid_vaccination(jsonObject.get("d_covid_vaccination").getAsString());
                    vaccination_item_bean.setI_covid_vaccine_dose_number(jsonObject.get("i_covid_vaccine_dose_number").getAsInt());
                    vaccination_item_bean.setV_covid_vaccine_batch_no(jsonObject.get("v_covid_vaccine_batch_no").getAsString());
                    vaccination_item_bean.setV_covid_vaccine_facility(jsonObject.get("v_covid_vaccine_facility").getAsString());
                    vaccination_item_bean.setV_covid_vaccine_type(jsonObject.get("v_covid_vaccine_type").getAsString());
                    list.add(vaccination_item_bean);
                }
            }

            return vaccination_bean;
        }else {
            return null;
        }

    }
}
