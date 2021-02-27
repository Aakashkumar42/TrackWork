package com.example.trackwork;

public class Model {

                private String jobid;
                private String cust_name;
                private String cust_mobile;
                private String cust_address;
                private String item;
                private String issue;
                private String service_charge;
                private String remarks;
                private String date;
                private String job_status;



    public Model(String jobid, String cust_name, String cust_mobile, String cust_address, String item, String issue, String service_charge, String remarks, String date,String job_status) {
        this.jobid = jobid;
        this.cust_name = cust_name;
        this.cust_mobile = cust_mobile;
        this.cust_address = cust_address;
        this.item = item;
        this.issue = issue;
        this.service_charge = service_charge;
        this.remarks = remarks;
        this.date = date;
        this.job_status=job_status;

    }



    public String getJobid() {
        return jobid;
    }

    public String getCust_name() {
        return cust_name;
    }

    public String getCust_mobile() {
        return cust_mobile;
    }

    public String getCust_address() {
        return cust_address;
    }

    public String getItem() {
        return item;
    }

    public String getIssue() {
        return issue;
    }

    public String getService_charge() {
        return service_charge;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDate() {
        return date;
    }

    public String getJob_status() {
        return job_status;
    }
}
