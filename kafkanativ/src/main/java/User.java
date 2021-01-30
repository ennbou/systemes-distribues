public class User {
  private String name;
  private int v;

  public User() {
  }

  public User(String name,int v) {
    this.name = name;
    this.v = v;
  }

  public int getV() {
    return v;
  }

  public void setV(int v) {
    this.v = v;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
