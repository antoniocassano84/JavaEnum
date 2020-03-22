// Try This 12-1

// A simulation of the traffic light that uses
// an enumeration to describe the light's color.

// An enumeration of the colors of a traffic light.
enum TrafficLightColor { RED, GREEN, YELLOW }

// A computerized traffic light.
class TrafficLightSimulator implements Runnable {
    private TrafficLightColor tlc;  // holds the traffic light color
    private boolean stop = false;  // set to true to stop the simulation
    private boolean changed = false;  // true when the light has changed

    TrafficLightSimulator(TrafficLightColor init) { tlc = init; }

    // Start up the light.
    public void run() {
      System.out.println(" (" + Thread.currentThread().getName() + ")  in run()");
        while(!stop) {
            try {
                switch (tlc) {
                    case GREEN:
                        Thread.sleep(10000);  // green for 10 s
                        break;
                    case YELLOW:
                        Thread.sleep(2000);  // yellow for 2 s
                        break;
                    case RED:
                        Thread.sleep(12000);  // red for 12 s
                        break;
                }
            } catch(InterruptedException exc) { System.out.println(exc); }
            changeColor();
        }
    }

    // Change color.
    synchronized void changeColor() {
      System.out.println(" (" + Thread.currentThread().getName() + ")  in changeColor()");
        switch (tlc) {
            case RED:
                tlc = TrafficLightColor.GREEN;
                break;
            case YELLOW:
                tlc = TrafficLightColor.RED;
                break;
            case GREEN:
                tlc = TrafficLightColor.YELLOW;
        }
        changed = true;
        notify();  // signal that the light has changed
        System.out.println(" (" + Thread.currentThread().getName() + ")  after notify() - changed is " + changed);
    }

    // Wait until a light change occurs.
    synchronized void waitForChange() {  // main thread here
      System.out.println(" (" + Thread.currentThread().getName() + ")  in waitForChange()");
        try {
            while(!changed) wait();  // wait for light to change
            changed = false;
            System.out.println(" (" + Thread.currentThread().getName() + ")  after wait() - changed is " + changed);
        } catch (InterruptedException exc) { System.out.println(exc); }
    }

    // Return current color.
    synchronized TrafficLightColor getColor() {
      System.out.println(" (" + Thread.currentThread().getName() + ")  in getColor()");
      return tlc; }

    // Stop the traffic light.
    synchronized void cancel() {
      System.out.println(" (" + Thread.currentThread().getName() + ")  in cancel()");
      stop = true;
    }
}

class TrafficLightDemo {
    public static void main(String[] args) {
	TrafficLightSimulator t1 = new TrafficLightSimulator(TrafficLightColor.GREEN);
	new Thread(t1).start();

	for(int i=0; i<9; i++) {
        System.out.println((i+1) + ") " + t1.getColor());
        t1.waitForChange();
	}
	t1.cancel();
    }
}
