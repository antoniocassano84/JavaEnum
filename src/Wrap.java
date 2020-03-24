class Wrap {
  public static void main(String[] args) {

    Integer iOb = Integer.valueOf(100);  // manual boxing
    int i = iOb.intValue();  // manual unboxing

    System.out.println(i + " " + iOb);

    iOb = 100;  // Autobox
    i = iOb;  // Auto-unbox

    System.out.println(i + " " + iOb);
  }
}
