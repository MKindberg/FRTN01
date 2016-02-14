package beamRegulator;
import SimEnvironment.AnalogSink;
import SimEnvironment.AnalogSource;

// BeamRegul class to be written by you
public class BeamRegul extends Thread {
	// IO interface declarations
	private AnalogSource analogIn;
	private AnalogSink analogOut;
	private AnalogSink analogRef;

	private double uMin = -10.0;
	private double uMax = 10.0;

	private PI controller;

	private ReferenceGenerator refgen;

	public BeamRegul(ReferenceGenerator refgen, Beam beam, int priority) {
		// ...
		// Code to initialize the IO
		analogIn = beam.getSource(0);
		analogOut = beam.getSink(0);
		analogRef = beam.getSink(1);
		// ...

		controller = new PI("Regulator");

		this.refgen = refgen;
		setPriority(priority);
	}

	public void run() {
		long t = System.currentTimeMillis();
		while (!Thread.interrupted()) {
			// Code to perform IO
			double y = analogIn.get();
			double ref = refgen.getRef();

			synchronized (controller) {
				double u = controller.calculateOutput(y, ref);
				u = Math.min(u, uMax);
				u = Math.max(u, uMin);
				analogOut.set(u);
				controller.updateState(u);
			}

			analogRef.set(ref);

			t += controller.getHMillis();
			long duration = t - System.currentTimeMillis();
			try {
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("BeamRegulator stopped.");
	}

}