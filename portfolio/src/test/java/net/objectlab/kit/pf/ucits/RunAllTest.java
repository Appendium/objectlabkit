package net.objectlab.kit.pf.ucits;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber-html-report" }, strict = true, tags = "~@ignore")
public class RunAllTest {

}
