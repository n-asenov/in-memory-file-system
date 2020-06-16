package filesystem;

public abstract class File {
  private String name;

  protected File(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public abstract int getSize();
}
