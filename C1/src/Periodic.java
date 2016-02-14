public class Periodic extends Thread {
	private int period;

	public static void main(String[] args) {
		Periodic[] ps = new Periodic[args.length];
		for (int i = 0; i < args.length; i++) {
			ps[i] = new Periodic(Integer.parseInt(args[i]));
			ps[i].start();
		}
		System.out.println("Active threads: " + Thread.activeCount());
	}

	public Periodic(int period) {
		this.period = period;
	}

	public void run() {
		Thread.currentThread().setPriority(Thread.currentThread().getPriority() + 1);
		System.out.println("Thread priority: " + Thread.currentThread().getPriority());
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
