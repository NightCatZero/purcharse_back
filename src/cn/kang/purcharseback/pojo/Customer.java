package cn.kang.purcharseback.pojo;

public class Customer {
    String custname;
    String custmail;
    String password;
    String custqq;

    public Customer() {
    }

    public Customer(String custname, String custmail, String password, String custqq) {
        this.custname = custname;
        this.custmail = custmail;
        this.password = password;
        this.custqq = custqq;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustmail() {
        return custmail;
    }

    public void setCustmail(String custmail) {
        this.custmail = custmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustqq() {
        return custqq;
    }

    public void setCustqq(String custqq) {
        this.custqq = custqq;
    }
}
