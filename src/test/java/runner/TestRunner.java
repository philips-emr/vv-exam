package runner;

import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions"
)

public class TestRunner {

    public static void main(String[] args) throws IOException {
        Result result = JUnitCore.runClasses(TestRunner.class);
        saveTestResultsToFile(result);
    }

    private static void saveTestResultsToFile(Result result) throws IOException {
        String filePath = "/src/test/resources/log/test_results.txt";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Total tests performed: " + result.getRunCount() + "\n");
            writer.write("Total failed tests: " + result.getFailureCount() + "\n");

            if (result.wasSuccessful()) {
                writer.write("All tests passed successfully!");
            } else {
                writer.write("Some tests failed. See the details below:\n");
                for (org.junit.runner.notification.Failure failure : result.getFailures()) {
                    writer.write(failure.toString() + "\n");
                }
            }

            System.out.println("Test results have been saved to" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
