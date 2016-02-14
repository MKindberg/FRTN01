
public class PeriodicRunnable extends Base implements Runnable {
	private int period;

	public static void main(String[] args) {
		PeriodicRunnable[] ps = new PeriodicRunnable[args.length];
		Thread[] ts = new Thread[args.length];
		for (int i = 0; i < args.length; i++) {
			ps[i] = new PeriodicRunnable(Integer.parseInt(args[i]));
			ts[i] = new Thread(ps[i]);
			ts[i].start();
		}
		System.out.println("Active threads: " + Thread.activeCount());
	}

	public PeriodicRunnable(int period) {
		this.period = period;
	}

	public void run() {
		while (!Thread.interrupted()) {
			System.out.print(period + ", ");
			try {
				Thread.sleep(period);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Thread stopped.");
	}

}
