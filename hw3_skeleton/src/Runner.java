import javax.swing.SwingUtilities;
import speccheck.SpecCheck;

public class Runner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SpecCheck.testAndZip(hw3.SpecChecker.class, "SUBMIT_THIS_hw3", "hw3", new String[]{
                    "src/hw3/Pearls.java",
                    "src/hw3/PearlUtil.java"
                });
            }
        });
    }
}
