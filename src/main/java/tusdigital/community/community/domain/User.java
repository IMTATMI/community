package tusdigital.community.community.domain;


public class User {

  private int id;
  private String name;
  private String password;
  private String email;
  private int logintype;
  private int usertype;
  private int credit;
  private String token;
  private long createtime;
  private long modifidetime;
  private String avatarUrl;

    public String getAvatar_url() {
        return avatarUrl;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatarUrl = avatar_url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getModifidetime() {
        return modifidetime;
    }

    public void setModifidetime(long modifidetime) {
        this.modifidetime = modifidetime;
    }

    public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public long getLogintype() {
    return logintype;
  }

  public void setLogintype(int logintype) {
    this.logintype = logintype;
  }


  public long getUsertype() {
    return usertype;
  }

  public void setUsertype(int usertypt) {
    this.usertype = usertypt;
  }


  public int getCredit() {
    return credit;
  }

  public void setCredit(int credit) {
    this.credit = credit;
  }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", logintype=" + logintype +
                ", usertype=" + usertype +
                ", credit=" + credit +
                ", token='" + token + '\'' +
                ", createtime=" + createtime +
                ", modifidetime=" + modifidetime +
                ", avatar_url='" + avatarUrl + '\'' +
                '}';
    }
}
