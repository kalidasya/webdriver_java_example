package modelbased;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import osmo.tester.OSMOConfiguration;
import osmo.tester.OSMOTester;
import osmo.tester.generator.algorithm.BalancingAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.reporting.coverage.HTMLCoverageReporter;
import pages2.Page2Model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kalidasya on 2015.05.08..
 */
public class Page2ModelTest {

  private WebDriver driver;
  private OSMOTester tester;

  @BeforeClass
  public void setup() throws IOException {
    driver = driver = new FirefoxDriver();

    tester = new OSMOTester();

    tester.addModelObject(new Page2Model(driver));

    OSMOConfiguration config = tester.getConfig();
    config.setAlgorithm(new BalancingAlgorithm());
    // config.addTestEndCondition(new Or(new TransitionCoverage(1.8), new Length(30)));
    config.setTestEndCondition(new Length(30));
    config.setSuiteEndCondition(new Length(2));
  }

  public void test() throws IOException {
    tester.generate(2131231);
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws IOException {
    driver.close();

    HTMLCoverageReporter rep = new HTMLCoverageReporter(tester.getSuite().getCoverage(), tester.getSuite().getAllTestCases(), tester.getFsm());
    Path path = Paths.get("portal-model.matrix.html");
    Files.deleteIfExists(path);
    path = Files.createFile(path);
    BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset());
    writer.append(rep.getTraceabilityMatrix());
    writer.flush();
  }
}
