package net.objectlab.kit.pf.ucits;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "html:target/cucumber-html-report", "json:target/cucumber-json-report.json" }, strict = true, tags = "~@ignore")
public class RunAllTest {

}
